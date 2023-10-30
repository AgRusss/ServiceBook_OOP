package com.codingwithjks.animeapp

import android.R
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithjks.animeapp.Model.Car
import com.codingwithjks.animeapp.Model.ServiceBook
import com.codingwithjks.animeapp.ViewModel.CarRecyclerAdapter
import com.codingwithjks.animeapp.ViewModel.CarViewModel
import com.codingwithjks.animeapp.ViewModel.ServiceBookRecyclerAdapter
import com.codingwithjks.animeapp.ViewModel.ServiceBookViewModel
import com.codingwithjks.animeapp.databinding.ActivityCarBinding
import com.codingwithjks.animeapp.databinding.ActivityServicebookBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class ServiceBookActivity : AppCompatActivity() {
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    private lateinit var binding: ActivityServicebookBinding
    private val serviceBookViewModel: ServiceBookViewModel by viewModels()
    lateinit var adapter: ServiceBookRecyclerAdapter
    var carId: String = ""

    @FlowPreview
    @ExperimentalCoroutinesApi
    //когда приложение запускается
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityServicebookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val extras = intent.extras
        val value = extras!!.getString("id")
        if (value == null) { return; }
        carId = value

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this@ServiceBookActivity, AddServiceBookActivity::class.java)
            intent.putExtra("mode", "add");
            intent.putExtra("idCar", carId)
            resultLauncher.launch(intent);
        }
        //получение и отправка запроса
        serviceBookViewModel.getServiceBooks(carId)
    }

    //после старта обновляем список
    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            serviceBookViewModel.getServiceBooks(carId)
        }, 1000)
        updateRecycler()

        print("ON RESUME")
    }

    fun editServiceBook(serviceBook: ServiceBook) {
        val intent = Intent(this@ServiceBookActivity, AddServiceBookActivity::class.java)
        intent.putExtra("mode", "edit");
        intent.putExtra("id", serviceBook.id)
        intent.putExtra("date", serviceBook.date)
        intent.putExtra("description", serviceBook.description)
        intent.putExtra("mileage", serviceBook.mileage.toString())
        intent.putExtra("idCar", carId)

        resultLauncher.launch(intent);
    }

    fun removeServiceBook(serviceBook: ServiceBook) {
        AlertDialog.Builder(this)
            .setTitle("Удаление")
            .setMessage("Вы точно хотите удалить запись в сервисной книжке от " + serviceBook.date + "?")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(
                R.string.yes,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    serviceBookViewModel.deleteServiceBook(serviceBook.id)

                    val extras = intent.extras
                    val value = extras!!.getString("id")
                    if (value != null) {
                        Handler().postDelayed({
                            serviceBookViewModel.getServiceBooks(value)
                        }, 1000)

                        Toast.makeText(
                            this@ServiceBookActivity,
                            "Успешно удалено",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            .setNegativeButton(R.string.no, null).show()
    }

    private fun updateRecycler() {
        //когда сервер ответил
        serviceBookViewModel.serviceBookResponse.observe(this, Observer { response ->
            val serviceBooks = response
            //заполняем recycler view
            val layoutManager = LinearLayoutManager(this)
            binding.serviceBookList.layoutManager = layoutManager
            //создаем адаптер и заполняем данными
            adapter = ServiceBookRecyclerAdapter()
            adapter.serviceBookList = serviceBooks.toMutableList();

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            adapter.context = this
            //прикрепляем адаптер к списку
            binding.serviceBookList.adapter = adapter
        })
    }
}

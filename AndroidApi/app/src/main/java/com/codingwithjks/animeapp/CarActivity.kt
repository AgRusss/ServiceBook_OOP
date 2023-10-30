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
import com.codingwithjks.animeapp.Model.Driver
import com.codingwithjks.animeapp.ViewModel.CarRecyclerAdapter
import com.codingwithjks.animeapp.ViewModel.CarViewModel
import com.codingwithjks.animeapp.databinding.ActivityCarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class CarActivity : AppCompatActivity() {
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    private lateinit var binding: ActivityCarBinding
    private val carViewModel: CarViewModel by viewModels()
    lateinit var adapter: CarRecyclerAdapter
    var idDriver: String = ""
    @FlowPreview
    @ExperimentalCoroutinesApi
    //когда приложение запускается
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val extras = intent.extras
        val value = extras!!.getString("id")

        if (value == null) { return; }
        idDriver = value
        binding.btnCarAdd.setOnClickListener {
            val intent = Intent(this@CarActivity, AddCarActivity::class.java)
            intent.putExtra("mode", "add");
            intent.putExtra("idDriver", idDriver)
            resultLauncher.launch(intent);
        }
        //получение и отправка запроса
        carViewModel.getCars(value)
    }

    fun openServiceBook(id: String) {
        val intent = Intent(this@CarActivity, ServiceBookActivity::class.java)
        intent.putExtra("id", id);
        resultLauncher.launch(intent);
    }

    fun editCar(car: Car) {
        val intent = Intent(this@CarActivity, AddCarActivity::class.java)
        intent.putExtra("mode", "edit");
        intent.putExtra("id", car.id)
        intent.putExtra("brand", car.brand)
        intent.putExtra("model", car.model)
        intent.putExtra("number", car.numberOfCar)
        intent.putExtra("idDriver", idDriver)

        resultLauncher.launch(intent);
    }

    fun removeCar(car: Car) {
        AlertDialog.Builder(this)
            .setTitle("Удаление")
            .setMessage("Вы точно хотите удалить ТС " + car.brand + " " + car.model + "?")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(
                R.string.yes,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    carViewModel.deleteCar(car.id)

                    val extras = intent.extras
                    val value = extras!!.getString("id")
                    if (value != null) {
                        Handler().postDelayed({
                            carViewModel.getCars(value)
                        }, 1000)

                        Toast.makeText(
                            this@CarActivity,
                            "Успешно удалено",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            .setNegativeButton(R.string.no, null).show()
    }


    //после старта обновляем список
    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            carViewModel.getCars(idDriver)
        }, 1000)
        updateRecycler()

    }

    private fun updateRecycler() {
        //когда сервер ответил
        carViewModel.carResponse.observe(this, Observer { response ->
            val cars = response
            //заполняем recycler view
            val layoutManager = LinearLayoutManager(this)
            binding.carList.layoutManager = layoutManager
            //создаем адаптер и заполняем данными
            adapter = CarRecyclerAdapter()
            adapter.carList = cars.toMutableList();

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            adapter.context = this

            //прикрепляем адаптер к списку
            binding.carList.adapter = adapter
        })
    }
}

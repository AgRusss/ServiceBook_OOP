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
import com.codingwithjks.animeapp.Model.Driver
import com.codingwithjks.animeapp.ViewModel.DriverRecyclerAdapter
import com.codingwithjks.animeapp.ViewModel.DriverViewModel
import com.codingwithjks.animeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import okhttp3.internal.wait


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    private lateinit var binding: ActivityMainBinding
    private val driverViewModel: DriverViewModel by viewModels()
    lateinit var adapter: DriverRecyclerAdapter

    @FlowPreview
    @ExperimentalCoroutinesApi
    //когда приложение запускается
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAdddriver.setOnClickListener {
            val intent = Intent(this@MainActivity, AddDriverActivity::class.java)
            intent.putExtra("mode", "add");
            resultLauncher.launch(intent);
        }

        //получение и отправка запроса
        driverViewModel.getDrivers()
    }

    //после старта обновляем список
    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            driverViewModel.getDrivers()

        }, 1000)

        updateRecycler()
        print("ON RESUME")
    }

    fun openCar(id: String) {
        val intent = Intent(this@MainActivity, CarActivity::class.java)
        intent.putExtra("id", id);
        resultLauncher.launch(intent);
    }

    fun editDriver(driver: Driver) {
        val intent = Intent(this@MainActivity, AddDriverActivity::class.java)
        intent.putExtra("mode", "edit");
        intent.putExtra("id", driver.id)
        intent.putExtra("fullName", driver.fullName)
        resultLauncher.launch(intent);
    }

    fun removeDriver(driver: Driver) {
        AlertDialog.Builder(this)
            .setTitle("Удаление")
            .setMessage("Вы точно хотите удалить водителя с именем  - " + driver.fullName + "?")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(
                R.string.yes,
                DialogInterface.OnClickListener { dialog, whichButton ->

                    driverViewModel.deleteDriver(driver.id)
                    Handler().postDelayed({
                        driverViewModel.getDrivers()
                    }, 1000)

                    Toast.makeText(
                        this@MainActivity,
                        "Успешно удалено",
                        Toast.LENGTH_SHORT
                    ).show()

                })
            .setNegativeButton(R.string.no, null).show()
    }


    private fun updateRecycler() {
        //когда сервер ответил
        driverViewModel.driverResponse.observe(this, Observer { response ->
            val drivers = response
            //заполняем recycler view
            val layoutManager = LinearLayoutManager(this)
            binding.driverList.layoutManager = layoutManager
            //создаем адаптер и заполняем данными
            adapter = DriverRecyclerAdapter()
            adapter.driverList = drivers.toMutableList();

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            adapter.context = this

            //прикрепляем адаптер к списку
            binding.driverList.adapter = adapter
        })
    }
}

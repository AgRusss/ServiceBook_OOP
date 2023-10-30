package com.codingwithjks.animeapp

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithjks.animeapp.Model.DtoDriver
import com.codingwithjks.animeapp.ViewModel.CarRecyclerAdapter
import com.codingwithjks.animeapp.ViewModel.CarViewModel
import com.codingwithjks.animeapp.ViewModel.DriverViewModel
import com.codingwithjks.animeapp.databinding.ActivityAddDriverBinding
import com.codingwithjks.animeapp.databinding.ActivityCarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class AddDriverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDriverBinding
    private val driverViewModel: DriverViewModel by viewModels()

    @FlowPreview
    @ExperimentalCoroutinesApi
    //когда приложение запускается
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddDriverBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val extras = intent.extras
        val mode = extras!!.getString("mode")
        if (mode == "edit") {
            val fullName = extras!!.getString("fullName")
            binding.fullNameInput.setText(fullName)
            binding.sendButton.text = "Обновить"
        } else {
            binding.sendButton.text = "Создать"
        }

        binding.sendButton.setOnClickListener {
            val fio = binding.fullNameInput.text.toString()
            if (fio != "") {
                if (mode == "edit") {
                    val id = extras!!.getString("id")
                    val driverData = DtoDriver(id, fio)

                    driverViewModel.updateDriver(driverData)
                    binding.fullNameInput.text.clear()
                    Toast.makeText(
                        this@AddDriverActivity,
                        "Успешно обновлено",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    val driverData = DtoDriver(null, fio)

                    driverViewModel.createDriver(driverData)
                    binding.fullNameInput.text.clear()
                    Toast.makeText(
                        this@AddDriverActivity,
                        "Успешно создано",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                this.onBackPressed();
            }
        }
    }

    //после старта обновляем список
    override fun onResume() {
        super.onResume()
        print("ON RESUME")
    }
}

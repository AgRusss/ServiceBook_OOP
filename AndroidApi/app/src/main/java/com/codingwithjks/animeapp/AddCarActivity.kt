package com.codingwithjks.animeapp


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codingwithjks.animeapp.Model.DriverId
import com.codingwithjks.animeapp.Model.DtoCar
import com.codingwithjks.animeapp.Model.DtoDriver
import com.codingwithjks.animeapp.ViewModel.CarViewModel
import com.codingwithjks.animeapp.databinding.ActivityAddCarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class AddCarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCarBinding
    private val carViewModel: CarViewModel by viewModels()

    @FlowPreview
    @ExperimentalCoroutinesApi
    //когда приложение запускается
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val extras = intent.extras
        val mode = extras!!.getString("mode")
        if (mode == "edit") {
            val brand = extras!!.getString("brand")
            val model = extras!!.getString("model")
            val number = extras!!.getString("number")
            binding.brandInput.setText(brand)
            binding.modelInput.setText(model)
            binding.numberInput.setText(number)

            binding.sendButton.text = "Обновить"
        } else {
            binding.sendButton.text = "Создать"
        }
        val idDriver = extras!!.getString("idDriver")
        if (idDriver == null) {
            return;
        }
        binding.sendButton.setOnClickListener {
            val brand = binding.brandInput.text.toString()
            val model = binding.modelInput.text.toString()
            val number = binding.numberInput.text.toString()
            val extras = intent.extras


            val id = extras!!.getString("id")
            val driversId = ArrayList<DriverId>()
            driversId.add(DriverId(idDriver))

            if (brand != "" && model != "" && number != "") {
                if (mode == "edit") {
                    val carData = DtoCar(id, brand, model, number, null)
                    carViewModel.updateCar(carData)
                    Toast.makeText(
                        this@AddCarActivity,
                        "Успешно обновлено",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    val carData = DtoCar(null, brand, model, number, driversId)
                    print(carData)
                    carViewModel.createCar(carData)
                    Toast.makeText(
                        this@AddCarActivity,
                        "Успешно создано",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                binding.brandInput.text.clear()
                binding.modelInput.text.clear()
                binding.numberInput.text.clear()
                this.onBackPressed();
            }
        }
    }
}

package com.codingwithjks.animeapp


import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codingwithjks.animeapp.Model.CarId
import com.codingwithjks.animeapp.Model.DtoServiceBook
import com.codingwithjks.animeapp.ViewModel.ServiceBookViewModel
import com.codingwithjks.animeapp.databinding.ActivityAddServicebookBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddServiceBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddServicebookBinding
    private val serviceViewModel: ServiceBookViewModel by viewModels()

    fun updateDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this@AddServiceBookActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var month = (monthOfYear + 1).toString()
            month = if (month.toString().length < 2) ("0" + month) else month
            binding.dateInput.setText(year.toString() + "-" + month + "-" + dayOfMonth)
        }, year, month, day)
        dpd.show()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    //когда приложение запускается
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddServicebookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val extras = intent.extras
        val mode = extras!!.getString("mode")
        if (mode == "edit") {
            val date = extras!!.getString("date")
            val description = extras!!.getString("description")
            val miliage = extras!!.getString("mileage")
            binding.dateInput.setText(date)
            binding.descInput.setText(description)
            binding.miliageInput.setText(miliage)

            binding.sendButton.text = "Обновить"
        } else {
            binding.sendButton.text = "Создать"
        }
        val idCar = extras!!.getString("idCar")
        if (idCar == null) {
            return;
        }

        val c: Date = Calendar.getInstance().time

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate: String = df.format(c)
        binding.dateInput.setText(formattedDate)

        binding.dateInput.setOnClickListener {
            updateDate()
        }

        binding.sendButton.setOnClickListener {
            val date = binding.dateInput.text.toString()
            val description = binding.descInput.text.toString()
            val miliage = binding.miliageInput.text.toString()

            val extras = intent.extras
            val id = extras!!.getString("id")

            if (date != "" && description != "" && miliage != "") {
                if (mode == "edit") {
                    val serviceBook = DtoServiceBook(id, date, miliage.toInt(), description, null)
                    serviceViewModel.updateServiceBook(serviceBook)
                    Toast.makeText(
                        this@AddServiceBookActivity,
                        "Успешно обновлено",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    val serviceBook = DtoServiceBook(null, date, miliage.toInt(), description, CarId(idCar))
                    serviceViewModel.createServiceBook(serviceBook)
                    Toast.makeText(
                        this@AddServiceBookActivity,
                        "Успешно создано",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                binding.descInput.text.clear()
                binding.miliageInput.text.clear()
                this.onBackPressed();
            }
        }
    }
}

package com.codingwithjks.animeapp.ViewModel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codingwithjks.animeapp.CarActivity
import com.codingwithjks.animeapp.MainActivity
import com.codingwithjks.animeapp.Model.Driver
import com.codingwithjks.animeapp.R


//связка отрисовки списка (карточки)
//с данными
class DriverRecyclerAdapter : RecyclerView.Adapter<DriverRecyclerAdapter.ViewHolder>() {
    //с какой активности вызван
    lateinit var context: MainActivity
    var driverList: MutableList<Driver> = emptyList<Driver>().toMutableList()

    //получение количичества элементов в списке
    override fun getItemCount(): Int {
        return driverList.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        //берем шаблон карточки
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.driver_card, viewGroup, false)
        return ViewHolder(v)
    }


    //когда ставим данные
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        //данные карточки
        viewHolder.fullName.text = driverList[i].fullName

        viewHolder.buttonCar.setOnClickListener {
            context.openCar(driverList[i].id)
        }

        viewHolder.buttonEdit.setOnClickListener {
            context.editDriver(driverList[i])
        }

        viewHolder.buttonRemove.setOnClickListener {
            context.removeDriver(driverList[i])
        }
    }

    //описание полей карточки
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fullName: TextView
        var buttonCar: Button
        var buttonEdit: Button
        var buttonRemove: Button

        init {
            fullName = itemView.findViewById(R.id.brand)
            buttonCar = itemView.findViewById(R.id.btnOpen)
            buttonEdit = itemView.findViewById(R.id.btnEdit)
            buttonRemove = itemView.findViewById(R.id.btnRemove)
        }
    }
}
package com.codingwithjks.animeapp.ViewModel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codingwithjks.animeapp.CarActivity
import com.codingwithjks.animeapp.Model.Car
import com.codingwithjks.animeapp.R


//связка отрисовки списка (карточки)
//с данными
class CarRecyclerAdapter : RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder>() {
    //с какой активности вызван
    lateinit var context: CarActivity
    var carList: MutableList<Car> = emptyList<Car>().toMutableList()

    //получение количичества элементов в списке
    override fun getItemCount(): Int {
        return carList.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        //берем шаблон карточки
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.car_card, viewGroup, false)
        return ViewHolder(v)
    }


    //когда ставим данные
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        //данные карточки
        viewHolder.number.text = carList[i].numberOfCar
        viewHolder.brand.text = carList[i].brand
        viewHolder.model.text = carList[i].model

        viewHolder.buttonOpen.setOnClickListener {
            context.openServiceBook(carList[i].id)
        }

        viewHolder.buttonEdit.setOnClickListener {
            context.editCar(carList[i])
        }

        viewHolder.buttonRemove.setOnClickListener {
            context.removeCar(carList[i])
        }
    }

    //описание полей карточки
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var number: TextView
        var brand: TextView
        var model: TextView
        var buttonOpen: Button
        var buttonEdit: Button
        var buttonRemove: Button

        init {
            number = itemView.findViewById(R.id.number)
            brand = itemView.findViewById(R.id.brand)
            model = itemView.findViewById(R.id.model)
            buttonOpen = itemView.findViewById(R.id.btnOpen)
            buttonEdit = itemView.findViewById(R.id.btn_change)
            buttonRemove = itemView.findViewById(R.id.btn_delete)
        }
    }
}
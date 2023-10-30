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
import com.codingwithjks.animeapp.Model.ServiceBook
import com.codingwithjks.animeapp.R
import com.codingwithjks.animeapp.ServiceBookActivity
import org.w3c.dom.Text


//связка отрисовки списка (карточки)
//с данными
class ServiceBookRecyclerAdapter : RecyclerView.Adapter<ServiceBookRecyclerAdapter.ViewHolder>() {
    lateinit var context: ServiceBookActivity
    //с какой активности вызван
    var serviceBookList: MutableList<ServiceBook> = emptyList<ServiceBook>().toMutableList()

    //получение количичества элементов в списке
    override fun getItemCount(): Int {
        return serviceBookList.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        //берем шаблон карточки
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.service_book_card, viewGroup, false)
        return ViewHolder(v)
    }


    //когда ставим данные
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        //данные карточки
        viewHolder.date.text = serviceBookList[i].date
        viewHolder.mileage.text = serviceBookList[i].mileage.toString()
        viewHolder.description.text = serviceBookList[i].description

        viewHolder.buttonEdit.setOnClickListener {
            context.editServiceBook(serviceBookList[i])
        }

        viewHolder.buttonDelete.setOnClickListener {
            context.removeServiceBook(serviceBookList[i])
        }
    }

    //описание полей карточки
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView
        var mileage: TextView
        var description: TextView
        var buttonEdit: Button
        var buttonDelete: Button

        init {
            date = itemView.findViewById(R.id.date)
            mileage = itemView.findViewById(R.id.mileage)
            description = itemView.findViewById(R.id.description)
            buttonEdit = itemView.findViewById(R.id.btn_edit)
            buttonDelete = itemView.findViewById(R.id.btn_remove)
        }
    }
}
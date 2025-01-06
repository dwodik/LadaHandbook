package com.example.ladahandbook

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(list: ArrayList<Item>, context: Context, rcView: RecyclerView): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    // принимаем на вход лист элементов и контекст, наследуем от RecyclerView.Adapter и передаем ему же адаптер
    // сначала добавляем и реализуем onCreateViewHolder и getItemCount, затем bind в ViewHolder, потом onBindViewHolder

    var listRecycler = list // получаем копию списка, контекста, ресайклерВью, иначе к ним нет доступа
    var contextRecycler = context
    var recycler = rcView

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) { // находим в item_layout нужные компоненты
        val imageItem: ImageView = view.findViewById(R.id.image_item)
        val titleItem: TextView = view.findViewById(R.id.item_title)
        val descriptionItem: TextView = view.findViewById(R.id.item_description)

        fun bind(item: Item, context: Context, position: Int) { // метод образования связи/ bind - "связывать"
            imageItem.setImageResource(item.image) // записываем изображение
            titleItem.text = item.title // записываем заголовок
            var text = item.text // получаем текст-описание элемента
            if (text.length > 100) text = text.substring(0, 100) + "..." // обрезаем часть текста если он длинный
            descriptionItem.text = text // записываем описание либо полностью либо обрезанное
            itemView.setOnClickListener { // itemView это и есть наш items_layout
                val intent = Intent(context, ContentActivity::class.java).apply { // создаём intent("намерение") - переход на другой активити, создав "клички" передаваемых параметров, чтобы их по этим кличкам найти в ContentActivity
                    putExtra("index", position)
                    putExtra("category", item.category)
                }
                context.startActivity(intent) // запускаем активити
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // создаём "держатель вида"
        val inflater = LayoutInflater.from(contextRecycler) // "надуваем", рисуем элемент из items_layout
        return ViewHolder(inflater.inflate(R.layout.items_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // связыватель "держателя вида"
        val item = listRecycler.get(position) // достаём нужный элемент
        return holder.bind(item, contextRecycler, position) // передаем ViewHolder.bind тот что выше нужные данные - нужный итем и контекст
    }

    override fun getItemCount(): Int { // получение количества элементов в списке
        return listRecycler.size
    }

    fun updateAdapter(list: List<Item>) { // обновление адаптера - например открыты нивы, чтобы обновил список и показал например самары
        listRecycler.clear() // очищаем список
        recycler.smoothScrollToPosition(0) // делаем так, чтобы при открытии нового раздела курсор возвращался наверх
        listRecycler.addAll(list) // добавляем заново
        notifyDataSetChanged() // уведомление об изменении данных
    }
}
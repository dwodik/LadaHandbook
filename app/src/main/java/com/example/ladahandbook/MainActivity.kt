package com.example.ladahandbook

import android.content.res.TypedArray
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var selector: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // добавляем в ActionBar кнопку меню/назад
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //включаем кнопку
        drawerLayout = findViewById(R.id.drawer_layout_activity_main) // связываем drawer_layout из activity_main с кодом
        selector = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) // делаем переключатель меню/назад
        drawerLayout.addDrawerListener(selector) // добавляем слушатель нажатий
        selector.syncState() // этот метод как раз делаем переключение кнопки

        val navView: NavigationView = findViewById(R.id.nav_view) // находим nav_view из activity_main
        navView.setNavigationItemSelectedListener(this) // добавляем слушатель нажатий - им будет весь этот класс, а в нем соответственно есть onNavigationItemSelected который ниже
        val list = ArrayList<Item>() // создаем список элементов, у каждого есть заголовок, описание, картинка
        list.addAll(fillArrays(getImageID(R.array.history_image), resources.getStringArray(R.array.history_title), resources.getStringArray(R.array.history_description), 0)) // заполняем список для отображения на стартовой странице

        val rcView: RecyclerView = findViewById(R.id.rc_view_drawer_content)
        rcView.hasFixedSize() // для красоты
        rcView.layoutManager = LinearLayoutManager(this) // для красоты
        adapter = MyAdapter(list, this, rcView) // передаем адаптеру список в этот активити
        rcView.adapter = adapter // и устанавливаем адаптер для RecyclerView
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // ну тут понятно - обработка нажатий на пункт меню
        when(item.itemId) {
            R.id.id_history -> {
                adapter.updateAdapter(fillArrays(getImageID(R.array.history_image), resources.getStringArray(R.array.history_title), resources.getStringArray(R.array.history_description), 0))
            }
            R.id.id_classic -> {
                adapter.updateAdapter(fillArrays(getImageID(R.array.classic_image), resources.getStringArray(R.array.classic_title), resources.getStringArray(R.array.classic_description), 1))
            }
            R.id.id_niva -> {
                adapter.updateAdapter(fillArrays(getImageID(R.array.niva_image), resources.getStringArray(R.array.niva_title), resources.getStringArray(R.array.niva_description), 2))
            }
            R.id.id_samara -> {
                adapter.updateAdapter(fillArrays(getImageID(R.array.samara_image), resources.getStringArray(R.array.samara_title), resources.getStringArray(R.array.samara_description), 3))
            }
            R.id.id_oka -> {
                adapter.updateAdapter(fillArrays(getImageID(R.array.oka_image), resources.getStringArray(R.array.oka_title), resources.getStringArray(R.array.oka_description), 4))
            }
            R.id.id_desyatka-> {
                adapter.updateAdapter(fillArrays(getImageID(R.array.priora_image), resources.getStringArray(R.array.priora_title), resources.getStringArray(R.array.priora_description), 5))
            }
            R.id.id_kalina -> {
                adapter.updateAdapter(fillArrays(getImageID(R.array.kalina_image), resources.getStringArray(R.array.kalina_title), resources.getStringArray(R.array.kalina_description), 6))
            }
            R.id.id_vesta -> {
                adapter.updateAdapter(fillArrays(getImageID(R.array.renault_image), resources.getStringArray(R.array.renault_title), resources.getStringArray(R.array.renault_description), 7))
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout_activity_main)
        drawerLayout.closeDrawer(GravityCompat.START) // команда закрытия меню после выбранного пункта меню
        return true
    }
    fun fillArrays(imageArray: IntArray, titleArray: Array<String>, descArray: Array<String>, category: Int): List<Item> {
        // метод записи всех элементов в один список
        val listItem = ArrayList<Item>() // список элементов, в свою очередь состоящий из списков
        for (n in 0..imageArray.size - 1) { // проходим по любому из трех массивов на входе, т.к. все три по определению одинаковые
            val item = Item(imageArray[n], titleArray[n], descArray[n], category)
            listItem.add(item)
        }
        return listItem
    }
    fun getImageID(imageArrayId: Int): IntArray{ // расшифровка изображений, на вход поступает идентификатор массива с изображениями
        val typedArray: TypedArray = resources.obtainTypedArray(imageArrayId) // расшифровка
        val imagesId = IntArray(typedArray.length()) // новый массив, в который будем записывать изображения в формате чисел
        for (i in imagesId.indices) {
            imagesId[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle() // эта команда "переработать", чтобы потом можно было его переиспользовать
        return imagesId // на выходе возвращаем массив расшифрованных изображений, т.е. массив чисел
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // метод для обработки нажатий на кнопку меню
        if (selector.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }
}
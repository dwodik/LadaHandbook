package com.example.ladahandbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class ContentActivity: AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.content_html_layout) // подключаем нужную графику
        val webView: WebView = findViewById(R.id.web_view) // соединяем код и графическое представление
        val category: Int = intent.getIntExtra("category", 0) // получаем из адаптера значения нужной категории и индекса
        val index: Int = intent.getIntExtra("index", 0)

        // для того, чтобы нашу веб-страницу можно было масштабировать, увеличивая изображения с текстом
        webView.getSettings().setJavaScriptEnabled(true)
        webView.getSettings().setBuiltInZoomControls(true)
        webView.getSettings().setDisplayZoomControls(false)

        webView.loadUrl("file:///android_asset/category_${category}_index_$index.html") // загружаем нужную страницу по категории и индексу

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // добавление нажатия назад + снизу метод по обработке нажатия
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
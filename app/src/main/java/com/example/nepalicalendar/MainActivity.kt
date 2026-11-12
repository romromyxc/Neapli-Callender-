package com.example.nepalicalendar

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
        }

        val infoText = TextView(this).apply {
            text = "Nepali Calendar Wallpaper Widget\n\nTap below to place it directly on your home screen, or long-press your wallpaper -> Widgets -> Nepali Calendar."
            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 40)
        }

        val addBtn = Button(this).apply {
            text = "Add Widget to Wallpaper"
            textSize = 16f
            setOnClickListener { pinWidgetToHomeScreen() }
        }

        layout.addView(infoText)
        layout.addView(addBtn)
        setContentView(layout)
    }

    private fun pinWidgetToHomeScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appWidgetManager = getSystemService(AppWidgetManager::class.java)
            val myProvider = ComponentName(this, NepaliCalendarWidget::class.java)

            if (appWidgetManager.isRequestPinAppWidgetSupported) {
                appWidgetManager.requestPinAppWidget(myProvider, null, null)
            } else {
                Toast.makeText(this, "Long-press your wallpaper -> Widgets -> Nepali Calendar", Toast.LENGTH_LONG).show()
            }
        }
    }
}

package com.example.nepalicalendar

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View
    private val currentCalendar: Calendar = Calendar.getInstance()
    private val todayCalendar: Calendar = Calendar.getInstance()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        overlayView = LayoutInflater.from(this).inflate(R.layout.layout_transparent_dock, null)

        val layoutParamsType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutParamsType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            x = 0
            y = 0
        }

        windowManager.addView(overlayView, params)
        setupWeekdayHeader()
        renderCalendar()

        overlayView.findViewById<TextView>(R.id.btn_prev_month).setOnClickListener {
            currentCalendar.add(Calendar.MONTH, -1)
            renderCalendar()
        }

        overlayView.findViewById<TextView>(R.id.btn_next_month).setOnClickListener {
            currentCalendar.add(Calendar.MONTH, 1)
            renderCalendar()
        }
    }

    private fun setupWeekdayHeader() {
        val rowWeekdays = overlayView.findViewById<LinearLayout>(R.id.row_weekdays)
        rowWeekdays.removeAllViews()
        val days = listOf("S", "M", "T", "W", "T", "F", "S")

        for (day in days) {
            val tv = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                text = day
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
                textSize = 12f
                setTextColor(Color.parseColor("#B0FFFFFF"))
            }
            rowWeekdays.addView(tv)
        }
    }

    private fun renderCalendar() {
        val tvMonthYear = overlayView.findViewById<TextView>(R.id.tv_month_year)
        val gridDates = overlayView.findViewById<GridLayout>(R.id.grid_dates)
        val tvActiveFestival = overlayView.findViewById<TextView>(R.id.tv_active_festival)
        val tvActiveDesc = overlayView.findViewById<TextView>(R.id.tv_active_desc)

        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.US)
        tvMonthYear.text = monthFormat.format(currentCalendar.time).uppercase()

        gridDates.removeAllViews()

        val tempCal = currentCalendar.clone() as Calendar
        tempCal.set(Calendar.DAY_OF_MONTH, 1)

        val firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1
        val maxDaysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 0 until firstDayOfWeek) {
            val emptyCell = TextView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = dpToPx(34)
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
            }
            gridDates.addView(emptyCell)
        }

        for (day in 1..maxDaysInMonth) {
            tempCal.set(Calendar.DAY_OF_MONTH, day)
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(tempCal.time)
            val festival = FestivalData.festivals.find { it.date == dateStr }

            val isToday = (tempCal.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                    tempCal.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
                    tempCal.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH))

            val dateCell = TextView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = dpToPx(34)
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                text = day.toString()
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
                textSize = 13f

                if (festival != null) {
                    setTextColor(Color.parseColor("#FFD700"))
                } else {
                    setTextColor(Color.WHITE)
                }

                if (isToday) {
                    background = ContextCompat.getDrawable(context, R.drawable.circle_today)
                    if (festival != null) {
                        tvActiveFestival.text = "TODAY ($day): ${festival.name}"
                        tvActiveDesc.text = festival.details
                    } else {
                        tvActiveFestival.text = "TODAY: $day ${SimpleDateFormat("MMMM", Locale.US).format(todayCalendar.time)}"
                        tvActiveDesc.text = "No major Nepali festival on this date"
                    }
                }

                setOnClickListener {
                    if (festival != null) {
                        tvActiveFestival.text = "Date $day: ${festival.name}"
                        tvActiveDesc.text = festival.details
                    } else {
                        tvActiveFestival.text = "Date $day"
                        tvActiveDesc.text = "No major Nepali festival on this date"
                    }
                }
            }

            gridDates.addView(dateCell)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::overlayView.isInitialized) {
            windowManager.removeView(overlayView)
        }
    }
}

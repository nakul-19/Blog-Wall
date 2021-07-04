package com.nakul.blogWall.utils

import android.os.Handler
import android.widget.Button
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

object UtilFunctions {

    fun getGreetings(): String {
        return when {
            LocalTime.now().hour > 15 -> "Good Evening!"
            LocalTime.now().hour > 12 -> "Good Afternoon!"
            else -> "Good Morning!"
        }
    }

    fun Button.load(load: Boolean = true) {
        if (!this.text.toString().contains("LOADING")) {
            if (load) {
                this.text = "LOADING   "
                Handler().postDelayed({
                    this.load(false)
                }, 400)
            }
            return
        }

        if (this.text.toString() == "LOADING   ") {
            this.text = "LOADING.  "
        } else if (this.text.toString() == "LOADING.  ") {
            this.text = "LOADING.. "
        } else if (this.text.toString() == "LOADING.. ") {
            this.text = "LOADING..."
        } else {
            this.text = "LOADING   "
        }
        Handler().postDelayed({
            this.load(false)
        }, 400)
    }

    fun String.toDate(
        dateFormat: String = "yyyy-MM-dd",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)!!
    }

    fun Date.format(dateFormat: String = "dd MMM',' yyyy", timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }
}
package com.nakul.blogWall.utils

import android.os.Handler
import android.widget.Button
import java.time.LocalTime

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
}
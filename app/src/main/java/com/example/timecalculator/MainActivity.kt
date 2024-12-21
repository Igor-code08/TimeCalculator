package com.example.timecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var timeInput1: EditText
    private lateinit var timeInput2: EditText
    private lateinit var resultText: TextView
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Time Calculator"
        supportActionBar?.subtitle = "Add and Subtract Time"

        timeInput1 = findViewById(R.id.timeInput1)
        timeInput2 = findViewById(R.id.timeInput2)
        resultText = findViewById(R.id.resultText)
        clearButton = findViewById(R.id.clearButton)

        val addButton: Button = findViewById(R.id.addButton)
        val subtractButton: Button = findViewById(R.id.subtractButton)
        addButton.setOnClickListener {
            calculateTime(true)
        }

        subtractButton.setOnClickListener {
            calculateTime(false)
        }

        clearButton.setOnClickListener {
            timeInput1.text.clear()
            timeInput2.text.clear()
            resultText.text = ""
            resultText.setTextColor(getColor(android.R.color.black))
            Toast.makeText(this, "Данные очищены", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateTime(isAddition: Boolean) {
        val time1 = convertToSeconds(timeInput1.text.toString())
        val time2 = convertToSeconds(timeInput2.text.toString())

        if (time1 == null || time2 == null) {
            Toast.makeText(this, "Неправильный формат времени", Toast.LENGTH_SHORT).show()
            return
        }

        val resultInSeconds = if (isAddition) {
            time1 + time2
        } else {
            time1 - time2
        }

        val result = convertSecondsToTimeFormat(resultInSeconds)
        resultText.text = result
        resultText.setTextColor(getColor(R.color.dark_red))

        val toastMessage = if (isAddition) "Результат сложения: $result" else "Результат вычитания: $result"
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private fun convertToSeconds(time: String): Int? {
        var totalSeconds = 0
        val pattern = "^((\n+?)h)?((\n+?)m)?((\n+?)s)?$".toRegex()

        val matchResult = pattern.find(time) ?: return null

        val (h, m, s) = matchResult.destructured.toList().map { it.toIntOrNull() ?: 0 }

        totalSeconds += h * 3600 + m * 60 + s

        return totalSeconds
    }

    private fun convertSecondsToTimeFormat(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60

        return buildString {
            if (hours > 0) append("${hours}h")
            if (minutes > 0 || hours > 0) append("${minutes}m")
            append("${secs}s")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show()
    }
}
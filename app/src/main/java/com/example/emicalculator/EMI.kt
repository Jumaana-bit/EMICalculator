package com.example.emicalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class EMI : AppCompatActivity(), View.OnClickListener {

    //properties that are accessible onClick
    private lateinit var P: EditText
    private lateinit var I: EditText
    private lateinit var Y: EditText
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emi)

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        P = findViewById(R.id.principal)
        I = findViewById(R.id.rate)
        Y = findViewById(R.id.editYears)
        result = findViewById(R.id.result)

        val btnCalculate = findViewById<Button>(R.id.button)

        // Set the click listener to the button
        btnCalculate.setOnClickListener(this)

        val refreshButton: Button = findViewById(R.id.refresh)
        refreshButton.setOnClickListener {
            val intent = intent // Get the current intent
            finish() // Finish the current activity
            startActivity(intent) // Start the activity again
        }
    }

    override fun onClick(v: View) {
        // Get values from EditTexts and perform EMI calculation
        val principal = P.text.toString().toFloatOrNull() ?: 0f
        val rate = I.text.toString().toFloatOrNull() ?: 0f
        val years = Y.text.toString().toFloatOrNull() ?: 0f

        val output = emiCal(principal, rate, years)
        result.text = String.format("EMI = %.2f", output)
    }


    private fun emiCal(P: Float, I: Float, Y: Float): Float {
        val N = Y * 12  // Convert years to months
        return if (I == 0f) {
            // If interest rate is 0, EMI is simply principal divided by months
            P / N
        } else {
            // Proper EMI calculation
            val emiResult = (P * I * (1 + I).toDouble().pow(N.toDouble())).toFloat() /
                    ((1 + I).toDouble().pow(N.toDouble()) - 1).toFloat()
            emiResult
        }
    }
}
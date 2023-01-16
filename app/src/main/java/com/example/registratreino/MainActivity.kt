package com.example.registratreino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLn1: LinearLayout = findViewById(R.id.ln1)
        val btnLn2: LinearLayout = findViewById(R.id.ln2)

        btnLn1.setOnClickListener {
            goToRegisterExercise()
        }
        btnLn2.setOnClickListener {
            goToRegisterTraining()
        }

    }
    private fun goToRegisterExercise(){
        val intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
    }
    private fun goToRegisterTraining(){
        val intent = Intent(this, TrainingActivity::class.java)
        startActivity(intent)
    }
}
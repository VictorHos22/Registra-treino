package com.example.registratreino

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.registratreino.model.App
import com.example.registratreino.model.Result

class ExerciseActivity : AppCompatActivity() {

    private lateinit var edtNameExercise: EditText
    private lateinit var edtWeight: EditText
    private lateinit var edtRepetitions: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        edtNameExercise = findViewById(R.id.which_exercise)
        edtWeight = findViewById(R.id.which_weight)
        edtRepetitions = findViewById(R.id.how_many_repetitions)

        val btnSend: Button = findViewById(R.id.btn_save_exercise)
        btnSend.setOnClickListener {
            if(!validate()){
                Toast.makeText(this, R.string.invalid_text, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val exerciseName = edtNameExercise.text.toString()
            val weight = edtWeight.text.toString().toDouble()
            val repetitions = edtRepetitions.text.toString().toInt()

            val result = getString(R.string.simple_result, exerciseName, weight, repetitions)


            AlertDialog.Builder(this)
                .setTitle(R.string.want_to_save)
                .setMessage(getString(R.string.simple_result, exerciseName, weight, repetitions))
                .setPositiveButton(R.string.no) {dialog, which ->

                }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.resultDao()
                        dao.insert(Result(type= "exercise", res = result))
                    runOnUiThread{
                        openListActivity()
                    }
                        edtNameExercise.setText("")
                        edtWeight.setText("")
                        edtRepetitions.setText("")
                    }.start()
                }
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_search){
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity(){
        runOnUiThread{
            val intent = Intent(this, ListResultActivity::class.java)
            intent.putExtra("type", "exercise")
            startActivity(intent)
        }
    }

    private fun validate(): Boolean{
        return(edtNameExercise.text.toString().isNotEmpty()
                && edtWeight.text.toString().isNotEmpty()
                && edtRepetitions.text.toString().isNotEmpty()
                && !edtWeight.text.toString().startsWith("0")
                && !edtRepetitions.text.toString().startsWith("0"))
    }
}
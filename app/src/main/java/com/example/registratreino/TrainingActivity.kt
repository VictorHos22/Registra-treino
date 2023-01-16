package com.example.registratreino

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.registratreino.model.App
import com.example.registratreino.model.Result

class TrainingActivity : AppCompatActivity() {

    private lateinit var edtTraining: AutoCompleteTextView
    private lateinit var edtIntensity: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        edtTraining = findViewById(R.id.auto_training)
        val itemsTraining = resources.getStringArray(R.array.trainings)
        edtTraining.setText(itemsTraining.first())
        val adapterTraining = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsTraining)
        edtTraining.setAdapter(adapterTraining)

        edtIntensity = findViewById(R.id.auto_intensity)
        val itemsIntensity = resources.getStringArray(R.array.intensity)
        edtIntensity.setText(itemsIntensity.first())
        val adapterIntensity = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsIntensity)
        edtIntensity.setAdapter(adapterIntensity)

        val btnSave: Button = findViewById(R.id.btn_save_training)
        btnSave.setOnClickListener {
            val training = edtTraining.text.toString()
            val intensity = edtIntensity.text.toString()

            val result = getString(R.string.simple_training_result, training, intensity)

            AlertDialog.Builder(this)
                .setTitle(R.string.want_to_save)
                .setMessage(result)
                .setPositiveButton(R.string.no){ dialog, which ->

                }
                .setNegativeButton(R.string.save){ dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.resultDao()
                        dao.insert(
                            Result(type= "training", res= result)
                        )
                        runOnUiThread{
                            openListActivity()
                        }

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
            intent.putExtra("type", "training")
            startActivity(intent)
        }
    }
}
package com.example.registratreino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registratreino.model.App
import com.example.registratreino.model.Result
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

class ListResultActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_result)

        val result = mutableListOf<Result>()
        val adapter = ListResultAdapter(result)


        rv = findViewById(R.id.rv_list)
        rv.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
            reverseLayout = true
        }
        rv.adapter = adapter



        val type = intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        Thread{
            val app = application as App
            val dao = app.db.resultDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread{
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()
    }

    private inner class ListResultAdapter(private val listResult: List<Result>):
            RecyclerView.Adapter<ListResultAdapter.ListResultViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
        ): ListResultAdapter.ListResultViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_list, parent, false)
            return ListResultViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListResultAdapter.ListResultViewHolder,
            position: Int
        ) {
            val itemCurrent = listResult[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {
            return listResult.size
        }

     private inner class ListResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
         fun bind(item: Result) {
             val tv = itemView as TextView
             val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
             val data = sdf.format(item.createdDate)
             val res = item.res

             tv.text = getString(R.string.result, res, data)

         }
     }
    }
}
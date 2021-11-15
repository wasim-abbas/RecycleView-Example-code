package com.example.recyclviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.custom_diloge_box.view.*
import kotlinx.android.synthetic.main.custom_diloge_box.view.myitemD
import kotlinx.android.synthetic.main.custom_diloge_box.view.myitemName
import java.lang.reflect.Type
import java.util.ArrayList


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var adapter: ShoesListAdapter? = null

    val data = arrayListOf(
        Shoes(R.drawable.ic_launcher1, "white shoes", "this shoes is bought by wasim"),
        Shoes(R.drawable.ic_launcher2, "Balck shoes", "This document has been pr"),
        Shoes(R.drawable.ic_launcher3, "brown shoes", "This is Brown shoes"),
        Shoes(R.drawable.ic_launcher4, "green shoes", "This is green shoes"),
        Shoes(R.drawable.ic_launcher5, "yello shoes", "This is yello shoes"),
        Shoes(R.drawable.ic_launcher6, "indigo shoes", "This is indigo shoes"),
        Shoes(R.drawable.ic_launcher7, "yan shoes", "This is yan shoes"),
        Shoes(R.drawable.ic_launcher8, "teall shoes", "This is teall shoes"),
        Shoes(R.drawable.ic_launcher9, "Grey shoes", "This is Grey shoes"),
        Shoes(R.drawable.ic_launcher10, "A shoes", "This is A shoes"),
        Shoes(R.drawable.ic_launcher11, "B shoes", "This is B shoes"),
        Shoes(R.drawable.ic_launcher12, "C shoes", "This is C shoes"),
        Shoes(R.drawable.ic_launcher13, "D shoes", "This is D shoes"),
        Shoes(R.drawable.ic_launcher14, "E shoes", "This is E shoes"),
        Shoes(R.drawable.ic_launcher15, "F shoes", "This is F shoes"),
        Shoes(R.drawable.ic_launcher16, "G shoes", "This is G shoes"),
        Shoes(R.drawable.ic_launcher017, "H shoes", "This is H shoes"),
        Shoes(R.drawable.ic_launcher18, "M shoes", "This is M shoes"),

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        init()
        btnfloatAdd.setOnClickListener(this)

        val mySP = getSharedPreferences("File", MODE_PRIVATE)
        val jsonString = mySP.getString("key", "")
        //val type : Type = object : TypeToken<ArrayList<Shoes>?>() {}.type
        val gson : Gson = Gson()
        val myshoesObj : Shoes = gson.fromJson(jsonString, Shoes::class.java)
        data.addAll(listOf(myshoesObj))
    }


    fun init() {
        adapter = ShoesListAdapter(data, this)
        myRecycleView.layoutManager = LinearLayoutManager(this)
        myRecycleView.adapter = adapter
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapter?.filter?.filter(query)
                return true
            }
        })
    }

    override fun onClick(v: View?) {
        val myDialogView = View.inflate(this, R.layout.custom_diloge_box, null) //infalte the layout
        val myAlert = android.app.AlertDialog.Builder(this).setView(myDialogView).show()

        myDialogView.btnDelete.setText("calcel")
        myDialogView.btnedit.setText("Add")

        myDialogView.btnedit.setOnClickListener {
            var edname = myDialogView.myitemName.text.toString()
            var edDes = myDialogView.myitemD.text.toString()

            val sharedPreferences = getSharedPreferences("File", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val myjson = gson.toJson(Shoes(R.drawable.ic_launcher1, edname, edDes))
            editor.putString("key", myjson)
            editor.apply()
            editor.commit()

//            val mySP = getSharedPreferences("File", MODE_PRIVATE)
//            val myJsonString= mySP.getString("key",null)
//            val model = gson.fromJson(myJsonString,Shoes.class)

            val mySP = getSharedPreferences("File", MODE_PRIVATE)
            val jsonString = mySP.getString("key", "")
            //val type : Type = object : TypeToken<ArrayList<Shoes>?>() {}.type

            val myshoesObj : Shoes = gson.fromJson(jsonString, Shoes::class.java)

            data.addAll(listOf(myshoesObj))
            adapter?.notifyItemInserted(data.size - 1)
            myRecycleView.smoothScrollToPosition(data.size - 1)
            myAlert.dismiss()

        }
        myDialogView.btnDelete.setOnClickListener {
            myAlert.dismiss()
        }
    }
}
//
//val sp = getSharedPreferences("file", AppCompatActivity.MODE_PRIVATE)
//val editor = sp.edit()
//editor.putInt("img", R.drawable.ic_launcher10)
//editor.putString("itemName", edname)
//editor.putString("itemDes", edDes)
//editor.commit()
//editor.apply()
//
//data.addAll(listOf(Shoes(R.drawable.ic_launcher1,edname,edDes)))
////    data.addAll(listOf(Shoes(img,itname.toString(),itemDe.toString())))
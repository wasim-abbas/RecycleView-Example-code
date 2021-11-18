package com.example.recyclviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.custom_diloge_box.view.*
import kotlinx.android.synthetic.main.custom_diloge_box.view.myitemD
import kotlinx.android.synthetic.main.custom_diloge_box.view.myitemName
import java.util.ArrayList


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var adapter: ShoesListAdapter? = null
    var dataList = arrayListOf(
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

    }


    fun init() {

        //dataLoadFRomSharedPRefrence()
        singletonMEthod()
    }

    fun singletonMEthod() {

        var mySingleTonList = arrayListOf<Shoes>()

        mySingleTonList = MySingleton.myDataList      // 1st method of accessing singlton class
       // mySingleTonList = MSingleton.myDataList    //2nd method of accessing singlton class

        mySingleTonList.removeAt(0)/// removing value from SingleTonList
        mySingleTonList.addAll(listOf(Shoes(R.drawable.ic_launcher12,"Kotlin","Hello Kotlin"))) //Adding value fromList

        adapter = ShoesListAdapter(mySingleTonList, this)
        myRecycleView.layoutManager = LinearLayoutManager(this)
        myRecycleView.adapter = adapter
    }

    ///Load data from shsred PRefrences and save list to shared preprences
    fun dataLoadFRomSharedPRefrence() {
        val mySP = getSharedPreferences("File", MODE_PRIVATE)
        val jsonString = mySP.getString("key", "")
        val type = object : TypeToken<ArrayList<Shoes>?>() {}.type
        val gson = Gson()

        if (jsonString == "") {
            Toast.makeText(applicationContext, "datalist ", Toast.LENGTH_SHORT).show()
            adapter = ShoesListAdapter(dataList, this)
        } else {
            val myshoesObj: ArrayList<Shoes> = gson.fromJson(jsonString, type)
            Toast.makeText(applicationContext, "myobjString ", Toast.LENGTH_SHORT).show()

            //        data.addAll(listOf(myshoesObj))
            adapter = ShoesListAdapter(myshoesObj, this)
        }

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

            try {
                var edname = myDialogView.myitemName.text.toString()
                var edDes = myDialogView.myitemD.text.toString()

                dataList.addAll(listOf(Shoes(R.drawable.ic_launcher1, edname, edDes)))

                val sharedPreferences = getSharedPreferences("File", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                val gson = Gson()
                val myjson = gson.toJson(dataList)
                editor.putString("key", myjson)
                editor.apply()
                editor.commit()

                //dataList.addAll(listOf(Shoes(R.drawable.ic_launcher1, edname, edDes)))
                adapter?.notifyItemInserted(dataList.size - 1)
                adapter?.notifyDataSetChanged()
                myRecycleView.smoothScrollToPosition(dataList.size - 1)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            myAlert.dismiss()

        }
        myDialogView.btnDelete.setOnClickListener {
            myAlert.dismiss()
        }
    }


}


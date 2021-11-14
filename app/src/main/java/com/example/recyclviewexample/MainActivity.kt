package com.example.recyclviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import kotlinx.android.synthetic.main.layout_recycle_view.*


class MainActivity : AppCompatActivity() {

    var adapter: ShoesListAdapter? = null
    val data = listOf(
        Shoes(
            R.drawable.ic_launcher1,
            "white shoes",
            "this shoes is bought by wasim by amount of hundered rupee"
        ),
        Shoes(
            R.drawable.ic_launcher2,
            "Balck shoes",
            "This document has been produced with the financial assistance"
        ),
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

        adapter = ShoesListAdapter(data, this)
        myRecycleView.layoutManager = LinearLayoutManager(this)
        myRecycleView.findViewHolderForAdapterPosition(4)


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


//        myRecycleView.layoutManager = LinearLayoutManager(this)
//        myRecycleView.adapter = ShoesListAdapter(data,this)
    }


}
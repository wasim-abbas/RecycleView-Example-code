package com.example.recyclviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_detail_view.*

class DetailViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        img.setImageResource(intent.getIntExtra("imgId",R.drawable.ic_launcher1))
        tvNAme.setText(intent.getStringExtra("itemName"))
        tvDes.setText(intent.getStringExtra("itemDes"))

    }
}
package com.example.galleryapp

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.galleryapp.adapter.VideoViewAdapter
import com.example.galleryapp.databinding.ActivityVideoBinding
import com.example.galleryapp.model.DataModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VideoActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoBinding
    var videoList = ArrayList<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageData = intent.getStringExtra("uri")
        Log.d("imagedata", "${imageData}")
        val g = Gson()
        videoList = g.fromJson(imageData, object : TypeToken<ArrayList<DataModel>>() {}.type)

        Log.d("img", "$videoList")


        // initializing layout manager
        val recyclerView = binding.vidRecyclerView

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = VideoViewAdapter(videoList,this)
        recyclerView.adapter = adapter

    }

}
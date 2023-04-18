package com.example.galleryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.adapter.ImageGridAdapter
import com.example.galleryapp.databinding.SecondImageBinding
import com.example.galleryapp.model.DataModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ImageActivity : AppCompatActivity() {
    var imageList = ArrayList<DataModel>()

    private lateinit var binding: SecondImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageData = intent.getStringExtra("uri")
        Log.d("imagedata", "${imageData}")
        val g  = Gson()
        imageList = g.fromJson(imageData, object: TypeToken<ArrayList<DataModel>>(){}.type)

        Log.d("img", "$imageList")


        val recyclerView = binding.imgRecyclerView

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val adapter = ImageGridAdapter(imageList,this)
        recyclerView.adapter = adapter


//        Uri.parse(uriList[0].uri)
    }
}
package com.example.galleryapp
import android.app.Activity
import android.content.Intent
import android.media.MediaMetadataRetriever
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.galleryapp.model.DataModel
import com.google.gson.Gson


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val IMAGE_REQUEST_CODE = 100
    private val VIDEO_REQUEST_CODE = 200

    private lateinit var binding: ActivityMainBinding
    val uriList = ArrayList<DataModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onClick(view: View) {
        when (view) {
            binding.imgGalleryBtn -> {
                this.uriList.clear()
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    IMAGE_REQUEST_CODE
                )
            }

            binding.vidGalleryBtn -> {
                this.uriList.clear()
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "video/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                startActivityForResult(
                    Intent.createChooser(intent, "Select videos"),
                    VIDEO_REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val clipData = data.clipData
            if (clipData != null) {
                // Multiple images selected
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    // Do something with the URI, like display the image in an ImageView
                    this.uriList.add(DataModel(uri.toString(),null))
                }
//                Passing Image list data to Image activity
                val g = Gson()
                val imageListString = g.toJson(this.uriList)
                val intent1 = Intent(this, ImageActivity::class.java)
               Log.d("img10", "$imageListString" )
                intent1.putExtra("uri",imageListString)
                startActivity(intent1)

            } else {
                // Single image selected
                val uri = data.data
                if(uri!=null) {
                    // Do something with the URI, like display the image in an ImageView
                    this.uriList.add(DataModel(uri.toString(),null))

                    val g = Gson()
                    val imageListString = g.toJson(this.uriList)
                    val intent1 = Intent(this, ImageActivity::class.java)
                    intent1.putExtra("uri",imageListString)
                    Log.d("img1", "$imageListString" )
                    startActivity(intent1)
                }
            }

        } else if (requestCode == VIDEO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            //Multiple Video
            val clipData = data.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    val mMMR = MediaMetadataRetriever();
                    mMMR.setDataSource(this, uri);
                    val bitmap = mMMR.getFrameAtTime()
                    this.uriList.add(DataModel(uri.toString(),bitmap))
                }
                Log.i("video", "${this.uriList}")

//              Passing Video list data to another activity
                val g = Gson()
                val videoListString = g.toJson(this.uriList)
                val intent1 = Intent(this, VideoActivity::class.java)
                intent1.putExtra("uri",videoListString)
                Log.d("vid10", "$videoListString" )
                startActivity(intent1)

            } else {
                //Single Video
                val uri = data.data
                val mMMR = MediaMetadataRetriever();
                mMMR.setDataSource(this, uri);
                val bitmap = mMMR.getFrameAtTime()
                if(uri!=null) {
                    this.uriList.add(DataModel(uri.toString(),bitmap))
                }
                Log.i("video", "${this.uriList}")
                //Passing Video list data to Video Fragment
                    val g = Gson()
                    val videoListString = g.toJson(this.uriList)
                    val intent1 = Intent(this,VideoActivity::class.java)
                    intent1.putExtra("uri",videoListString)
                    Log.d("vid1", "$videoListString" )
                    startActivity(intent1)
            }
        }
    }
}

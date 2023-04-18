package com.example.galleryapp.adapter

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.databinding.ImageItemListBinding
import com.example.galleryapp.model.DataModel

class ImageGridAdapter(var imageList: ArrayList<DataModel>, var context: Context) :
    RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder>() {
    class ImageViewHolder(var binding: ImageItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataModel: DataModel, context: Context) {
            val uri = Uri.parse(dataModel.uri)

            Glide.with(context).load(uri).into(binding.imageView)

            binding.imageView.setOnClickListener {

                val dialog = Dialog(context)
                dialog.setContentView(R.layout.image_dialog_box)
                dialog.window?.apply {
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT

                    )
                    setGravity(Gravity.CENTER)
                    val image = findViewById<ImageView>(R.id.dialog_image_view)
                    image.setImageURI(uri)
                    dialog.show()
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ImageItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position],context)

        holder.binding.deleteBtn.setOnClickListener{

            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                imageList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, imageList.size)
            }
        }

    }

}
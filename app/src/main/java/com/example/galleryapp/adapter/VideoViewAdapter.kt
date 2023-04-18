package com.example.galleryapp.adapter

import android.app.Dialog
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.databinding.VideoItemListBinding
import com.example.galleryapp.model.DataModel
import com.potyvideo.library.AndExoPlayerView

class VideoViewAdapter(var videoList: ArrayList<DataModel>, var context: Context) :
    RecyclerView.Adapter<VideoViewAdapter.VideoViewHolder>() {
    class VideoViewHolder(var binding: VideoItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataModel: DataModel, context: Context) {
//            val uri = Uri.parse(dataModel.uri)
            val bitmap = dataModel.bitmap
            Glide.with(context).load(bitmap).into(binding.videoThumbnail)

            binding.videoThumbnail.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.video_dialog_box)
                dialog.window?.apply {
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setGravity(Gravity.CENTER)

                    //to play the video
                    val videoView = findViewById<AndExoPlayerView>(R.id.videoDialogView)
                    videoView.setSource(dataModel.uri)
//                    val mediaController = MediaController(context)
//                    mediaController.setAnchorView(videoView)
//                    videoView.setMediaController(mediaController)
//                    videoView.start()
                    dialog.show()

                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            VideoItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoList[position], context)

        holder.binding.deleteBtn.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                videoList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, videoList.size)
            }
        }
    }
}
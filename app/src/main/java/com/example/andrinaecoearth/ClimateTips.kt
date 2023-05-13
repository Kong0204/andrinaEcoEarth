package com.example.andrinaecoearth

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.recyclerview.widget.LinearLayoutManager

class ClimateTips : AppCompatActivity() {
    // declaring a null variable for VideoViews
    var tipsVideoView1: VideoView? = null
    var tipsVideoView2: VideoView? = null


    // declaring a null variable for MediaController
    var mediaControls: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_climate_tips)


        // assigning id of VideoView from activity_main.xml layout file
        tipsVideoView1 = findViewById<VideoView>(R.id.tipsVideoView1) as VideoView
        tipsVideoView2 = findViewById<VideoView>(R.id.tipsVideoView2) as VideoView


        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.tipsVideoView1)
            mediaControls!!.setAnchorView(this.tipsVideoView2)

        }

        // set the media controller for video views
        tipsVideoView1!!.setMediaController(mediaControls)
        tipsVideoView2!!.setMediaController(mediaControls)


        // set the absolute path of the video file which is going to be played
        tipsVideoView1!!.setVideoURI(
            Uri.parse("android.resource://"
                + packageName + "/" + R.raw.How_to_save_the_earth_from_climate_change))

        tipsVideoView2!!.setVideoURI(
            Uri.parse("android.resource://"
                    + packageName + "/" + R.raw.How_to_save_the_earth_from_climate_change))

        tipsVideoView1!!.requestFocus()
        tipsVideoView2!!.requestFocus()


        // starting the video
        tipsVideoView1!!.start()
        tipsVideoView2!!.start()




        // display a toast message if error occurs while playing the video
        tipsVideoView1!!.setOnErrorListener { mp, what, extra ->
            Toast.makeText(applicationContext, "An Error Occurred " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }
        tipsVideoView2!!.setOnErrorListener { mp, what, extra ->
            Toast.makeText(applicationContext, "An Error Occurred " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }

    }

}
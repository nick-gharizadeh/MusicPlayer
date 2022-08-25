package com.example.musicplayer.ui.homefragment

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.musicplayer.data.Music

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var musicsList = listOf<Music>()

    init {
        musicsList = getAllMusicsFromDevice(application.applicationContext)
    }

    fun getAllMusicsFromDevice(context: Context): List<Music> {
        val tempAudioList: MutableList<Music> = ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST
        )

        val c = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        if (c != null) {
            while (c.moveToNext()) {
                val audioModel = Music()
                val path: String = c.getString(0)
                val album: String = c.getString(1)
                val artist: String = c.getString(2)
                val name = path.substring(path.lastIndexOf("/") + 1)
                audioModel.name = name
                audioModel.album = album
                audioModel.artist = artist
                audioModel.path = path
                Log.e("Nameee :$name", " Album :$album")
                Log.e("Path :$path", " Artist :$artist")
                tempAudioList.add(audioModel)
            }
            c.close()
        }
        return tempAudioList
    }


}
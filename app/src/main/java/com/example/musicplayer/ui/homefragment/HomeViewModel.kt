package com.example.musicplayer.ui.homefragment

import android.app.Application
import android.content.ContentUris
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
            MediaStore.Audio.ArtistColumns.ARTIST,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.Media.ALBUM_ID
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
                val name = c.getString(3)
                audioModel.name = name
                audioModel.album = album
                audioModel.artist = artist
                audioModel.path = path
                val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
                val imgUri = ContentUris.withAppendedId(
                    sArtworkUri,
                    c.getLong(4)
                )
                audioModel.albumArt = imgUri
                Log.e("Nameee :$name", " Album :$album")
                Log.e("Path :$path", " Artist :$artist")
                tempAudioList.add(audioModel)
            }
            c.close()
        }
        return tempAudioList
    }

}
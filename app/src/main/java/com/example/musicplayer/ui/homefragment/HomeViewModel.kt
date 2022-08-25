package com.example.musicplayer.ui.homefragment

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
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
                audioModel.albumArt = getAudioAlbumImageContentUri(
                    this.getApplication<Application>().applicationContext,
                    path
                )
                Log.e("Nameee :$name", " Album :$album")
                Log.e("Path :$path", " Artist :$artist")
                tempAudioList.add(audioModel)
            }
            c.close()
        }
        return tempAudioList
    }

    @SuppressLint("Range")
    fun getAudioAlbumImageContentUri(context: Context, filePath: String): Uri? {
        val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.DATA + "=? "
        val projection = arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM_ID)
        val cursor: Cursor? = context.contentResolver.query(
            audioUri,
            projection,
            selection, arrayOf(filePath), null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val albumId: Long =
                cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
            val imgUri = ContentUris.withAppendedId(
                sArtworkUri,
                albumId
            )
            cursor.close()
            return imgUri
        }
        return null
    }
}
package com.example.musicplayer.data

import android.net.Uri

data class Music(
    var path: String? = null,
    var name: String? = null,
    var album: String? = null,
    var artist: String? = null,
    var albumArt: Uri? = null

)


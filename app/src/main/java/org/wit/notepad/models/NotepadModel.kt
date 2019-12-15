package org.wit.notepad.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotepadModel(var id: Long = 0,
                     var title: String = "",
                     var description: String = "",
                     var selection: String = "",
                     var image: String = "",
                     var lat : Double = 0.0,
                     var lng: Double = 0.0,
                     var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable


package org.wit.rove.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

var image: Uri = Uri.EMPTY
@Parcelize
data class RoveModel (var id: Long = 0,
                      var title: String = "",
                      var description: String = "",
                      //var rating: String = "",
                      var image: Uri = Uri.EMPTY,
                      var lat : Double = 0.0,
                      var lng : Double = 0.0,
                      var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double =0.0,
                    var zoom: Float = 0f): Parcelable
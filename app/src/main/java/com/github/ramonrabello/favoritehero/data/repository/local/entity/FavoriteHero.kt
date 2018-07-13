package com.github.ramonrabello.favoritehero.data.repository.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "heroes")
data class FavoriteHero(@PrimaryKey(autoGenerate = true) val id: Long, val name: String, val description: String?, val thumbnail:String) : Parcelable {
    @Ignore @IgnoredOnParcel var isFavorite: Boolean = false
}
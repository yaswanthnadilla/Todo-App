package com.example.tanmayee.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class TaskEntry(
    @PrimaryKey(autoGenerate = true)  var id: Int,
    var title: String,
    var priority: Int,
    var timStamp: Long
):Parcelable


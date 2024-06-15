package com.fit.popularperson.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "known_for",
    foreignKeys = [ForeignKey(
        entity = ProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class KnownForEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: Long, // Foreign key to ProfileEntity
    val originalName: String,
    val originalTitle: String,
    val posterPath: String,
    val overview: String,
    val popularity: Int,
)

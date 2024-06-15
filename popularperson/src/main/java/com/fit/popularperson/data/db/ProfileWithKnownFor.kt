package com.fit.popularperson.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileWithKnownFor(
    @Embedded val profile: ProfileEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "profileId"
    )
    val knownFor: List<KnownForEntity>
)

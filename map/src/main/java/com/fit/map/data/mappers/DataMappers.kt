package com.fit.map.data.mappers

import com.fit.map.data.dto.DataLocationDto
import com.fit.map.domain.model.LocationModel
import com.google.firebase.firestore.GeoPoint


fun LocationModel.toServerModel(): DataLocationDto {
    return DataLocationDto(
        GeoPoint(this.latitude, this.longitude),
        this.latitude,
        this.longitude
    )
}
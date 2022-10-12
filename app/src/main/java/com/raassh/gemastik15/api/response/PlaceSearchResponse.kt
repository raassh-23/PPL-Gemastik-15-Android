package com.raassh.gemastik15.api.response

import com.google.gson.annotations.SerializedName

data class PlaceSearchResponse(

	@field:SerializedName("data")
	val data: List<PlacesItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class PlacesItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("distance")
	val distance: Double,

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("facilities")
	val facilities: List<FacilitiesItem>
)
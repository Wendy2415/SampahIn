package com.capstone.sampahin.data.maps

import com.google.gson.annotations.SerializedName

data class MapsResponse(

	@field:SerializedName("places")
	val places: List<PlacesItem?>? = null,

	@field:SerializedName("query_location")
	val queryLocation: QueryLocation? = null
)

data class PlacesItem(
	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: Location? = null
)

data class Location(

	@field:SerializedName("lng")
	val lng: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class QueryLocation(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)

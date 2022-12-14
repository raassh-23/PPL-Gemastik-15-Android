package com.raassh.gemastik15.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ContributionResponse(
    @field:SerializedName("data")
    val data: ContributionData,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ContributionData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("place_id")
    val place_id: String,

    @field:SerializedName("facility")
    val facility: String,

    @field:SerializedName("quality")
    val quality: Int
)

data class ReviewResponse(
    @field:SerializedName("data")
    val data: ReviewResponseData,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
    )

data class ReviewResponseData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("place_id")
    val place_id: String,

    @field:SerializedName("review")
    val text: String
)

data class ContributionCountResponse(

    @field:SerializedName("data")
    val data: ContributionCountData,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ContributionCountData(

    @field:SerializedName("contribution count")
    val contributionCount: Int
)

data class ContributionsPlaceResponse(

    @field:SerializedName("data")
    val data: List<ReviewData>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class ReviewData(

    @field:SerializedName("user")
    val user: UserData,

    @field:SerializedName("place_id")
    val place_id: String,

    @field:SerializedName("facilities")
    val facilities: List<FacilityQualityItem>,

    @field:SerializedName("review")
    val review: String?,

    @field:SerializedName("modified_at")
    val modified_at: String?
) : Parcelable


@Parcelize
data class FacilityQualityItem(

    @field:SerializedName("facility")
    val facility: String,

    @field:SerializedName("quality")
    var quality: Int,
) : Parcelable

data class ContributionUserPlaceResponse(

    @field:SerializedName("data")
    val data: ContributionUserPlaceData,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class ContributionUserPlaceData(

    @field:SerializedName("user_id")
    val user_id: String,

    @field:SerializedName("place_id")
    val place_id: String,

    @field:SerializedName("facilities")
    val facilities: List<FacilityQualityItem>,

    @field:SerializedName("review")
    val review: String?,

    @field:SerializedName("is_moderated")
    val is_moderated: Boolean,

    @field:SerializedName("moderation_reason")
    val moderation_reason: String?,

    @field:SerializedName("modified_at")
    val modified_at: String?
) : Parcelable


data class ContributionUserResponse(

    @field:SerializedName("data")
    val data: List<ContributionUserData>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class ContributionUserData(

    @field:SerializedName("user_id")
    val user_id: String,

    @field:SerializedName("place")
    val place: PlacesItem,

    @field:SerializedName("facilities")
    val facilities: List<FacilityQualityItem>,

    @field:SerializedName("review")
    val review: String?,

    @field:SerializedName("is_moderated")
    val is_moderated: Boolean,

    @field:SerializedName("moderation_reason")
    val moderation_reason: String?,

    @field:SerializedName("modified_at")
    val modified_at: String?
) : Parcelable
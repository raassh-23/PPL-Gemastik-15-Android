package com.raassh.gemastik15.api

import com.raassh.gemastik15.api.request.*
import com.raassh.gemastik15.api.response.*
import retrofit2.http.*

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): UserResponse

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): TokenResponse

    @POST("auth/logout")
    suspend fun logout(
        @Body body: LogoutRequest
    ): GeneralResponse

    @GET("places/detail/{id}")
    suspend fun getPlaceDetail(
        @Path("id") id: String,
        @Query("lat") lat: Double?,
        @Query("long") long: Double?
    ): PlaceDetailResponse

    @GET("places/search")
    suspend fun searchPlaceByName(
        @Query("name") name: String,
        @Query("lat") lat: Double?,
        @Query("long") long: Double?,
    ): PlaceSearchResponse

    @GET("places/search/facilities")
    suspend fun searchPlaceByFacility(
        @Query("facility") facilities: List<String>,
        @Query("lat") lat: Double?,
        @Query("long") long: Double?,
    ): PlaceSearchResponse

    @GET("places/nearby")
    suspend fun searchPlaceNearby(
        @Query("lat") lat: Double,
        @Query("long") long: Double,
    ): PlaceSearchResponse

    @POST("contributions/review")
    suspend fun addReview(
        @Header("Authorization") authorization: String,
        @Body body: ReviewRequest
    ): ReviewResponse

    @PUT("contributions/review")
    suspend fun changeReview(
        @Header("Authorization") authorization: String,
        @Body body: ReviewRequest
    ): ReviewResponse

    @GET("contributions/count")
    suspend fun getContributionCount(
        @Header("Authorization") authorization: String
    ): ContributionCountResponse

    @GET("contributions/{place_id}")
    suspend fun getContributionsofPlace(
        @Path("place_id") placeId: String
    ): ContributionsPlaceResponse

    @GET("contributions/{place_id}/users/{user_id}")
    suspend fun getContributionsofPlacebyUser(
        @Path("place_id") placeId: String,
        @Path("user_id") userId: String
    ): ContributionUserPlaceResponse

    @GET("contributions/users/{user_id}")
    suspend fun getContributionsofUser(
        @Path("user_id") userId: String,
        @Query("lat") lat: Double,
        @Query("long") long: Double,
    ): ContributionUserResponse

    @DELETE("contributions/delete/{place_id}")
    suspend fun deleteContribution(
        @Header("Authorization") authorization: String,
        @Path("place_id") placeId: String
    ): GeneralResponse

    @GET("places/favorites")
    suspend fun getFavoritePlaces(
        @Header("Authorization") authorization: String
    ): PlaceSearchResponse

    @POST("places/favorites/{place_id}")
    suspend fun addFavoritePlace(
        @Header("Authorization") authorization: String,
        @Path("place_id") placeId: String
    ): GeneralResponse

    @DELETE("places/favorites/{place_id}")
    suspend fun deleteFavoritePlace(
        @Header("Authorization") authorization: String,
        @Path("place_id") placeId: String
    ): GeneralResponse

    @GET("places/recommendation")
    suspend fun getRecommendationPlaces(
        @Header("Authorization") authorization: String,
        @Query("lat") lat: Double,
        @Query("long") long: Double,
    ): PlaceSearchResponse

    @POST("auth/disabilities")
    suspend fun setDisabilities(
        @Header("Authorization") authorization: String,
        @Body body: AddDisabilitiesRequest
    ): GeneralResponse

    @POST("auth/password/change")
    suspend fun changePassword(
        @Header("Authorization") authorization: String,
        @Body body: ChangePasswordRequest
    ): GeneralResponse

    @GET("auth/detail")
    suspend fun getUserDetail(
        @Header("Authorization") authorization: String
    ): ProfileResponse

    @POST("auth/detail")
    suspend fun editUserDetail(
        @Header("Authorization") authorization: String,
        @Body body: EditProfileRequest
    ): ProfileResponse

    @POST("auth/verify/resend")
    suspend fun resendVerification(
        @Body body: ResendVerificationRequest
    ): GeneralResponse

    @GET("auth/search")
    suspend fun searchUserByUsernameOrName(
        @Query("query") username: String
    ): SearchUserResponse

    @GET("readings/news")
    suspend fun getNews(
        @Query("limit") limit: Int
    ): ArticleResponse

    @GET("readings/articles")
    suspend fun getArticles(
        @Query("limit") limit: Int
    ): ArticleResponse

    @GET("readings/guidelines")
    suspend fun getGuidelines(
        @Query("limit") limit: Int
    ): ArticleResponse

    @POST("reports/contributions")
    suspend fun reportContribution(
        @Header("Authorization") authorization: String,
        @Body body: ReportContributionRequest
    ): GeneralResponse

    @GET("reports/contributions")
    suspend fun getContributionReports(
        @Header("Authorization") authorization: String
    ): ListReportContributionResponse

    @GET("reports/contributions/details/{place_id}/users/{user_id}")
    suspend fun getContributionReportDetails(
        @Header("Authorization") authorization: String,
        @Path("place_id") placeId: String,
        @Path("user_id") userId: String
    ): DetailReportContributionResponse

    @POST("reports/contributions/dismiss")
    suspend fun dismissContributionReport(
        @Header("Authorization") authorization: String,
        @Body body: DismissContributionReportRequest
    ): GeneralResponse

    @POST("reports/contributions/moderate")
    suspend fun moderateContributionReport(
        @Header("Authorization") authorization: String,
        @Body body: ReportContributionRequest
    ): GeneralResponse

    @POST("reports/users")
    suspend fun reportUser(
        @Header("Authorization") authorization: String,
        @Body body: ReportUserRequest
    ): GeneralResponse

    @GET("reports/users")
    suspend fun getUserReports(
        @Header("Authorization") authorization: String
    ): ListReportUserResponse

    @GET("reports/users/details/{user_id}")
    suspend fun getUserReportDetails(
        @Header("Authorization") authorization: String,
        @Path("user_id") userId: String
    ): DetailReportUserResponse

    @POST("reports/users/dismiss")
    suspend fun dismissUserReport(
        @Header("Authorization") authorization: String,
        @Body body: DismissUserReportRequest
    ): GeneralResponse

    @POST("reports/users/moderate")
    suspend fun moderateUserReport(
        @Header("Authorization") authorization: String,
        @Body body: ReportUserRequest
    ): GeneralResponse

    @POST("chat/start")
    suspend fun startChat(
        @Body body: StartChatRequest
    ): StartChatResponse

    @POST("chat/send")
    suspend fun sendChatMessage(
        @Body body: SendChatRequest
    ): SendChatResponse

    @GET("auth/users/{user_id}")
    suspend fun getUserDetailbyId(
        @Path("user_id") userId: String
    ): DetailUserResponse

    @GET("auth/pictures/{user_id}")
    suspend fun getUserPicture(
        @Path("user_id") userId: String
    ): SingleUserFieldResponse

    @GET("auth/usernames/{user_id}")
    suspend fun getUsername(
        @Path("user_id") userId: String
    ): SingleUserFieldResponse
}
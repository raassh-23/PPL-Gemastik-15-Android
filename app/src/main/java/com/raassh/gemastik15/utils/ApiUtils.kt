package com.raassh.gemastik15.utils

import com.google.gson.Gson
import com.raassh.gemastik15.api.response.ErrorResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException

fun getErrorResponse(body: ResponseBody?): ErrorResponse = if (body != null) {
    Gson().fromJson(body.string(), ErrorResponse::class.java)
} else {
    ErrorResponse("Unknown error", true, "Unknown error")
}

fun <T> callApi(apiCall: suspend () -> T) = flow<Resource<*>> {
    emit(Resource.Loading(null))

    val data = apiCall()
    emit(Resource.Success(data))
}.catch { e ->
    if (e is HttpException) {
        val error = getErrorResponse(e.response()?.errorBody())
        emit(Resource.Error(error.message, error))
    } else {
        emit(Resource.Error(e.message, null))
    }
}
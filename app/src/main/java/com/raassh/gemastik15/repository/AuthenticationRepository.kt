package com.raassh.gemastik15.repository

import com.raassh.gemastik15.api.ApiService
import com.raassh.gemastik15.api.request.LoginRequest
import com.raassh.gemastik15.utils.callApi
import com.raassh.gemastik15.api.request.RegisterRequest

class AuthenticationRepository(private val apiService: ApiService) {
    fun register(name: String, email: String, password: String) = callApi {
        val req = RegisterRequest(
            name=name,
            email=email,
            password=password
        )

        apiService.register(req).data
    }

    fun login(email: String, password: String) = callApi {
        val req = LoginRequest(
            email=email,
            password=password
        )

        apiService.login(req).data
    }
}
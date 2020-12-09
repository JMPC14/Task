package com.jack.utilitatask.api

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET

interface UtilitaApi {

    @GET("status")
    fun getPosts(): Call<JsonElement>
}
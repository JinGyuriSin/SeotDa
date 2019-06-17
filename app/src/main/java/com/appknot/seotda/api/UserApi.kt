package com.appknot.seotda.api

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *
 * @author Jin on 2019-06-17
 */
interface UserApi {

    @FormUrlEncoded
    @POST("registerToken.php")
    fun registerToken(
        @Field("id") id: String,
        @Field("token") token: String
    ): Single<ApiResponse>

    @FormUrlEncoded
    @POST("enterToRoom.php")
    fun enterToRoom(
        @Field("id") id: String
    ): Single<ApiResponse>
}
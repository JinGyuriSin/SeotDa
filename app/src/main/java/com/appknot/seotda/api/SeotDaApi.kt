package com.appknot.seotda.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *
 * @author Jin on 2019-06-17
 */
interface SeotDaApi {

    @FormUrlEncoded
    @POST("registerToken.php")
    fun registerToken(
        @Field("id") id: String,
        @Field("token") token: String
    ): Single<Response<ApiResponse>>

    @FormUrlEncoded
    @POST("enterToRoom.php")
    fun enterToRoom(
        @Field("user_idx") userIdx: String
    ): Single<Response<ApiResponse>>

    @FormUrlEncoded
    @POST("leaveFromRoom.php")
    fun leaveFromRoom(
        @Field("user_idx") userIdx: String
    ): Single<Response<ApiResponse>>

    @FormUrlEncoded
    @POST("ready.php")
    fun ready(
        @Field("user_idx") userIdx: String,
        @Field("ready") ready: String
    ): Single<Response<ApiResponse>>
}
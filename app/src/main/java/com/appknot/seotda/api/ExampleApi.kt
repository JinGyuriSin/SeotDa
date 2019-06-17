package com.appknot.seotda.api

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *
 * @author Jin on 2019-06-17
 */
interface ExampleApi {

    @FormUrlEncoded
    @POST
    fun login(
        @Field("token") token: String
    ): Single<ApiResponse>
}
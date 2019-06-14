package com.appknot.seotda.api

import com.appknot.seotda.api.ApiResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *
 * @author Jin on 2019-06-14
 */
class RetrofitAPI {

    interface Example {
        @FormUrlEncoded
        @POST
        fun addParams(
            @Field("token") token: String
        ): Single<ApiResponse>
    }
}
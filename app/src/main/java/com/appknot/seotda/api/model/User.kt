package com.appknot.seotda.api.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author Jin on 2019-06-26
 */
data class User(
    var id: String,
    @SerializedName("profile_img_path")
    var profileImgPath: String,
    var token: String,
    var coin: String,
    @SerializedName("win_count")
    var winCount: String,
    @SerializedName("lose_count")
    var loseCount: String
) : BaseModel()
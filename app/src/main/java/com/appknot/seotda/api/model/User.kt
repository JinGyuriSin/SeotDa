package com.appknot.seotda.api.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author Jin on 2019-06-26
 */
data class User(
    var id: String = "",
    @SerializedName("profile_img_path")
    var profileImgPath: String = "",
    var token: String = "",
    var coin: String = "0",
    @SerializedName("win_count")
    var winCount: String = "0",
    @SerializedName("lose_count")
    var loseCount: String = "0",
    var position: String = "5"
) : BaseModel()
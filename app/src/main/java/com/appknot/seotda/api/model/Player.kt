package com.appknot.seotda.api.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author Jin on 2019-07-04
 */

data class Player(
    @SerializedName("user_idx")
    var userIdx: String,
    @SerializedName("room_idx")
    var roomIdx: String,
    @SerializedName("total_coin")
    var totalCoin: String,
    @SerializedName("call_coin")
    var callCoin: String,
    @SerializedName("pay_coin")
    var payCoin: String,
    @SerializedName("run_count")
    var runCount: String,
    @SerializedName("hand_rankings_idx")
    var handRankingsIdx: String,
    var state: String,
    var ready: String,
    @SerializedName("room_exit")
    var roomExit: String,
    @SerializedName("last_action")
    var lastAction: String,
    @SerializedName("card_1")
    var cardOne: String,
    @SerializedName("card_2")
    var cardTwo: String,
    @SerializedName("card_3")
    var cardThree: String
) : BaseModel()
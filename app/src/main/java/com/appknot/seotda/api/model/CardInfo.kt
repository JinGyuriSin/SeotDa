package com.appknot.seotda.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * @author Jin on 2019-07-10
 */

data class CardInfo(
    @SerializedName("hand_rankings_list")
    var handRankingsList: Array<HandRanking>,
    @SerializedName("card_idx")
    var cardIdx: Array<String>
): Serializable {

    data class HandRanking(
        var title: String,
        @SerializedName("card_1")
        var cardOne: String,
        @SerializedName("card_2")
        var cardTwo: String
    ) : BaseModel()
}
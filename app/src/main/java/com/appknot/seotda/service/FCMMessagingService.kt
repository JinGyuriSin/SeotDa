package com.appknot.seotda.service

import com.appknot.seotda.App.Companion.app
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

/**
 *
 * @author Jin on 2019-06-17
 */

/**
 * FCM 리시버
 */
class FCMMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data?.isNotEmpty()?.let {
            sendNotification(remoteMessage.data)
        }
    }

    /**
     * InstanceID 이 갱신되었을 때 호출된다.
     */
    override fun onNewToken(token: String?) {
        // 필요하다면 써드파티 서버에 FCM 토큰 전송
    }

    /**
     * 노티피케이션 생성
     */
    private fun sendNotification(data: Map<String, String>) {
        val title = data["title"]
        val content = data["body"]
        val payload = Gson().fromJson(data["payload"], HashMap<String, String>().javaClass)

        app.logMessage("fcm test        !!! ")
//        app.logMessage(payload)

    }
}
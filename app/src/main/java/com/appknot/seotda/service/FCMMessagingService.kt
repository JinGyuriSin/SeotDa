package com.appknot.seotda.service

import android.content.Intent
import com.appknot.seotda.App.Companion.app
import com.appknot.seotda.api.model.User
import com.appknot.seotda.extensions.parse
import com.appknot.seotda.extensions.toMap
import com.appknot.seotda.ui.main.MainActivity.Companion.ACTION_BROADCAST
import com.appknot.seotda.ui.main.MainActivity.Companion.KEY_CODE
import com.appknot.seotda.ui.main.MainActivity.Companion.KEY_USER
import com.appknot.seotda.ui.main.MainActivity.Companion.KEY_USER_LIST
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
            sendBroadcast(remoteMessage.data)
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

    private fun sendBroadcast(data: Map<String, String>) {
        val type = data["type"]
        val code = data["code"]
        val payload = Gson().fromJson(data["payload"], HashMap<String, Any>().javaClass)
        val intent = Intent(ACTION_BROADCAST)

        when (code) {
            "1" -> {
                val userList = payload["user_list"]
                userList?.let {
                    intent.putExtra(KEY_CODE, code)
                    intent.putExtra(KEY_USER_LIST, parse(it, Array<User>::class.java))
                    sendBroadcast(intent)
                }
            }
            "2" -> {
                val user = payload["user"]
                user?.let {
                    intent.putExtra(KEY_CODE, code)
                    intent.putExtra(KEY_USER, parse(it, User::class.java))
                    sendBroadcast(intent)
                }
            }
        }

    }
}
package com.appknot.seotda.api


/**
 * appknot 컨벤션 기반 API 응답 구조
 * @author Xellsky(Alex Ji)
 */
class ApiResponse {
    lateinit var code: String
    lateinit var msg: Msg
    var data: Any? = null

    inner class Msg {
        lateinit var ko: String
        lateinit var en: String
    }
}
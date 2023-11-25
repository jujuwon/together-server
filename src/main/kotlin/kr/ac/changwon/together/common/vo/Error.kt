package kr.ac.changwon.together.common.vo

import kr.ac.changwon.together.common.coded.ErrorCode

data class Error(
    val code: Int,
    val message: String
) {
    companion object {
        fun of(errorCode: ErrorCode) = Error(
            code = errorCode.code,
            message = errorCode.message
        )
    }
}

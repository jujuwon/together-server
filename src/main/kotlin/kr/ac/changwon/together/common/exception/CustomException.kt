package kr.ac.changwon.together.common.exception

import kr.ac.changwon.together.common.coded.ErrorCode

class CustomException(error: ErrorCode) : RuntimeException() {
    val error: ErrorCode = error
}
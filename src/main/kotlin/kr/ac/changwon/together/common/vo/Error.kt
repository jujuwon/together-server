package kr.ac.changwon.together.common.vo

import kr.ac.changwon.together.common.coded.ErrorCode

data class Error(
    val code: Int,
    val message: String
)

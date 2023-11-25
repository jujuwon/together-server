package kr.ac.changwon.together.common.coded

enum class ErrorCode(
    val code: Int,
    val message: String
) {
    NOT_VALID_USER(100, "인증되지 않은 사용자입니다.")
}
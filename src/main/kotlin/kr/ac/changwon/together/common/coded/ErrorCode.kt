package kr.ac.changwon.together.common.coded

enum class ErrorCode(
    val code: Int,
    val message: String
) {
    NOT_VALID_USER(100, "인증되지 않은 사용자입니다."),
    NOT_FOUND_USER(101, "존재하지 않는 사용자입니다.")
}
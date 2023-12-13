package kr.ac.changwon.together.common.coded

enum class ErrorCode(
    val code: Int,
    val message: String
) {
    UNKNOWN_ERROR(0, "알 수 없는 오류가 발생했습니다."),
    NOT_VALID_USER(1000, "인증되지 않은 사용자입니다."),
    NOT_FOUND_USER(1001, "존재하지 않는 사용자입니다."),
    INVALID_PASSWORD(1002, "비밀번호가 일치하지 않습니다."),
    POST_SAVE_ERROR(2000, "게시글 저장에 실패했습니다."),
    NOT_FOUND_POST(2001, "존재하지 않는 게시글입니다."),
    INVALID_POST_ID(2002, "유효하지 않은 게시글 ID입니다."),
}
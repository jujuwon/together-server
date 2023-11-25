package kr.ac.changwon.together.common.vo

class ApiResponse<T>(
    val success: Boolean = true,
    val error: Error? = null,
    val result: T? = null
) {
    companion object {
        fun <T> success(result: T) = ApiResponse(result = result)
    }
}
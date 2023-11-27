package kr.ac.changwon.together.common.handler

import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.vo.RestApiResponse
import kr.ac.changwon.together.common.vo.Error
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): RestApiResponse<*> {
        return RestApiResponse(
            success = false,
            error = Error(
                code = e.error.code,
                message = e.error.message
            ),
            result = null
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleException(e: RuntimeException): RestApiResponse<*> {
        return RestApiResponse(
            success = false,
            error = Error(
                code = 0,
                message = e.message ?: "알 수 없는 오류가 발생했습니다."
            ),
            result = null
        )
    }
}
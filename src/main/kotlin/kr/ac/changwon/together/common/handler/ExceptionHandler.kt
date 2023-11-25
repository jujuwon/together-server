package kr.ac.changwon.together.common.handler

import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.vo.ApiResponse
import kr.ac.changwon.together.common.vo.Error
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleException(e: CustomException): ApiResponse<*> {
        return ApiResponse(
            success = false,
            error = Error.of(errorCode = e.error),
            result = null
        )
    }
}
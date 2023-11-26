package kr.ac.changwon.together.common.handler

import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.vo.RestApiResponse
import kr.ac.changwon.together.common.vo.Error
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleException(e: CustomException): RestApiResponse<*> {
        return RestApiResponse(
            success = false,
            error = Error.of(errorCode = e.error),
            result = null
        )
    }
}
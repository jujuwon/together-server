package kr.ac.changwon.together.common.util.health

import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.vo.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/health")
    fun health() = ApiResponse.success("ok")

    @GetMapping("/exception")
    fun exception() {
        throw CustomException(ErrorCode.NOT_VALID_USER)
    }
}
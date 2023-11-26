package kr.ac.changwon.together.common.util

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.changwon.together.auth.vo.ResUserInfo
import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.vo.RestApiResponse
import kr.ac.changwon.together.user.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["테스트 API"], description = "테스트 및 유틸성 API")
@RestController
@RequestMapping("/api/util")
class UtilController(
    private val service: UserService
) {
    @ApiOperation(value = "health check")
    @GetMapping("/health")
    fun health() = RestApiResponse.success("ok")

    @ApiOperation(value = "error response example")
    @GetMapping("/exception")
    fun exception() {
        throw CustomException(ErrorCode.NOT_VALID_USER)
    }

    @ApiOperation(value = "로그인 유저 정보", notes = "로그인 유저의 회원 정보를 반환합니다. (디버깅용)")
    @GetMapping("/user-info")
    fun getUserInfo(@AuthenticationPrincipal user: User): RestApiResponse<ResUserInfo> =
        RestApiResponse.success(service.getUserInfo(id = user.username.toLong()))
}
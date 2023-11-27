package kr.ac.changwon.together.user.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.changwon.together.common.vo.RestApiResponse
import kr.ac.changwon.together.user.service.UserService
import kr.ac.changwon.together.user.vo.ReqUpdateUser
import kr.ac.changwon.together.user.vo.ResUserPageInfo
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(tags = ["회원 API"])
@RestController
@RequestMapping("/api/user")
class UserController(
    private val service: UserService
) {
    @ApiOperation(value = "사용자 화면 - me", notes = "로그인한 사용자의 정보를 반환합니다.")
    @GetMapping("/info")
    fun retrieve(@AuthenticationPrincipal user: User): RestApiResponse<ResUserPageInfo> =
        RestApiResponse.success(service.getUserPageInfo(id = user.username.toLong()))

    @ApiOperation(value = "사용자 화면 - 프로필 편집", notes = "로그인한 사용자의 정보를 수정합니다.")
    @PatchMapping("/info")
    fun update(@AuthenticationPrincipal user: User, @Valid @RequestBody req: ReqUpdateUser): RestApiResponse<Unit> {
        return RestApiResponse.success(service.updateUserInfo(id = user.username.toLong(), req = req))
    }
}
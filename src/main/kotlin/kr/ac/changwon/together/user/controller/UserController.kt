package kr.ac.changwon.together.user.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.changwon.together.auth.vo.ResUserInfo
import kr.ac.changwon.together.common.vo.RestApiResponse
import kr.ac.changwon.together.user.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["회원 API"])
@RestController
@RequestMapping("/api/user")
class UserController(
    private val service: UserService
) {

}
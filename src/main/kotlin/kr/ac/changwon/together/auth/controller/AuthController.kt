package kr.ac.changwon.together.auth.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.changwon.together.auth.service.AuthService
import kr.ac.changwon.together.auth.vo.ReqSignIn
import kr.ac.changwon.together.auth.vo.ReqSignUp
import kr.ac.changwon.together.auth.vo.ResLoginToken
import kr.ac.changwon.together.common.vo.RestApiResponse
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(tags = ["인증 API"], description = "회원가입/로그인 (토큰 필요 없음)")
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val service: AuthService
) {
    @ApiOperation(value = "회원가입 (완료)", notes = "회원가입 성공시 JWT를 반환합니다.")
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody req: ReqSignUp): RestApiResponse<ResLoginToken> =
        RestApiResponse.success(service.signUp(req = req))

    @ApiOperation(value = "로그인 (완료)", notes = "로그인 성공시 JWT를 반환합니다.")
    @GetMapping("/sign-in")
    fun signIn(@Valid req: ReqSignIn): RestApiResponse<ResLoginToken> =
        RestApiResponse.success(service.signIn(req = req))
}
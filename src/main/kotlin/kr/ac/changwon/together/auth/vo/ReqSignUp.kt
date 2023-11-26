package kr.ac.changwon.together.auth.vo

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

data class ReqSignUp(
    @ApiModelProperty(value = "이메일")
    @field:NotNull
    val email: String,
    @ApiModelProperty(value = "이름")
    @field:NotNull
    val name: String,
    @ApiModelProperty(value = "닉네임")
    @field:NotNull
    val nickname: String,
    @ApiModelProperty(value = "비밀번호")
    @field:NotNull
    val password: String
)

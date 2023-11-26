package kr.ac.changwon.together.auth.vo

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

data class ReqSignIn(
    @ApiModelProperty(value = "이메일")
    @field:NotNull
    val email: String,
    @ApiModelProperty(value = "비밀번호")
    @field:NotNull
    val password: String
)
package kr.ac.changwon.together.auth.vo

import io.swagger.annotations.ApiModelProperty

data class ResLoginToken(
    @ApiModelProperty(value = "JWT 토큰")
    val token: String
)

package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty

data class ResSearchUser(
    @ApiModelProperty(value = "사용자 ID")
    val userId: Long,
    @ApiModelProperty(value = "사용자 닉네임")
    val nickname: String
)

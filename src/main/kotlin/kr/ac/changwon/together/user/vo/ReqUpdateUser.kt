package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

data class ReqUpdateUser(
    @ApiModelProperty(value = "닉네임")
    @field:NotNull
    val nickname: String,
    @ApiModelProperty(value = "프로필 이미지 URL")
    @field:NotNull
    val profileImage: String,
    @ApiModelProperty(value = "소개글")
    @field:NotNull
    val introduce: String
)

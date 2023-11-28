package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty

data class ResProfileImgUrl(
    @ApiModelProperty(value = "프로필 이미지 URL")
    val profileImgUrl: String
)
package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty

data class Articles(
    @ApiModelProperty(value = "게시글 ID")
    val id: Long,
    @ApiModelProperty(value = "게시글 이미지 URL")
    val image: String,
)
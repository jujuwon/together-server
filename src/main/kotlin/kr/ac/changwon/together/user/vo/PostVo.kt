package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty

data class PostVo(
    @ApiModelProperty(value = "게시글 ID")
    val id: Long,
    @ApiModelProperty(value = "게시글 이미지 URL")
    val imgUrl: String,
)

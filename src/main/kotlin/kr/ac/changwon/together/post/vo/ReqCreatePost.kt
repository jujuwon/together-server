package kr.ac.changwon.together.post.vo

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

data class ReqCreatePost(
    @ApiModelProperty(value = "이미지 URL")
    @field:NotNull
    val imgUrl: String,
    @ApiModelProperty(value = "내용")
    @field:NotNull
    val content: String
)

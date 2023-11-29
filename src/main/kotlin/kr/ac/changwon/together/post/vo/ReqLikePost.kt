package kr.ac.changwon.together.post.vo

import io.swagger.annotations.ApiModelProperty

data class ReqLikePost(
    @ApiModelProperty(value = "좋아요 여부", notes = "true: 좋아요, false: 좋아요 취소")
    val isLike: Boolean
)

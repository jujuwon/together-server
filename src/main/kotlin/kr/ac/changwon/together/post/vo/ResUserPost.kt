package kr.ac.changwon.together.post.vo

import io.swagger.annotations.ApiModelProperty

data class ResUserPost(
    @ApiModelProperty(value = "게시글 목록")
    val posts: List<UserPostVo>
)

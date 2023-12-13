package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty
import kr.ac.changwon.together.post.vo.UserPostVo

data class ResFavoritePost(
    @ApiModelProperty(value = "게시글 목록")
    val posts: List<UserPostVo>
)

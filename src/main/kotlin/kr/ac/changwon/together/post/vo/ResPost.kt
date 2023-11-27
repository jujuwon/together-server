package kr.ac.changwon.together.post.vo

import io.swagger.annotations.ApiModelProperty
import kr.ac.changwon.together.post.entity.Post

data class ResPost(
    @ApiModelProperty(value = "게시글 ID")
    val id: Long?,
    @ApiModelProperty(value = "게시글 이미지 URL")
    val imgUrl: String,
    @ApiModelProperty(value = "게시글 내용")
    val content: String,
) {
    companion object {
        fun of(post: Post) = ResPost(
            id = post.id,
            imgUrl = post.imgUrl,
            content = post.content
        )
    }
}

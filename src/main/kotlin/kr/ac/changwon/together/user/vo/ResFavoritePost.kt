package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty
import kr.ac.changwon.together.post.entity.Post
import java.time.LocalDateTime

data class ResFavoritePost(
    @ApiModelProperty(value = "게시글 ID")
    val id: Long,
    @ApiModelProperty(value = "게시글 작성 시간")
    val createdAt: LocalDateTime,
    @ApiModelProperty(value = "게시글 이미지 URL")
    val imgUrl: String,
    @ApiModelProperty(value = "즐겨찾기 여부")
    val isFavorite: Boolean,
    @ApiModelProperty(value = "댓글 목록")
    val comments: List<CommentDto>
) {
    companion object {
        fun of(post: Post, comments: List<CommentDto>) =
            ResFavoritePost(
                id = post.id!!,
                createdAt = post.createdAt,
                imgUrl = post.imgUrl,
                isFavorite = true,
                comments = comments
            )
    }
}

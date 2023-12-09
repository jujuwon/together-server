package kr.ac.changwon.together.post.vo

import io.swagger.annotations.ApiModelProperty
import kr.ac.changwon.together.post.entity.Post
import java.time.LocalDateTime

data class UserPostVo(
    @ApiModelProperty(value = "게시글 ID")
    val postId: Long,
    @ApiModelProperty(value = "게시글 작성 시간")
    val createdAt: LocalDateTime,
    @ApiModelProperty(value = "작성자 ID")
    val authorId: Long,
    @ApiModelProperty(value = "작성자 닉네임")
    val authorNickname: String,
    @ApiModelProperty(value = "작성자 프로필 이미지 URL")
    val authorProfileImgUrl: String,
    @ApiModelProperty(value = "게시글 내용")
    val content: String,
    @ApiModelProperty(value = "게시글 이미지 URL")
    val imgUrl: String,
    @ApiModelProperty(value = "좋아요 수")
    val likeCount: Int,
    @ApiModelProperty(value = "현재 사용자 해당 게시글 좋아요 여부")
    val isLike: Boolean,
    @ApiModelProperty(value = "현재 사용자 해당 게시글 즐겨찾기 여부")
    val isFavorite: Boolean,
    @ApiModelProperty(value = "댓글 목록")
    val comments: List<CommentDto>
) {
    companion object {
        fun of(
            post: Post,
            isLike: Boolean,
            isFavorite: Boolean,
            comments: List<CommentDto>
        ): UserPostVo = with(post) {
            UserPostVo(
                postId = id!!,
                createdAt = createdAt,
                authorId = user.id!!,
                authorNickname = user.nickname,
                authorProfileImgUrl = user.profileImgUrl,
                content = content,
                imgUrl = imgUrl,
                likeCount = likes.size,
                isLike = isLike,
                isFavorite = isFavorite,
                comments = comments
            )
        }
    }
}

package kr.ac.changwon.together.post.utils

import kr.ac.changwon.together.post.entity.Comment
import kr.ac.changwon.together.post.vo.CommentDto

// 댓글 정보 가져오기
fun Comment.mapCommentToDto(): CommentDto {
    val subComments = this.comments.map { mapCommentToDto() }
    return CommentDto(
        userId = this.user.id!!,
        nickname = this.user.nickname,
        commentId = this.id!!,
        content = this.content,
        subComments = subComments
    )
}

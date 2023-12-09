package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty

data class CommentDto(
    @ApiModelProperty(value = "댓글 ID")
    val commentId: Long,
    @ApiModelProperty(value = "댓글 내용")
    val content: String,
    @ApiModelProperty(value = "댓글 작성자 ID")
    val userId: Long,
    @ApiModelProperty(value = "댓글 작성자 닉네임")
    val nickname: String,
    @ApiModelProperty(value = "대댓글 목록")
    val subComments: List<CommentDto>
)
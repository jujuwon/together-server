package kr.ac.changwon.together.post.vo

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

data class ReqCreateComment(
    @ApiModelProperty(value = "댓글 내용", required = true)
    @field:NotNull
    val content: String,

    @ApiModelProperty(value = "부모 댓글 ID", notes = "대댓글인 경우 부모 댓글 ID 입력. 없는 경우 null", required = false)
    val parentId: Long? = null
)

package kr.ac.changwon.together.post.vo

import io.swagger.annotations.ApiModelProperty

data class ReqFavoritePost(
    @ApiModelProperty(value = "즐겨찾기 여부", notes = "true: 즐겨찾기, false: 즐겨찾기 해제")
    val isFavorite: Boolean
)

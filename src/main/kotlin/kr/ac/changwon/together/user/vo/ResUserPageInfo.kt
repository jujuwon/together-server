package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty
import kr.ac.changwon.together.user.entity.User

data class ResUserPageInfo(
    @ApiModelProperty(value = "이메일")
    val email: String,
    @ApiModelProperty(value = "닉네임")
    val nickname: String,
    // TODO
    @ApiModelProperty(value = "프로필 이미지 URL")
    val profileImgUrl: String,
    @ApiModelProperty(value = "소개글")
    val introduce: String,
    @ApiModelProperty(value = "게시물 수")
    val articleCount: Int,
    @ApiModelProperty(value = "팔로잉 수")
    val followingCount: Int,
    @ApiModelProperty(value = "팔로워 수")
    val followerCount: Int,
    @ApiModelProperty(value = "게시글 리스트")
    val articles: List<Articles>
) {
    companion object {
        fun of(user: User, followingCount: Int, followerCount: Int, articles: List<Articles>): ResUserPageInfo =
            ResUserPageInfo(
                email = user.email,
                nickname = user.nickname,
                profileImgUrl = user.profileImgUrl,
                introduce = user.introduce ?: "",
                articleCount = articles.size,
                followingCount = followingCount,
                followerCount = followerCount,
                articles = articles
            )
    }
}

data class Articles(
    val id: Long,
    val image: String,
)

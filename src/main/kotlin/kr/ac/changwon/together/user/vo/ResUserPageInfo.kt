package kr.ac.changwon.together.user.vo

import io.swagger.annotations.ApiModelProperty
import kr.ac.changwon.together.user.entity.User

data class ResUserPageInfo(
    @ApiModelProperty(value = "이메일")
    val email: String,
    @ApiModelProperty(value = "닉네임")
    val nickname: String,
    @ApiModelProperty(value = "프로필 이미지 URL")
    val profileImgUrl: String,
    @ApiModelProperty(value = "소개글")
    val introduce: String,
    @ApiModelProperty(value = "게시물 수")
    val postCount: Int,
    @ApiModelProperty(value = "팔로잉 수")
    val followingCount: Int,
    @ApiModelProperty(value = "팔로워 수")
    val followerCount: Int,
    @ApiModelProperty(value = "게시글 리스트")
    val posts: List<PostVo>
) {
    companion object {
        fun of(user: User, followingCount: Int, followerCount: Int, posts: List<PostVo>): ResUserPageInfo =
            ResUserPageInfo(
                email = user.email,
                nickname = user.nickname,
                profileImgUrl = user.profileImgUrl,
                introduce = user.introduce ?: "",
                postCount = posts.size,
                followingCount = followingCount,
                followerCount = followerCount,
                posts = posts
            )
    }
}

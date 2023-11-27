package kr.ac.changwon.together.auth.vo

import kr.ac.changwon.together.user.entity.User

data class ResUserInfo(
    val email: String,
    val name: String,
    val nickname: String,
    val profileImgUrl: String?,
) {
    companion object {
        fun of(user: User) = with(user) {
            ResUserInfo(
                email = email,
                name = name,
                nickname = nickname,
                profileImgUrl = profileImgUrl,
            )
        }
    }
}

package kr.ac.changwon.together.user.entity

import javax.persistence.*

@Entity
class Follow(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    val following: User
) {
    @Id
    val id: Long? = null
}
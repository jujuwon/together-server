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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    companion object {
        fun create(user: User, following: User): Follow =
            Follow(user, following)
                .also { user.addFollowing(it) }
    }
}
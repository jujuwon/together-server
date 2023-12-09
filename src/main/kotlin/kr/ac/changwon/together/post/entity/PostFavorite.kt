package kr.ac.changwon.together.post.entity

import kr.ac.changwon.together.user.entity.User
import javax.persistence.*

@Entity
class PostFavorite(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
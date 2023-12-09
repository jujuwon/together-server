package kr.ac.changwon.together.post.entity

import kr.ac.changwon.together.user.entity.User
import javax.persistence.*

@Entity
class PostLike(
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

    companion object {
        fun create(post: Post, user: User) =
            PostLike(
                post = post,
                user = user
            ).also { post.addLike(it) }
    }
}
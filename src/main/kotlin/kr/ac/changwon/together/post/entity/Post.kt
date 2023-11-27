package kr.ac.changwon.together.post.entity

import kr.ac.changwon.together.common.entity.BaseTimeEntity
import kr.ac.changwon.together.post.coded.State
import kr.ac.changwon.together.user.entity.User
import javax.persistence.*

@Entity
class Post(
    @Column
    val imgUrl: String,
    @Column
    val content: String,
    user: User
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User = user

    @Column
    @Enumerated(value = EnumType.STRING)
    var state: State = State.COMPLETE
        protected set

    fun delete() {
        state = State.DELETED
    }

    companion object {
        fun create(user: User, imgUrl: String, content: String) =
            Post(
                user = user,
                imgUrl = imgUrl,
                content = content
            ).also { user.posts.add(it) }
    }
}
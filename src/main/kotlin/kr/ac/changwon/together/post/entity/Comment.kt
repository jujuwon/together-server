package kr.ac.changwon.together.post.entity

import kr.ac.changwon.together.common.entity.BaseTimeEntity
import kr.ac.changwon.together.post.coded.State
import kr.ac.changwon.together.user.entity.User
import javax.persistence.*

@Entity
class Comment(
    @Column
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val comment: Comment? = null,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "comment")
    val comments: MutableList<Comment> = mutableListOf()

    @Column
    @Enumerated(value = EnumType.STRING)
    var state: State = State.COMPLETE
        protected set

    fun addComment(comment: Comment) {
        this.comments.add(comment)
    }

    companion object {
        fun create(user: User, post: Post, comment: Comment?, content: String) =
            Comment(
                content = content,
                user = user,
                post = post,
                comment = comment
            ).also {
                post.addComment(comment = it)
                comment?.addComment(comment = it)
            }
    }
}
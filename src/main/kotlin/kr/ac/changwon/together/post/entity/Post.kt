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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column
    @Enumerated(value = EnumType.STRING)
    var state: State = State.COMPLETE
        protected set

    @OneToMany(mappedBy = "post")
    val comments: MutableList<Comment> = mutableListOf()

    @OneToMany(mappedBy = "post")
    val likes: MutableSet<PostLike> = mutableSetOf()

    @OneToMany(mappedBy = "post")
    val favorites: MutableSet<PostFavorite> = mutableSetOf()

    fun delete() {
        state = State.DELETED
    }

    // 연관관계 편의 메소드
    fun addComment(comment: Comment) {
        comments.add(comment)
    }

    fun addLike(like: PostLike) {
        likes.add(like)
    }

    fun addFavorite(favorite: PostFavorite) {
        favorites.add(favorite)
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
package kr.ac.changwon.together.user.entity

import kr.ac.changwon.together.common.entity.BaseTimeEntity
import kr.ac.changwon.together.post.entity.Post
import javax.persistence.*

@Entity
class User(
    @Column
    val email: String,
    @Column
    val pwd: String,
    @Column
    val name: String,
    nickname: String,
    profileImgUrl: String = "base_profile.png"
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column
    var profileImgUrl: String = profileImgUrl
        protected set

    @Column
    var introduce: String? = null
        protected set

    @Column
    var nickname: String = nickname
        protected set

    // TODO 팔로잉/팔로워 연관관계 체크하기
    @OneToMany(mappedBy = "user")
    val following: MutableSet<Follow> = mutableSetOf()

    @OneToMany(mappedBy = "user")
    val posts: MutableList<Post> = mutableListOf()

    fun update(nickname: String, profileImgUrl: String, introduce: String) {
        this.nickname = nickname
        this.profileImgUrl = profileImgUrl
        this.introduce = introduce
    }
}
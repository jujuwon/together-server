package kr.ac.changwon.together.user.entity

import kr.ac.changwon.together.common.entity.BaseTimeEntity
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
    profileImage: String = "base_profile.png"
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column
    var profileImage: String = profileImage
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

    fun update(nickname: String, profileImage: String, introduce: String) {
        this.nickname = nickname
        this.profileImage = profileImage
        this.introduce = introduce
    }
}
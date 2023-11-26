package kr.ac.changwon.together.user.entity

import kr.ac.changwon.together.common.entity.BaseTimeEntity
import org.springframework.data.jpa.domain.support.AuditingEntityListener
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
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column
    var profileImage: String? = null
        private set

    @Column
    var introduce: String? = null
        private set

    @Column
    var nickname: String = nickname
        private set

    fun updateProfileImage(profileImage: String) {
        this.profileImage = profileImage
    }

    fun updateIntroduce(introduce: String) {
        this.introduce = introduce
    }

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }
}
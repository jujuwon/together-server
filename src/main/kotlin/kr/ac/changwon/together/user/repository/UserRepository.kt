package kr.ac.changwon.together.user.repository

import kr.ac.changwon.together.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmailAndPwd(email: String, pwd: String): User?

    fun findByEmailOrNickname(email: String, nickname: String): User?

    fun findByNickname(nickname: String): User?

    fun findByEmail(email: String): User?

    fun findByNicknameContaining(nickname: String): List<User>
}
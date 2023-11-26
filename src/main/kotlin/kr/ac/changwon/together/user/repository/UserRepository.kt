package kr.ac.changwon.together.user.repository

import kr.ac.changwon.together.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmailAndPwd(email: String, pwd: String): User?
}
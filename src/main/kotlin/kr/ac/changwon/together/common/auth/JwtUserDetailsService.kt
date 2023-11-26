package kr.ac.changwon.together.common.auth

import kr.ac.changwon.together.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val repository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails
        = repository.findByIdOrNull(id = username.toLong())
            ?.let { User(it.id.toString(), it.pwd, ArrayList()) }
            ?: throw UsernameNotFoundException("${username}에 해당하는 ID를 가진 사용자가 없습니다.")
}

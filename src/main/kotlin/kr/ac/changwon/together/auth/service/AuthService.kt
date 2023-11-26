package kr.ac.changwon.together.auth.service

import kr.ac.changwon.together.common.auth.JwtTokenUtil
import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.user.entity.User
import kr.ac.changwon.together.user.repository.UserRepository
import kr.ac.changwon.together.auth.vo.ReqSignIn
import kr.ac.changwon.together.auth.vo.ReqSignUp
import kr.ac.changwon.together.auth.vo.ResLoginToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil
) {

    @Transactional
    fun signUp(req: ReqSignUp): ResLoginToken = with(req) {
        val user = userRepository.save(
            User(
                email = email,
                pwd = password,
                name = name,
                nickname = nickname
            )
        )

        return ResLoginToken(token = generateToken(id = user.id))
    }

    fun signIn(req: ReqSignIn): ResLoginToken = with(req) {
        return userRepository.findByEmailAndPwd(email = email, pwd = password)?.let {
            ResLoginToken(token = generateToken(id = it.id))
        } ?: throw CustomException(error = ErrorCode.NOT_FOUND_USER)
    }

    private fun generateToken(id: Long?): String = jwtTokenUtil.generateToken(id = id.toString())
}
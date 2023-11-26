package kr.ac.changwon.together.user.service

import kr.ac.changwon.together.auth.vo.ResUserInfo
import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository
) {
    fun getUserInfo(id: Long): ResUserInfo =
        repository.findByIdOrNull(id)?.let { ResUserInfo.of(it) }
            ?: throw CustomException(ErrorCode.NOT_VALID_USER)
}
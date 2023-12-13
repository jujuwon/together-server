package kr.ac.changwon.together.user.service

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import kr.ac.changwon.together.TogetherApplication
import kr.ac.changwon.together.auth.service.AuthService
import kr.ac.changwon.together.auth.vo.ReqSignUp
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("local")
@SpringBootTest(classes = [TogetherApplication::class])
class UserServiceTest(
    private val authService: AuthService,
    private val userService: UserService
) : ShouldSpec({
    extension(SpringExtension)

    beforeSpec {
        authService.signUp(
            req = ReqSignUp(
                email = "test@gmail.com",
                name = "이주원",
                nickname = "투게더주원",
                password = "test1234!"
            )
        )

        authService.signUp(
            req = ReqSignUp(
                email = "test2@gmail.com",
                name = "차왕준",
                nickname = "투게더왕준",
                password = "test1234!"
            )
        )
    }

    context("사용자 검색") {
        should("유효한 사용자 닉네임 검색 시 해당 닉네임의 사용자 정보를 반환한다") {
            userService.searchUser(userId = 1L, keyword = "왕준").run {
                size shouldBe 1
                first().nickname shouldBe "투게더왕준"
            }
        }

        should("존재하지 않는 사용자 닉네임 검색 시 정보가 표시되지 않는다") {
            userService.searchUser(userId = 1L, keyword = "쥬원").run {
                size shouldBe 0
            }
        }
    }
})
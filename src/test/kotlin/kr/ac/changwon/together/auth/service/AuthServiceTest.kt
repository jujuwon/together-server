package kr.ac.changwon.together.auth.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import kr.ac.changwon.together.TogetherApplication
import kr.ac.changwon.together.auth.vo.ReqSignIn
import kr.ac.changwon.together.auth.vo.ReqSignUp
import kr.ac.changwon.together.common.exception.CustomException
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("local")
@SpringBootTest(classes = [TogetherApplication::class])
@Transactional
class AuthServiceTest(
    private val service: AuthService
) : ShouldSpec({
    extension(SpringExtension)

    beforeSpec {
        service.signUp(
            req = ReqSignUp(
                email = "test@gmail.com",
                name = "이주원",
                nickname = "투게더주원",
                password = "test1234!"
            )
        )
    }

    context("회원가입") {
        should("유효한 회원가입 정보 입력 시 회원가입을 성공한다") {
            service.signUp(
                req = ReqSignUp(
                    email = "juwon@gmail.com",
                    name = "이주원",
                    nickname = "juwon-test",
                    password = "test1234!"
                )
            ).run {
                token.split(".").size shouldBe 3
            }
        }

        should("중복된 이메일 주소 입력 시 예외가 발생한다") {
            val result = {
                service.signUp(
                    req = ReqSignUp(
                        email = "test@gmail.com",
                        name = "이주원2",
                        nickname = "투게더주원2",
                        password = "test1234!"
                    )
                )
            }

            val exception = shouldThrow<CustomException> { result() }
            exception.error.code shouldBe 1003
            exception.error.message shouldBe "이미 존재하는 이메일입니다."
        }
    }

    context("로그인") {
        should("유효한 로그인 정보 입력 시 로그인을 성공한다") {
            service.signIn(
                req = ReqSignIn(email = "test@gmail.com", password = "test1234!")
            ).run {
                token.split(".").size shouldBe 3
            }
        }

        should("잘못된 이메일 주소 입력 시 예외가 발생한다") {
            val result = {
                service.signIn(
                    req = ReqSignIn(email = "nullable@gmail.com", password = "test1234!")
                )
            }
            val exception = shouldThrow<CustomException> { result() }
            exception.error.code shouldBe 1001
            exception.error.message shouldBe "존재하지 않는 사용자입니다."
        }

        should("올바른 이메일 주소와 잘못된 비밀번호 입력 시 예외가 발생한다") {
            val result = {
                service.signIn(
                    req = ReqSignIn(email = "test@gmail.com", password = "nullablePw!!")
                )
            }
            val exception = shouldThrow<CustomException> { result() }
            exception.error.code shouldBe 1002
            exception.error.message shouldBe "비밀번호가 일치하지 않습니다."
        }
    }
})
package kr.ac.changwon.together.user.repository

import kr.ac.changwon.together.user.entity.Follow
import kr.ac.changwon.together.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository : JpaRepository<Follow, Long> {
    // user 가 팔로우한 사람의 수를 반환 (user 의 팔로잉 수) TODO 삭제
    fun countByUser(user: User): Long
    // user 를 팔로우한 사람의 수를 반환 (user 의 팔로워 수)
    fun countByFollowing(following: User): Int
}
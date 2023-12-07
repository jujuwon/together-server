package kr.ac.changwon.together.post.repository

import kr.ac.changwon.together.post.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
}
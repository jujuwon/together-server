package kr.ac.changwon.together.post.repository

import kr.ac.changwon.together.post.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
}
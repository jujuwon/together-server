package kr.ac.changwon.together.post.repository

import kr.ac.changwon.together.post.entity.Post
import kr.ac.changwon.together.post.entity.PostFavorite
import kr.ac.changwon.together.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface PostFavoriteRepository : JpaRepository<PostFavorite, Long> {
    fun findByPostAndUser(post: Post, user: User): PostFavorite?
}
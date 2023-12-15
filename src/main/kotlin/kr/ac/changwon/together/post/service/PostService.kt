package kr.ac.changwon.together.post.service

import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.util.ImageUploader
import kr.ac.changwon.together.post.coded.State
import kr.ac.changwon.together.post.entity.Comment
import kr.ac.changwon.together.post.entity.Post
import kr.ac.changwon.together.post.entity.PostFavorite
import kr.ac.changwon.together.post.entity.PostLike
import kr.ac.changwon.together.post.repository.CommentRepository
import kr.ac.changwon.together.post.repository.PostFavoriteRepository
import kr.ac.changwon.together.post.repository.PostLikeRepository
import kr.ac.changwon.together.post.repository.PostRepository
import kr.ac.changwon.together.post.utils.mapCommentToDto
import kr.ac.changwon.together.post.vo.*
import kr.ac.changwon.together.user.entity.User
import kr.ac.changwon.together.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postFavoriteRepository: PostFavoriteRepository,
    private val imageUploader: ImageUploader,
) {

    @Transactional
    fun post(id: Long, req: ReqCreatePost) = with(req) {
        val user = getUser(userId = id)

        postRepository.save(
            Post.create(
                user = user,
                imgUrl = imgUrl,
                content = content,
            )
        ).id ?: throw CustomException(ErrorCode.POST_SAVE_ERROR)
    }

    fun retrieve(userId: Long): List<ResPost> {
        val user = getUser(userId = userId)

        return user.posts
            .filter {
                it.state == State.COMPLETE
            }.map {
                ResPost.of(it)
            }
    }

    fun find(postId: Long) {
        val post = getPost(postId = postId)

        // TODO 게시글 정보, 댓글 정보 등 상세 조회
    }

    fun uploadImage(id: Long, file: MultipartFile): ResPostImgUrl =
        userRepository.findByIdOrNull(id)
            ?.let {
                ResPostImgUrl(imgUrl = imageUploader.upload(file))
            } ?: throw CustomException(ErrorCode.NOT_FOUND_USER)

    @Transactional
    fun createComment(userId: Long, postId: Long, req: ReqCreateComment): Long? {
        val user = getUser(userId = userId)
        val post = getPost(postId = postId)

        val parentComment = req.parentId?.run {
            commentRepository.findByIdOrNull(this)
        }

        return commentRepository.save(
            Comment.create(
                user = user,
                post = post,
                content = req.content,
                comment = parentComment
            )
        ).id
    }

    @Transactional
    fun like(userId: Long, postId: Long, req: ReqLikePost) {
        val user = getUser(userId = userId)
        val post = getPost(postId = postId)

        if (req.isLike) saveLike(user = user, post = post)
        else deleteLike(user = user, post = post)
    }

    @Transactional
    fun favorite(userId: Long, postId: Long, req: ReqFavoritePost) {
        val user = getUser(userId = userId)
        val post = getPost(postId = postId)

        if (req.isFavorite) saveFavorite(user = user, post = post)
        else deleteFavorite(user = user, post = post)
    }

    private fun saveFavorite(user: User, post: Post) =
        postFavoriteRepository.findByPostAndUser(
            post = post,
            user = user
        ) ?: run {
            postFavoriteRepository.save(
                PostFavorite(
                    post = post,
                    user = user
                )
            )
        }

    private fun deleteFavorite(user: User, post: Post) =
        postFavoriteRepository.findByPostAndUser(
            post = post,
            user = user
        )?.run {
            postFavoriteRepository.delete(this)
        }

    private fun saveLike(user: User, post: Post) =
        postLikeRepository.findByPostAndUser(
            post = post,
            user = user
        ) ?: run {
            postLikeRepository.save(
                PostLike.create(
                    post = post,
                    user = user
                )
            )
        }

    private fun deleteLike(user: User, post: Post) =
        postLikeRepository.findByPostAndUser(
            post = post,
            user = user
        )?.run {
            postLikeRepository.delete(this)
        }

    private fun getUser(userId: Long) =
        userRepository.findByIdOrNull(userId)
            ?: throw CustomException(ErrorCode.NOT_FOUND_USER)

    private fun getPost(postId: Long) =
        postRepository.findByIdOrNull(postId)
            ?: throw CustomException(ErrorCode.NOT_FOUND_POST)

    fun getFollowingPosts(userId: Long): ResUserPost {
        val user = getUser(userId = userId)

        return ResUserPost(posts = user.followings
            .flatMap { it.following.posts }
            .filter { it.state == State.COMPLETE }
            .sortedByDescending { it.createdAt }
            .map {
                UserPostVo.of(
                    post = it,
                    isLike = it.likes.any { like -> like.user == user },
                    isFavorite = it.favorites.any { favorite -> favorite.user == user },
                    comments = it.comments
                        .filter { comment -> comment.comment == null }
                        .map { comment ->  comment.mapCommentToDto() }
                )
            })
    }

    fun getOtherUserPosts(userId: Long, otherUserId: Long): ResUserPost {
        val user = getUser(userId = userId)
        val otherUser = getUser(userId = otherUserId)

        return ResUserPost(posts = otherUser.posts
            .filter { it.state == State.COMPLETE }
            .sortedByDescending { it.createdAt }
            .map {
                UserPostVo.of(
                    post = it,
                    isLike = it.likes.any { like -> like.user == user },
                    isFavorite = it.favorites.any { favorite -> favorite.user == user },
                    comments = it.comments
                        .filter { comment -> comment.comment == null }
                        .map { comment -> comment.mapCommentToDto() }
                )
            })
    }

    fun retrieveDetail(userId: Long, postId: Long): UserPostVo {
        val user = getUser(userId = userId)
        val post = getPost(postId = postId)

        return UserPostVo.of(
            post = post,
            isLike = post.likes.any { like -> like.user == user },
            isFavorite = post.favorites.any { favorite -> favorite.user == user },
            comments = post.comments
                .filter { comment -> comment.comment == null }
                .map { it.mapCommentToDto() }
        )
    }
}
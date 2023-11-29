package kr.ac.changwon.together.post.service

import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.util.ImageUploader
import kr.ac.changwon.together.post.coded.State
import kr.ac.changwon.together.post.entity.Post
import kr.ac.changwon.together.post.repository.PostRepository
import kr.ac.changwon.together.post.vo.ReqCreatePost
import kr.ac.changwon.together.post.vo.ResPost
import kr.ac.changwon.together.post.vo.ResPostImgUrl
import kr.ac.changwon.together.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val imageUploader: ImageUploader
) {

    @Transactional
    fun post(id: Long, req: ReqCreatePost) = with(req) {
        val user = userRepository.findByIdOrNull(id)
            ?: throw CustomException(ErrorCode.NOT_FOUND_USER)

        postRepository.save(
            Post.create(
                user = user,
                imgUrl = imgUrl,
                content = content,
            )
        ).id ?: throw CustomException(ErrorCode.POST_SAVE_ERROR)
    }

    fun retrieve(userId: Long): List<ResPost> {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw CustomException(ErrorCode.NOT_FOUND_USER)

        return user.posts
            .filter {
                it.state == State.COMPLETE
            }.map {
                ResPost.of(it)
            }
    }

    fun find(postId: Long) {
        postRepository.findByIdOrNull(postId)
            ?: throw CustomException(ErrorCode.NOT_FOUND_POST)

        // TODO 게시글 정보, 댓글 정보 등 상세 조회
    }

    fun uploadImage(id: Long, file: MultipartFile): ResPostImgUrl =
        userRepository.findByIdOrNull(id)
            ?.let {
                ResPostImgUrl(imgUrl = imageUploader.upload(file))
            } ?: throw CustomException(ErrorCode.NOT_FOUND_USER)
}
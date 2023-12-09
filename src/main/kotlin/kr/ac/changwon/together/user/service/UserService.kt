package kr.ac.changwon.together.user.service

import kr.ac.changwon.together.auth.vo.ResUserInfo
import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.util.ImageUploader
import kr.ac.changwon.together.post.entity.Comment
import kr.ac.changwon.together.user.repository.FollowRepository
import kr.ac.changwon.together.user.repository.UserRepository
import kr.ac.changwon.together.user.vo.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository,
    private val imageUploader: ImageUploader
) {
    fun getUserInfo(id: Long): ResUserInfo =
        userRepository.findByIdOrNull(id)?.let { ResUserInfo.of(it) }
            ?: throw CustomException(ErrorCode.NOT_VALID_USER)

    fun getUserPageInfo(id: Long): ResUserPageInfo {
        val user = userRepository.findByIdOrNull(id)
            ?: throw CustomException(ErrorCode.NOT_FOUND_USER)

        val followerCount = followRepository.countByFollowing(following = user)

        return ResUserPageInfo.of(
            user = user,
            followingCount = user.following.size,
            followerCount = followerCount,
            posts = user.posts.map {
                PostVo(
                    id = it.id ?: throw CustomException(ErrorCode.INVALID_POST_ID),
                    imgUrl = it.imgUrl
                )
            }
        )
    }

    @Transactional
    fun updateUserInfo(id: Long, req: ReqUpdateUser) = with(req) {
        userRepository.findByIdOrNull(id)
            ?.updateInfo(
                nickname = nickname,
                introduce = introduce
            ) ?: throw CustomException(ErrorCode.NOT_FOUND_USER)
    }

    @Transactional
    fun uploadProfileImage(id: Long, file: MultipartFile): ResProfileImgUrl {
        return userRepository.findByIdOrNull(id)
            ?.let {
                it.updateProfileImgUrl(profileImgUrl = imageUploader.upload(file))
                ResProfileImgUrl(profileImgUrl = it.profileImgUrl)
            } ?: throw CustomException(ErrorCode.NOT_FOUND_USER)
    }

    fun getFavorites(userId: Long): List<ResFavoritePost> =
        getUser(userId = userId).favorites.map {
            ResFavoritePost.of(
                post = it.post,
                comments = it.post.comments.map { comment -> mapCommentToDto(comment) }
            )
        }

    private fun mapCommentToDto(comment: Comment): CommentDto {
        val subComments = comment.comments.map { mapCommentToDto(it) }
        return CommentDto(
            userId = comment.user.id!!,
            nickname = comment.user.nickname,
            commentId = comment.id!!,
            content = comment.content,
            subComments = subComments
        )
    }

    private fun getUser(userId: Long) =
        userRepository.findByIdOrNull(userId)
            ?: throw CustomException(ErrorCode.NOT_FOUND_USER)
}

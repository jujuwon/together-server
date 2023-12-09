package kr.ac.changwon.together.user.service

import kr.ac.changwon.together.auth.vo.ResUserInfo
import kr.ac.changwon.together.common.coded.ErrorCode
import kr.ac.changwon.together.common.exception.CustomException
import kr.ac.changwon.together.common.util.ImageUploader
import kr.ac.changwon.together.post.utils.mapCommentToDto
import kr.ac.changwon.together.user.entity.Follow
import kr.ac.changwon.together.user.entity.User
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

    private fun getUser(userId: Long) =
        userRepository.findByIdOrNull(userId)
            ?: throw CustomException(ErrorCode.NOT_FOUND_USER)

    fun getUserInfo(id: Long): ResUserInfo =
        userRepository.findByIdOrNull(id)?.let { ResUserInfo.of(it) }
            ?: throw CustomException(ErrorCode.NOT_VALID_USER)

    fun getUserPageInfo(id: Long): ResUserPageInfo {
        val user = getUser(userId = id)
        val followerCount = followRepository.countByFollowing(following = user)

        return ResUserPageInfo.of(
            user = user,
            followingCount = user.followings.size,
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
        getUser(userId = userId).favorites.map { favorite ->
            ResFavoritePost.of(
                post = favorite.post,
                comments = favorite.post.comments.map { it.mapCommentToDto() }
            )
        }

    fun getFollowingList(userId: Long): List<ResFriends> =
        getUser(userId = userId).run {
            followings.map {
                ResFriends(
                    userId = it.following.id!!,
                    nickname = it.following.nickname
                )
            }
        }

    fun getFollowerList(userId: Long): List<ResFriends> =
        getUser(userId = userId).run {
            followRepository.findByFollowing(this)
                .map {
                    ResFriends(
                        userId = it.user.id!!,
                        nickname = it.user.nickname
                    )
                }
        }

    @Transactional
    fun follow(userId: Long, followingId: Long) {
        val user = getUser(userId = userId)
        val following = getUser(userId = followingId)

        followRepository.findByUserAndFollowing(
            user = user,
            following = following
        ) ?: followRepository.save(
            Follow.create(
                user = user,
                following = following
            )
        )
    }

    @Transactional
    fun unfollow(userId: Long, followingId: Long) {
        val user = getUser(userId = userId)
        val following = getUser(userId = followingId)

        followRepository.findByUserAndFollowing(
            user = user,
            following = following
        )?.run {
            followRepository.delete(this)
        }
    }

    fun getOtherUserInfo(userId: Long, otherUserId: Long): ResOtherUserInfo {
        val user = getUser(userId = userId)
        val otherUser = getUser(userId = otherUserId)
        val followerCount = followRepository.countByFollowing(following = otherUser)

        return ResOtherUserInfo.of(
            user = otherUser,
            followingCount = otherUser.followings.size,
            followerCount = followerCount,
            posts = otherUser.posts.map {
                PostVo(
                    id = it.id ?: throw CustomException(ErrorCode.INVALID_POST_ID),
                    imgUrl = it.imgUrl
                )
            },
            isFollowing = isFollowed(user = user, following = otherUser)
        )
    }

    private fun isFollowed(user: User, following: User): Boolean =
        followRepository.findByUserAndFollowing(
            user = user,
            following = following
        ) != null

    fun searchUser(userId: Long, keyword: String): List<ResSearchUser> =
        // TODO 캐시 적용
        userRepository.findByNicknameContaining(nickname = keyword)
            .filter { it.id != userId }
            .let { users ->
                users.map {
                    ResSearchUser(
                        userId = it.id!!,
                        nickname = it.nickname,
                    )
                }
            }

    fun searchFollowing(userId: Long, keyword: String): List<ResSearchUser> =
        // TODO 캐시 적용
        getUser(userId = userId).followings
            .filter { it.following.nickname.contains(keyword) }
            .map {
                ResSearchUser(
                    userId = it.following.id!!,
                    nickname = it.following.nickname
                )
            }

    fun searchFollower(userId: Long, keyword: String): List<ResSearchUser> =
        // TODO 캐시 적용
        followRepository.findByFollowing(
            following = getUser(userId = userId)
        ).filter { it.user.nickname.contains(keyword) }
            .map {
                ResSearchUser(
                    userId = it.user.id!!,
                    nickname = it.user.nickname
                )
            }
}

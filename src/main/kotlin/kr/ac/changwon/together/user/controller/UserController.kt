package kr.ac.changwon.together.user.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.changwon.together.common.vo.RestApiResponse
import kr.ac.changwon.together.user.service.UserService
import kr.ac.changwon.together.user.vo.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@Api(tags = ["회원 API"])
@RestController
@RequestMapping("/api/user")
class UserController(
    private val service: UserService
) {
    @ApiOperation(value = "사용자 화면 - me (완료)", notes = "로그인한 사용자의 정보를 반환합니다.")
    @GetMapping("/info")
    fun get(@AuthenticationPrincipal user: User): RestApiResponse<ResUserPageInfo> =
        RestApiResponse.success(service.getUserPageInfo(id = user.username.toLong()))

    @ApiOperation(value = "사용자 화면 - 프로필 편집 (완료)", notes = "로그인한 사용자의 정보를 수정합니다.")
    @PatchMapping("/info")
    fun update(@AuthenticationPrincipal user: User, @Valid @RequestBody req: ReqUpdateUser): RestApiResponse<Unit> =
        RestApiResponse.success(service.updateUserInfo(id = user.username.toLong(), req = req))

    @ApiOperation(value = "사용자 화면 - 프로필 이미지 업로드 (완료)", notes = "로그인한 사용자의 프로필 사진을 업로드합니다.")
    @PostMapping("/info/profile-image")
    fun uploadProfileImage(@AuthenticationPrincipal user: User, @RequestPart file: MultipartFile) =
        RestApiResponse.success(service.uploadProfileImage(id = user.username.toLong(), file = file))

    @ApiOperation(value = "사용자 화면 - 즐겨찾기 (완료)", notes = "로그인한 사용자가 즐겨찾기한 게시글들을 반환합니다.")
    @GetMapping("/favorites")
    fun getFavorites(@AuthenticationPrincipal user: User): RestApiResponse<List<ResFavoritePost>> =
        RestApiResponse.success(service.getFavorites(userId = user.username.toLong()))

    @ApiOperation(value = "팔로잉 목록 (완료)", notes = "로그인한 사용자가 팔로잉한 사용자들의 목록을 반환합니다.")
    @GetMapping("/following")
    fun getFollowing(@AuthenticationPrincipal user: User): RestApiResponse<List<ResFriends>> =
        RestApiResponse.success(service.getFollowingList(userId = user.username.toLong()))

    @ApiOperation(value = "팔로잉 검색 (완료)", notes = "로그인한 사용자가 팔로잉한 사용자들 중 검색한 키워드가 포함되는 닉네임을 가진 사용자들의 목록을 반환합니다.")
    @GetMapping("/following/search/{keyword}")
    fun searchFollowing(
        @AuthenticationPrincipal user: User,
        @PathVariable keyword: String
    ): RestApiResponse<List<ResSearchUser>> =
        RestApiResponse.success(service.searchFollowing(userId = user.username.toLong(), keyword = keyword))

    @ApiOperation(value = "팔로워 목록 (완료)", notes = "로그인한 사용자를 팔로우한 사용자들의 목록을 반환합니다.")
    @GetMapping("/follower")
    fun getFollower(@AuthenticationPrincipal user: User): RestApiResponse<List<ResFriends>> =
        RestApiResponse.success(service.getFollowerList(userId = user.username.toLong()))

    @ApiOperation(value = "팔로워 검색 (완료)", notes = "로그인한 사용자를 팔로우한 사용자들 중 검색한 키워드가 포함되는 닉네임을 가진 사용자들의 목록을 반환합니다.")
    @GetMapping("/follower/search/{keyword}")
    fun searchFollower(
        @AuthenticationPrincipal user: User,
        @PathVariable keyword: String
    ): RestApiResponse<List<ResSearchUser>> =
        RestApiResponse.success(service.searchFollower(userId = user.username.toLong(), keyword = keyword))

    @ApiOperation(value = "팔로우 (완료)", notes = "로그인한 사용자가 특정 사용자를 팔로우합니다.")
    @PostMapping("/follow/{userId}")
    fun follow(@AuthenticationPrincipal user: User, @PathVariable userId: Long) =
        RestApiResponse.success(service.follow(userId = user.username.toLong(), followingId = userId))

    @ApiOperation(value = "언팔로우 (완료)", notes = "로그인한 사용자가 특정 사용자를 언팔로우합니다.")
    @DeleteMapping("/follow/{userId}")
    fun unfollow(@AuthenticationPrincipal user: User, @PathVariable userId: Long) =
        RestApiResponse.success(service.unfollow(userId = user.username.toLong(), followingId = userId))

    @ApiOperation(value = "홈화면 - 사용자검색 (완료)", notes = "검색한 키워드가 포함되는 닉네임을 가진 사용자를 검색합니다.")
    @GetMapping("/search/{keyword}")
    fun searchUser(
        @AuthenticationPrincipal user: User,
        @PathVariable keyword: String
    ): RestApiResponse<List<ResSearchUser>> =
        RestApiResponse.success(service.searchUser(userId = user.username.toLong(), keyword = keyword))

    @ApiOperation(value = "사용자 조회 (완료)", notes = "사용자의 정보를 반환합니다.")
    @GetMapping("/{userId}")
    fun getOtherUser(
        @AuthenticationPrincipal user: User,
        @PathVariable userId: Long
    ): RestApiResponse<ResOtherUserInfo> =
        RestApiResponse.success(service.getOtherUserInfo(userId = user.username.toLong(), otherUserId = userId))
}
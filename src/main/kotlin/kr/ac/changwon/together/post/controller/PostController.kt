package kr.ac.changwon.together.post.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.changwon.together.common.vo.RestApiResponse
import kr.ac.changwon.together.post.service.PostService
import kr.ac.changwon.together.post.vo.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@Api(tags = ["게시글 API"])
@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    @ApiOperation(value = "게시글 이미지 업로드 (완료)", notes = "게시글에 들어갈 이미지를 업로드합니다.")
    @PostMapping("/image")
    fun uploadImage(
        @AuthenticationPrincipal user: User,
        @RequestPart file: MultipartFile
    ): RestApiResponse<ResPostImgUrl> =
        RestApiResponse.success(postService.uploadImage(id = user.username.toLong(), file = file))

    @ApiOperation(value = "게시글 작성 (완료)", notes = "게시글을 작성합니다.")
    @PostMapping
    fun post(@AuthenticationPrincipal user: User, @Valid @RequestBody req: ReqCreatePost): RestApiResponse<Long> =
        RestApiResponse.success(postService.post(id = user.username.toLong(), req = req))

    @ApiOperation(value = "게시글 목록 조회 (명세에는 존재하지 않지만 예시로 작성)", notes = "특정 사용자의 게시글 목록을 조회합니다.")
    @GetMapping
    fun findList(userId: Long) =
        RestApiResponse.success(postService.retrieve(userId = userId))

    @ApiOperation(value = "홈 화면 - 팔로잉 게시글 조회 (완료)", notes = "사용자가 팔로잉한 사용자들의 게시글 목록을 반환합니다.")
    @GetMapping("/following")
    fun getFollowingPosts(@AuthenticationPrincipal user: User): RestApiResponse<ResUserPost> =
        RestApiResponse.success(postService.getFollowingPosts(userId = user.username.toLong()))

    @ApiOperation(value = "특정 사용자 게시글 조회 (완료)", notes = "해당 사용자의 게시글 목록을 반환합니다.")
    @GetMapping("/user/{userId}")
    fun getOtherUserPosts(@AuthenticationPrincipal user: User, @PathVariable userId: Long): RestApiResponse<ResUserPost> =
        RestApiResponse.success(postService.getOtherUserPosts(userId = user.username.toLong(), otherUserId = userId))

    @ApiOperation(value = "댓글 작성 (완료)", notes = "댓글을 작성합니다.")
    @PostMapping("/{postId}/comment")
    fun comment(
        @AuthenticationPrincipal user: User,
        @PathVariable postId: Long,
        @Valid @RequestBody req: ReqCreateComment
    ) =
        RestApiResponse.success(postService.createComment(userId = user.username.toLong(), postId = postId, req = req))

    @ApiOperation(value = "게시글 즐겨찾기 (완료)", notes = "특정 게시글을 즐겨찾기 합니다.")
    @PostMapping("/{postId}/favorite")
    fun favorite(
        @AuthenticationPrincipal user: User,
        @PathVariable postId: Long,
        @Valid @RequestBody req: ReqFavoritePost
    ) =
        RestApiResponse.success(postService.favorite(userId = user.username.toLong(), postId = postId, req = req))

    @ApiOperation(value = "게시글 좋아요 (완료)", notes = "특정 게시글의 좋아요/좋아요 취소 버튼을 클릭합니다.")
    @PostMapping("/{postId}/like")
    fun like(@PathVariable postId: Long, @AuthenticationPrincipal user: User, @Valid @RequestBody req: ReqLikePost) =
        RestApiResponse.success(postService.like(userId = user.username.toLong(), postId = postId, req = req))
}
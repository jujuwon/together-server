package kr.ac.changwon.together.post.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.ac.changwon.together.common.vo.RestApiResponse
import kr.ac.changwon.together.post.service.PostService
import kr.ac.changwon.together.post.vo.ReqCreatePost
import kr.ac.changwon.together.post.vo.ResPostImgUrl
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

    @ApiOperation(value = "게시글 이미지 업로드", notes = "게시글에 들어갈 이미지를 업로드합니다.")
    @PostMapping("/image")
    fun uploadImage(
        @AuthenticationPrincipal user: User,
        @RequestPart file: MultipartFile
    ): RestApiResponse<ResPostImgUrl> =
        RestApiResponse.success(postService.uploadImage(id = user.username.toLong(), file = file))

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다.")
    @PostMapping
    fun post(@AuthenticationPrincipal user: User, @Valid @RequestBody req: ReqCreatePost): RestApiResponse<Long> =
        RestApiResponse.success(postService.post(id = user.username.toLong(), req = req))

    @ApiOperation(value = "게시글 목록 조회", notes = "특정 사용자의 게시글 목록을 조회합니다.")
    @GetMapping
    fun findList(userId: Long) =
        RestApiResponse.success(postService.retrieve(userId = userId))

    @ApiOperation(value = "게시글 상세 조회", notes = "특정 게시글의 상세 내용을 조회합니다.")
    @GetMapping
    fun get() {
        // TODO
    }

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성합니다.")
    @PostMapping
    fun comment() {
        // TODO
    }
}
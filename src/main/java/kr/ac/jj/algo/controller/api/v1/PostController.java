package kr.ac.jj.algo.controller.api.v1;

import javassist.NotFoundException;
import kr.ac.jj.algo.domain.Likes;
import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.LikesDTO;
import kr.ac.jj.algo.dto.PostDTO;
import kr.ac.jj.algo.dto.ReplyDTO;
import kr.ac.jj.algo.dto.ResultDTO;
import kr.ac.jj.algo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostDTO writePost(@AuthenticationPrincipal User user, @RequestBody PostDTO.Creation request) {
        return postService.createPost(user, request);
    }

    @GetMapping
    public Page<PostDTO> getPostsByPage(Pageable pageable) {
        return postService.loadPosts(pageable);
    }

    @GetMapping(value = "/{id}")
    public PostDTO.Detail getPostById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id) {
        return postService.loadPostById(id);
    }

    @PatchMapping(value = "/{id}")
    public PostDTO.Detail patchPostById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id, PostDTO.Patch request) {
        return postService.patchPostById(user, id, request);
    }

    @DeleteMapping(value ="/{id}")
    public ResultDTO deletePostById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id) {
        postService.deletePostById(user, id);
        return new ResultDTO("게시물을 정상적으로 삭제하였습니다.");
    }

    @PostMapping(value = "/{id}/like")
    public LikesDTO likePost(@AuthenticationPrincipal User user, @PathVariable("id") Integer id) {
        return postService.likePostById(id, user);
    }

    @GetMapping(value = "/{id}/reply")
    public Page<ReplyDTO> loadRepliesByPostId(@PathVariable("id") Integer id, Pageable pageable) {
        return postService.loadRepliesByPostId(id, pageable);
    }
}

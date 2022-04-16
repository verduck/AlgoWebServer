package kr.ac.jj.algo.controller.api.v1;

import javassist.NotFoundException;
import kr.ac.jj.algo.domain.Likes;
import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.LikesDTO;
import kr.ac.jj.algo.dto.PostDTO;
import kr.ac.jj.algo.dto.ReplyDTO;
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
    private final ModelMapper modelMapper;
    private final PostService postService;

    @Autowired
    public PostController(final ModelMapper modelMapper, final PostService postService) {
        this.modelMapper = modelMapper;
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO.Response> writePost(@AuthenticationPrincipal User user, @RequestBody PostDTO.Request request) {
        PostDTO.Response response = new PostDTO.Response();
        Post post = modelMapper.map(request, Post.class);
        post.setUser(user);
        post = postService.createPost(post);
        response.setSuccess(true);
        response.setMessage("게시물을 성공적으로 작성하였습니다.");
        response.setPost(modelMapper.map(post, PostDTO.class));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PostDTO>> getPostsByPage(@AuthenticationPrincipal User user, Pageable pageable){
        Page<PostDTO> response = postService.loadPosts(pageable)
                .map(p -> modelMapper.map(p, PostDTO.class));
        response.forEach(p -> p.setNumLikes(postService.loadLikesCountByPostId(p.getId())));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO.Response> getPostById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id) {
        PostDTO.Response response = new PostDTO.Response();
        Post post;
        try {
            post = postService.loadPostById(id);
        } catch (NotFoundException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        postDTO.setNumLikes(postService.loadLikesCountByPostId(id));
        response.setSuccess(true);
        response.setMessage("게시물을 성공적으로 불러왔습니다.");
        response.setPost(postDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<PostDTO.Response> putPostById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id, @RequestBody PostDTO.Request request) {
        PostDTO.Response response = new PostDTO.Response();
        Post post;
        try {
            post = postService.loadPostById(id);
        } catch (NotFoundException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
        if (user.getId().equals(post.getUser().getId())) {
            response.setSuccess(false);
            response.setMessage("변경 권한이 없습니다.");
            response.setPost(modelMapper.map(post, PostDTO.class));
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post = postService.updatePost(post);
        response.setSuccess(true);
        response.setMessage("게시물을 성공적으로 수정하였습니다.");
        response.setPost(modelMapper.map(post, PostDTO.class));
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<PostDTO.Response> patchPostById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id, @RequestBody PostDTO.Request request) {
        PostDTO.Response response = new PostDTO.Response();
        Post post;
        try {
            post = postService.loadPostById(id);
        } catch (NotFoundException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
        if (user.getId().equals(post.getUser().getId())) {
            response.setSuccess(false);
            response.setMessage("변경 권한이 없습니다.");
            response.setPost(modelMapper.map(post, PostDTO.class));
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        post = postService.updatePost(post);
        response.setSuccess(true);
        response.setMessage("게시물을 성공적으로 수정하였습니다.");
        response.setPost(modelMapper.map(post, PostDTO.class));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<PostDTO.Response> deletePostById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id) {
        PostDTO.Response response = new PostDTO.Response();
        Post post;
        try {
            post = postService.loadPostById(id);
        } catch (NotFoundException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
        if (user.getId().equals(post.getUser().getId())) {
            response.setSuccess(false);
            response.setMessage("삭제 권한이 없습니다.");
            response.setPost(modelMapper.map(post, PostDTO.class));
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        postService.deletePost(post);
        response.setSuccess(true);
        response.setMessage("게시물을 성공적으로 삭제하였습니다.");
        response.setPost(modelMapper.map(post, PostDTO.class));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/{id}/like")
    public ResponseEntity<LikesDTO.Response> likePost(@AuthenticationPrincipal User user, @PathVariable("id") Integer id) {
        LikesDTO.Response response = new LikesDTO.Response();
        try {
            Likes likes = postService.likePostById(id, user);
            response.setSuccess(true);
            response.setMessage("게시물을 좋아합니다.");
            response.setLikes(modelMapper.map(likes, LikesDTO.class));
        } catch (NotFoundException e) {
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}/reply")
    public ResponseEntity<Page<ReplyDTO>> loadRepliesByPostId(@PathVariable("id") Integer id, Pageable pageable) throws NotFoundException {
        Page<ReplyDTO> response = postService.loadRepliesByPostId(id, pageable).map(r -> modelMapper.map(r, ReplyDTO.class));
        return ResponseEntity.ok(response);
    }
}

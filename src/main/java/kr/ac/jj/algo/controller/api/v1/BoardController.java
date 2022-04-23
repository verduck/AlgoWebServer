package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.domain.Board;
import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.dto.BoardDTO;
import kr.ac.jj.algo.dto.PostDTO;
import kr.ac.jj.algo.dto.PostsDTO;
import kr.ac.jj.algo.service.BoardService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    private final ModelMapper modelMapper;
    private final BoardService boardService;

    @Autowired
    public BoardController(ModelMapper modelMapper, final BoardService boardService) {
        this.modelMapper = modelMapper;
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoard() {
        return ResponseEntity.ok(boardService.loadAll());
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<BoardDTO.Response> getBoardById(@PathVariable String name) {
        BoardDTO.Response response = new BoardDTO.Response();
        Board board;
        try {
            board = boardService.loadBoardByName(name);
            response.setSuccess(true);
            response.setMessage("게시판 정보를 성공적으로 불러왔습니다.");
            response.setBoard(modelMapper.map(board, BoardDTO.class));
        } catch (NotFoundException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{name}/posts")
    public ResponseEntity<PostsDTO> getPostsByBoard(@PathVariable String name, @RequestParam Pageable pageable) {
        PostsDTO response = new PostsDTO();
        Board board;
        try {
            board = boardService.loadBoardByName(name);
        } catch (NotFoundException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Page<Post> posts = boardService.loadPostsByBoard(board, pageable);
        response.setSuccess(true);
        response.setMessage("게시판의 게시물들을 성공적으로 불러왔습니다.");
        response.setPosts(posts.map(p -> modelMapper.map(p, PostDTO.class)));
        return ResponseEntity.ok(response);
    }
}

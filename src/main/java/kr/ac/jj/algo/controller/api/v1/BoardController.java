package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.dto.BoardDTO;
import kr.ac.jj.algo.dto.PostDTO;
import kr.ac.jj.algo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(final BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<BoardDTO> getAllBoard() {
        return boardService.loadAll();
    }

    @GetMapping(value = "/{name}")
    public BoardDTO getBoardByName(@PathVariable String name) {
        return boardService.loadBoardByName(name);
    }

    @GetMapping(value = "/{name}/posts")
    public Page<PostDTO> getPostsByName(@PathVariable String name, @RequestParam Pageable pageable) {
        return boardService.loadPostsByName(name, pageable);
    }
}

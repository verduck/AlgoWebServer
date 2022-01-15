package com.algo.algoweb.service;

import com.algo.algoweb.domain.Board;
import com.algo.algoweb.domain.Post;
import com.algo.algoweb.repository.BoardRepository;
import com.algo.algoweb.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @Autowired
    public BoardService(final BoardRepository boardRepository, PostRepository postRepository) {
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
    }

    public List<Board> loadAll() {
        return boardRepository.findAll();
    }

    public Board loadBoardById(Long id) throws NotFoundException {
        return boardRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 게시판을 찾을 수 없습니다. id: " + id));
    }

    public Page<Post> loadPostsByBoard(Board board, Pageable pageable) {
        return postRepository.findByBoard(board, pageable);
    }
}

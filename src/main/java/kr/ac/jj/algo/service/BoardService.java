package kr.ac.jj.algo.service;

import kr.ac.jj.algo.dto.BoardDTO;
import kr.ac.jj.algo.dto.PostDTO;
import kr.ac.jj.algo.exception.ApiException;
import kr.ac.jj.algo.exception.ErrorCode;
import kr.ac.jj.algo.repository.BoardRepository;
import kr.ac.jj.algo.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @Autowired
    public BoardService(ModelMapper modelMapper, final BoardRepository boardRepository, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
    }

    public List<BoardDTO> loadAll() {
        return boardRepository.findAll().stream().map(b -> modelMapper.map(b, BoardDTO.class)).collect(Collectors.toList());
    }

    public BoardDTO loadBoardByName(String name) {
        var board = boardRepository.findByName(name).orElseThrow(() -> new ApiException(ErrorCode.BOARD_NOT_FOUND));
        return modelMapper.map(board, BoardDTO.class);
    }

    public Page<PostDTO> loadPostsByName(String name, Pageable pageable) {
        var board = boardRepository.findByName(name).orElseThrow(() -> new ApiException(ErrorCode.BOARD_NOT_FOUND));
        return postRepository.findByBoard(board, pageable).map(p -> modelMapper.map(p, PostDTO.class));
    }
}

package kr.ac.jj.algo.service;

import kr.ac.jj.algo.domain.Reply;
import kr.ac.jj.algo.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public Reply createReply(Reply reply) {
        reply.setCreatedAt(LocalDateTime.now());
        return replyRepository.save(reply);
    }

    public Reply updateReply(Reply reply) {
        reply.setUpdatedAt(LocalDateTime.now());
        return replyRepository.save(reply);
    }
}

package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.domain.Reply;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.ReplyDTO;
import kr.ac.jj.algo.service.ReplyService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {
    private final ModelMapper modelMapper;
    private final ReplyService replyService;

    public ReplyController(ModelMapper modelMapper, ReplyService replyService) {
        this.modelMapper = modelMapper;
        this.replyService = replyService;
    }

    @PostMapping
    public ResponseEntity<ReplyDTO.Response> writeReply(@AuthenticationPrincipal User user, ReplyDTO.Request request) {
        ReplyDTO.Response response = new ReplyDTO.Response();
        Reply reply = new Reply();
        reply.setContent(request.getContent());
        reply.setPost(modelMapper.map(request.getPost(), Post.class));
        reply.setUser(user);
        if (request.getReply() != null) {
            reply.setReply(modelMapper.map(request.getReply(), Reply.class));
        }
        reply = replyService.createReply(reply);
        response.setSuccess(true);
        response.setMessage("댓글을 성공적으로 작성하였습니다.");
        response.setReply(modelMapper.map(reply, ReplyDTO.class));
        return ResponseEntity.ok(response);
    }
}

package ro.utcn.sd.agui.a1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.agui.a1.dto.AnswerDTO;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.service.AnswerManagementService;
import ro.utcn.sd.agui.a1.service.AnswerVoteManagementService;
import ro.utcn.sd.agui.a1.service.UserDetailsManagementService;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswersController {
    private final AnswerManagementService answerManagementService;
    private final UserDetailsManagementService userDetailsManagementService;
    private final AnswerVoteManagementService answerVoteManagementService;

    @GetMapping("/question/{questionId}/answers")
    public List<AnswerDTO> listAllAnswersPerQuestion(@PathVariable("questionId") int questionId) {
        return answerManagementService.listAllAnswersPerQuestion(questionId);
    }

    @GetMapping("/question/{questionId}/answers-by-score")
    public List<AnswerDTO> listAllAnswersPerQuestionByScore(@PathVariable("questionId") int questionId) {
        return answerManagementService.listAllAnswersPerQuestionByScore(questionId);
    }

    @PostMapping("/question/{questionId}/answers")
    public AnswerDTO createAnswerForQuestion(@PathVariable("questionId") int questionId, @RequestBody AnswerDTO bodyContent) {
        User user = userDetailsManagementService.loadCurrentUser();
        return answerManagementService.addAnswer(questionId, user.getUserId(), bodyContent.getText());
    }

    @PutMapping("/question/{questionId}/answers/{answerId}")
    public AnswerDTO updateAnswerForQuestion(@PathVariable("answerId") int answerId, @RequestBody AnswerDTO bodyContent) {
        User user = userDetailsManagementService.loadCurrentUser();
        return answerManagementService.updateAnswer(answerId, user.getUserId(), bodyContent.getText());
    }

    @DeleteMapping("/question/{questionId}/answers/{answerId}")
    public void deleteAnswerForQuestion(@PathVariable("answerId") int answerId) {
        User user = userDetailsManagementService.loadCurrentUser();
        answerManagementService.deleteAnswer(answerId, user.getUserId());
    }

    @PostMapping("/question/{questionId}/answers/{answerId}/vote")
    public AnswerDTO voteAnswer(@PathVariable("answerId") Integer answerId,
                                               @RequestParam("isUpVote") boolean isUpVote) {
        User user = userDetailsManagementService.loadCurrentUser();
        answerVoteManagementService.addAnswerVote(answerId, user.getUserId(), isUpVote);
        return answerManagementService.findAnswerById(answerId);
    }


}

package ro.utcn.sd.agui.a1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.agui.a1.dto.QuestionDTO;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.service.QuestionManagementService;
import ro.utcn.sd.agui.a1.service.QuestionVoteManagementService;
import ro.utcn.sd.agui.a1.service.UserDetailsManagementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionsController {
    private final QuestionManagementService questionManagementService;
    private final QuestionVoteManagementService questionVoteManagementService;
    private final UserDetailsManagementService userDetailsManagementService;

    @GetMapping("/questions")
    public List<QuestionDTO> readAll() {
        return questionManagementService.listAllQuestions();
    }

    @GetMapping("/questions/title/{filterTitle}")
    public List<QuestionDTO> readQuestionByTitle(@PathVariable("filterTitle") String title) {
        return questionManagementService.filterQuestionsByTitle(title);
    }

    @GetMapping("/questions/tag/{tagTitle}")
    public List<QuestionDTO> readQuestionsByTag(@PathVariable("tagTitle") String tagTitle) {
        return questionManagementService.filterQuestionsByTag(tagTitle);
    }

    @PostMapping("/questions")
    public QuestionDTO createAnswerForQuestion(@RequestBody QuestionDTO bodyContent) {
        User user = userDetailsManagementService.loadCurrentUser();
        return questionManagementService.addQuestion(user.getUserId(), bodyContent.getTitle(),
                bodyContent.getText(), bodyContent.getTags());
    }

    @PostMapping("/questions/{questionId}/vote")
    public QuestionDTO voteQuestion(@PathVariable("questionId") Integer questionId,
                                               @RequestParam("isUpVote") boolean isUpVote) {
        User user = userDetailsManagementService.loadCurrentUser();
        questionVoteManagementService.addQuestionVote(questionId, user.getUserId(), isUpVote);
        return questionManagementService.findQuestionById(questionId);
    }
}

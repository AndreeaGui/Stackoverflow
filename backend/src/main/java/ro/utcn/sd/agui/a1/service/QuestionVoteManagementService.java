package ro.utcn.sd.agui.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.QuestionVote;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.persistence.QuestionRepository;
import ro.utcn.sd.agui.a1.persistence.QuestionVoteRepository;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;
import ro.utcn.sd.agui.a1.persistence.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionVoteManagementService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<QuestionVote> listAllQuestionVotes() {
        return repositoryFactory.createQuestionVoteRepository().findAll();
    }

    @Transactional
    public int addQuestionVote(int questionId, int userId, Boolean type) {

        QuestionVoteRepository questionVoteRepository = repositoryFactory.createQuestionVoteRepository();
        QuestionRepository questionRepository = repositoryFactory.createQuestionRepository();
        UserRepository userRepository = repositoryFactory.createUserRepository();

        Optional<Question> foundQuestion = questionRepository.findById(questionId);
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundQuestion.isPresent()) {
            if (userId != foundQuestion.get().getUserId()) {
                Optional<QuestionVote> foundVote = questionVoteRepository.findByUserAndQuestion(foundUser.get(), foundQuestion.get());
                if(foundVote.isPresent()){
                    return -3; //the vote is already present
                }
                else{
                    QuestionVote newQuestionVote = new QuestionVote(null, questionId, userId, type);
                    return questionVoteRepository.save(newQuestionVote).getVoteId();
                }
            } else {
                return -2; //cannot vote own question
            }
        } else {
            return -1; //no question
        }
    }

    @Transactional
    public int deleteQuestionVote(int voteId, int userId) {

        QuestionVoteRepository questionVoteRepository = repositoryFactory.createQuestionVoteRepository();
        Optional<QuestionVote> foundVote =questionVoteRepository.findById(voteId);

        if (foundVote.isPresent()) {
            if (foundVote.get().getUserId() == userId) {
                questionVoteRepository.remove(foundVote.get());
                return voteId;
            } else {
                return -2; //the vote is not of the current user
            }
        } else {
            return -1; //vote not found
        }
    }

    @Transactional
    public int updateQuestionVote(int voteId, int userId, Boolean type) {

        QuestionVoteRepository questionVoteRepository = repositoryFactory.createQuestionVoteRepository();
        Optional<QuestionVote> foundVote =questionVoteRepository.findById(voteId);

        if (foundVote.isPresent()) {
            if (foundVote.get().getUserId() == userId) {
                QuestionVote updatedVote = foundVote.get();
                updatedVote.setType(type);
                questionVoteRepository.save(updatedVote);
                return voteId;
            } else {
                return -2; //the vote is not of the current user
            }
        } else {
            return -1; //answer not found
        }
    }

}

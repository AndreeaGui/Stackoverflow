package ro.utcn.sd.agui.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.agui.a1.dto.AnswerDTO;
import ro.utcn.sd.agui.a1.entity.*;
import ro.utcn.sd.agui.a1.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerVoteManagementService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<AnswerVote> listAllAnswerVotes() {
        return repositoryFactory.createAnswerVoteRepository().findAll();
    }

    @Transactional
    public int addAnswerVote(int answerId, int userId, Boolean type) {

        AnswerVoteRepository answerVoteRepository = repositoryFactory.createAnswerVoteRepository();
        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();
        UserRepository userRepository = repositoryFactory.createUserRepository();

        Optional<Answer> foundAnswer = answerRepository.findById(answerId);
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundAnswer.isPresent()) {
            if (userId != foundAnswer.get().getUserId()) {
                //Optional<AnswerVote> foundVote = answerVoteRepository.findByUserAndQuestion(foundUser.get(), foundQuestion.get());
                //if(foundVote.isPresent()){
                //    return -3; //the vote is already present
                //}
                //else{
                AnswerVote newAnswerVote = new AnswerVote(null, answerId, userId, type);
                return answerVoteRepository.save(newAnswerVote).getVoteId();
                // }
            } else {
                return -2; //cannot vote own question
            }
        } else {
            return -1; //no question
        }
    }

    @Transactional
    public int deleteAnswerVote(int voteId, int userId) {

        AnswerVoteRepository answerVoteRepository = repositoryFactory.createAnswerVoteRepository();
        Optional<AnswerVote> foundVote = answerVoteRepository.findById(voteId);

        if (foundVote.isPresent()) {
            if (foundVote.get().getUserId() == userId) {
                answerVoteRepository.remove(foundVote.get());
                return voteId;
            } else {
                return -2; //the vote is not of the current user
            }
        } else {
            return -1; //vote not found
        }
    }

    @Transactional
    public int updateAnswerVote(int voteId, int userId, Boolean type) {

        AnswerVoteRepository answerVoteRepository = repositoryFactory.createAnswerVoteRepository();
        Optional<AnswerVote> foundVote = answerVoteRepository.findById(voteId);

        if (foundVote.isPresent()) {
            if (foundVote.get().getUserId() == userId) {
                AnswerVote updatedVote = foundVote.get();
                updatedVote.setType(type);
                answerVoteRepository.save(updatedVote);
                return voteId;
            } else {
                return -2; //the vote is not of the current user
            }
        } else {
            return -1; //answer not found
        }
    }

    /*public List<AnswerDTO> listAnswersByScore(int questionId){

        AnswerVoteRepository answerVoteRepository = repositoryFactory.createAnswerVoteRepository();
        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();
        QuestionRepository questionRepository = repositoryFactory.createQuestionRepository();

        List<AnswerDTO> answerDTOList = new ArrayList<AnswerDTO>();
        Map<Answer, Integer[]> unsorted = new HashMap<>();
        Optional<Question> foundQuestion = questionRepository.findById(questionId);
        if(!foundQuestion.isPresent()){
            return null;
        }
        List<Answer> foundAnswers = answerRepository.findAllByQuestion(foundQuestion.get());
        for(Answer iterator: foundAnswers){
            List<AnswerVote> votes = answerVoteRepository.findAllPerAnswer(iterator);
            int score = 0;
            int upVotes = 0;
            int downVotes= 0;
            for(AnswerVote voteIterator: votes){
                if(voteIterator.getType()==true){
                    upVotes +=1;
                }
                else{
                    downVotes+=1;
                }
            }
            unsorted.put(iterator, [upVotes, downVotes]);
        }

        Map<Answer, Integer[]> sorted = unsorted.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        sorted.keySet().forEach(a->{
                    answerDTOList.add(AnswerDTO.ofEntity(a, upVotes, downVotes));
                }
        );

        return answerDTOList;
    }*/

}

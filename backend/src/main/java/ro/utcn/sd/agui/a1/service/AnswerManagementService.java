package ro.utcn.sd.agui.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.agui.a1.dto.AnswerDTO;
import ro.utcn.sd.agui.a1.dto.QuestionDTO;
import ro.utcn.sd.agui.a1.entity.Answer;
import ro.utcn.sd.agui.a1.entity.AnswerVote;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.exception.AnswerNotFoundException;
import ro.utcn.sd.agui.a1.exception.InvalidPermissionException;
import ro.utcn.sd.agui.a1.exception.MyRuntimeException;
import ro.utcn.sd.agui.a1.exception.QuestionNotFoundException;
import ro.utcn.sd.agui.a1.persistence.AnswerRepository;
import ro.utcn.sd.agui.a1.persistence.QuestionRepository;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerManagementService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<Answer> listAllAnswers() {
        return repositoryFactory.createAnswerRepository().findAll();
    }

    @Transactional
    public AnswerDTO findAnswerById(Integer answerId){
        Answer answer = repositoryFactory.createAnswerRepository().findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);
        List<Integer> votes = getAnswerVotes(answer.getQuestionId());
        return AnswerDTO.ofEntity(answer, votes.get(0), votes.get(1));

    }

    @Transactional
    public AnswerDTO addAnswer(int questionId, int userId, String text) {

        QuestionRepository questionRepository = repositoryFactory.createQuestionRepository();
        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();


        questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        Answer answer = new Answer(null, questionId, userId, text, dateTime);
        answer = answerRepository.save(answer);
        return AnswerDTO.ofEntity(answer, 0, 0);

    }

    @Transactional
    public void deleteAnswer(int answerId, int userId) {

        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();
        Answer foundAnswer = answerRepository.findById(answerId).orElseThrow(AnswerNotFoundException::new);
        if (foundAnswer.getUserId() != userId)
            throw new InvalidPermissionException();

        answerRepository.remove(foundAnswer);
    }

    @Transactional
    public AnswerDTO updateAnswer(int answerId, int userId, String text) {

        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();
        Answer foundAnswer = answerRepository.findById(answerId).orElseThrow(AnswerNotFoundException::new);
        if (foundAnswer.getUserId() != userId)
            throw new InvalidPermissionException();

        foundAnswer.setText(text);
        answerRepository.save(foundAnswer);
        List<Integer> score = getAnswerVotes(foundAnswer.getAnswerId());
        return AnswerDTO.ofEntity(foundAnswer, score.get(0), score.get(1));
    }

    @Transactional
    public List<AnswerDTO> listAllAnswersPerQuestion(int questionId) {
        List<AnswerDTO> answerDTOList = new ArrayList<AnswerDTO>();
        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();
        QuestionRepository questionRepository = repositoryFactory.createQuestionRepository();
        Optional<Question> foundQuestion = questionRepository.findById(questionId);

        if (foundQuestion.isPresent())
            answerRepository.findAllByQuestion(foundQuestion.get()).forEach(a -> {
                List<Integer> votes = getAnswerVotes(a.getAnswerId());
                answerDTOList.add(AnswerDTO.ofEntity(a, votes.get(0), votes.get(1)));
            });
        else
            return null;
        return answerDTOList;
    }

    @Transactional
    public List<AnswerDTO> listAllAnswersPerQuestionByScore(int questionId) {
        List<AnswerDTO> answerDTOList = new ArrayList<AnswerDTO>();
        List<AnswerDTO> finalAnswerDTOList = new ArrayList<AnswerDTO>();
        Map<AnswerDTO, Integer> scores = new HashMap<AnswerDTO, Integer>();
        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();
        QuestionRepository questionRepository = repositoryFactory.createQuestionRepository();
        Optional<Question> foundQuestion = questionRepository.findById(questionId);

        if (foundQuestion.isPresent())
            answerRepository.findAllByQuestion(foundQuestion.get()).forEach(a -> {
                List<Integer> votes = getAnswerVotes(a.getAnswerId());
                answerDTOList.add(AnswerDTO.ofEntity(a, votes.get(0), votes.get(1)));
                scores.put(AnswerDTO.ofEntity(a, votes.get(0), votes.get(1)), votes.get(0) - votes.get((1)));
            });
        else
            return null;
        Map<AnswerDTO, Integer> sortedList = scores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        //return answerDTOList;

        finalAnswerDTOList = sortedList.keySet().stream().collect(Collectors.toList());
        Collections.reverse(finalAnswerDTOList);
        return finalAnswerDTOList;
    }


    private List<Integer> getAnswerVotes(Integer answerId) {
        List<AnswerVote> answerVotes = repositoryFactory.createAnswerVoteRepository().findAll().
                stream().filter(x -> x.getAnswerId().equals(answerId)).collect(Collectors.toList());
        List<Integer> votes = new ArrayList<>();
        Integer upVotes = Math.toIntExact(answerVotes.stream().filter(x -> x.getType().equals(true)).count());
        Integer downVotes = Math.toIntExact(answerVotes.stream().filter(x -> x.getType().equals(false)).count());
        votes.add(upVotes);
        votes.add(downVotes);
        return votes;
    }


}

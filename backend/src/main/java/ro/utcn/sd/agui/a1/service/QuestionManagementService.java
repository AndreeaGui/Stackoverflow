package ro.utcn.sd.agui.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.agui.a1.dto.QuestionDTO;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.QuestionVote;
import ro.utcn.sd.agui.a1.entity.Tag;
import ro.utcn.sd.agui.a1.exception.QuestionNotFoundException;
import ro.utcn.sd.agui.a1.persistence.QuestionRepository;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;
import ro.utcn.sd.agui.a1.persistence.TagRepository;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionManagementService {
    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<QuestionDTO> listAllQuestions() {
        List<Question> questions = repositoryFactory.createQuestionRepository().findAll();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        questions.forEach(q -> {
            List<Integer> votes = getQuestionVotes(q.getQuestionId());
            questionDTOList.add(QuestionDTO.ofEntity(q, votes.get(0), votes.get(1)));
        });
        return questionDTOList;
    }

    @Transactional
    public QuestionDTO findQuestionById(Integer questionId) {
        Question q = repositoryFactory.createQuestionRepository().findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        List<Integer> votes = getQuestionVotes(q.getQuestionId());
        return QuestionDTO.ofEntity(q, votes.get(0), votes.get(1));
    }

    @Transactional
    public QuestionDTO addQuestion(int userId, String title, String text, List<String> tags) {

        TagRepository tagRepository = repositoryFactory.createTagRepository();
        QuestionRepository questionRepository = repositoryFactory.createQuestionRepository();

        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        //create the object
        Question question = new Question(null, userId, title, text, dateTime);
        //save the object in the persistence
        question = questionRepository.save(question);

        List<Tag> tagsOfQuestion = new ArrayList<>(); //the tags of the question as objects
        for (String iterationTagName : tags) {
            Optional<Tag> foundTag = tagRepository.findByName(iterationTagName);
            if (foundTag.isPresent()) {
                tagRepository.addTagToQuestion(foundTag.get(), question);
            } else {
                Tag newTag = new Tag(null, iterationTagName); //create tag object
                newTag = tagRepository.save(newTag); //add the tag object in the persistence
                tagRepository.addTagToQuestion(newTag, question); //link the tag to the question in persistence
            }
        }

        List<Integer> votes = getQuestionVotes(question.getQuestionId());
        return QuestionDTO.ofEntity(question, votes.get(0), votes.get(1));

    }


    @Transactional
    public List<QuestionDTO> filterQuestionsByTag(String tagName) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        Optional<Tag> tag = repositoryFactory.createTagRepository().findByName(tagName);
        List<Question> questions = repositoryFactory.createQuestionRepository().filterByTag(tag.get()).stream()
                .sorted(Comparator.comparing(Question::getDateTime))
                .collect(Collectors.toList());
        questions.forEach(q -> {
            List<Integer> votes = getQuestionVotes(q.getQuestionId());
            questionDTOList.add(QuestionDTO.ofEntity(q, votes.get(0), votes.get(1)));
        });
        Collections.reverse(questionDTOList);
        return questionDTOList;
    }

    @Transactional
    public List<QuestionDTO> filterQuestionsByTitle(String title) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questions = repositoryFactory.createQuestionRepository().findAll().stream().filter(x -> x.getTitle().toLowerCase()
                .contains(title.toLowerCase())).sorted(Comparator.comparing(Question::getDateTime))
                .collect(Collectors.toList());
        questions.forEach(q -> {
            List<Integer> votes = getQuestionVotes(q.getQuestionId());
            questionDTOList.add(QuestionDTO.ofEntity(q, votes.get(0), votes.get(1)));
        });
        Collections.reverse(questionDTOList);
        return questionDTOList;
    }

    private List<Integer> getQuestionVotes(Integer questionId) {
        List<QuestionVote> questionVotes = repositoryFactory.createQuestionVoteRepository().findAll().
                stream().filter(x -> x.getQuestionId().equals(questionId)).collect(Collectors.toList());
        List<Integer> votes = new ArrayList<>();
        Integer upVotes = Math.toIntExact(questionVotes.stream().filter(x -> x.getType().equals(true)).count());
        Integer downVotes = Math.toIntExact(questionVotes.stream().filter(x -> x.getType().equals(false)).count());
        votes.add(upVotes);
        votes.add(downVotes);
        return votes;
    }
}

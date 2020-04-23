package ro.utcn.sd.agui.a1.service;

import org.junit.Assert;
import org.junit.Test;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.Tag;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.exception.TagNotFoundException;
import ro.utcn.sd.agui.a1.persistence.QuestionRepository;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;
import ro.utcn.sd.agui.a1.persistence.TagRepository;
import ro.utcn.sd.agui.a1.persistence.UserRepository;
import ro.utcn.sd.agui.a1.persistence.memory.InMemoryRepositoryFactory;

import java.sql.Timestamp;

public class TagManagementServiceTest {

    private static RepositoryFactory createMockedFactory() {

        RepositoryFactory factory = new InMemoryRepositoryFactory();

        UserRepository userRepository = factory.createUserRepository();
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(new User(null, "A", "abc1", 0, "USER", false));
            userRepository.save(new User(null, "B", "abc2", 0, "USER", false));
            userRepository.save(new User(null, "C", "abc3", 0, "USER", false));
        }
        QuestionRepository questionRepository = factory.createQuestionRepository();
        if (questionRepository.findAll().isEmpty()) {
            questionRepository.save(new Question(null, 1, "Title One", "Text One", new Timestamp(System.currentTimeMillis())));//1
            questionRepository.save(new Question(null, 2, "Title Two", "Text Two", new Timestamp(System.currentTimeMillis())));
        }

        TagRepository tagRepository = factory.createTagRepository();
        if(tagRepository.findAll().isEmpty()){
            tagRepository.save(new Tag(null, "random"));//1
            tagRepository.save(new Tag(null, "late"));
        }
        tagRepository.addTagToQuestion(tagRepository.findAll().get(0), questionRepository.findAll().get(0));

        return factory;
    }

    @Test
    public void findTagByNameTest(){
        RepositoryFactory factory = createMockedFactory();
        TagManagementService service = new TagManagementService(factory);
        Tag tag = service.findTagByName("late");
        Tag expected = factory.createTagRepository().findAll().get(1);
        Assert.assertEquals(expected, tag);
    }

    @Test(expected = TagNotFoundException.class)
    public void findTagByNameTestError(){
        RepositoryFactory factory = createMockedFactory();
        TagManagementService service = new TagManagementService(factory);
        Tag tag = service.findTagByName("latenight");
        Tag expected = factory.createTagRepository().findAll().get(1);
        Assert.assertEquals(expected, tag);
    }
}

package ro.utcn.sd.agui.a1.service;

import org.junit.Assert;
import org.junit.Test;
import ro.utcn.sd.agui.a1.entity.Question;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.persistence.QuestionRepository;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;
import ro.utcn.sd.agui.a1.persistence.UserRepository;
import ro.utcn.sd.agui.a1.persistence.memory.InMemoryRepositoryFactory;

import java.sql.Timestamp;
import java.util.Optional;

public class UserManagementServiceTest {
    private static RepositoryFactory createMockedFactory(){

        RepositoryFactory factory = new InMemoryRepositoryFactory();

        UserRepository userRepository = factory.createUserRepository();
        if(userRepository.findAll().isEmpty()){
            userRepository.save(new User(null, "A", "abc1", 0, "USER", false));
            userRepository.save(new User(null, "B", "abc2", 0, "USER", false));
            userRepository.save(new User(null, "C", "abc3", 0, "USER", false));
        }

        QuestionRepository questionRepository = factory.createQuestionRepository();
        if(questionRepository.findAll().isEmpty()){
            questionRepository.save(new Question(null, 1, "Title One", "Text One", new Timestamp(System.currentTimeMillis())));
            questionRepository.save(new Question(null, 2, "Title Two", "Text Two", new Timestamp(System.currentTimeMillis())));
        }

        return factory;
    }

    @Test
    public void registerAsUserTest(){
        RepositoryFactory factory = createMockedFactory();
        UserManagementService service = new UserManagementService(factory);
        boolean registered = service.registerAsUser("Whatever", "creca");
        boolean actual = factory.createUserRepository().findAll().stream().anyMatch(x->x.getPassword().equals("creca")&&x.getUsername()
                .equals("Whatever"));
        Assert.assertEquals(registered, actual);
    }

    @Test
    //login invalid
    public void loginInvalidTest(){
        RepositoryFactory factory = createMockedFactory();
        UserManagementService service = new UserManagementService(factory);
        boolean logged = service.login("CEva", "gresit").isPresent(); //false to be
        Assert.assertFalse(logged);
    }


}

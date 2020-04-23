package ro.utcn.sd.agui.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final RepositoryFactory repositoryFactory;


    @Transactional
    public List<User> listAllUsers(){

        return repositoryFactory.createUserRepository().findAll();
    }

    @Transactional
    public boolean registerAsUser(String username, String password){

        Optional<User> foundUser= repositoryFactory.createUserRepository().findByUsername(username);
        if(foundUser.isPresent()){
            return false;
        }
        else
        {
            User newRegisteredUser = new User(null, username, password, 0, "USER", false);
            repositoryFactory.createUserRepository().save(newRegisteredUser);
            return true;
        }
    }

    @Transactional
    public Optional<User> login(String username, String password){
        Optional<User> foundUser = repositoryFactory.createUserRepository().findByUsername(username);
        if(foundUser.isPresent()){
            if(foundUser.get().getPassword().equals(password))
            {
                return foundUser;
            }

        }
        return Optional.empty();
    }



}

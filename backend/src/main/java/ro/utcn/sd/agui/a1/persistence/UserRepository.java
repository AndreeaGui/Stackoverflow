package ro.utcn.sd.agui.a1.persistence;

import ro.utcn.sd.agui.a1.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user); //update or create

    Optional<User> findById(int id);

    void remove(User user);

    List<User> findAll();

    Optional<User> findByUsername(String username);

}

package ro.utcn.sd.agui.a1.persistence.memory;

import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.persistence.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryUserRepository implements UserRepository {
    //by design, this is a singleton class - has only one set of data
    //CRUD repository

    private final Map<Integer, User> data = new ConcurrentHashMap<>();
    private  final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public synchronized User save(User user) {
        if(user.getUserId()== null){
            user.setUserId(currentId.incrementAndGet()); //prepare to insert
        }
        data.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(int id) {

        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(User user) {
        data.remove(user.getUserId());
    }

    @Override
    public List<User> findAll() {

        return new ArrayList<User>(data.values());
    }

    @Override
    public Optional<User> findByUsername(String username) {

        User foundUser = null;
        for(User userIterator : data.values()){
            if(userIterator.getUsername().equals(username)){
                foundUser = userIterator;
                break;
            }
        }
        return Optional.ofNullable(foundUser);

    }
}

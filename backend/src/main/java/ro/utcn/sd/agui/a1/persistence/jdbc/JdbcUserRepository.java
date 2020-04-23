package ro.utcn.sd.agui.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.agui.a1.entity.User;
import ro.utcn.sd.agui.a1.persistence.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor

public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate template;


    @Override
    public User save(User user) {
        if (user.getUserId() != null) {
            update(user);
        } else {
            int id = insert(user);
            user.setUserId(id);
        }
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        List<User> users = template.query("SELECT * FROM user WHERE user_id = ?",
                new Object[]{id},
                (resultSet, i) -> new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("score"),
                        resultSet.getString("type"),
                        resultSet.getBoolean("banned")
                )
        );

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public void remove(User user) {
        template.update("DELETE FROM user WHERE user_id = ?", user.getUserId());
    }

    @Override
    public List<User> findAll() {
        return template.query("SELECT * FROM user",
                (resultSet, i) -> new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("score"),
                        resultSet.getString("type"),
                        resultSet.getBoolean("banned")
                )
        );
    }


    private int insert(User user) {
        //we adopt this method because we need to obtain the auto-incremented key
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("user");
        insert.setGeneratedKeyName("user_id");

        Map<String, Object> data = new HashMap<>();
        //the data map makes the link between column names and pojo fileds
        data.put("username", user.getUsername());
        data.put("password", user.getPassword());
        data.put("score", user.getScore());
        data.put("type", user.getType());
        data.put("banned", user.getBanned());

        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(User user) {
        template.update("UPDATE user SET username = ?, password = ?, score = ?, type = ?, banned = ? WHERE user_id=?",
                user.getUsername(), user.getPassword(), user.getScore(), user.getType(), user.getBanned(), user.getUserId());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> users = template.query("SELECT * FROM user WHERE username = ?",
                new Object[]{username},
                (resultSet, i) -> new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("score"),
                        resultSet.getString("type"),
                        resultSet.getBoolean("banned")
                ));

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
}

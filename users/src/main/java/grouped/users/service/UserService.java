package grouped.users.service;

import grouped.users.model.User;
import grouped.users.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo){
        this.repo = repo;
    }

    public Iterable<User> findAll() {
        return repo.findAll();
    }

    public User findById(int id) {
        return repo.findById(id).orElseThrow(InternalError::new);
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public User saveUser(User user) {
        return repo.save(user);
    }

    public User updateUser(User user) {
        User existingUser = repo.findById(user.getId()).orElseThrow(InternalError::new);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPseudo(user.getPseudo());
        existingUser.setBirthday(user.getBirthday());
        existingUser.setAddress(user.getAddress());
        existingUser.setEmail(user.getEmail()); //can be modified ?
        existingUser.setPassword(user.getPassword());
        existingUser.setUserType(user.getUserType()); //can be modified ?
        return repo.save(existingUser);
    }

    public void deleteUser(int id) {
        repo.deleteById(id);
    }
}

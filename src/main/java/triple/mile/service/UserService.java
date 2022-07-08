package triple.mile.service;

import org.springframework.stereotype.Service;
import triple.mile.entity.User;
import triple.mile.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByUserId(UUID userId) { return userRepository.findByUserId(userId);}

    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}

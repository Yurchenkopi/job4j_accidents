package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserSpringDataRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SpringDataUserService implements UserService {
    private final UserSpringDataRepository userSpringDataRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UserSpringDataRepository.class.getName());

    @Override
    public Optional<User> save(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            rsl = Optional.of(userSpringDataRepository.save(user));
        } catch (Exception e) {
            LOG.error("Error occurred while saving user:  " + e.getMessage());
        }
        return rsl;
    }

}

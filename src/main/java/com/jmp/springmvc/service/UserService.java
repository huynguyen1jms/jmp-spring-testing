package com.jmp.springmvc.service;

import com.jmp.springmvc.dao.UserAccountRepository;
import com.jmp.springmvc.dao.UserRepository;
import com.jmp.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    /**
     * Gets user by its id.
     *
     * @return User.
     */
    public User getUserById(long userId) {
        return userRepository.getById(userId);
    }

    /**
     * Gets user by its email. Email is strictly matched.
     *
     * @return User.
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param name     Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     */
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return userRepository.findByNameContaining(name, pageable);
    }

    /**
     * Creates new user. User id should be auto-generated.
     *
     * @param user User data.
     * @return Created User object.
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates user using given data.
     *
     * @param user User data for update. Should have id set.
     * @return Updated User object.
     */
    public User updateUser(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.save(user);
        }
        return null;
    }

    /**
     * Deletes user by its id.
     *
     * @param userId User id.
     * @return Flag that shows whether user has been deleted.
     */
    public boolean deleteUser(long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}

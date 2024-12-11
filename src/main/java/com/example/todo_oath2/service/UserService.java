package com.example.todo_oath2.service;

import com.example.todo_oath2.model.User;
import com.example.todo_oath2.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found email:" + email));

    }
    public User createUser(User user){
        return repository.save(user);
    }
}

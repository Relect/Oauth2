package com.example.todo_oath2.dto;

import com.example.todo_oath2.model.Role;

public record UserDto(Long id, String name, String email, Role role){}

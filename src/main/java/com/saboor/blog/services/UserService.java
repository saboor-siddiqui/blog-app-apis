package com.saboor.blog.services;

import com.saboor.blog.entities.User;
import com.saboor.blog.payloads.UserDto;

import java.util.List;

public interface UserService {

    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto, Integer userId);
    public UserDto getUserBy(Integer id);
    public List<UserDto> getAllUser();
    public void deleteUser(Integer userId);
}

package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.mapper.BlankMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final BlankMapper mapper;

    @Override
    public UserDto createUser(User user) {
        return mapper.toUserDto(userStorage.createUser(user));
    }

    @Override
    public UserDto updateUser(User user, Long userId) {
        return mapper.toUserDto(userStorage.updateUser(user, userId));
    }

    @Override
    public UserDto getUser(Long id) {
        return mapper.toUserDto(userStorage.getUser(id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userStorage.getAllUsers().stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userStorage.deleteUser(id);
    }
}

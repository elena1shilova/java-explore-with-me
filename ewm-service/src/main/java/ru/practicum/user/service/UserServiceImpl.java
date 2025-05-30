package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addUserAdmin(NewUserRequest newUserRequest) {

        log.info("Добавление нового пользователя: {}", newUserRequest);

        if (isUserExistByEmail(newUserRequest.getEmail())) {
            log.info("Нарушение целостности данных");
            throw new IllegalArgumentException("Пользователь с email: " + newUserRequest.getEmail() + " уже зарегистрирован");
        }

        log.info("Пользователь зарегистрирован");
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(newUserRequest)));
    }

    @Override
    public List<UserDto> getAllUsersAdmin(int from, int size) {
        log.info("Получение списка пользователей");

        List<UserDto> users = userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .toList();

        log.info("Пользователи найдены");
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserAdmin(Long userId) {

        log.info("Удаление пользователя с id: {}", userId);

        if (userRepository.existsById(userId)) {
            log.info("Пользователь удален");
            userRepository.deleteById(userId);
        } else {
            log.info("Пользователь не найден или недоступен");
            throw new NotFoundException("Пользователь с id: " + userId + " не найден");
        }
    }

    private boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

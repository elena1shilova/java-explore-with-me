package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addRequestPrivate(@PathVariable Long userId,
                                                     @RequestParam Long eventId) {
        log.info("Добавлен запрос пользователя {} с событием {}", userId, eventId);
        return requestService.addRequestPrivate(userId, eventId);
    }

    @GetMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestsPrivate(@PathVariable Long userId) {
        return requestService.getRequestsPrivate(userId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        return requestService.cancelRequest(userId, requestId);

    }
}

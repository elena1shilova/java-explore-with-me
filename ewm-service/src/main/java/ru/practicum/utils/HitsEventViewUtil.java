package ru.practicum.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import ru.practicum.StatsClient;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HitsEventViewUtil {

    public static Long getHitsEvent(Long eventId, LocalDateTime start, LocalDateTime end, Boolean unique, StatsClient statsClient) {

        if (start.isAfter(end)) {
            throw new ValidationException("Start date cannot be after end date");
        }

        List<String> uris = new ArrayList<>();
        uris.add("/events/" + eventId);

        ResponseEntity<Object> response = statsClient.getStats(start, end, uris, unique);

        Object responseBody = response.getBody();

        List<ViewStatsDto> output = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        if (responseBody != null) {
            output = mapper.convertValue(responseBody, new TypeReference<>() {
            });
        }

        Long view = 0L;

        if (!output.isEmpty()) {
            view = output.getFirst().getHits();
        }
        return view;

    }
}


package ru.practicum.event.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventUserParam {

    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private String sort;
    private Integer from;
    private Integer size;

}

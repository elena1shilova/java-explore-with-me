package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EndpointHitDto {

    private String app; // Идентификатор сервиса, для которого записывается информация

    private String uri; // URI, для которого был осуществлен запрос

    private String ip; // IP-адрес пользователя, осуществившего запрос

    private LocalDateTime timestamp; // Дата и время, когда был совершен запрос к эндпоинту
}

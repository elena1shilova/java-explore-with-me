package ru.practicum.comment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.StatsClient;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

import static ru.practicum.utils.HitsEventViewUtil.getHitsEvent;

@RequiredArgsConstructor
@Component
public class CommentMapper {

    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final StatsClient statsClient;

    public Comment toNewComment(NewCommentDto newCommentDto, Event event, User author) {

        return Comment.builder()
                .event(event)
                .text(newCommentDto.getText())
                .author(author)
                .created(LocalDateTime.now())
                .build();

    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .event(eventMapper.toShort(comment.getEvent(), getHitsEvent(
                        comment.getEvent().getId(),
                        LocalDateTime.now().minusDays(100),
                        LocalDateTime.now(), false, statsClient
                )))
                .text(comment.getText())
                .author(userMapper.toUserShortDto(comment.getAuthor()))
                .created(comment.getCreated())
                .updated(comment.getUpdated())
                .build();
    }

}


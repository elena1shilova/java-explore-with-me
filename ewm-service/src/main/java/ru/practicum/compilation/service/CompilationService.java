package ru.practicum.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto addCompilationAdmin(NewCompilationDto newCompilationDto);

    void deleteCompilationAdmin(Long compilationId);

    CompilationDto updateCompilationAdmin(Long compilationId, UpdateCompilationRequest updateCompilationRequest);

    List<CompilationDto> getCompilationsPublic(Boolean pinned, Pageable pageable);

    CompilationDto getCompilationPublic(Long compilationId);

}

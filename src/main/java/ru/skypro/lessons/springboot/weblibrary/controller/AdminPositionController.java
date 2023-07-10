package ru.skypro.lessons.springboot.weblibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.weblibrary.dto.CreatePosition;
import ru.skypro.lessons.springboot.weblibrary.service.PositionService;

@RestController
@RequestMapping("/admin/position")
@RequiredArgsConstructor
@Tag(name = "Должности", description = "Создание должностей")
public class AdminPositionController {

    private final PositionService positionService;

    @PostMapping
    @Operation(summary = "Создание новой должности", description = "Создает новую должность. " +
            "При создании необходимо указать название должности.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Должность создана", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CreatePosition.class))}),
            @ApiResponse(
                    responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(
                    responseCode = "403", description = "Доступ запрещен")
    })
    public CreatePosition addPosition(@RequestBody CreatePosition createPosition) {
        return positionService.addPosition(createPosition);
    }
}

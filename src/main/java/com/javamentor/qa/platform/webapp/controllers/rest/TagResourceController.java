package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.service.abstracts.dto.tag.TagDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/tag")
@Api("Контроллер для работы с Tag")
public class TagResourceController {

    private final TagDtoService tagDtoService;

    public TagResourceController(TagDtoService tagDtoService) {
        this.tagDtoService = tagDtoService;
    }

    @GetMapping("/related")
    @ApiOperation(value = "Получение списка самых популярных тэгов (использующихся на наибольшем количестве вопросов, макс. 10 штук)", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success request. List of RelatedTagDto returned"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "No tags in DB yet")})
    public ResponseEntity<?> getRelatedTagsDtoList() {
        return ResponseEntity.ok(tagDtoService.getRelatedTagsDto());
    }
}
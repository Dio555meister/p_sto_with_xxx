package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.user.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.GroupBookmarkDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.answer.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.user.UserProfileQuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.GroupBookmarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/profile")
@Api(value = "User profile controller")
public class ProfileUserResourceController {

    private final AnswerDtoService answerDtoService;
    private final UserProfileQuestionDtoService userProfileQuestionDtoService;
    private final GroupBookmarkService groupBookmarkService;
    private final GroupBookmarkDtoService groupBookmarkDtoService;

    @GetMapping("/questions")
    @ApiOperation(
            value = "Getting all UserProfileQuestionDto of authorized User",
            response = UserProfileQuestionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success request. List of UserProfileQuestionDto has been successfully returned"),
            @ApiResponse(code = 400, message = "Invalid password"),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    public ResponseEntity<?> getAllUserAuthorizedQuestions(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userProfileQuestionDtoService.getAllUserProfileQuestionDtoByUserId(user.getId()));
    }

    /**
     * Method return JSON with list all removed questions
     * @return {@link ResponseEntity} with status Ok and {@link List <UserProfileQuestionDto>} in body
     */
    @GetMapping("/delete/questions")
    @ApiOperation(
            value = "Getting all removed UserProfileQuestionDto",
            response = UserProfileQuestionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success request. UserProfileQuestionDto has been successfully returned"),
            @ApiResponse(code = 400, message = "Invalid password"),
            @ApiResponse(code = 403, message = "Forbidden")})
    public ResponseEntity <?> getUserRemovedQuestion (@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userProfileQuestionDtoService.getAllUserRemovedQuestion(user.getId()));
    }

    @GetMapping("/question/week")
    @ApiOperation(
            value = "Getting the count of all user's answers by user ID per week")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success request. The count of all user's answers has been successfully returned"),
            @ApiResponse(code = 400, message = "Invalid password"),
            @ApiResponse(code = 403, message = "Forbidden")})
    public ResponseEntity<?> getAllAuthorizedUserAnswersPerWeek(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(answerDtoService.getCountAllAnswersPerWeekByUserId(user.getId()));
    }
    @PostMapping("/bookmark/group")
    @ApiOperation(
            value = "Add group for bookmarks in profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Group bookmark was successfully added"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "Forbidden")})
    public ResponseEntity<?> addGroupOfBookmarks(@AuthenticationPrincipal User user,
                                                 @RequestBody String groupName) throws NotFoundException {
        GroupBookmark groupBookmark = groupBookmarkService.groupBookmarkPersist(user, groupName);
        return ResponseEntity.ok(groupBookmarkDtoService.getGroupBookmarkById(groupBookmark.getId()));
    }
}

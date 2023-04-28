package com.javamentor.qa.platform.dao.abstracts.dto.question.pagination;

import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionViewDto;


import java.util.Optional;

public interface QuestionDtoDao {
    Optional<QuestionDto> getQuestionDtoById(Long id);
}

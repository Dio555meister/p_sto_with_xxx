package com.javamentor.qa.platform.service.impl.dto.question;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.answer.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.tag.TagDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.answer.AnswerDto;
import com.javamentor.qa.platform.models.dto.question.QuestionViewDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.PageDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.question.QuestionViewDtoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
@Service
public class QuestionViewDtoServiceImpl extends PageDtoService<QuestionViewDto> implements QuestionViewDtoService {

    private final TagDtoDao tagDtoDao;
    private final AnswerDtoDao answerDtoDao;

    public QuestionViewDtoServiceImpl(
            TagDtoDao tagDtoDao,
            AnswerDtoDao answerDtoDao,
            Map<String, PageDtoDao<QuestionViewDto>> beansMap) {
        super(beansMap);
        this.tagDtoDao = tagDtoDao;
        this.answerDtoDao = answerDtoDao;
    }

    @Override
    public PageDto<QuestionViewDto> getAllQuestionDto(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "paginationQuestionDtoDaoGetAllImpl");
        PageDto<QuestionViewDto> pageDto = pageDto(param);
        List<QuestionViewDto> listQuestionViewDto = pageDto.getItems();
        List<Long> questionId = listQuestionViewDto.stream().map(QuestionViewDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionViewDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(questionId);
        listQuestionViewDto.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));
        return pageDto;
    }

    @Override
    public PageDto<QuestionViewDto> getPageWithListQuestionDtoWithoutAnswer(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "questionDtoWithoutAnswerPaginationDaoImpl");
        PageDto<QuestionViewDto> pageDto = pageDto(param);
        List<QuestionViewDto> listQuestionViewDto = pageDto.getItems();
        List<Long> questionId = listQuestionViewDto.stream().map(QuestionViewDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionViewDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(questionId);
        listQuestionViewDto.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));
        return pageDto;
    }

    @Override
    public PageDto<QuestionViewDto> getPageWithListMostPopularQuestionDto(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "questionDtoDaoSortedByPopularityImpl");
        PageDto<QuestionViewDto> pageDto = pageDto(param);
        List<QuestionViewDto> listQuestionViewDto = pageDto.getItems();
        List<Long> questionId = listQuestionViewDto.stream().map(QuestionViewDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionViewDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(questionId);
        listQuestionViewDto.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));
        return pageDto;
    }

    @Override
    public PageDto<QuestionViewDto> getPageWithListQuestionDtoSortedByNewest(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "questionDtoSortedByNewestDaoImpl");
        PageDto<QuestionViewDto> pageDto = pageDto(param);
        List<QuestionViewDto> listQuestionViewDto = pageDto.getItems();
        List<Long> questionId = listQuestionViewDto.stream().map(QuestionViewDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionViewDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(questionId);
        listQuestionViewDto.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));
        return pageDto;
    }

    @Override
    public PageDto<QuestionViewDto> getPageWithListMostPopularQuestionForMonthDto(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "questionDtoDaoSortedByPopularityForMonthImpl");
        param.put("monthAgo", LocalDateTime.now().minusMonths(1));
        PageDto<QuestionViewDto> pageDto = pageDto(param);
        List<QuestionViewDto> listQuestionDto = pageDto.getItems();
        List<Long> questionId = listQuestionDto.stream().map(QuestionViewDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));
        return pageDto;
    }
}

package com.javamentor.qa.platform.service.impl.dto.question;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.answer.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.question.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.question.QuestionDtoSortedByNewestDao;
import com.javamentor.qa.platform.dao.abstracts.dto.question.QuestionDtoDaoSortedByPopularity;
import com.javamentor.qa.platform.dao.abstracts.dto.question.QuestionDtoWithoutAnswerPaginationDao;
import com.javamentor.qa.platform.dao.abstracts.dto.tag.TagDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.answer.AnswerDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.PageDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.question.QuestionDtoService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;


@Service
public class QuestionDtoServiceImpl extends PageDtoService<QuestionDto> implements QuestionDtoService {

    private final QuestionDtoDao questionDtoDao;
    private final TagDtoDao tagDtoDao;
    private final AnswerDtoDao answerDtoDao;
    private final QuestionDtoWithoutAnswerPaginationDao questionDtoWithoutAnswerPaginationDao;
    private final QuestionDtoDaoSortedByPopularity questionDtoDaoSortedByPopularity;
    private final QuestionDtoSortedByNewestDao questionDtoSortedByNewestDao;

    public QuestionDtoServiceImpl(
            QuestionDtoDao questionDtoDao,
            TagDtoDao tagDtoDao,
            AnswerDtoDao answerDtoDao,
            Map<String, PageDtoDao<QuestionDto>> beansMap,
            QuestionDtoWithoutAnswerPaginationDao questionDtoWithoutAnswerPaginationDao,
            QuestionDtoDaoSortedByPopularity questionDtoDaoSortedByPopularity,
            QuestionDtoSortedByNewestDao questionDtoSortedByNewestDao) {
        super(beansMap);
        this.questionDtoDao = questionDtoDao;
        this.tagDtoDao = tagDtoDao;
        this.answerDtoDao = answerDtoDao;
        this.questionDtoWithoutAnswerPaginationDao = questionDtoWithoutAnswerPaginationDao;
        this.questionDtoDaoSortedByPopularity = questionDtoDaoSortedByPopularity;
        this.questionDtoSortedByNewestDao = questionDtoSortedByNewestDao;
    }


    @Override
    public QuestionDto getQuestionDtoById(Long id) throws NotFoundException {
        Optional<QuestionDto> questionDtoOptional = questionDtoDao.getQuestionDtoById(id);

        if (questionDtoOptional.isPresent()) {
            QuestionDto questionDto = questionDtoOptional.get();
            questionDto.setListTagDto(tagDtoDao.getTagDtoById(id));
            questionDto.setListAnswerDto(answerDtoDao.getAllByQuestionIdSortedByUsefulAndCount(id));

            return questionDto;
        }
        throw new NotFoundException("QuestionDto with id = " + id + " not found");
    }

    @Override
    public PageDto<QuestionDto> getAllQuestionDto(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "paginationQuestionDtoDaoGetAllImpl");

        PageDto<QuestionDto> pageDto = pageDto(param);
        List<QuestionDto> listQuestionDto = pageDto.getItems();
        List<Long> questionId = listQuestionDto.stream().map(QuestionDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));


        return pageDto;
    }

    @Override
    public PageDto<QuestionDto> getPageWithListQuestionDtoWithoutAnswer(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "questionDtoWithoutAnswerPaginationDaoImpl");
        PageDto<QuestionDto> pageDto = pageDto(param);
        List<QuestionDto> questionDtoList = pageDto.getItems();
        List<Long> listQuestionId = new ArrayList<>();
        for (QuestionDto questionDto : questionDtoList) {
            listQuestionId.add(questionDto.getId());
        }
        Map<Long, List<TagDto>> tagsMapByQuestionId = questionDtoWithoutAnswerPaginationDao.getTagsMapByQuestionId(listQuestionId);
        for (QuestionDto questionDto : questionDtoList) {
            questionDto.setListTagDto(tagsMapByQuestionId.get(questionDto.getId()));
        }
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(listQuestionId);
        questionDtoList.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));
        return pageDto;
    }

    @Override
    public PageDto<QuestionDto> getPageWithListMostPopularQuestionDto(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "questionDtoDaoSortedByPopularityImpl");

        PageDto<QuestionDto> pageDto = pageDto(param);
        List<QuestionDto> listQuestionDto = pageDto.getItems();
        List<Long> questionId = listQuestionDto.stream().map(QuestionDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));

        return pageDto;
    }

    @Override
    public PageDto<QuestionDto> getPageWithListQuestionDtoSortedByNewest(HashMap<String, Object> param) {
        param.put("daoDtoImpl", "questionDtoSortedByNewestDaoImpl");
        PageDto<QuestionDto> pageDto = pageDto(param);
        List<QuestionDto> listQuestionDto = pageDto.getItems();
        List<Long> questionId = listQuestionDto.stream().map(QuestionDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));
        Map<Long, List<AnswerDto>> answersMap = answerDtoDao.getAnswersMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListAnswerDto(answersMap.get(lQuestionDto.getId())));

        return pageDto;
    }
    @Override
    public PageDto<QuestionDto> getPageWithListMostPopularQuestionForMonthDto(HashMap<String, Object> param) throws NotFoundException {
        param.put("daoDtoImpl", "questionDtoDaoSortedByPopularityForMonthImpl");
        param.put("monthAgo", LocalDateTime.now().minusMonths(1));
        PageDto<QuestionDto> pageDto = pageDto(param);
        List<QuestionDto> listQuestionDto = pageDto.getItems();
        List<Long> questionId = listQuestionDto.stream().map(QuestionDto::getId).collect(toList());
        Map<Long, List<TagDto>> tagsMap = tagDtoDao.getTagsMapByQuestionId(questionId);
        listQuestionDto.forEach(lQuestionDto -> lQuestionDto.setListTagDto(tagsMap.get(lQuestionDto.getId())));

        return pageDto;
    }
}

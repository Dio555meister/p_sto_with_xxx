package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;


public interface VoteQuestionService extends ReadWriteService<VoteQuestion, Long> {

    boolean isUserAlreadyVoted(Question question, User user);
    VoteQuestion voteUpQuestion(Question question, User user);
    VoteQuestion voteDownQuestion(Question question, User user);


}

package com.mysite.sbb.question.service;

import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor// 자동으로 생성자를 만들어줌(final 꼭 써줘야함)
public class QuestionService {
//    @Autowired //자동 객체 생성
    private final QuestionRepository questionRepository; //final 안쓰면 생성자 안생김

    public List<Question> getList(){
        List<Question> questionList = questionRepository.findAll();
        return questionList;
    }

    public Question getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티가 없습니다."));
        return question;
    }

    public void createQuestion(String subject, String content) {
        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .created(LocalDateTime.now())
                .build();
        questionRepository.save(question);
    }
}

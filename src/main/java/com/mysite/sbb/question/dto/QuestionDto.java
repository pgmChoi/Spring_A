package com.mysite.sbb.question.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor  // 1. 스프링이 Form 데이터를 바인딩할 때 필수로 필요한 기본 생성자
@AllArgsConstructor // 2. @Builder를 유지하기 위해 필요한 전체 생성자

public class QuestionDto {
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 150, message = "제목은 150자 이내로 입력하세요.") //Validation
    private String subject;

    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;

    @NotEmpty(message = "평점")
    private String star;
}

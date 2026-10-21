package com.mysite.sbb.answer.dto;

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
public class AnswerDto {

    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;
}

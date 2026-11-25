package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor // 자동으로 생성자를 만들어줌(final 꼭 써줘야함)
@RequestMapping("/question")
public class QuestionController {

//    @Autowired //자동 객체 생성
    private final QuestionService questionService;
    private final MemberService memberService;

    @GetMapping("/list")
    //@ResponseBody //첫번 째 데이터 값 보내기
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Question> paging = questionService.getList(page);
        log.info("================================> list : {}", paging);
        model.addAttribute("paging", paging); // "list" -> "paging"으로 수정
        return "question/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id,
                         AnswerDto answerDto,
                         Model model){ // @PathVariable는 getmapping으로 들어온 id를 변수처리(자동으로 값을 세팅)
        log.info("========================> id : {}",id);
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question/detail";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 한 사용자만 접근 가능
    @GetMapping("/create")
    public String createInputForm(QuestionDto questionDto) // 알아서 들어감
    {
        return "question/inputForm";
    }

//    @PostMapping("/create")
//    public String createQuestion(@RequestParam("subject") String subject,
//                                 @RequestParam("content") String content){
//        log.info("=========> subject : {} content : {}", subject, content);
//        questionService.createQuestion(subject, content);
//        return "redirect:/question/list";
//    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createQuestion(@Valid QuestionDto questionDto,
                                 BindingResult bindingResult,
                                 Principal principal){ //현재 로그인 한 사용자

        log.info("=========> questionDto : {}", questionDto);
        if(bindingResult.hasErrors()){
            return "question/inputForm";
        }
        Member member = memberService.getMember(principal.getName());

        // 현재 로그인한 회원 정보 가져오기
//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
        // 질문 등록
        questionService.createQuestion(questionDto, member);

        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyQuestion(@PathVariable("id") Long id,
                                 QuestionDto questionDto,
                                 Principal principal){

        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        questionDto.setSubject(question.getSubject());
        questionDto.setContent(question.getContent());

        return "question/inputForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyQuestion(@PathVariable("id") Long id,
                                 @Valid QuestionDto questionDto,
                                 BindingResult bindingResult,
                                 Principal principal){

        if(bindingResult.hasErrors()){
            return "question/inputForm";
        }

        log.info("=========> Create : {}", questionDto);

        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        questionService.modifyQuestion(question, questionDto);

        return "redirect:/question/" + id;
    }

}

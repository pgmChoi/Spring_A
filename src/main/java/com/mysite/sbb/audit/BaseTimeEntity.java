package com.mysite.sbb.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseTimeEntity {
    @CreatedDate // 만든 날짜 자동
    @Column(updatable = false)// 처음으로 만든 날짜는 그대로 사용
    private LocalDateTime created;

    @LastModifiedDate //수정한 날짜 자동
    private LocalDateTime updated;
}

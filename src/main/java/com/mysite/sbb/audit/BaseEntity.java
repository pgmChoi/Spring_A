package com.mysite.sbb.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseEntity extends BaseTimeEntity{
    @CreatedBy // 누구의 의해서 만들어 졌는지
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy //누구의 의해서 수정되었는지
    private String modifiedBy;
}

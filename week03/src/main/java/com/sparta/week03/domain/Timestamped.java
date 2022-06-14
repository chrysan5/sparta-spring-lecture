package com.sparta.week03.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter //변수들이 private므로 접근위해 getter 필요
@MappedSuperclass // timestamped를 상속한 녀석이 자동으로 생성시간과 수정시간을 컬럽으로 잡도록 도움
@EntityListeners(AuditingEntityListener.class) // 생성,변경 시간의 변화를 자동으로 업데이트합니다.
public abstract class Timestamped {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}

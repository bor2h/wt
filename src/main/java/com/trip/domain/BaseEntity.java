package com.trip.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedBy
	@Column(updatable = false ,columnDefinition = "varchar(255) comment '생성자 ID'")
	private String createdId;

	@LastModifiedBy
	@Column(columnDefinition = "varchar(255) comment '수정자 ID'")
	private String updatedId;

	@CreatedDate
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(updatable = false , columnDefinition = "datetime default current_timestamp comment '등록일시'")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(columnDefinition = "datetime default current_timestamp comment '수정일시'")
	private LocalDateTime updatedAt;

}

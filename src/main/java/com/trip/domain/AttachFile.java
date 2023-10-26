package com.trip.domain;

import com.trip.common.enums.FileType;
import com.trip.dto.AttachFileDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class AttachFile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileNo;

    @Column(columnDefinition = "varchar(255) comment '참조 id'")
    private String refId;

    @Column(columnDefinition = "varchar(255) comment '파일명'")
    private String fileName;

    @Column(columnDefinition = "varchar(255) comment '저장된 파일경로'")
    private String uploadPath;

    @Column(columnDefinition = "varchar(255) comment '파일 타입'")
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(columnDefinition = "varchar(255) comment '용량'")
    private Long fileSize;

    @Column(columnDefinition = "varchar(255) comment '컨텐츠 타입'")
    private String contentType;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false,columnDefinition = "varchar(10) comment '삭제여부'")
//    private TableStatus isDeleted;

    public AttachFileDto.AttachFileInfo toDto() {
        return AttachFileDto.AttachFileInfo.builder()
                .uploadPath(this.uploadPath)
                .refId(this.refId)
                .fileType(this.fileType)
                .fileNo(this.fileNo)
                .fileName(this.fileName)
                .fileSize(this.fileSize)
                .contentType(this.contentType)
                .build();
    }
}

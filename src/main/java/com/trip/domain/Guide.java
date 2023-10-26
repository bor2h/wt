package com.trip.domain;

import com.trip.common.enums.GuideStatus;
import com.trip.common.enums.TableStatus;
import com.trip.dto.GuideDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Guide extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint comment 'Guide id'")
    private Long id;

    private Long userNo;

    @Column(columnDefinition = "varchar(500) comment 'profileImg'")
    private String profileImg;

    @Builder.Default
    @Column(columnDefinition = "bigint default 0 comment '조회수'")
    private Long viewCount = 0L;

    @Builder.Default
    @Column(columnDefinition = "bigint default 0 comment '좋아요수'")
    private Long likeCount = 0L;

    @Column(columnDefinition = "varchar(500) comment 'content'")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) comment '검수 완료'")
    private GuideStatus isPass;

//    @JoinColumn(name = "id", insertable = false, updatable = false)
//    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
//    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "guide", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LikeGuide> LikeGuides = new ArrayList<>();

    public void updatePassStatus(GuideStatus status){
        setIsPass(status);
    }

    public GuideDTO.GuideResponse toDto(){
        return GuideDTO.GuideResponse.builder()
                .id(id)
                .userNo(userNo)
                .content(content)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .isPass(isPass)
                .likeCount(likeCount)
                .viewCount(viewCount)
                .profileImg(profileImg)
                .build();
    }
}
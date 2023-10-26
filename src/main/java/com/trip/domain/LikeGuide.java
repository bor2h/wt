package com.trip.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(LikeGuide.LikeGuideId.class)
@Entity
public class LikeGuide  {

    @Id
    private Long guideNo;

    @Id
    private Long userNo;

    @Builder.Default
    @Column(updatable = false)
    private LocalDateTime likeDate = LocalDateTime.now();

    @ManyToOne(targetEntity = Guide.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "guideNo", insertable = false, updatable = false)
    private Guide guide;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LikeGuideId implements Serializable {
        private Long guideNo;
        private Long userNo;
    }
}

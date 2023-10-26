package com.trip.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGuide is a Querydsl query type for Guide
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuide extends EntityPathBase<Guide> {

    private static final long serialVersionUID = 607971630L;

    public static final QGuide guide = new QGuide("guide");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdId = _super.createdId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.trip.common.enums.GuideStatus> isPass = createEnum("isPass", com.trip.common.enums.GuideStatus.class);

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final ListPath<LikeGuide, QLikeGuide> LikeGuides = this.<LikeGuide, QLikeGuide>createList("LikeGuides", LikeGuide.class, QLikeGuide.class, PathInits.DIRECT2);

    public final StringPath profileImg = createString("profileImg");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedId = _super.updatedId;

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QGuide(String variable) {
        super(Guide.class, forVariable(variable));
    }

    public QGuide(Path<? extends Guide> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGuide(PathMetadata metadata) {
        super(Guide.class, metadata);
    }

}


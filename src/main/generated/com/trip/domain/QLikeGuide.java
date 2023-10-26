package com.trip.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeGuide is a Querydsl query type for LikeGuide
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeGuide extends EntityPathBase<LikeGuide> {

    private static final long serialVersionUID = -318634281L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeGuide likeGuide = new QLikeGuide("likeGuide");

    public final QGuide guide;

    public final NumberPath<Long> guideNo = createNumber("guideNo", Long.class);

    public final DateTimePath<java.time.LocalDateTime> likeDate = createDateTime("likeDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public QLikeGuide(String variable) {
        this(LikeGuide.class, forVariable(variable), INITS);
    }

    public QLikeGuide(Path<? extends LikeGuide> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeGuide(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeGuide(PathMetadata metadata, PathInits inits) {
        this(LikeGuide.class, metadata, inits);
    }

    public QLikeGuide(Class<? extends LikeGuide> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.guide = inits.isInitialized("guide") ? new QGuide(forProperty("guide")) : null;
    }

}


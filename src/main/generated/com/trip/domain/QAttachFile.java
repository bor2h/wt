package com.trip.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttachFile is a Querydsl query type for AttachFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttachFile extends EntityPathBase<AttachFile> {

    private static final long serialVersionUID = -1789260177L;

    public static final QAttachFile attachFile = new QAttachFile("attachFile");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath contentType = createString("contentType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdId = _super.createdId;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> fileNo = createNumber("fileNo", Long.class);

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final EnumPath<com.trip.common.enums.FileType> fileType = createEnum("fileType", com.trip.common.enums.FileType.class);

    public final StringPath refId = createString("refId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedId = _super.updatedId;

    public final StringPath uploadPath = createString("uploadPath");

    public QAttachFile(String variable) {
        super(AttachFile.class, forVariable(variable));
    }

    public QAttachFile(Path<? extends AttachFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttachFile(PathMetadata metadata) {
        super(AttachFile.class, metadata);
    }

}


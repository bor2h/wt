package com.trip.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDaySchedule is a Querydsl query type for DaySchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDaySchedule extends EntityPathBase<DaySchedule> {

    private static final long serialVersionUID = -337977595L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDaySchedule daySchedule = new QDaySchedule("daySchedule");

    public final StringPath address = createString("address");

    public final StringPath checkList = createString("checkList");

    public final NumberPath<Integer> dateNumber = createNumber("dateNumber", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> dayScheduleNo = createNumber("dayScheduleNo", Long.class);

    public final QSchedule schedule;

    public final StringPath scheduleId = createString("scheduleId");

    public QDaySchedule(String variable) {
        this(DaySchedule.class, forVariable(variable), INITS);
    }

    public QDaySchedule(Path<? extends DaySchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDaySchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDaySchedule(PathMetadata metadata, PathInits inits) {
        this(DaySchedule.class, metadata, inits);
    }

    public QDaySchedule(Class<? extends DaySchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schedule = inits.isInitialized("schedule") ? new QSchedule(forProperty("schedule")) : null;
    }

}


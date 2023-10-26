package com.trip.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = 821813029L;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> adults = createNumber("adults", Integer.class);

    public final NumberPath<Integer> child = createNumber("child", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdId = _super.createdId;

    public final NumberPath<Integer> days = createNumber("days", Integer.class);

    public final ListPath<DaySchedule, QDaySchedule> daySchedules = this.<DaySchedule, QDaySchedule>createList("daySchedules", DaySchedule.class, QDaySchedule.class, PathInits.DIRECT2);

    public final StringPath distance = createString("distance");

    public final DatePath<java.time.LocalDate> endAt = createDate("endAt", java.time.LocalDate.class);

    public final StringPath hashtag = createString("hashtag");

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final StringPath scheduleId = createString("scheduleId");

    public final StringPath scheduleImage = createString("scheduleImage");

    public final StringPath scheduleName = createString("scheduleName");

    public final DatePath<java.time.LocalDate> startAt = createDate("startAt", java.time.LocalDate.class);

    public final StringPath startingLocation = createString("startingLocation");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedId = _super.updatedId;

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QSchedule(String variable) {
        super(Schedule.class, forVariable(variable));
    }

    public QSchedule(Path<? extends Schedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchedule(PathMetadata metadata) {
        super(Schedule.class, metadata);
    }

}


# -------------------------
# create table
# -------------------------

use trip;

show tables;
# DROP TABLE IF EXISTS day_schedule;
# DROP TABLE IF EXISTS guide;
# DROP TABLE IF EXISTS like_guide;
# DROP TABLE IF EXISTS schedule;
# DROP TABLE IF EXISTS user_tb;
# DROP TABLE IF EXISTS attach_file;

create table attach_file (
     file_no bigint not null auto_increment,
     created_at datetime default current_timestamp comment '등록일시',
     created_id varchar(255) comment '생성자 ID',
     updated_at datetime default current_timestamp comment '수정일시',
     updated_id varchar(255) comment '수정자 ID',
     content_type varchar(255) comment '컨텐츠 타입',
     file_name varchar(255) comment '파일명',
     file_size varchar(255) comment '용량',
     file_type varchar(255) comment '파일 타입',
     ref_id varchar(255) comment '참조 id',
     upload_path varchar(500) comment '저장된 파일경로',
     primary key (file_no)
) engine=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment '첨부파일';


create table day_schedule (
      day_schedule_no bigint not null auto_increment,
      address varchar(255) comment '주소',
      check_list varchar(1000) comment '체크리스트',
      date_number int comment 'N일차',
      date_time varchar(255) comment '날짜/시간',
      schedule_id varchar(255),
      primary key (day_schedule_no)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment '상세 일정';


create table guide (
   id bigint comment 'id' not null auto_increment,
   created_at datetime default current_timestamp comment '등록일시',
   created_id varchar(255) comment '생성자 ID',
   updated_at datetime default current_timestamp comment '수정일시',
   updated_id varchar(255) comment '수정자 ID',
   content varchar(1000) comment 'content',
   is_pass varchar(10) comment '검수 완료',
   like_count bigint default 0 comment '좋아요수',
   profile_img varchar(255) comment 'profileImg',
   user_no bigint,
   view_count bigint default 0 comment '조회수',
   primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment '가이드';


create table like_guide (
    guide_no bigint not null,
    user_no bigint not null,
    like_date datetime(6),
    primary key (guide_no, user_no)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment '가이드 좋아요';


create table schedule (
  schedule_id varchar(255) not null,
  created_at datetime default current_timestamp comment '등록일시',
  created_id varchar(255) comment '생성자 ID',
  updated_at datetime default current_timestamp comment '수정일시',
  updated_id varchar(255) comment '수정자 ID',
  adults int comment '성인 인원수 (만 18세 이상)',
  child int comment '아동 인원수 (만 7세 이상)',
  days int comment '여행기간',
  distance varchar(255) comment '여행의 거리 (단거리,장거리)',
  end_at varchar(100) comment '일정 종료일',
  hashtag varchar(255) comment 'hashtag',
  like_count bigint default 0 comment '좋아요수',
  schedule_image varchar(255) comment '여행 기록 이미지 파일 아이디',
  schedule_name varchar(255) comment '여행 기록 제목',
  start_at varchar(100) comment '일정 시작일',
  starting_location varchar(255) comment '출발 위치',
  user_no bigint,
  view_count bigint default 0 comment '조회수',
  primary key (schedule_id)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment '일정';


create table user_tb (
     id bigint not null auto_increment,
     created_at datetime default current_timestamp comment '등록일시',
     created_id varchar(255) comment '생성자 ID',
     updated_at datetime default current_timestamp comment '수정일시',
     updated_id varchar(255) comment '수정자 ID',
     age varchar(50) comment '나이',
     birth_date varchar(100) null comment '생년월일 YYYYYMMDD',
     email varchar(100) comment '이메일 ',
     gender varchar(50) null comment '성별',
     login_id varchar(100) comment '로그인 아이디',
     nick_name varchar(255) comment '닉네임',
     password varchar(255) comment '비밀번호',
     phone varchar(50) comment '휴대폰 번호 01012345678',
     profile_img varchar(255) comment '프로필 사진',
     role varchar(100) comment '계정 권한',
     travel_style_one varchar(100) comment '여행스타일 1 (예술적인,활동적인,모르겠어요)',
     travel_style_two varchar(100) comment '여행스타일 2 (즉흥적,계획적,모르겠어요)',
     user_name varchar(100) comment '이름',
     user_status varchar(100) comment '계정 상태',
     primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci comment '회원';


alter table user_tb
    add constraint UK_2dlfg6wvnxboknkp9d1h75icb unique (email);


alter table user_tb
    add constraint UK_rretl105i3hjpvry7hf0vv74e unique (login_id);

alter table day_schedule
    add constraint FKj7op10v1rulfp22nlddsokqjy
    foreign key (schedule_id)
    references schedule (schedule_id);


alter table like_guide
    add constraint FKgau77t1lxx9m7p0ifall2fp45
    foreign key (guide_no)
    references guide (id);

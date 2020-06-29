insert into line (`line_id`, `name`, `start_time`, `end_time`, `interval_time`, `created_at`, `updated_at`) values (1, '1호선', CURRENT_TIME(), CURRENT_TIME(), 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line (`line_id`, `name`, `start_time`, `end_time`, `interval_time`, `created_at`, `updated_at`) values (2, '2호선', CURRENT_TIME(), CURRENT_TIME(), 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line (`line_id`, `name`, `start_time`, `end_time`, `interval_time`, `created_at`, `updated_at`) values (3, '3호선', CURRENT_TIME(), CURRENT_TIME(), 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line (`line_id`, `name`, `start_time`, `end_time`, `interval_time`, `created_at`, `updated_at`) values (4, '4호선', CURRENT_TIME(), CURRENT_TIME(), 18, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into station (`station_id`, `name`, `created_at`, `updated_at`) values (5, '신당', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into station (`station_id`, `name`, `created_at`, `updated_at`) values (6, '상왕십리', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into station (`station_id`, `name`, `created_at`, `updated_at`) values (7, '왕십리', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into station (`station_id`, `name`, `created_at`, `updated_at`) values (8, '한양대', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into station (`station_id`, `name`, `created_at`, `updated_at`) values (9, '뚝섬', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into station (`station_id`, `name`, `created_at`, `updated_at`) values (10, '충무로', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


insert into line_station (`line_station_id`, `line_id`, `station_id`, `pre_station_id`, `distance`, `duration`, `created_at`, `updated_at`) values (11, 1, 1+4, null, 0, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line_station (`line_station_id`, `line_id`, `station_id`, `pre_station_id`, `distance`, `duration`, `created_at`, `updated_at`) values (12, 1, 2+4, 1+4, 5, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line_station (`line_station_id`, `line_id`, `station_id`, `pre_station_id`, `distance`, `duration`, `created_at`, `updated_at`) values (13, 1, 3+4, 2+4, 5, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line_station (`line_station_id`, `line_id`, `station_id`, `pre_station_id`, `distance`, `duration`, `created_at`, `updated_at`) values (14, 1, 4+4, 3+4, 3, 4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line_station (`line_station_id`, `line_id`, `station_id`, `pre_station_id`, `distance`, `duration`, `created_at`, `updated_at`) values (15, 2, 4+4, null, 0, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line_station (`line_station_id`, `line_id`, `station_id`, `pre_station_id`, `distance`, `duration`, `created_at`, `updated_at`) values (16, 2, 2+4, 4+4, 9, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line_station (`line_station_id`, `line_id`, `station_id`, `pre_station_id`, `distance`, `duration`, `created_at`, `updated_at`) values (17, 2, 5+4, 2+4, 3, 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into line_station (`line_station_id`, `line_id`, `station_id`, `pre_station_id`, `distance`, `duration`, `created_at`, `updated_at`) values (18, 3, 6+4, null, 0, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into member (`member_id`, `email`, `name`, `password`, `created_at`, `updated_at`) values (19, 'newlec@newlec.com', '뉴렉', '123123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into member (`member_id`, `email`, `name`, `password`, `created_at`, `updated_at`) values (20, 'chlwlgus4@github.io', '최지', 'ahWL', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into member (`member_id`, `email`, `name`, `password`, `created_at`, `updated_at`) values (21, 'Jong012@github.io', '종성', 'lover', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

insert into favorite (`favorite_id`, `member_id`, `source_station_id`, `target_station_id`, `created_at`, `updated_at`) values (22, 19, 8, 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into favorite (`favorite_id`, `member_id`, `source_station_id`, `target_station_id`, `created_at`, `updated_at`) values (23, 19, 7, 10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into favorite (`favorite_id`, `member_id`, `source_station_id`, `target_station_id`, `created_at`, `updated_at`) values (24, 19, 5, 9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into favorite (`favorite_id`, `member_id`, `source_station_id`, `target_station_id`, `created_at`, `updated_at`) values (25, 20, 7, 10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into favorite (`favorite_id`, `member_id`, `source_station_id`, `target_station_id`, `created_at`, `updated_at`) values (26, 20, 5, 6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
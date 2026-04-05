alter table alarms
    add column if not exists is_overslept boolean not null default false;

create table if not exists alarm_repeat_days (
    alarm_id bigint not null,
    day_of_week varchar(8) not null,
    constraint fk_alarm_repeat_days_alarm
        foreign key (alarm_id) references alarms(id) on delete cascade
);

create unique index if not exists ux_alarm_repeat_days
    on alarm_repeat_days(alarm_id, day_of_week);

alter table alarms
    drop column if exists repeat_pattern;
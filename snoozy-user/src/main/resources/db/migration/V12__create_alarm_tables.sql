create table if not exists alarms (
    id bigserial primary key,
    owner_id bigint not null,
    title varchar(120) not null,
    alarm_time timestamp not null,
    enabled boolean not null default true,
    sound_name varchar(120),
    difficulty_level integer,
    is_overslept boolean not null default false,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists alarm_repeat_days (
    alarm_id bigint not null,
    day_of_week varchar(8) not null,
    constraint fk_alarm_repeat_days_alarm
        foreign key (alarm_id) references alarms(id) on delete cascade
);

create unique index if not exists ux_alarm_repeat_days
    on alarm_repeat_days(alarm_id, day_of_week);

create table if not exists alarm_permissions (
    id bigserial primary key,
    owner_id bigint not null,
    target_user_id bigint not null,
    permission_type varchar(32) not null,
    is_active boolean not null default true,
    created_at timestamp not null
);

create table if not exists alarm_actions (
    id bigserial primary key,
    alarm_id bigint not null,
    actor_user_id bigint not null,
    target_user_id bigint not null,
    action_type varchar(32) not null,
    status varchar(32) not null,
    executed_at timestamp,
    message_text varchar(300),
    created_at timestamp not null,
    constraint fk_alarm_actions_alarm
        foreign key (alarm_id) references alarms(id) on delete cascade
);
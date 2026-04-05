create table if not exists alarms (
    id bigserial primary key,
    owner_id bigint not null,
    title varchar(120) not null,
    alarm_time timestamp not null,
    enabled boolean not null default true,
    repeat_pattern varchar(32) not null,
    sound_name varchar(120),
    difficulty_level integer,
    created_at timestamp not null,
    updated_at timestamp not null
);

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
    constraint fk_alarm_actions_alarm foreign key (alarm_id) references alarms(id) on delete cascade
);

create index if not exists idx_alarms_owner_id on alarms(owner_id);
create index if not exists idx_alarm_permissions_owner_target on alarm_permissions(owner_id, target_user_id);
create index if not exists idx_alarm_actions_target_user on alarm_actions(target_user_id);
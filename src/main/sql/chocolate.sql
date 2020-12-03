
create table chocolate (
    id serial primary key not null,
    name text,
    qty int
    );
--\i sql/create_teacher.sql

insert into chocolate (name,qty) values ('Lindt',10);
insert into chocolate (name,qty) values ('ki',15);
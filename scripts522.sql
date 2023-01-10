create table cars
(
    id    serial primary key,
    brand text not null,
    model text not null,
    cost  numeric check ( cost >= 0 )
);


create table people
(
    id     serial primary key,
    name   text not null,
    age    integer check ( age >= 0 ),
    driver boolean default false,
    car_id integer references cars (id)
);

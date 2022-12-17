select *
from student;

select *
from student
where age > 10 and age < 20;

select name
from student;

select *
from student
where name like '%o%';

select *
from student
where age < id;

select *
from student
order by age;

select f.name, s.name, s.id
from faculty as f,
     student as s
where f.id = s.faculty_id
order by f.name, s.name;


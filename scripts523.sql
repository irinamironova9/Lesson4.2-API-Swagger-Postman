select student.name, student.age, faculty.name
from student
         full outer join faculty on student.faculty_id = faculty.id;


select student.name, student.age
from student
         inner join avatar on student.id = avatar.student_id;

SELECT s.name AS student_name, s.age, f.name AS faculty_name
FROM student s
         LEFT JOIN faculty f ON s.faculty_id = f.id;


SELECT s.name AS student_name, s.age, f.name AS faculty_name
FROM student s
         INNER JOIN faculty f on s.faculty_id = f.id
         INNER JOIN avatar a ON s.id = a.student_id;



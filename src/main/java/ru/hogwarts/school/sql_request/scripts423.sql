SELECT s.name AS student_name, s.age, f.name AS faculty_name
FROM students s
         JOIN faculties f ON s.faculty_id = f.id
WHERE s.faculty_id = (SELECT id FROM faculty WHERE name = 'Hogwarts');


SELECT s.name AS student_name, s.age, f.name AS faculty_name
FROM students s
         JOIN faculties f ON s.faculty_id = f.id
         JOIN avatars a ON s.id = a.student_id
WHERE s.faculty_id = (SELECT id FROM faculty WHERE name = 'Hogwarts');
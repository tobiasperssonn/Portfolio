SELECT
ins.employee_id AS "Instructor Id",
per.first_name AS "First Name",
per.last_name AS "Last Name",
COUNT(les.session_id) AS "No of Lessons"


FROM
instructor ins
LEFT JOIN
lesson les ON ins.id = les.instructor_id
LEFT JOIN
person per ON ins.person_id = per.id

GROUP BY
ins.employee_id, per.first_name, per.last_name

HAVING
COUNT(les.session_id) > 1

ORDER BY
"No of Lessons" DESC;
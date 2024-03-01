SELECT

TO_CHAR(ts.start_time, 'Day') AS day,
genre.name AS "Genre",
CASE
	WHEN COUNT(sl.student_id) >= ens.max_num_of_students THEN 'No Seats'
	WHEN COUNT(sl.student_id) = ens.max_num_of_students -2 THEN '1 or 2 Seats'
	WHEN COUNT(sl.student_id) = ens.max_num_of_students -1 THEN '1 or 2 Seats'
	ELSE 'Many Seats'
END AS "No of Free Seats"
	
FROM
ensemble ens

LEFT JOIN 
genre ON ens.genre_id = genre.id

LEFT JOIN 
time_slot ts ON ens.lesson_id = ts.lesson_id

LEFT JOIN 
student_lesson sl ON ens.lesson_id = sl.lesson_id

WHERE
ts.start_time >= CURRENT_DATE AND ts.start_time < CURRENT_DATE + INTERVAL '7 days'

GROUP BY
sl.lesson_id, ts.start_time, genre.name, ens.max_num_of_students

ORDER BY
ts.start_time
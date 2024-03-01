SELECT /*Outer query counts the number of students for each count of sibling*/
"No of Siblings",
COUNT(student_id) AS "No of Students"

FROM ( /*Inner query gets the number of siblings each student have*/
    SELECT
        stu.student_id,
        COALESCE(COUNT(sibling_id), 0) AS "No of Siblings"
    FROM
        student stu
    LEFT JOIN
        sibling sib ON stu.id = sib.student_id
    GROUP BY
        stu.student_id
)

GROUP BY
"No of Siblings"

ORDER BY
"No of Siblings";

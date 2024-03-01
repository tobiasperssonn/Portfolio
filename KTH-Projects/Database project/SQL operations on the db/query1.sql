SELECT
EXTRACT(MONTH FROM les.date) AS month,
COUNT(les.id) AS Total,
COUNT(ind.lesson_id) AS Individual,
COUNT(grp.lesson_id) AS Group,
COUNT(ens.lesson_id) AS Ensemble

FROM
lesson les
LEFT JOIN
individual_lesson ind ON les.id = ind.lesson_id
LEFT JOIN
group_lesson grp ON les.id = grp.lesson_id
LEFT JOIN
ensemble ens ON les.id = ens.lesson_id

WHERE
EXTRACT(YEAR FROM les.date) = 2023

GROUP BY
EXTRACT(MONTH FROM les.date)

ORDER BY
EXTRACT(MONTH FROM les.date);

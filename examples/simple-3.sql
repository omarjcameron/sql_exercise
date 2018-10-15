SELECT
    t1.letter AS l1,
    t2.letter AS l2,
    t3.letter AS l3
FROM
    simple AS t1,
    simple AS t2,
    simple AS t3
WHERE
    t1.number >= t2.number AND
    t2.number <= t3.number

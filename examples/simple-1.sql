SELECT
    t1.letter AS l1,
    t1.number AS n1,
    t2.letter AS l2,
    t2.number AS n2
FROM
    simple AS t1,  -- should load from "simple.table.json"
    simple AS t2

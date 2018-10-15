-- Type mismatch
SELECT name
FROM cities
WHERE name = "z" AND name != population

# SQL Evaluator - Starter code for Go

This includes starter code to parse the table and query JSON formats into Go structs.

Although we try to write idiomatic Go, this code is intentionally bare-bones in order to avoid imposing a particular programming style on you. Free to modify it as much as you like to make your life easier.

To run (in POSIX shells):

```
GOPATH=`pwd` go build sql_evaluator

./sql_evaluator parse-table ../examples/cities.table.json

../sql-to-json ../examples/cities-1.sql >cities-1.sql.json
./sql_evaluator parse-query cities-1.sql.json
```

# Programming Assignment: SQL Evaluator

Write a program that executes simple SQL queries.

You don't have to write an SQL parser -- we've provided a tool (`sql-to-json`) that converts [our subset of SQL](#sql-syntax) into JSON for your program to load.

Your program should:
1. Accept a single file as a command-line argument.  This file will contain a [JSON-formatted SQL query](#json-formatted-sql).
2. Load the tables referenced by the query.  For example, if the query references the table name "countries", load the table data from "countries.table.json" (see [Table JSON](#table-json)).
3. Detect logical errors in the query.
4. If there are no errors, execute the query and print the result table to standard output.

You can assume the JSON input files are correctly structured.  However, you should detect logical errors in the queries, such as:
- References to column names or table names that don't exist.
- Ambiguous column references (the column name exists in multiple tables).
- Use of a comparison operator (e.g. ">") on incompatible types (string vs integer).

You should perform the evaluation entirely in memory, using the standard data structures provided by your programming language.
- You can use external libraries to help with reading/writing JSON.
- If you're writing in Java or Go, we've provided code to handle the JSON parsing part; see the "starter-java/" and "starter-go/" folders.

## Examples

You need Python 2.7+ or 3.2+ installed to run the provided `sql-to-json` tool (check: `python --version`).

In the "examples/" folder:
- Table data is in the ".table.json" files
- Sample queries are in the ".sql" files.
- The expected output for each query is in the ".out" files.  You don't have to match the text exactly, and the order of the rows doesn't matter.

For example, to try the "simple-1.sql" query:

```
$ cd examples
$ ../sql-to-json simple-1.sql > simple-1.json
$ YOUR-PROGRAM simple-1.json
```

In Bash or Zsh, you can do this without creating an intermediate ".json" file:

```
$ YOUR-PROGRAM <(../sql-to-json simple-1.sql)
```

To start, skim over the "\*.sql" files to get an idea of what queries look like.  We recommend starting with the "simple-\*.sql" examples, which cover the fundamentals.

## Evaluation Criteria

When you submit your code, include a ReadMe with:
- Instructions for how to run your code.
- If you had 5 more hours to work on this, what would you do?
- Anything else you'd like us to know.

Primarily, we're looking for code that is correct and easy to understand.  After that, consider ways to improve efficiency for realistic queries.  Assume that the normal usage pattern involves loading the table data once and then executing many queries.

Obviously, time is limited so not everything can be perfect, but be prepared to discuss the choices you made.

## File Formats

### Table JSON

Each ".table.json" file is a JSON array.  The first element is a list of column definitions and the rest of the elements are the table contents (see "examples/cities.table.json").

A column definition is an array where the first element is the column name and the second element is the column type (either "str" or "int").  Each cell in the table contents is either a string or an integer.

### SQL Syntax

(You don't have to write a parser for this syntax.  The included Python program `sql-to-json` will convert SQL to a JSON-formatted equivalent.)

```
Query =
    "SELECT" Selector ( "," Selector )*
    "FROM" TableRef ( "," TableRef )*
    ( "WHERE" Comparison ( "AND" Comparison )* )?

Selector = ColumnRef ( "AS" <identifier> )?

TableRef = <identifier> ( "AS" <identifier> )?

Comparison = Term ( "=" | "!=" | ">" | ">=" | "<" | "<=" ) Term

Term = ColumnRef | <string-literal> | <integer-literal>

ColumnRef = <identifier> ( "." <identifier> )?
```

Comments start with "--" and go to the end of the line.

Joins are performed using [implicit cross-join notation](https://en.wikipedia.org/wiki/Join_(SQL)#Inner_join).

### JSON-formatted SQL

```
Query = {
    select: Array<Selector>  // non-empty array
    from: Array<TableRef>  // non-empty array
    where: Array<Comparison>
    // The 'as' names in the 'select' and 'from' arrays are guaranteed
    // to be unique by the parser.
}

Selector = {
    column: ColumnRef
    as: string  // automatically derived from 'column' if there's no explicit "AS"
}

TableRef = {
    source: string  // the file to load (without the ".table.json" extension)
    as: string  // automatically derived from 'source' if there's no explicit "AS"
}

Comparison = {
    op: "=" | "!=" | ">" | ">=" | "<" | "<="
    left: Term
    right: Term
}

Term = {column: ColumnRef} | {literal: int | string}

ColumnRef = {
    name: string
    table: string | null  // non-null if the reference is fully-qualified ("table1.column2")
}
```

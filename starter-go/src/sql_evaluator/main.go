package main

import (
	"encoding/json"
	"fmt"
	"os"

	"sql_evaluator/sqleval"
)

const usageFormatString = "Usage:\n" +
	"    %s parse-query <query-json-file>\n" +
	"    %s parse-table <table-json-file>\n"

func usageString() string {
	return fmt.Sprintf(usageFormatString, os.Args[0], os.Args[0])
}

func die(message string) {
	os.Stderr.WriteString(message)
	os.Exit(1)
}

func main() {
	if len(os.Args) != 3 {
		die(usageString())
	}

	filename := os.Args[2]
	reader, err := os.Open(filename)
	if err != nil {
		die(fmt.Sprintf("Invalid file %q: %s", filename, err))
	}
	defer reader.Close()

	command := os.Args[1]
	switch command {
	case "parse-query":
		var query sqleval.Query
		err = json.NewDecoder(reader).Decode(&query)
		if err != nil {
			die(fmt.Sprintf("Error decoding query JSON from %q: %s", filename, err))
		}
		queryJson, err := json.Marshal(query)
		if err != nil {
			panic(fmt.Sprintf("Can't happen: value should remarshal, but got error: %v", err))
		}
		fmt.Printf("%s\n", queryJson)

	case "parse-table":
		var table sqleval.Table
		err = json.NewDecoder(reader).Decode(&table)
		if err != nil {
			die(fmt.Sprintf("Error decoding table JSON from %q: %s", filename, err))
		}
		tableJson, err := json.Marshal(table)
		if err != nil {
			panic(fmt.Sprintf("Can't happen: value should remarshal, but got error: %v", err))
		}
		fmt.Printf("%s\n", tableJson)

	default:
		die(fmt.Sprintf("Invalid command %q.\n", command))
	}
}

require 'json'
require 'pp'
load 'table_parser.rb'
load 'query_validator.rb'
include TableParser
include QueryValidator


# Handle elements from standard input
if ARGV.empty?
  p "Please prove a JSON-formatted SQL Query"
else
# Capture contents of file from input
# Parse JSON file and save contents into hash data structure
# Pluck a few useful blocks of from query_hash to be used during validations
  file = File.read(ARGV[0])
  query_hash = JSON.parse(file)
  comparison_block = query_hash.fetch("where")
  selector_block = query_hash.fetch("select")
  table_ref_block = query_hash.fetch("from")

# Find and extract needed tables to load
# Remove duplicate table types from array and store tables for the possiblity of running >1 query
  list_of_tables = extract_tables(query_hash)
  list_of_tables = list_of_tables.uniq
  stored_table_data = load_tables(list_of_tables)


# Validate query by detecting logical errors
  column_names_and_types = get_column_types(list_of_tables, stored_table_data)

  pp validate_comparison(comparison_block, column_names_and_types)
  pp ambiguous_references?(selector_block, table_ref_block, stored_table_data)
  validate_columns(query_hash, stored_table_data)

# If all is sound, execute query and Write results to new file
  # Ran out of time on the last validation but if all the test/checks passed, this is where I'd execute the query and write to standard output.
end

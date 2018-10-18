module QueryValidator
# Helper functions to identify logical errors in the query
# Grab and store the names of the columns and their types as key,value pairs
  def get_column_types(list_of_tables, stored_table_data)
    column_names_and_types = []
    list_of_tables.each do |table_name|
      column_names_and_types << stored_table_data[0]["#{table_name}"][0].to_h
    end
      column_names_and_types = column_names_and_types.reduce { |sum, hash| sum.merge(hash) }
      column_names_and_types
  end


  # Ensure there are no ambiguous column references (the column name exists in multiple tables).
  def ambiguous_references?(selector_block, table_ref_block, stored_table_data)
    unique_table_list = table_ref_block.map { |item| item["source"] }.uniq

    # get all the column names from all tables
    all_column_names = []
    unique_table_list.each do |table|
      all_column_names << stored_table_data[0].fetch("#{table}")[0].flatten
    end
    all_column_names = all_column_names.flatten


    # Iterate through the select statements and check if more than one table ambiguously references a column name
    selector_block.each do |selector|
      if selector["column"]["table"] == nil
        matches = all_column_names.each_index.select {|i| all_column_names[i] == selector["column"]["name"]}
        return false if matches.count > 1
      end
    end
    return true
  end


# Validate the proper use of a comparison operator on compatible types (string vs integer).
def validate_comparison(comparison_block, column_names_and_types)
  results_of_check = []

  return true if comparison_block.empty?
  comparison_block.each do |comparison|
    if comparison["right"]["column"] == nil
      right_object_type = ''
      object = comparison["right"]["literal"]

      case object
      when Integer
        right_object_type = "int"
      when String
        right_object_type = "str"
      end

      if column_names_and_types["#{comparison["left"]["column"]["name"]}"] == right_object_type
        results_of_check << true
      else
        results_of_check << false
      end
    else
      if column_names_and_types["#{comparison["left"]["column"]["name"]}"] ==     column_names_and_types["#{comparison["right"]["column"]["name"]}"]
         results_of_check << true
      else
         results_of_check << false
      end
    end
  end
  return results_of_check.all?
end

# Ensure there are no references to column names or table names that don't exist.
def validate_columns(query_hash, stored_table_data)
  # ran out of time here
end


end

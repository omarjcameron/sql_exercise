module TableParser
# Helper functions/logic to capture the correct tables to load
  def extract_tables(query_hash)
    table_alias_pairs = query_hash.fetch("from")
    table_alias_pairs.map { |table, als| table["source"] }
  end

  def load_tables(list_of_tables)
    hashed_tables = []
    tables = Hash.new

    list_of_tables.each do |table|
        file = File.read("#{table}.table.json")
        tables["#{table}"] = JSON.parse(file)
    end
    hashed_tables << tables
  end
end

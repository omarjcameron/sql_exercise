package sql_evaluator;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public final class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage:");
            System.err.println("    COMMAND parse-query <query-json-file>");
            System.err.println("    COMMAND parse-table <table-json-file>");
            System.exit(1); return;
        }

        String command = args[0];
        String filePath = args[1];

        switch (command) {
            case "parse-query":
                Query query;
                try {
                    query = JacksonUtil.readFully(filePath, Query.class);
                } catch (JsonProcessingException ex) {
                    System.err.println("Error: " + ex.getMessage());
                    System.exit(1); return;
                }
                JacksonUtil.writeLines(System.out, query);
                break;
            case "parse-table":
                Table table;
                try {
                    table = JacksonUtil.readFully(filePath, Table.class);
                } catch (JsonProcessingException ex) {
                    System.err.println("Error: " + ex.getMessage());
                    System.exit(1); return;
                }
                JacksonUtil.writeLines(System.out, table);
                break;
            default:
                System.err.println("Error: invalid command \"" + command + "\"");
                System.exit(1); return;
        }
    }
}

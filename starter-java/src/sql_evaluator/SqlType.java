package sql_evaluator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SqlType {
    STR("str"), INT("int");

    @JsonValue
    final String name;

    SqlType(String name) { this.name = name; }
}

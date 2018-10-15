package sql_evaluator;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public final class JacksonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET)
            .disable(
                MapperFeature.AUTO_DETECT_CREATORS,
                MapperFeature.AUTO_DETECT_GETTERS,
                MapperFeature.AUTO_DETECT_IS_GETTERS,
                MapperFeature.AUTO_DETECT_SETTERS);
    private static final DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter() {{
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", "\n");
        this._objectFieldValueSeparatorWithSpaces = ": ";
        this.indentArraysWith(indenter);
        this.indentObjectsWith(indenter);
    }};

    public static String toString(Object value) {
        StringWriter sw = new StringWriter();
        try {
            write(sw, value);
        }
        catch (IOException ex) {
            throw new AssertionError("IOException from StringWriter?");
        }
        return sw.getBuffer().toString();
    }

    public static String toStringLines(Object value) {
        StringWriter sw = new StringWriter();
        try {
            writeLines(sw, value);
        }
        catch (IOException ex) {
            throw new AssertionError("IOException from StringWriter?");
        }
        return sw.getBuffer().toString();
    }

    public static void write(Writer out, Object value) throws IOException {
        try {
            objectMapper.writer().writeValue(out, value);
        }
        catch (JsonGenerationException|JsonMappingException ex) {
            throw new AssertionError("JSON serialization error", ex);
        }
    }
    public static void writeLines(Writer out, Object value) throws IOException {
        try {
            objectMapper.writer(prettyPrinter).writeValue(out, value);
        }
        catch (JsonGenerationException|JsonMappingException ex) {
            throw new AssertionError("JSON serialization error", ex);
        }
        out.write('\n');
    }

    public static void write(OutputStream out, Object value) throws IOException {
        try {
            objectMapper.writer().writeValue(out, value);
        }
        catch (JsonGenerationException|JsonMappingException ex) {
            throw new AssertionError("JSON serialization error", ex);
        }
    }
    public static void writeLines(OutputStream out, Object value) throws IOException {
        try {
            objectMapper.writer(prettyPrinter).writeValue(out, value);
        }
        catch (JsonGenerationException|JsonMappingException ex) {
            throw new AssertionError("JSON serialization error", ex);
        }
        out.write('\n');
    }

    public static void write(PrintStream out, Object value) {
        try {
            write((OutputStream) out, value);
        }
        catch (IOException ex) {
            throw new AssertionError("IOException from PrintStream?");
        }
    }

    public static void writeLines(PrintStream out, Object value) {
        try {
            writeLines((OutputStream) out, value);
        }
        catch (IOException ex) {
            throw new AssertionError("IOException from PrintStream?");
        }
    }

    public static <T> T readFully(String path, Class<T> cls) throws IOException {
        JsonParser jp = objectMapper.getFactory().createParser(new File(path));
        T value = objectMapper.readValue(jp, cls);
        if (jp.nextToken() != null) {
            throw new JsonParseException(jp, "found unexpected data after entire value was parsed");
        }
        return value;
    }
}

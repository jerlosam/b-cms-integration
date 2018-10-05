package io.crazy88.beatrix.e2e.bulkchangemanager.csv;

import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class CsvWriter {

    private static final String SEPARATOR = "\t";

    public void writeLines(File file, List<String> headers, List<String> fields) throws IOException {
        PrintWriter writer = open(file.getPath());

        Stream.concat(Stream.of(headers), Stream.of(fields))
                .map(this::convertToLine)
                .forEach(writer::println);

        writer.flush();
        writer.close();
    }

    private PrintWriter open(String filePath) throws IOException {
        return new PrintWriter(new FileWriter(filePath));
    }

    private String convertToLine(List<String> fields) {
        return fields.stream()
                .collect(joining(SEPARATOR));
    }
}

/*
 * Copyright 2026 Jonathan Chang, Chun-yien <ccy@musicapoetica.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ertyu.oe26.lab2;

import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CsvExample {

  private static Path data_source;

  public static void main(String[] args) {

    try {
      data_source = Path.of(CsvExample.class.getResource("/lab2_students_1000.csv").toURI());
    } catch (URISyntaxException ex) {
      System.getLogger(CsvExample.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }

    try (var ins = Files.newInputStream(data_source);
            var reader = new InputStreamReader(ins, StandardCharsets.UTF_8)) {

      CSVParser parser = CSVFormat.DEFAULT.builder()
              .setHeader()
              .setSkipHeaderRecord(true)
              .get()
              .parse(reader);

      for (CSVRecord record : parser) {
        String id = record.get("student_id");
        String name = record.get("student_name");
        String level = record.get("student_level");
        String university = record.get("university");
        String department = record.get("department");
        String advisor = record.get("advisor");
        String transcript = record.get("transcript");

        System.out.println("%-6s %s, %s, %s (%s)".formatted(
                id, name, department, university, advisor));
        var total = 0;
        var courses = transcript.split(";");
        for (String course : courses) {
          System.out.println("\t" + course);
          try {
            total += Integer.parseInt(course.split(",")[3]);
          } catch (Exception e) {
            System.out.println(e);
          }
        }
        System.out.println("\tAverage = " + (total / courses.length));
      }

    } catch (Exception e) {
      System.out.println(e);
    }

  }
}

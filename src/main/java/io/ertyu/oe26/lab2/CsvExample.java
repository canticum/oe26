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
 * 這段程式示範如何使用 Apache Common CSV 將資料由 csv 檔讀出來、以及 transcript 的解析方式--使用字串方法
 * split()。
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CsvExample {

  private static Path data_source;

  /**
   * 首欄標題，亦可直接使用字串取值，如 <code>record.get("student_id");</code>。注意若設定了
   * .setHeader(Header.class) 的話，它將直接按順序對應 csv 的 header 欄，因此名稱無須相同。
   */
  enum Header {

    studentId, name, level, school, department, advisor, transcript
  }

  public static void main(String[] args) {

    try {
      data_source = Path.of(CsvExample.class.getResource("/lab2_students_1000.csv").toURI());
    } catch (NullPointerException ex) {
      System.out.println("檔案不存在。");
      System.getLogger(CsvExample.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    } catch (URISyntaxException ex) {
      System.out.println("檔案讀取錯誤。");
      System.getLogger(CsvExample.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }

    try (var ins = Files.newInputStream(data_source);
            var reader = new InputStreamReader(ins, StandardCharsets.UTF_8)) {

      CSVParser parser = CSVFormat.DEFAULT.builder()
              .setHeader(Header.class)
              .setSkipHeaderRecord(true)
              .get()
              .parse(reader);

      for (CSVRecord record : parser) {
        String id = record.get(Header.studentId);
        String name = record.get(Header.name);
        String level = record.get(Header.level);
        String school = record.get(Header.school);
        String department = record.get(Header.department);
        String advisor = record.get(Header.advisor);
        String transcript = record.get(Header.transcript);

        System.out.println("%-6s %s, %s, %s, %s (%s)".formatted(
                id, name, level, department, school, advisor));
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

    } catch (Exception ex) {
      System.getLogger(CsvExample.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
  }
}

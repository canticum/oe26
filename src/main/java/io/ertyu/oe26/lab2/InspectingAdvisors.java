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
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

/**
 * 這裡示範一個語義上的探索，究竟同名（但可能不同校）的老師該如何看待？
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class InspectingAdvisors {

  private static Path data_source;

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
              .setHeader(CsvExample.Header.class)
              .setSkipHeaderRecord(true)
              .get()
              .parse(reader);

      var advisors = parser.stream()
              .collect(Collectors.groupingBy(rec -> "%s (%s)".formatted(rec.get(CsvExample.Header.advisor), rec.get(CsvExample.Header.school)),
                      Collectors.counting()));

      var total = advisors.entrySet().stream()
              .sorted(Map.Entry.comparingByKey())
              .peek(e -> System.out.println("%-50s %3s".formatted(e.getKey(), e.getValue())))
              .mapToLong(Entry::getValue)
              .sum();
      
      System.out.println("驗算結果：學生總人數 = " + total);
    } catch (Exception ex) {
      System.getLogger(CsvExample.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
  }
}

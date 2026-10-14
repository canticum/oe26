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
package io.ertyu.oe26.wk6;

import io.ertyu.oe26.wk5.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class GenericExample {

  public static void main(String[] args) {

    // fix import: CTRL-SHIFT I
    List<Student> students = new ArrayList<>();

    students.add(new Student("123", "John"));
    students.add(new Student("456", "Mary"));
    students.add(new Student("789", "Jonathan"));
    students.add(new Student("1234", "Susan"));

//    students.stream()
//            .filter(s -> s.getName().startsWith("J"))
//            .map(s -> s.getId() + "\t" + s.getName())
//            .forEach(str -> System.out.println(str));
    Map<String, String> names = students.stream()
            .collect(Collectors.toMap(Student::getId, Student::getName));

    System.out.println(names);

  }
}

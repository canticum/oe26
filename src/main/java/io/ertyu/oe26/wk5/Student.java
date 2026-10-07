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
package io.ertyu.oe26.wk5;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Student extends Person {

  String studentID;
  String dept;

  public Student(String ID, String name) {
    
    super(ID, name);
  }

  public void setStudentID(String studnetID) {

    this.studentID = studnetID;
  }

  public void setDept(String dept) {

    this.dept = dept;
  }

  public void printStudent() {

    System.out.println(this.studentID + "\t" + this.name + "\t" + this.dept);
  }
}

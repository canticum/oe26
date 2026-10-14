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
public class Person {

  private String name;
  private Gender gender;
  private String id;

  public Person(String id, String name) {

    this.name = name;
    this.id = id;
  }
  
  public String getId(){
  
    return this.id;
  }

  public String getName() {

    return this.name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public Gender getGender() {

    return this.gender;
  }

  public void setGender(Gender gender) {

    this.gender = gender;
  }

  public void printPerson() {

    System.out.println(this.id + "\t" + this.name + "\t" + this.gender);
  }
}

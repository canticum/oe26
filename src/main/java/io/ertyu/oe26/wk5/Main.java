package io.ertyu.oe26.wk5;

public class Main {

  public static void main(String... args) {

    Student s1 = new Student("B123456789", "Jonathan");
    s1.setGender(Gender.Male);
    s1.setStudentID("411551025");
    s1.setDept("Computer Science");

    Student s2 = new Student("A223456789", "Jennifer");
    s2.setGender(Gender.Female);
    s2.setStudentID("411551255");
    s2.setDept("Computer Science");

    System.out.println("=== printPerson() ===");
    s1.printPerson();
    s2.printPerson();

    System.out.println();

    System.out.println("=== printStudent() ===");
    s1.printStudent();
    s2.printStudent();

  }
}

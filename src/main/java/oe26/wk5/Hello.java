package oe26.wk5;

public class Hello {

  public static void main(String... args) {
    
    Student s = new Student("B1234566789", "Jonathan");
    s.printPerson();
    
    s.setStudentID("411551025");
    s.setDept("Computer Science");
    
    s.printStudent();
  }
}

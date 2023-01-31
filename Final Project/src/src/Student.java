package src;

public class Student extends Person {

    //Creating the attributes for Student
    private String course;
    private int semester;

    // Constructor without the pay as a paramenter
    public Student(int i, String n, int a, String g, String c, int s) {
        this.id = i;
        this.name = n;
        this.age = a;
        this.gender = g;
        this.course = c;
        this.semester = s;
    }

    // Constructor with the pay as a paramenter
    public Student(int i, String n, int a, String g, String c, int s, int di) {
        this.id = i;
        this.name = n;
        this.age = a;
        this.gender = g;
        this.course = c;
        this.semester = s;
        this.depId = di;
    }

    // Empty constructor
    public Student() {
    }

    //Setters
    public void setCourse(String c) {
        this.course = c;
    }

    public void setSemester(int s) {
        this.semester = s;
    }

    //Getters
    public String getCourse() {
        return this.course;
    }

    public int getSemester() {
        return this.semester;
    }
}

package src;

public class Teacher extends Person implements PayRoll {

    //Creating the attributes of Teacher
    public String degree;
    private String specialty;
    private int saleDegree;
    private double pay;

    // Constructor with the pay as a paramenter
    public Teacher(int i, String n, int a, String g, String deg, String s, double p, int di) {
        this.id = i;
        this.name = n;
        this.age = a;
        this.gender = g;
        this.degree = deg;
        this.specialty = s;
        this.pay = p;
        this.depId = di;
    }

    // Constructor without the pay as a paramenter
    public Teacher(int i, String n, int a, String g, String deg, String s, int di) {
        this.id = i;
        this.name = n;
        this.age = a;
        this.gender = g;
        this.degree = deg;
        this.specialty = s;
        this.depId = di;
    }

    // Empty Constructor
    public Teacher() {
    }

    //Setters
    public void setDegree(String deg) {
        this.degree = deg;
    }

    public void setSpecialty(String s) {
        this.specialty = s;
    }

    public void setPay() {
        this.pay = computePayRoll();
    }

    //Gettters
    public String getDegree() {
        return this.degree;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public double getPay() {
        return this.pay;
    }

    //Overrides the method computePayRoll(double d) from Interface PayRoll
    @Override
    public double computePayRoll() {
        if ("Bachelor's".equals(this.degree)) {
            saleDegree = 42;
        } else if ("Master's".equals(this.degree)) {
            saleDegree = 82;
        } else if ("PhD".equals(this.degree)) {
            saleDegree = 112;
        }
        return (36 * saleDegree * 2) * 0.76;
    }
}

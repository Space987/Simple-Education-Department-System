package src;

// Class extends from Person and implements Payroll
public class Staff extends Person implements PayRoll {

    //Creating the specific attributes of a Staff
    String duty;
    int workload;
    double pay;

    // Constructor without the pay as a paramenter
    public Staff(int i, String n, int a, String g, String d, int w, int di) {
        this.id = i;
        this.name = n;
        this.age = a;
        this.gender = g;
        this.duty = d;
        this.workload = w;
        this.depId = di;
    }

    // Constructor with the pay as a paramenter
    public Staff(int i, String n, int a, String g, String d, int w, double p, int di) {
        this.id = i;
        this.name = n;
        this.age = a;
        this.gender = g;
        this.duty = d;
        this.workload = w;
        this.pay = p;
        this.depId = di;
    }

    // Empty constructor
    public Staff() {
    }

    // Getters 
    public String getDuty() {
        return this.duty;
    }

    public double getPay() {
        return this.pay;
    }

    public int getWorkload() {
        return this.workload;
    }

    // Setters
    public void setDuty(String duty) {
        this.duty = duty;
    }

    public void setPay() {
        this.pay = computePayRoll();
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    // Method used to compute the payroll
    @Override
    public double computePayRoll() {
        return (workload * 32 * 2) * 0.85;

    }
}

package src;

public abstract class Person {

    //Creating the attributes of Person
    protected int id;
    protected String name;
    protected int age;
    protected String gender;
    protected int depId;

    //Setters
    public void setId(int i) {
        this.id = i;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setAge(int a) {
        this.age = a;
    }

    public void setGender(String g) {
        this.gender = g;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    //Getters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public int getDepId() {
        return depId;
    }
}

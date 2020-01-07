package model;

public class Pojo {
    private String Name;
    private int Age;

    public Pojo(String Name, int Age) {
        this.Name = Name;
        this.Age = Age;
    }

    public String getName() {
        return this.Name;
    }
    public int getAge() {
        return this.Age;
    }
}
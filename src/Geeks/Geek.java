package Geeks;

public class Geek {
    private String name;
    private int phoneNumber;

    public Geek(String _name, int _phone){
        this.name = _name;
        this.phoneNumber = _phone;
    }

    public String getName(){
        return this.name;
    }

    public int getPhoneNumber(){
        return this.phoneNumber;
    }
}

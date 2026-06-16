package Object;

public class Customer {

    private String name;
    private String eMail;
    private String grade;

    public Customer(String name, String eMail, String grade) {
        this.name = name;
        this.eMail = eMail;
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getEMail() {
        return eMail;
    }

    public String getGrade() {
        return grade;
    }
}

package javabean;

public class BookStore {
    private String phoneNumber;
    private String isbnNumber;
    private String btime;
    private int flag;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public void setFlag(int flag) {this.flag = flag;}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public String getBtime() {
        return btime;
    }

    public int getFlag() {return flag;}
}

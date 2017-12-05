package javabean;

public class HBookStore {
    private String phoneNumber;
    private String isbnNumber;
    private String btime;

    public HBookStore(String phoneNumber, String isbnNumber, String btime) {
        this.phoneNumber = phoneNumber;
        this.isbnNumber = isbnNumber;
        this.btime = btime;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public String getBtime() {
        return btime;
    }

}

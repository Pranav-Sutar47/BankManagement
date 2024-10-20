package org.example.finalbank;

public class Data {
    private String acNo;
    private String name;
    private String address;
    private String mobileNumber;
    private String birthDate;
    private String occupation;
    private String gender;
    private String accountOpening;
    private String uname;
    private String adharNo;
    private String accountType;

    private float balance;

    public Data(String acNo, String name, String address, String mobileNumber, String birthDate, String occupation,String gender, String accountOpening, String uname, String adharNo, String accountType, float balance) {
        this.acNo = acNo;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.gender = gender;
        this.accountOpening = accountOpening;
        this.uname = uname;
        this.adharNo = adharNo;
        this.accountType = accountType;
        this.balance = balance;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setAccountOpening(String accountOpening) {
        this.accountOpening = accountOpening;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setAdharNo(String adharNo) {
        this.adharNo = adharNo;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getAcNo() {
        return acNo;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getAccountOpening() {
        return accountOpening;
    }

    public String getUname() {
        return uname;
    }

    public String getAdharNo() {
        return adharNo;
    }

    public String getAccountType() {
        return accountType;
    }
    
    public String getGender(){
        return gender;
    }
    public float getBalance() {
        return balance;
    }
}

package com.example.finalsemproject;

public class PMPMLTable {
    private String name;
    private int age;
    private String gender;
    private String workplace;
    private String workplaceAddress;
    private String aadharNumber;
    private String busRoute;
    private String passType;
    private String startDate;
    private String endDate;
    private int passAmount;
    private String mobileNumber;
    private String email;

    public PMPMLTable() {
        // Default constructor required for Firebase
    }

    public PMPMLTable(String name, int age, String gender, String workplace, String workplaceAddress,
                      String aadharNumber, String mobileNumber,String email, String busRoute, String passType, String startDate, String endDate,int passAmount) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.workplace = workplace;
        this.workplaceAddress = workplaceAddress;
        this.aadharNumber = aadharNumber;
        this.mobileNumber = mobileNumber;
        this.email =email;
        this.busRoute = busRoute;
        this.passType = passType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.passAmount = passAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getWorkplaceAddress() {
        return workplaceAddress;
    }

    public void setWorkplaceAddress(String workplaceAddress) {
        this.workplaceAddress = workplaceAddress;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(String busRoute) {
        this.busRoute = busRoute;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPassAmount() {
        return passAmount;
    }

    public void setPassAmount(int passAmount) {
        this.passAmount = passAmount;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package xyz.toothlessos.db;

import java.io.Serializable;
import java.text.*;
import java.util.Comparator;
import java.util.Date;

public class PeopleRecord implements Comparable<PeopleRecord> {
    private String givenName;
    private String familyName;
    private String companyName;
    private String address;
    private String city;
    private String county;
    private String state;
    private String zip;
    private String phone1;
    private String phone2;
    private String email;
    private String web;
    private String birthday;

    public PeopleRecord(String givenName, String familyName,
                        String companyName, String address,
                        String city, String county,
                        String state, String zip,
                        String phone1, String phone2,
                        String email, String web, String birthday) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.companyName = companyName;
        this.address = address;
        this.city = city;
        this.county = county;
        this.state = state;
        this.zip = zip;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.web = web;
        this.birthday = birthday;
    }

    public int compareTo(PeopleRecord o) {
        // Notes on the comparison logic
        // First, compare the given name
        // If inconclusive, compare the family name
        if (this.givenName.compareTo(o.givenName) < 0) {
            return -1;
        }
        else if (this.givenName.compareTo(o.givenName) > 0) {
            return 1;
        }
        else{
            if (this.familyName.compareTo(o.familyName) < 0) {
                return -1;
            }
            else if (this.familyName.compareTo(o.familyName) > 0) {
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    public String getGivenName() {
        return this.givenName;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public String getCompanyName() { return this.companyName; }

    public String getAddress() { return this.address; }
    public String getCity() { return this.city; }
    public String getCounty() { return this.county; }
    public String getState() { return this.state; }
    public String getZip() { return this.zip; }
    public String getPhone1() { return this.phone1; }
    public String getPhone2() { return this.phone2; }
    public String getEmail() { return this.email; }
    public String getWeb() { return this.web; }
    public String getBirthday() { return this.birthday; }


    public String toString(){
        return String.format("%s %s", givenName, familyName);
    }
}


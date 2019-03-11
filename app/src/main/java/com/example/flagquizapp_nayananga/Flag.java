package com.example.flagquizapp_nayananga;

public class Flag {
    private String path;
    private String countryName;
    private String countryShortCode;


    public Flag(String countryName, String countryShortCode) {
        this.countryName = countryName;
        this.countryShortCode = countryShortCode;
    }

    public String getCountryShortCode() {
        return countryShortCode;
    }

    public void setCountryShortCode(String countryShortCode) {
        this.countryShortCode = countryShortCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

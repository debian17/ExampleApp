package com.example.citilin.testapp.ui.contacts;

 class Contact {
    private String name;
    private String phoneNumber;

     Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    String getName() {
        return name;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

}

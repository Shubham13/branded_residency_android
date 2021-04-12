package com.digivalet.brandresidential.helpers;

public enum ErrorMessage {
    none("Something is missing!"),
    name("Invalid name"),
    genericName("Name should be less than 40 characters"),
    preferredName("Invalid preferred name"),
    email("Invalid email address"),
    card("Invalid card number"),
    confirmPassword("Invalid confirm password"),
    mobileNumber("Invalid mobile number"),
    passwordSignIn("Invalid password sign in"),
    passwordSignUpCount("Password length Must be at least 8 characters"),
    passwordSignUp("Password should contain atleast one symbol, small letter, capital letter, number, special symbol."),
    idNumber("Invalid id number"),
    idType("Invalid id type"),
    address("Invalid address"),
    occupation("Invalid occupation field"),
    status("Invalid status field"),
    relationToOwner("Invalid relation field"),
    birthDay("Invalid birthday field");

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

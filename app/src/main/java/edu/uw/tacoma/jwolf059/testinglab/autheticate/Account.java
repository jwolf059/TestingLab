package edu.uw.tacoma.jwolf059.testinglab.autheticate;

import java.util.regex.Pattern;

/**
 * Created by jwolf on 11/15/2016.
 */

public class Account {
    /**
     * Email validation pattern
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private final static int PASSWORD_LEN = 6;
    private String mEmail;
    private String mPassword;


    public Account(String theEmail, String thePassword) {

        try {
            setmEmail(theEmail);
            setmPassword(thePassword);
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    public void setmEmail(String theEmail) {
        if (isValidEmail(theEmail)) {
            mEmail = theEmail;
        } else {
            throw new IllegalArgumentException("Invaild input for Email");
        }
    }

    public void setmPassword(String thePassword) {

        if (isValidPassword(thePassword)) {
            mPassword = thePassword;
        } else {
            throw new IllegalArgumentException("Invalid Password format");
        }
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    /**
     * Validates if the given input is a valid email address.
     *
     * @param email The email to validate.
     * @return {@code true} if the input is a valid email. {@code false} otherwise.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates if the given password is valid.
     * Valid password must be at last 6 characters long
     * with at least one digit and one symbol.
     *
     * @param password The password to validate.
     * @return {@code true} if the input is a valid password.
     * {@code false} otherwise.
     */
    public static boolean isValidPassword(String password) {
        boolean foundDigit = false, foundSymbol = false;
        if (password == null ||
                password.length() < PASSWORD_LEN)
            return false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i)))
                foundDigit = true;
            if (!Character.isLetterOrDigit(password.charAt(i)))
                foundSymbol = true;
        }
        return foundDigit && foundSymbol;
    }
}

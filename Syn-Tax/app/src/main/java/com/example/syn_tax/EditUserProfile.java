package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class EditUserProfile {

    public EditUserProfile(User user) {}

    public void editUserProfile(User user) {

        /*
        String Ousername = user.name;
        String Ophonenumber = user.phoneNumber;
        String Oemail = user.email;

        EditText username = (EditText) findViewById(R.id.username);
        price.setText(Ousername.toString(), TextView.BufferType.EDITABLE);

       EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        price.setText(OphoneNumber.toString(), TextView.BufferType.EDITABLE);

       EditText email = (EditText) findViewById(R.id.email);
        price.setText(Oemail.toString(), TextView.BufferType.EDITABLE);
         */
        String username = "";
        String email = "";
        String phoneNumber = "";


        user = new User(username, email, phoneNumber);
        user.contactInfo.add(username);
        user.contactInfo.add(email);
        user.contactInfo.add(phoneNumber);

    }

    //checks if edited user profile is correct
    public boolean valid() {
        boolean valid = true;
        //todo code to check if editied user profile is valid
        return valid;
    }

}

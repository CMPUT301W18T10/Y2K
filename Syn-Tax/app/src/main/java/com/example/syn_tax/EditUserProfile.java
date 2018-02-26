package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by hamdamare on 2018-02-22.
 */

public class EditUserProfile {

    private User user= new User("","","");

    public EditUserProfile(User user) {
        this.user=user;
    }

    public void editUserProfile(String username,String email,String phoneNumber) {
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

        this.user.retrieveContactInfo().add(0,username);
        this.user.retrieveContactInfo().add(1,email);
        this.user.retrieveContactInfo().add(2, phoneNumber);

    }

    //checks if edited user profile is correct
    public boolean valid() {
        boolean valid = true;
        //todo code to check if editied user profile is valid
        return valid;
    }

}

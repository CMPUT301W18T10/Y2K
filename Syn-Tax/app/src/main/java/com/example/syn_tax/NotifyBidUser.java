/*
 * Copyright (c) 2018 Term Winter 2018 . CMPUT 301. Team 43. University of Alberta. All Rights Reserved .
 * You may use , distribute, or modify the code under terms and conditions of the code of Students
 * Behaviour at University of Alberta.
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; Without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * Last Modified 05/04/18 3:19 PM
 */

package com.example.syn_tax;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by abdulazeezojetola on 2018-04-05.
 */

public class NotifyBidUser extends AppCompatActivity {

    String userID;

    Intent NotifyUser = getIntent();
    Bundle bundle = NotifyUser.getExtras();
    String Username = (String) bundle.get("username");
    String flag = (String) bundle.get("flag");
    String title;
    String message;
    protected static String CHANNEL_ID = UUID.randomUUID().toString();

    public void NotifyUser(String Username) throws ExecutionException {
        ElasticSearchController.getUsers users = new ElasticSearchController.getUsers();
        users.execute(Username);


        try {
            //flag = "Declined";
            User tempUser = users.get().get(0);
            User NotifiedUser = users.get().get(0);
            if (flag == "Declined"){
                String title = flag;
                String message = "Sorry, Your Bid Has Been Declined!";

            }
            else if (flag == "Accepted"){
                String title = flag;
                String message = "Congratulations, Your Bid Has Been Accepted!";
            }
            //NotifiedUser.setNotificationMsg(title, message);
            ElasticSearchController.updateUser(tempUser,NotifiedUser);
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}


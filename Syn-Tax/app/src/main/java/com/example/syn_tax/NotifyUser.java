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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by abdulazeezojetola on 2018-04-05.
 */

public class NotifyUser extends AppCompatActivity {

    String userID;

//    Intent NotifyUser = getIntent();
//    Bundle bundle = NotifyUser.getExtras();
//    String Username = (String) bundle.get("username");
//    String flag = (String) bundle.get("flag");

    protected String flag;
    protected String title;
    protected String message;
    protected User user;
    protected int NotificationID;

    protected static String CHANNEL_ID = UUID.randomUUID().toString();

    public  NotifyUser()  {
    }

    public void Notify(User user ,String flag, String taskn) throws ExecutionException{
        User NotifiedUser = user;
        String title= taskn;
        if (flag == "Declined"){
            String message = "Sorry, Your Bid Has Been Declined!";
        }
        else if (flag == "Accepted"){
            String message = "Congratulations, Your Bid Has Been Accepted!";
        }
        else if (flag== "Coded"){
            String message = "Code for Task Is Now Available!";
        }
        else{
            String message= "Bid for Your Task Has Been Placed";
        }

        //Wait a bit to update
        long num=300;
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        NotifiedUser.setNotificationMsg(title, message);
        ElasticSearchController.updateUser(user,NotifiedUser);
    }




    public static void Display(Context context){
        //Notify user
        User currentUser;
        ElasticSearchController.getUsers user=new ElasticSearchController.getUsers();
        user.execute(LoginActivity.thisuser.getUsername());

        try {
            currentUser=user.get().get(0);
            //Display the notfication for that current user

            String display="";
            for(int i=0;i<currentUser.getNotification().size();i++){
                display= display + currentUser.getNotification().get(i)+ "\n";
            }

            currentUser.clearNotifications();
            //Connect elastic search and clear
            ElasticSearchController.updateUser(LoginActivity.thisuser, currentUser);

            if(!display.equals("")){
                Toast toasty = Toast.makeText ( context, display,  Toast.LENGTH_LONG);
                toasty.setGravity ( Gravity.TOP, 0, 300 );
                toasty.show ();
            }

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
    }
}


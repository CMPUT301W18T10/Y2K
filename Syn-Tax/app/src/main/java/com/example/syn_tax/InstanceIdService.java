///*
// * Copyright (c) 2018 Term Winter 2018 . CMPUT 301. Team 43. University of Alberta. All Rights Reserved .
// * You may use , distribute, or modify the code under terms and conditions of the code of Students
// * Behaviour at University of Alberta.
// * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
// * General Public License as published by the Free Software Foundation, either version 3 of the
// * License, or (at your option) any later version. This program is distributed in the hope that it
// * will be useful, but WITHOUT ANY WARRANTY; Without even the implied warranty of MERCHANTABILITY
// * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
// * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
// * Last Modified 05/04/18 5:44 PM
// */
//
//package com.example.syn_tax;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Created by abdulazeezojetola on 2018-04-05.
// */
//
//public class InstanceIdService extends FirebaseInstanceIdService {
//    String userId;
//    public InstanceIdService(String userId){
//        super();
//        this.userId = userId;
//    }
//
//
//    @Override
//    public void onTokenRefresh(){
//        super.onTokenRefresh();
//        String token = userId;
//
//        // sends this token to the serve
//        sendToServer(token);
//    }
//
//    private void sendToServer(String token){
//
//        try{
//            URL url = new URL("http://cmput301.softwareprocess.es:8080/syn-tax");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//
//            connection.setRequestMethod("POST");
//
//            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
//
//            dos.writeBytes("token=" + token);
//            dos.close();
//
//            connection.connect();
//
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//
//        }
//    }
//}
//
//
//

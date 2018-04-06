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
 * Last Modified 06/04/18 7:12 AM
 */

package com.example.syn_tax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FinishedCodesRequester extends AppCompatActivity {
    private static final String FILE_NAME = "code.txt";
    private static final Object DEBUGTAG = "JWP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_codes_requester);
        loadSavedFile();
    }



    //reads the file from the input stream
    public void loadSavedFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(fis)));
            EditText codes = (EditText) findViewById(R.id.codeSent);
            String line;

            while((line = bufferedReader.readLine())!=null) {
                fis.close();
                codes.append(line);
                codes.append("\n");
            }



        } catch (Exception e) {
            Log.d((String) DEBUGTAG, "Unable to read file");
        }



    }
}

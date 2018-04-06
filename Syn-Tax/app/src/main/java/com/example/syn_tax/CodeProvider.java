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
 * Last Modified 24/03/18 2:41 PM
 */

/*Citations: https://www.youtube.com/watch?v=EcfUkjlL9RI
            March 25 2018
 */

package com.example.syn_tax;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeProvider extends AppCompatActivity {
    //this allows the user to write code in an edittext then save it
    private static final String FILE_NAME = "code.txt";

    EditText codes;
    Button save,load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_provider);

        codes = findViewById(R.id.txt);
        save =  findViewById(R.id.savebtn);
        load = findViewById(R.id.loadbtn);
    }

    public void save(View v) {
        String code  = codes.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            fos.write(code.getBytes());
            codes.getText().clear();
            Toast.makeText(this,"Saved to "+getFilesDir()+"/"+FILE_NAME,Toast.LENGTH_LONG).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void load(View v) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text = bufferedReader.readLine()) != null) {
                sb.append(text).append("\n");
            }
            codes.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void send(View view) {
        Intent intent = new Intent();
        intent.putExtra(FILE_NAME,FinishedCodesRequester.class);
        startActivity(intent);
        Toast.makeText(CodeProvider.this,"CODE SENT!",Toast.LENGTH_SHORT);

    }

}

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
 * Last Modified 3/16/18 2:33 PM
 */

package com.example.syn_tax;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//this activity allows a user to view a task and edit it


class EditTaskActivity extends AppCompatActivity {

    private ListView taskList;


    private TextView editTitle, editDesc, editStatus, username;
    private Button editLocation, updateButton,EditphotoView;
    private String str_title, str_Editdesc, str_Status;
    private String id;
    private Task editTask;
    private User user = LoginActivity.thisuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        editTitle = findViewById(R.id.editTaskTitle);
        editDesc = findViewById(R.id.editDescription);
        editLocation = findViewById(R.id.editLocation);
        ImageView editphoto = findViewById(R.id.editPhotoView);

        updateButton = findViewById(R.id.updateBtn);
        editStatus = findViewById(R.id.status);
        username = findViewById(R.id.username);



    }

    protected void onStart() {
        super.onStart();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ElasticSearchController.connected()) {
                    if (addTask()) {
                        str_title = editTitle.getText().toString();
                        str_Editdesc = editDesc.getText().toString();
                        str_Status = editStatus.getText().toString();

                        //TODO get the photo and get the location

                        editTask = new Task(str_title, str_Editdesc, user, str_Status);

                        ElasticSearchController.addTasks uploadTask = new ElasticSearchController.addTasks();
                        uploadTask.execute(editTask);
                        updateButton();
                    } else {
                        Toast toasty = Toast.makeText(com.example.syn_tax.EditTaskActivity.this, "Title already exists!", Toast.LENGTH_LONG);
                        toasty.setGravity(Gravity.CENTER, 0, 0);
                        toasty.show();
                    }
                } else {
                    Toast toasty = Toast.makeText(com.example.syn_tax.EditTaskActivity.this, "Cannot edit task, internet connection lost!", Toast.LENGTH_LONG);
                    toasty.setGravity(Gravity.CENTER, 0, 0);
                    toasty.show();
                }

            }
        });
    }

    //TODO check to see if this works
    private boolean addTask() {
        //get the tasks
        ElasticSearchController.getTasks allTasks = new ElasticSearchController.getTasks();
        allTasks.execute(str_title);
        //get the users
        // ElasticSearchController.getUsers allUsers = new ElasticSearchController.getUsers();
        //allUsers.execute(str_username);
        //get the list of users and tasks
        ArrayList<Task> taskList;
        //ArrayList<User> userList;
        try {
            taskList = allTasks.get();
        } catch (Exception e) {
            taskList = new ArrayList<Task>();
        }

        if (taskList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //TODO update info on home homeactivity
    private void updateButton() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}

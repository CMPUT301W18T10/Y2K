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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * @author Aidan Paetsch
 * @date 03/11/2018
 * This class is an adapter for displaying an individual task inside of a list view
 */
public class TaskAdapter extends ArrayAdapter<Task> {
    private ArrayList<Task> tasks;
    private User requester;

    /**
     * Takes in a context and an array list of tasks and sets a list view of tasks
     * @param context
     * @param tasks
     */
    public TaskAdapter(Context context, ArrayList<Task> tasks){
        super(context, R.layout.task_list_item, tasks);
    }

    @Override
    /**
     * Gets the view and sets all of its attributes
     * @param pos position in the list of a click
     *
     */
    public View getView(final int pos, View convertView, ViewGroup parent){
        //layout inflater for a task item
        LayoutInflater inf = LayoutInflater.from(getContext());
        final View data = inf.inflate(R.layout.task_list_item, parent, false);

        // gets the data to be displayed for a list item based on its position in the array
        final String taskTitle = getItem(pos).getTitle();
        requester = getItem(pos).getRequester();
        final String taskUsername= requester.getUsername ();
        final String loginUsername= LoginActivity.thisuser.getUsername ();
        String taskStatus = getItem(pos).getStatus();

        //Text views of a task item
        TextView task_title = (TextView) data.findViewById(R.id.task_name);
        TextView task_stat = (TextView) data.findViewById(R.id.task_status);

        // layout for a singular task
        LinearLayout thisButton = (LinearLayout) data.findViewById(R.id.task_item);

        //setting the textviews of a list item
        task_title.setText(taskTitle);
        task_stat.setText(taskStatus);

        // click listener for a list item
        thisButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // for a task of a requester
                if(loginUsername.equals ( taskUsername )){
                    edit_or_view(v,pos,EditTaskActivity.class, taskTitle, "edit");
                }
                // for a task of a provider
                else{
                    edit_or_view(v,pos,ViewTaskProviderActivity.class, taskTitle, "view");
                }
            }
        });
        return data;
    }
    /**
     * @param pos position of a list item
     * @param c class for the intent
     */
    private void edit_or_view(final View v,final int pos, final Class c, final String title, String todo){
        //USER CAN only view/edit a task that they have bidded on
        if (todo== "view"){
            Intent intent = new Intent(getContext(),c);
            intent.putExtra(HomeActivity.POINTER,String.valueOf(pos));
            ((Activity)getContext()).startActivityForResult(intent,0);
        }

        //USER CAN EDIT AND DELETE ITS OWN TASK
        else{

            /**
             * DELETE or EDIT From the list of tasks when a task is CLicked on
             */

            AlertDialog.Builder btn = new AlertDialog.Builder(getContext ());
            btn.setMessage("DELETE OR EDIT A TASK");

            //Set the integer position1 to be constant so it can not be reassigned
            final int position1=pos;

            //DELETE the Task
            btn.setNeutralButton ("Delete", new DialogInterface.OnClickListener() {
                @Override
                /**
                 * DELETE the Task
                 */
                public void onClick(DialogInterface dialog, int which) {
                    ElasticSearchController.deleteTask delete= new ElasticSearchController.deleteTask();
                    delete.execute ( title);
                    Intent intent= new Intent(getContext (), HomeActivity.class);
                    ((Activity)getContext()).startActivityForResult(intent,0);
                }
            });

            //EDIT the Task
            btn.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                /**
                 * EDIT the task
                 */
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getContext(),c);
                    intent.putExtra ("state", "edit");
                    intent.putExtra(HomeActivity.POINTER,String.valueOf(pos));
                    ((Activity)getContext()).startActivityForResult(intent,0);
                }
            });

            //EDIT the Task
            btn.setNegativeButton ("View", new DialogInterface.OnClickListener() {
                @Override
                /**
                 * EDIT the task
                 */
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getContext(),c);
                    intent.putExtra ("state", "view");
                    intent.putExtra(HomeActivity.POINTER,String.valueOf(pos));
                    ((Activity)getContext()).startActivityForResult(intent,0);
                }
            });

            // Call to show the Alert Message
            btn.show();


        }
    }
}

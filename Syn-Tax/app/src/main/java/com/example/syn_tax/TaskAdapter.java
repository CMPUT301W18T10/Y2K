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
import java.util.concurrent.ExecutionException;

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
     * @param context The current context
     * @param tasks An array list of Tasks
     */
    public TaskAdapter(Context context, ArrayList<Task> tasks){
        super(context, R.layout.task_list_item, tasks);
    }

    /**
     * Gets the task information, Inflates the task list item layout and populates that layout with
     * The data from the task
     * @param pos The position in the tasks array list for a task
     * @param convertView The current view
     * @param parent The current view group
     * @return Returns the data for a task layout
     */
    @Override
    // Gets the view and sets all of its attributes
    public View getView(final int pos, View convertView, ViewGroup parent){
        //layout inflater for a task item
        LayoutInflater inf = LayoutInflater.from(getContext());
        final View data = inf.inflate(R.layout.task_list_item, parent, false);

        // gets the data to be displayed for a list item based on its position in the array
        final String taskTitle = getItem(pos).getTitle();
        requester = getItem(pos).getRequester();

        final String taskUsername= requester.getUsername ();
        final String loginUsername= LoginActivity.thisuser.getUsername ();
        final String taskStatus = getItem(pos).getStatus();
//
//        Log.e("e",taskStatus);
//        Log.e("ee", taskUsername);
//        Log.e("eee", LoginActivity.thisuser.getUsername ());

                //If assigned show the username and the amount for that task
        if (taskStatus.equals ( "assigned" )) {
            TextView title1 = data.findViewById ( R.id.usernameTitle );
            TextView title2 = data.findViewById ( R.id.bidTitle );
            title1.setVisibility ( View.VISIBLE );
            title2.setVisibility ( View.VISIBLE );

            final TextView task_username = data.findViewById ( R.id.username );
            TextView task_bid = data.findViewById ( R.id.bid );
            task_bid.setVisibility ( View.VISIBLE );
            task_username.setVisibility ( View.VISIBLE );

            //Set the fields
            if (taskUsername.equals ( LoginActivity.thisuser.getUsername () )) {
                title1.setText ( "PUsername:" );
                if (getItem ( pos ).getProvider () == null) {
                    task_username.setText ( "NONE" );
                } else {
                    task_username.setText ( getItem ( pos ).getProvider ().getUsername () );
                }
            } else {
                title2.setText ( "My Accecpted Bid:" );
                title1.setText ( "RUsername:" );
                task_username.setText ( getItem ( pos ).getRequester ().getUsername () );
            }


            try {
                if (getItem ( pos ).getLowestBid () == null) {
                    task_bid.setText ( "NONE" );
                } else {
                    Double amount = getItem ( pos ).getLowestBid ().getBidAmount ();
                    task_bid.setText ( amount.toString () );
                }

            } catch (ExecutionException e) {
                e.printStackTrace ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        //Check if task status is  bidded and were provider
        else if (taskStatus.equals ( "bidded" ) && !taskUsername.equals ( LoginActivity.thisuser.getUsername () )) {
            TextView title1 = data.findViewById ( R.id.usernameTitle );
            TextView title2 = data.findViewById ( R.id.bidTitle );
            TextView myBid = data.findViewById ( R.id.myBid );
            title1.setVisibility ( View.VISIBLE );
            title2.setVisibility ( View.VISIBLE );
            myBid.setVisibility ( View.VISIBLE );

            TextView task_username = data.findViewById ( R.id.username );
            TextView task_bid = data.findViewById ( R.id.bid );
            TextView myBidAmount = data.findViewById ( R.id.myBidAmount );

            task_bid.setVisibility ( View.VISIBLE );
            task_username.setVisibility ( View.VISIBLE );
            myBidAmount.setVisibility ( View.VISIBLE );

            title1.setText ( "RUsername:" );
            title2.setText ( "Lowest Bid:" );
            task_username.setText ( taskUsername );
            try {
                if (getItem ( pos ).getLowestBid () == null) {
                    task_bid.setText ( "NONE" );
                } else {
                    Double amount = getItem ( pos ).getLowestBid ().getBidAmount ();
                    task_bid.setText ( amount.toString () );
                }

            } catch (ExecutionException e) {
                e.printStackTrace ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            ArrayList<Bid> allBids = new ArrayList<Bid> ();
            ElasticSearchController.getBids bids = new ElasticSearchController.getBids ();
            bids.execute ( "", LoginActivity.thisuser.getUsername () );
            try {
                allBids = bids.get ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            } catch (ExecutionException e) {
                e.printStackTrace ();
            }

            for (int i = 0; i < allBids.size (); i++) {
                if (allBids.get ( i ).getTask ().getTitle ().equals ( taskTitle )) {
                    Double myAmount = allBids.get ( i ).getBidAmount ();
                    myBidAmount.setText ( myAmount.toString () );
                    break;
                }
            }
        }

        //Text views of a task item
        TextView task_title = (TextView) data.findViewById ( R.id.task_name );
        TextView task_stat = (TextView) data.findViewById ( R.id.task_status );

        // layout for a singular task
        LinearLayout thisButton = (LinearLayout) data.findViewById ( R.id.task_item );

        //setting the textviews of a list item
        task_title.setText ( taskTitle );
        task_stat.setText ( taskStatus );

        // click listener for a list item
        thisButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // for a task of a requester
                if(loginUsername.equals ( taskUsername )) {
                    edit_or_view ( v, pos, EditTaskActivity.class, taskTitle, "edit", taskStatus );
                }
                else{
                    // for a task of a Provider
                    edit_or_view(v,pos,ViewTaskProviderActivity.class, taskTitle, "view", taskStatus);
                }
            }
        });
        return data;
    }



    /**
     *
     * @param v The current view
     * @param pos Position of a task list item
     * @param c Class for the intent
     * @param title Title of the
     * @param todo String of what is being done
     * @param status status of a task
     */
    private void edit_or_view(final View v,final int pos, final Class c, final String title, String todo, final String status){
        AlertDialog.Builder btn = new AlertDialog.Builder(getContext ());
        //USER CAN only view/edit a task that they have bidded on
        if (todo == "view"){
            if(status.equals ( "assigned" )){
                btn.setMessage("VIEW ASSIGNED TASK");
                btn.setPositiveButton("VIEW/SUBMIT", new DialogInterface.OnClickListener() {

                    //On the click of an item that is to be viewed
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(),c);
                        intent.putExtra(HomeActivity.POINTER,String.valueOf(pos));
                        intent.putExtra ( "status", status );
                        intent.putExtra ( "title", title );
                        intent.putExtra ( "sss", "Home" );
                        ((Activity)getContext()).startActivityForResult(intent,0);
                    }
                });
            }

            else {
                btn.setMessage ( "EDIT BID" );
                btn.setPositiveButton ( "EDIT", new DialogInterface.OnClickListener () {
                    @Override
                    /**
                     * Edit the task
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent ( getContext (), c );
                        intent.putExtra ( HomeActivity.POINTER, String.valueOf ( pos ) );
                        intent.putExtra ( "status", status );
                        intent.putExtra ( "title", title );
                        intent.putExtra ( "sss", "Home" );
                        ((Activity) getContext ()).startActivityForResult ( intent, 0 );
                    }
                } );
            }
            btn.show();
        }

        //USER CAN EDIT AND DELETE ITS OWN TASK
        else{


            //DELETE or EDIT From the list of tasks when a task is CLicked on


            btn.setMessage ( "DELETE OR VIEW OR EDIT A TASK" );

            //Set the integer position1 to be constant so it can not be reassigned
            final int position1 = pos;

            //DELETE the Task
            btn.setNeutralButton ( "Delete", new DialogInterface.OnClickListener () {
                @Override
                /**
                 * DELETE the Task
                 */
                public void onClick(DialogInterface dialog, int which) {
                    ElasticSearchController.deleteTask delete = new ElasticSearchController.deleteTask ();
                    delete.execute ( title );
                    Intent intent = new Intent ( getContext (), HomeActivity.class );
                    ((Activity) getContext ()).startActivityForResult ( intent, 0 );
                }
            } );

            //view the Task
            btn.setNegativeButton ( "View", new DialogInterface.OnClickListener () {
                @Override
                /**
                 * view task
                 */
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent ( getContext (), c );
                    intent.putExtra ( "state", "view" );
                    intent.putExtra ( "status", status );
                    intent.putExtra ( "sss", "Home" );
                    intent.putExtra ( HomeActivity.POINTER, String.valueOf ( pos ) );
                    ((Activity) getContext ()).startActivityForResult ( intent, 0 );
                }
            } );

            //EDIT the Task
            btn.setPositiveButton ( "Edit", new DialogInterface.OnClickListener () {
                @Override
                /**
                 * EDIT the task
                 */
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent ( getContext (), c );
                    intent.putExtra ( "state", "edit" );
                    intent.putExtra ( "status", status );
                    intent.putExtra ( "sss", "Home" );
                    intent.putExtra ( HomeActivity.POINTER, String.valueOf ( pos ) );
                    ((Activity) getContext ()).startActivityForResult ( intent, 0 );
                }
            } );

            // Call to show the Alert Message
            btn.show();

        }
    }
}

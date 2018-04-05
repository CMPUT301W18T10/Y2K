
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
 * Last Modified 4/3/18 8:50 PM
 */

package com.example.syn_tax;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by aapae on 4/3/2018.
 */

public class SearchAdapter extends ArrayAdapter<Task> {
    private ArrayList<Task> tasks;
    private String lowestbid;

    public SearchAdapter(Context context, ArrayList<Task> tasks){
        super(context, R.layout.search_list_item, tasks);
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inf = LayoutInflater.from(getContext());
        final View data = inf.inflate(R.layout.search_list_item, parent, false);


        final String taskUsername = getItem(pos).getRequester().getUsername();
        final String taskStatus = getItem(pos).getStatus();
        final String taskTitle = getItem(pos).getTitle();
        if (taskStatus.equals("bidded")){
            lowestbid = "get the lowest bid here";
        }
        else{
            lowestbid = "NONE";
        }

        TextView tTitle = data.findViewById(R.id.task_title);
        TextView tUser = data.findViewById(R.id.task_user);
        TextView tStatus = data.findViewById(R.id.task_status);
        TextView tLowestBid = data.findViewById(R.id.task_lowest_bid);

        tTitle.setText(taskTitle);
        tUser.setText(taskUsername);
        tStatus.setText(taskStatus);
        tLowestBid.setText(lowestbid);

        LinearLayout thisButton = data.findViewById(R.id.my_search_item);

        thisButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: where the click goes
                // for a task of a requester

                Intent intent = new Intent(getContext(),ViewTaskProviderActivity.class);
                intent.putExtra(SearchActivity.POINTER,String.valueOf(pos));
                intent.putExtra ( "status", taskStatus );
                intent.putExtra ( "title", taskTitle );
                intent.putExtra ( "sss", "search" );
                ((Activity)getContext()).startActivityForResult(intent,0);
            }
        });

        return data;
    }
}

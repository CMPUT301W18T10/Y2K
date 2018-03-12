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
 * Created by aapae on 3/11/2018.
 * This class might have problems with clicking items,
 * unsure of how the getItem(pos) will react to getting split into
 * two separate lists
 */

public class AdapterTask extends ArrayAdapter<Task> {
    private ArrayList<Task> tasks;
    public AdapterTask(Context context, ArrayList<Task> tasks){
        super(context, R.layout.task_list_item, tasks);
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inf = LayoutInflater.from(getContext());
        final View data = inf.inflate(R.layout.task_list_item, parent, false);
        // gets the data to be displayed for a list item based on its position in the array
        String taskTitle = getItem(pos).getTitle();
        String requester = getItem(pos).getRequester();
        String provider = getItem(pos).getProvider();
        Bid bid = getItem(pos).returnBids().get(0);//probably an array list later
        String descript = getItem(pos).getDescription();

        TextView task_title = (TextView) data.findViewById(R.id.tasktitle);
        TextView task_provider = (TextView) data.findViewById(R.id.provider);
        TextView task_bid = (TextView) data.findViewById(R.id.cost);
        TextView task_description = (TextView) data.findViewById(R.id.description);
        LinearLayout thisButton = (LinearLayout) data.findViewById(R.id.distributelist)

        if(placeholder.username == requester) {
            thisButton = (LinearLayout) data.findViewById(R.id.requestlist);
        }
        task_title.setText(taskTitle);
        task_provider.setText(provider);
        task_bid.setText(bid.get(0));//unsure here...
        task_description.setText(descript);
        thisButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(Login.username == requester){
                    edit_or_view(pos,AddTaskActivity.class);
                }
                else{
                    edit_or_view(pos,ViewTaskProviderActivity.class);
                }
            }
        });
        return data;
    }
    private void edit_or_view(int pos, Class c){
        Intent intent = new Intent(getContext(),c);
        intent.putExtra(/*pointer*/,String.valueOf(pos));
        ((Activity)getContext()).startActivityForResult(intent,0);
    }
}

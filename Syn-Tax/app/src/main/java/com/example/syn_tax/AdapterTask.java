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

import java.util.ArrayList;

/**
 * @author Aidan Paetsch
 * @date 03/11/2018
 */
public class AdapterTask extends ArrayAdapter<Task> {
    private ArrayList<Task> tasks;
    private String requester;
    public AdapterTask(Context context, ArrayList<Task> tasks){
        super(context, R.layout.task_list_item, tasks);
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        //layout inflater for a task item
        LayoutInflater inf = LayoutInflater.from(getContext());
        final View data = inf.inflate(R.layout.task_list_item, parent, false);

        // gets the data to be displayed for a list item based on its position in the array
        String taskTitle = getItem(pos).getTitle();
        requester = getItem(pos).getRequester();
        String provider = getItem(pos).getProvider();
        double bid = (getItem(pos).returnBids().get(0)).getBidAmount();//probably an array list later
        String descript = getItem(pos).getDescription();

        //Text views of a task item
        TextView task_title = (TextView) data.findViewById(R.id.tasktitle);
        TextView task_provider = (TextView) data.findViewById(R.id.provider);
        TextView task_bid = (TextView) data.findViewById(R.id.cost);
        TextView task_description = (TextView) data.findViewById(R.id.description);

        // layout for a singular task
        LinearLayout thisButton = (LinearLayout) data.findViewById(R.id.task_item);

        //setting the textviews of a list item
        task_title.setText(taskTitle);
        task_provider.setText(provider);
        task_bid.setText(String.valueOf(bid));//unsure here...
        task_description.setText(descript);

        // click listener for a list item
        thisButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // for a task of a requester
                if(LoginActivity.username == requester){
                    edit_or_view(pos,AddTaskActivity.class);
                }
                // for a task of a provider
                else{
                    edit_or_view(pos,ViewTaskProviderActivity.class);
                }
            }
        });
        return data;
    }
    /**
     * @param pos position of a list item
     * @param c class for the intent
     */
    private void edit_or_view(int pos, Class c){
        Intent intent = new Intent(getContext(),c);
        intent.putExtra(HomeActivity.POINTER,String.valueOf(pos));
        ((Activity)getContext()).startActivityForResult(intent,0);
    }
}

package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-22.
 */



public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList( ArrayList<Task> tasklist){
        this.tasks=tasklist;
    }

    public ArrayList<Task> getTasks(){
        return tasks;
    }
}

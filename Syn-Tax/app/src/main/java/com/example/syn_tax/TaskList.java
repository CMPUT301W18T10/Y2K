package com.example.syn_tax;

import java.util.ArrayList;

/**
 * Created by Hamsemare on 2018-02-22.
 */


public class TaskList {

    private ArrayList<Task> tasks= new ArrayList<Task>();

    public TaskList(){}

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasTask(Task task) {
        for (int i=0; i<tasks.size(); i++){
            if(tasks.get(i)==task){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public Task getTask(int i){
        return tasks.get(i);
    }

    public void deleteTask(Task task){
        tasks.remove(task);
    }

    public int getPos(Task testtaskR1) {
        for (int i=0; i<tasks.size(); i++){
            if(tasks.get(i)==testtaskR1){
                return i;
            }
        }
        return -1;
    }

    public void editTask(int num, Task testtaskR2) {
        tasks.add(num, testtaskR2);
    }
}

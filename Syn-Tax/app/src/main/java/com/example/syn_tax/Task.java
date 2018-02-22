package com.example.syn_tax;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hamsemare on 2018-02-21.
 */

public class Task {
    private String TaskName;
    private String TaskObjective;
    private String TaskStatus;
    private Date TaskDate;
    private Double  TaskBid;
    private ArrayList<Double> TaskBids = new ArrayList<Double>();


    public Task(String Name, String Objective, String Status, Double Bid) {
        this.TaskName = Name;
        this.TaskObjective = Objective;
        this.TaskStatus = Status;
        this.TaskBid = Bid;
        //this.TaskDate = Date;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getTaskObjective() {
        return TaskObjective;
    }

    public void setTaskObjective(String taskObjective) {
        TaskObjective = taskObjective;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public Date getTaskDate() {
        return TaskDate;
    }

    public void setTaskDate(Date taskDate) {
        TaskDate = taskDate;
    }

    public void AddTaskBids(double Bid){
        TaskBid = Bid;
        TaskBids.add(0,TaskBid);
    }

    public Double getTaskBid(int count){

        return TaskBids.get(0);

    }

}

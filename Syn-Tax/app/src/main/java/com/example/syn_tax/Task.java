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
    private Double  CurrentBid;
    private ArrayList<Double> TaskBids = new ArrayList<Double>();
    private String BidStatus;
    private ArrayList<String> BidStatuses = new ArrayList<String>();


    public Task(String Name, String Objective, String Status, Double Bid) {
        this.TaskName = Name;
        this.TaskObjective = Objective;
        this.TaskStatus = Status;
        AddTaskBids(0,Bid);
        this.CurrentBid = getTaskBid();
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

    public void AddTaskBids(int pos, double Bid){
        CurrentBid = Bid;
        TaskBids.add(pos,CurrentBid);
    }

    public Double getTaskBid(){

        return TaskBids.get(0);

    }

    public void setBidStatus(int pos, String bidStatus){
        if (pos >=1) {
            for (int i = 0; i < (pos - 1); i++) {
                BidStatuses.set(i, bidStatus);
            }
        }
    }

    public void clearBids(int pos) {
        if (pos >= 1){
            for (int i = 0; i < (pos-1); i++){
                if (BidStatuses.get(i) != "Accepted"|| BidStatuses.get(i) != "Undecided"){
                    TaskBids.remove(i);
                    BidStatuses.remove(i);
                }
            }
        }
    }
}

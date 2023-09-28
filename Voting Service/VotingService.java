/* 
 * Author: Palmer Du
 * Class: CS3560 OOP
 * Date last modified: 9/28/23
 * - This program is designed to run with SimulationDriver.java
 * - This stores poll question and student attributes like questinon answers, student votes, and student IDs.
 */

import java.util.ArrayList;

class Question {
    String question;
    boolean MC = false;
    ArrayList<String> answers = new ArrayList<String>();

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String q) {
        question = q;
    }  
    public boolean getMC() {
        return MC;
    }
    public void setMC(boolean setting) {
        MC = setting;
    }
    public ArrayList<String> getAnswers() {
        return answers;
    }
    public void addAnswer(String a) {
        answers.add(a);
    }
    //given a list of students, sum up their votes and display them
    public void showResults(ArrayList<Student> studentList) {
        int[] results = new int[getAnswers().size()];
        for (int i = 0; i < studentList.size(); i++) {
            for (int j = 0; j < results.length; j++) {
                if (studentList.get(i).getVote().get(j) == true) {
                    results[j]++;
                }
            }
        }
        System.out.println("\nVoting Results:\nTotal number of participants: " + studentList.size());
        for (int k = 0; k < results.length; k++) {
            System.out.printf("    " + getAnswers().get(k) + ": %1s (%.4s%%)\n", results[k], (100*results[k]/(studentList.size()+0.0)));
        }
    }
}

class Student {
    ArrayList<Boolean> vote = new ArrayList<Boolean>();
    String ID;

    public String getID() {
        return ID;
    }
    public void setID(String id) {
        ID = id;
    }
    public ArrayList<Boolean> getVote() {
        return vote;
    }
    public void setVote(boolean[] nums) {
        vote.clear();
        for (int i = 0; i < nums.length; i++) {
            vote.add(nums[i]);
        }
    }
    public void setVote(boolean num) {
        vote.clear();
        vote.add(num);
    }
}

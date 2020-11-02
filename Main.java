package com.company;
import java.io.*;
import java.util.Scanner;

class Student{
    private String studName ;
    private String studRollNo ;
    private int[] attend = new int[4];
    public Student(String studRollNo, String studName,int[] ar) {
        this.studRollNo = studRollNo;
        this.studName = studName ;
        for(int i=0;i<4;i++)
            attend[i]=ar[i];
    }
}

class Course{
    public static int numberOfSubjects=0;
    private String subName ;
    private String subCode ;
    public Student[] obj = new Student[100];

    Course(String sub){
        String[] temp_lst  = sub.split(" - ");
        subCode = temp_lst[0];
        subName = temp_lst[1];
    }

    public static void setNumberOfSubjects() throws IOException {
        BufferedReader sc = new BufferedReader(new FileReader("D:\\3rd Sem psg tech\\OOPS\\Attend.csv"));
        String[] data_lst;
        String line= sc.readLine();
        data_lst = line.split(",");
        for (String s : data_lst) {
            if (!s.equals("")) {
                numberOfSubjects += 1;
            }
        }
        sc.close();
    }

    public void getData(){

    }
}

class Main{
    public static void main(String[] args)   throws IOException{
        Course.setNumberOfSubjects();
        Course[] sub_lst = new Course[Course.numberOfSubjects];

        //BufferedReader sc = new BufferedReader(new FileReader("C:\\Users\\Teja\\Downloads\\Attend.csv"));
        BufferedReader sc = new BufferedReader(new FileReader("C:\\Users\\Teja\\Downloads\\Attend.csv"));
        String line= sc.readLine();
        String[] data_lst;
        data_lst = line.split(",");
        System.out.println(data_lst.length);
        //reading subject names form file to class and instantiating sub list
        int index=0;
        for(String s: data_lst) {
            if()
                sub_lst[index] = new Course(data_lst[index]);
        }

        /*sc.readLine();
        sc.readLine();
        sc.readLine();
        line = sc.readLine();

        int studno=0;
        while(!line.equals("")){
            data_lst = line.split(",");
            int[] temp = new int[4];
            int count=0;
            while(count<Course.numberOfSubjects){
                for(int i=2+(Course.numberOfSubjects*4), index=0 ;i<6+(Course.numberOfSubjects*4);i++,index++)
                    temp[index]= Integer.parseInt(data_lst[i]);

                sub_lst[count].obj[studno] = new Student(data_lst[0],data_lst[1],temp);
            }
            studno++;
        }
        sc.close();*/
    }
}
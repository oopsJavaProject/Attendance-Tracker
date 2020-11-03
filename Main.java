package com.company;
import java.io.*;

class Student{
    private String studName ;
    private String studRollNo ;
    private int[] attend = new int[4];
    public Student(String studRollNo, String studName,int[] ar) {
        this.studRollNo = studRollNo;
        this.studName = studName ;
        System.arraycopy(ar, 0, attend, 0, 4);
    }

    public String toString(){
        return studName+" "+studRollNo+" "+attend[0]+" "+attend[1]+" "+attend[2]+" "+attend[3];
    }
}

class Course{
    public static int numberOfSubjects=0;
    private String subName ;
    private String subCode ;
    private Student[] obj = new Student[100];

    Course(String sub){
        String[] temp_lst  = sub.split(" - ");
        subCode = temp_lst[0];
        subName = temp_lst[1];
    }

    public static void setNumberOfSubjects() throws IOException,FileNotFoundException {
        BufferedReader sc = new BufferedReader(new FileReader("D:\\3rd Sem psg tech\\OOPS\\Attend.csv"));
        String[] data_lst;
        String line= sc.readLine();
        data_lst = line.split(",");
        for (String s : data_lst) {
            if (!s.equals("")) {
                numberOfSubjects += 1;
            }
        }
        numberOfSubjects--;
        sc.close();
    }

    public void setAttendance(String name,String rollnum,int[] temp,int studno){
        obj[studno] = new Student(name, rollnum, temp);
    }
    public void display(){
        System.out.println( subName+" "+subCode);
        for(Student s:obj)
            if(s!=null)
                System.out.println(s);
    }
}

class Readdata {
    static Course[] sub_lst;


    static void readData() throws IOException{
        try{
            Course.setNumberOfSubjects();}
        catch (Exception e){
            System.out.println(e);
        }
        sub_lst = new Course[Course.numberOfSubjects];

        BufferedReader sc = new BufferedReader(new FileReader("C:\\Users\\Teja\\Downloads\\Attend.csv"));
        String line = sc.readLine();
        String[] data_lst;
        data_lst = line.split(",");
        //reading subject names form file to class and instantiating sub list
        int index = 0;
        for (String s : data_lst) {
            if (!s.isEmpty() && !s.equals("September 2020")) {
                sub_lst[index] = new Course(s);
                index++;
            }
        }

        sc.readLine();
        sc.readLine();
        sc.readLine();
        line = sc.readLine();
        int studno = 0;
        while (line != null) {
            data_lst = line.split(",");
            if (data_lst.length == 0)
                break;
            int[] temp = new int[4];
            int count = 0;
            while (count < Course.numberOfSubjects) {
                for (int i = 2 + (count * 4), ind = 0; i < 6 + (count * 4); i++, ind++)
                    temp[ind] = Integer.parseInt(data_lst[i]);
                sub_lst[count].setAttendance(data_lst[0], data_lst[1], temp,studno);
                count++;
            }
            line = sc.readLine();
            studno++;
        }
        sc.close();
    }
}

class Main{
    public static void main(String[] args)   throws IOException{
        Readdata.readData();
        Readdata.sub_lst[0].display();
    }
}

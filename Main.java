package com.company;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.util.Scanner;


class Student{
    private String studName ,studRollNo ;
    private int[] attend = new int[4];

    public Student(String studRollNo, String studName,int[] ar) {
        this.studRollNo = studRollNo;
        this.studName = studName ;
        System.arraycopy(ar, 0, attend, 0, 4);
    }

    public Student(Student obj){
        studName = obj.studName;
        studRollNo = obj.studRollNo;
        System.arraycopy(obj.attend,0,attend,0,4);
    }

    public String toString(int flag){
        if(flag==1)
            return studName+","+studRollNo+","+attend[0]+","+attend[1]+","+attend[2]+","+attend[3];
        else
            return ","+attend[0]+","+attend[1]+","+attend[2]+","+attend[3];
    }

    public boolean check(String name, String roll){
        return studName.equalsIgnoreCase(name) && studRollNo.equalsIgnoreCase(roll);
    }

    public String toString(){
        return studName+" "+studRollNo+" "+attend[0]+" "+attend[1]+" "+attend[2]+" "+attend[3];
    }

    public String getName() {
        return studName+"-"+studRollNo;
    }

    public void incrementVal(int flag){
        attend[flag]++;
    }
}

class StudentLogIn{
    public static Student obj=null;

    public static void getinfo(){
        System.out.printf("%-40cStudent Login\n",' ');
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Username :");
        String userName = in.nextLine();
        System.out.print("Enter Password :");
        String password = in.nextLine();
        while(!StudentLogIn.checkMatch(userName, password)){
            System.out.println("INVALID USERNAME PASSWORD");
            StudentLogIn.getinfo();
        }
    }

    private static boolean checkMatch(String name, String roll){
        for(Course sub:Data.course){
            if(sub.getInfoStud(name,roll)!=null) {
                obj=sub.getInfoStud(name,roll);
                sub.display(0);
                System.out.println(obj.toString());
            }
        }
        return obj != null;
    }
}

class StaffLogin{
    private static int ind;

    static void getinfo(){
        System.out.printf("%-40cStaff Login\n",' ');
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Username :");    // course code
        String userName = in.nextLine();
        System.out.print("Enter Password :");    // course code PSGTECH
        String password = in.nextLine();
        ind=StaffLogin.checkMatch(userName, password);
        while(ind==-1){
            System.out.println("INVALID USERNAME PASSWORD");
            StaffLogin.getinfo();
        }
        StaffLogin.menu();
    }

    private static int checkMatch(String name,String pass){
        int ind=0;
        for(Course obj: Data.course){
            if(obj.check(name,pass))
                return ind;
        }
        return -1;
    }

    private static void menu(){
        System.out.printf("%40cStaff\n",' ');
        System.out.println("1. View");
        System.out.println("2. Edit student record");
        System.out.println("3. Attendance entry");
        Scanner in = new Scanner(System.in);
        int ch;
        ch = in.nextInt();
        if(ch==1) Data.course[ind].display(1);
        else if(ch==2) changeEntry(ind);
        else if(ch==3) attendanceEntry(ind);
        else {
            System.out.println("Invalid input");
            StaffLogin.menu();
        }
    }

    private static void attendanceEntry(int ind){
        System.out.printf("P - present\nL-late\nE-excused adsence\nU-unexcused adsence\n");
        boolean inpFlag=false;
        for(int i=0;i<100;i++){
            String ch;
            if(inpFlag){
                System.out.println("invalid input");
                inpFlag=false;
            }
            Scanner in = new Scanner(System.in);
            Student obj = Data.course[ind].getInfoStud(i);
            if(obj==null)
                break;
            System.out.printf("%d. %s%-30c",i+1,obj.getName(),' ');
            ch=in.nextLine();
            if(ch.equalsIgnoreCase("p")) obj.incrementVal(0);
            else if(ch.equalsIgnoreCase("l")) obj.incrementVal(1);
            else if(ch.equalsIgnoreCase("e")) obj.incrementVal(2);
            else if(ch.equalsIgnoreCase("u")) obj.incrementVal(3);
            else {
                inpFlag=true;
                i--;
            }
            Data.course[ind].setAttendance(obj,i);
            System.out.println("Entries recorded");
        }
    }

    private static void changeEntry(int ind){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter student name        : ");
        String Name = in.nextLine();
        System.out.print("Enter student roll number : ");
        String rollNum = in.nextLine();
        int studInd = Data.course[ind].getStudind(Name,rollNum);
        if(studInd==-1){
            System.out.println("Invalid input");
            changeEntry(ind);
        }
        System.out.println("Enter new values for Present, absent and unexcused absence respectively");
        int ar[] = new int[4];
        for(int i=0;i<4;i++)
            ar[i] = in.nextInt();
        Data.course[ind].setAttendance(rollNum,Name,ar,studInd);

        System.out.println("Entries recorded");
    }
}

class Course{
    public static int numberOfSubjects=0;
    private String subName ,subCode;
    private static String month;
    private Student[] obj = new Student[100];

    Course(String sub){
        String[] temp_lst  = sub.split(" - ");
        subCode = temp_lst[0];
        subName = temp_lst[1];
    }

    public static void setNumberOfSubjects() throws IOException {
        BufferedReader sc = new BufferedReader(new FileReader("C:\\Users\\Teja\\Downloads\\Attend.csv"));
        String[] data_lst;
        String line= sc.readLine();
        data_lst = line.split(",");
        for (String s : data_lst) {
            if(numberOfSubjects==0)
                month = s;
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

    public void setAttendance(Student obj,int ind){
        this.obj[ind]= new Student(obj);
    }

    public boolean check(String name, String pass){
        return subCode.equals(name) && pass.equals(subCode+"PSGTECH");
    }

    public void display(int flag){
        System.out.println(month);
        System.out.print(subName+" "+subCode+"\n");
        if(flag!=0){
            for(Student s:obj)
                if(s!=null)
                    System.out.println(s);
        }
    }

    public Student getInfoStud(int ind){
        return obj[ind];
    }

    public Student getInfoStud(String name, String rollnum){
        for(int i=0;i<100;i++)
            if(obj[i]!=null)
                if(obj[i].check(name,rollnum))
                    return obj[i];
        return null;
    }

    public int getStudind(String name, String rollnum){
        for(int i=0;i<100;i++)
            if(obj[i]!=null)
                if(obj[i].check(name,rollnum))
                    return i;
        return -1;
    }
}

class Data {
    static Course[] course;

    static void readData() throws IOException{
        try{
            Course.setNumberOfSubjects();}
        catch (Exception e){
            System.out.println(e);
        }
        course = new Course[Course.numberOfSubjects];

        BufferedReader sc = new BufferedReader(new FileReader("C:\\Users\\Teja\\Downloads\\Attend.csv"));
        String line = sc.readLine();
        String[] data_lst;
        data_lst = line.split(",");
        //reading subject names form file to class and instantiating sub list
        int index = 0,count=0;
        for (String s : data_lst) {
            if (!s.isEmpty() && count!=0) {
                course[index] = new Course(s);
                index++;
            }
            count++;
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
            count = 0;
            while (count < Course.numberOfSubjects) {
                for (int i = 2 + (count * 4), ind = 0; i < 6 + (count * 4); i++, ind++)
                    temp[ind] = Integer.parseInt(data_lst[i]);
                course[count].setAttendance(data_lst[0], data_lst[1], temp,studno);
                count++;
            }
            line = sc.readLine();
            studno++;
        }
        sc.close();
    }

    static void writeData() throws  IOException{
        BufferedReader inp = new BufferedReader(new FileReader("C:\\Users\\Teja\\Downloads\\Attend.csv"));
        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Teja\\Downloads\\Attend_copy.csv"));
        String line;
        int count=0;
        while((line=inp.readLine())!=null){
            out.write(line + "\n");
            count++;
            if(count==4)
                break;
        }

        Student obj;
        int flag=0;
        for(int i=0;i<100;i++) {
            count=0;
            StringBuilder s = new StringBuilder();
            while (count < Course.numberOfSubjects) {
                obj = course[count].getInfoStud(i);
                if(obj==null){
                    flag=1;
                    break;
                }
                if (count == 0)
                    s.append(obj.toString(1));
                else
                    s.append(obj.toString(0));
                count++;
            }
            if(flag==1)
                break;
            out.write(s.toString() +'\n');
        }
        inp.close();
        out.close();
    }

}

class Main{
    public static void main(String[] args)   throws IOException{
        Data.readData();
        //StudentLogIn.getinfo();
        //StaffLogin.getinfo();
        Data.writeData();
    }
}

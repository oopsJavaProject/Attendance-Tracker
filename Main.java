package pack2;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class Student{
    private String studName ,studRollNo ;
    private int[] attend = new int[4];

    public Student(String studRollNo, String studName,int[] ar) {
        this.studRollNo = studRollNo;
        this.studName = studName ;
        System.arraycopy(ar, 0, attend, 0, 4);
    }

    public String toString(int flag){
        if(flag==1)
            return studName+","+studRollNo+","+attend[0]+","+attend[1]+","+attend[2]+","+attend[3];
        else
            return ","+attend[0]+","+attend[1]+","+attend[2]+","+attend[3];
    }

    public boolean check(String name, String roll){
        if(studName.equalsIgnoreCase(name) && studRollNo.equalsIgnoreCase(roll))
            return true;
        return false;
    }

    public String toString(){
        return studName+" "+studRollNo+" "+attend[0]+" "+attend[1]+" "+attend[2]+" "+attend[3];
    }

    public int[] getAttend(){
        return attend;
    }

    public String nameRoll(int flag){
        if(flag == 1)
            return studName;
        else if(flag == 2)
            return studRollNo;
        else
            return null;
    }


}

class StudentLogIn{
    static Student obj=null;

    static void getinfo(JTextField jt1, JTextField jt2) throws IOException {

        String userName = jt1.getText();
        String password = jt2.getText();
        if(!StudentLogIn.checkMatch(userName, password)){
            JOptionPane.showMessageDialog(null, "INVALID USERNAME PASSWORD");
        }
    }

    private static boolean checkMatch(String name, String roll){
        String[] heading = {"Course","Present","Late","Excused absence","Unexcused absence"};
        String[][] data = new String[Data.course.length +1][5];
        data[0] = heading;
        int j =1 ;
        for(Course sub:Data.course){
            int i =0 ;
            if(sub.getInfoStud(name,roll)!=null) {
                obj=sub.getInfoStud(name,roll);
                if(i==0){
                    data[j][i] = sub.getCouseName();
                    i++;
                }
                if(i!=0) {
                    for(int att : obj.getAttend()) {
                        data[j][i] = String.valueOf(att);
                        //System.out.println(data[j][i]);
                        i++;
                    }
                }
                /*sub.display(0);
                System.out.println(obj.toString());*/
            }
            j++;
        }
        if(obj != null){
            JFrame newJf = new JFrame("Student LogIn");
            JLabel nameJl = new JLabel("Name : "+obj.nameRoll(1));
            JLabel rollJl = new JLabel("Roll No : "+obj.nameRoll(2));
            nameJl.setBounds(30, 10, 400, 20);
            rollJl.setBounds(30, 30, 100, 20);
            newJf.add(nameJl); newJf.add(rollJl);

            JTable jTab = new JTable(data, heading);
            jTab.setBounds(30, 60, 1000, 300);
            newJf.add(jTab);
            //JScrollPane sp=new JScrollPane(jTab);
            //newJf.add(sp);

            newJf.setSize(1100, 500);
            newJf.setLayout(null);
            newJf.setVisible(true);
        }
        return obj != null;
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
        BufferedReader sc = new BufferedReader(new FileReader("D:\\3rd Sem psg tech\\OOPS\\Attend.csv"));
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

    public void display(int flag){
        System.out.println(month);
        System.out.print(subName+" "+subCode+"\n");
        if(flag!=0){
            for(Student s:obj)
                if(s!=null)
                    System.out.println(s);
        }
    }

    public String getCouseName(){
        return subCode+" "+subName;
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

        BufferedReader sc = new BufferedReader(new FileReader("D:\\3rd Sem psg tech\\OOPS\\Attend.csv"));
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
        BufferedReader inp = new BufferedReader(new FileReader("D:\\3rd Sem psg tech\\OOPS\\Attend.csv"));
        BufferedWriter out = new BufferedWriter(new FileWriter("D:\\3rd Sem psg tech\\OOPS\\Attend_copy.csv"));
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
        //Data.course[0].display(1);
        //StudentLogIn.getinfo();
        Data.writeData();


        JFrame jf = new JFrame("Attendance Tracker");

        JButton jb = new JButton("Submit");
        JLabel jl1, jl2;
        jl1 = new JLabel("Username ");
        jl2 = new JLabel("Password ");

        JTextField jt1, jt2 ;
        jt1 = new JTextField();
        jt2 = new JPasswordField();
        jt1.setBounds(500, 200, 200, 20);
        jt2.setBounds(500, 240, 200, 20);
        jf.add(jt1);jf.add(jt2);


        jl1.setBounds(400, 200, 200, 20);
        jl2.setBounds(400, 240, 200, 20);
        jf.add(jl1);jf.add(jl2);

        JRadioButton rb1, rb2 ;
        ButtonGroup bg ;
        rb1 = new JRadioButton("Student");
        rb2 = new JRadioButton("Staff");
        rb1.setBounds(450, 300, 70, 20);
        rb2.setBounds(450, 350, 70, 20);
        bg = new ButtonGroup();
        bg.add(rb1);bg.add(rb2);
        jf.add(rb1);jf.add(rb2);

        jb.setBounds(450, 400, 100, 50);
        jf.add(jb);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = jt1.getText();
                String password = jt2.getText();
                if(rb1.isSelected()){
                    try {
                        StudentLogIn.getinfo(jt1, jt2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else if(rb2.isSelected()){

                }
                else JOptionPane.showMessageDialog(null, "Select either Student or Staff");
            }
        });


        jf.setSize(1100, 500);
        jf.setLayout(null);
        jf.setVisible(true);
    }
}
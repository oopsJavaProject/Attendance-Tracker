package pack2;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


class Student{
    private final String studName;
    private final String studRollNo ;
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

    public String getName() {
        return studRollNo+"-"+studName;
    }

    public void incrementVal(int flag){
        attend[flag]++;
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

            }/*sub.display(0);
                System.out.println(obj.toString());*/
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
            //JTableHeader header = jTab.getTableHeader();
            //header.setBackground(Color.LIGHT_GRAY);
            //JScrollPane sp=new JScrollPane(jTab);
            //newJf.add(sp);

            newJf.setSize(1100, 500);
            newJf.setLayout(null);
            newJf.setVisible(true);
        }
        return obj != null;
    }
}

class StaffLogin {
    private static int ind;

    static void getinfo(JTextField jt1, JTextField jt2) {
        // course code
        String userName = jt1.getText();
        // course code PSGTECH
        String password = jt2.getText();
        ind = StaffLogin.checkMatch(userName, password);
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "INVALID USERNAME PASSWORD");
        } else {
            StaffLogin.menu();
        }
    }

    private static int checkMatch(String name, String pass) {
        int ind = 0;
        for (Course obj : Data.course) {
            if (obj.check(name, pass))
                return ind;
            ind++;
        }
        return -1;
    }

    private static void menu() {
        JFrame stF = new JFrame("Staff LogIn");
        JButton view = new JButton("View Attendance");
        JButton edit = new JButton("Edit student record");
        JButton entry = new JButton("Attendance entry");
        view.setBounds(490, 100, 100, 40);
        edit.setBounds(490, 200, 100, 40);
        entry.setBounds(490, 300, 100, 40);

        stF.add(edit);
        stF.add(view);
        stF.add(entry);
        stF.setSize(1100, 500);
        stF.setLayout(null);
        stF.setVisible(true);

        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.course[ind].display();
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEntry(ind);
            }
        });

        entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attendanceEntry(ind);
            }
        });
        /*System.out.println("1. View");
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
        }*/

}


    private static void attendanceEntry(int ind){
        JFrame entF = new JFrame("Attendance Entry");
        JLabel inst = new JLabel("P - present      L-late      E-excused adsence      U-unexcused adsence ");
        inst.setBounds(50, 10, 600, 50);
        entF.add(inst);

        entF.setSize(1100, 750);
        entF.setLayout(null);
        entF.setVisible(true);

        JRadioButton[] p = new JRadioButton[100];
        JRadioButton[] l = new JRadioButton[100];
        JRadioButton[] e = new JRadioButton[100];
        JRadioButton[] u = new JRadioButton[100];
        ButtonGroup[] atten = new ButtonGroup[100];

        //System.out.print("P - present\nL-late\nE-excused adsence\nU-unexcused adsence\n");

        int  space =-1;
        for(int i=0;i<100;i++) {

            //Scanner in = new Scanner(System.in);
            Student obj = Data.course[ind].getInfoStud(i);
            if (obj == null) {
                break;
            }
            if (i%30==0) space+=1;

            JLabel name = new JLabel(obj.getName());
            name.setBounds(5+(space*312), (i%30) * 20 + 80, 150, 20);
            entF.add(name);

            //System.out.printf("%d. %s%-30c",i+1,obj.getName(),' ');
            p[i] = new JRadioButton("P");
            p[i].setBounds(190+(space*300), (i%30) * 20 + 80, 34, 20);
            entF.add(p[i]);
            l[i] = new JRadioButton("L");
            l[i].setBounds(220+(space*300), (i%30) * 20 + 80, 32, 20);
            entF.add(l[i]);
            e[i] = new JRadioButton("E");
            e[i].setBounds(250+(300*space), (i%30) * 20 + 80, 32, 20);
            entF.add(e[i]);
            u[i] = new JRadioButton("U");
            u[i].setBounds(280+(300*space), (i%30) * 20 + 80, 34, 20);
            entF.add(u[i]);
            atten[i] = new ButtonGroup();
            atten[i].add(p[i]);
            atten[i].add(l[i]);
            atten[i].add(e[i]);
            atten[i].add(u[i]);
        }
        JButton finish = new JButton("Finish");
        finish.setBounds(650, 10,100,50);
        entF.add(finish);
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {
                for(int i=0; i<100; i++) {
                    boolean inpFlag=false;
                    /*if (inpFlag) {
                        JOptionPane.showMessageDialog(null, "Attendance not alloted for all");
                        inpFlag = false;
                        break;
                    }*/
                    //Scanner in = new Scanner(System.in);
                    Student obj = Data.course[ind].getInfoStud(i);
                    if (obj == null)
                        break;

                    if (p[i].isSelected()) obj.incrementVal(0);
                    else if (l[i].isSelected()) obj.incrementVal(1);
                    else if (e[i].isSelected()) obj.incrementVal(2);
                    else if (u[i].isSelected()) obj.incrementVal(3);
                    else {
                        JOptionPane.showMessageDialog(null, "Attendance not alloted for all");
                        break;
                    }
                    Data.course[ind].setAttendance(obj, i);
                    //System.out.println("Entries recorded");
                }
            }
        });

    }

    private static void changeEntry(int ind){
        JFrame chan = new JFrame("Change Entry");
        JLabel name = new JLabel("Enter student name ");
        name.setBounds(400, 200, 200, 20);
        chan.add(name);

        JLabel roll = new JLabel("Enter student roll number ");
        roll.setBounds(400, 240, 200, 20);
        chan.add(roll);

        JTextField jtN = new JTextField();
        jtN.setBounds(550, 200, 200, 20);
        chan.add(jtN);
        JTextField jtR = new JTextField();
        jtR.setBounds(550, 240, 200, 20);
        chan.add(jtR);

        JButton check = new JButton("submit");
        check.setBounds(450, 400, 100, 50);
        chan.add(check);

        chan.setSize(1100, 500);
        chan.setLayout(null);
        chan.setVisible(true);


        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ec) {
                
                String Name = jtN.getText();
                String rollNum = jtR.getText();
                int studInd = Data.course[ind].getStudind(Name,rollNum);
                if(studInd==-1){
                    JOptionPane.showMessageDialog(null, "Student not found");
                }
                studInd = Data.course[ind].getStudind(Name,rollNum);

                if (studInd != -1) {
                    JFrame chan1 = new JFrame("Change entry");
                    chan1.setSize(1100, 500);
                    chan1.setLayout(null);
                    chan1.setVisible(true);
                    Student obj = Data.course[ind].getInfoStud(studInd);

                    JLabel info = new JLabel("Old values for Present, Late, ExcusedAbsence and UnexcusedAbsence are" + obj.toString(0) + " respectively");
                    info.setBounds(10, 30, 650, 20);
                    chan1.add(info);

                    JLabel info2 = new JLabel("Enter new values");
                    info2.setBounds(150, 60, 150, 20);
                    chan1.add(info2);

                    JLabel p = new JLabel("Present");
                    p.setBounds(50, 100, 100, 20);
                    chan1.add(p);
                    JTextField pt = new JTextField();
                    pt.setBounds(210, 105, 30, 15);
                    chan1.add(pt);

                    JLabel l = new JLabel("Late");
                    l.setBounds(50, 140, 100, 20);
                    chan1.add(l);
                    JTextField lt = new JTextField();
                    lt.setBounds(210, 145, 30, 15);
                    chan1.add(lt);

                    JLabel e = new JLabel("ExcusedAbsence");
                    e.setBounds(50, 180, 100, 20);
                    chan1.add(e);
                    JTextField et = new JTextField();
                    et.setBounds(210, 185, 30, 15);
                    chan1.add(et);

                    JLabel u = new JLabel("UnexcusedAbsence");
                    u.setBounds(50, 220, 100, 20);
                    chan1.add(u);
                    JTextField ut = new JTextField();
                    ut.setBounds(210, 225, 30, 15);
                    chan1.add(ut);

                    //System.out.println("Enter new values for Present, absent and unexcused absence respectively");
                    int[] ar = new int[4];
                    ar[0] = Integer.parseInt(pt.getText());
                    ar[1] = Integer.parseInt(lt.getText());
                    ar[2] = Integer.parseInt(et.getText());
                    ar[3] = Integer.parseInt(ut.getText());
                    JButton submit = new JButton("Submit");
                    submit.setBounds(100, 200, 80, 40);
                    chan1.add(submit);
                    submit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String Name = jtN.getText();
                            String rollNum = jtR.getText();
                            int studInd = Data.course[ind].getStudind(Name, rollNum);
                            Data.course[ind].setAttendance(rollNum, Name, ar, studInd);
                        }
                    });
                }

            }
        });
        String Name = jtN.getText();
        String rollNum = jtR.getText();
        int studInd = Data.course[ind].getStudind(Name,rollNum);
        if (studInd != -1) {
            JFrame chan1 = new JFrame("Change entry");
            chan1.setSize(1100, 500);
            chan1.setLayout(null);
            chan1.setVisible(true);
            Student obj = Data.course[ind].getInfoStud(studInd);

            JLabel info = new JLabel("Old values for Present, Late, ExcusedAbsence and UnexcusedAbsence are" + obj.toString(0) + " respectively");
            info.setBounds(10, 60, 250, 20);
            chan1.add(info);

            JLabel info2 = new JLabel("Enter new values");
            info2.setBounds(50, 80, 80, 20);
            chan1.add(info2);

            JLabel p = new JLabel("Present");
            p.setBounds(50, 100, 80, 20);
            chan1.add(p);
            JTextField pt = new JTextField();
            pt.setBounds(130, 100, 30, 30);
            chan1.add(pt);

            JLabel l = new JLabel("Late");
            l.setBounds(50, 120, 80, 20);
            chan1.add(l);
            JTextField lt = new JTextField();
            lt.setBounds(130, 120, 30, 30);
            chan1.add(lt);

            JLabel e = new JLabel("ExcusedAbsence");
            e.setBounds(50, 140, 80, 20);
            chan1.add(e);
            JTextField et = new JTextField();
            et.setBounds(130, 140, 30, 30);
            chan1.add(et);

            JLabel u = new JLabel("UnexcusedAbsence");
            u.setBounds(50, 160, 80, 20);
            chan1.add(u);
            JTextField ut = new JTextField();
            ut.setBounds(130, 160, 30, 30);
            chan1.add(ut);

            //System.out.println("Enter new values for Present, absent and unexcused absence respectively");
            int[] ar = new int[4];
            ar[0] = Integer.parseInt(pt.getText());
            ar[1] = Integer.parseInt(lt.getText());
            ar[2] = Integer.parseInt(et.getText());
            ar[3] = Integer.parseInt(ut.getText());
            JButton submit = new JButton("Submit");
            submit.setBounds(100, 200, 80, 40);
            chan1.add(submit);
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String Name = jtN.getText();
                    String rollNum = jtR.getText();
                    int studInd = Data.course[ind].getStudind(Name, rollNum);
                    Data.course[ind].setAttendance(rollNum, Name, ar, studInd);
                }
            });
        }
        //System.out.println("Entries recorded");
    }
}


class Course{
    public static int numberOfSubjects=0;
    protected final String subName;
    protected final String subCode;
    protected static String month;
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

    public void display(){
        JFrame view = new JFrame("View attendance");
        JPanel panel = new JPanel();
        JLabel nameJl = new JLabel("Course Name : "+subName);
        JLabel codeJl = new JLabel("Course Code : "+subCode);
        nameJl.setBounds(30, 10, 400, 20);
        codeJl.setBounds(30, 30, 200, 20);
        view.add(nameJl); view.add(codeJl);

        String[] heading = {"Student Name","Roll No","Present","Late","Excused absence","Unexcused absence"};
        String[][] data = new String[100][6];

        int j=0;
        for(Student s:obj) {
            if (s != null){
               data[j] = s.toString(1).split(",");
               j++;
            }
        }

        JTable jTab = new JTable(data, heading);
        jTab.setBounds(30, 60, 1000, 500);
        //view.add(jTab);

        JTableHeader header = jTab.getTableHeader();
        header.setBackground(Color.lightGray);
        JScrollPane pane = new JScrollPane(jTab);
        jTab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        panel.add(pane);
        view.add(panel);
        view.setUndecorated(true);
        view.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);


        view.setSize(1100, 500);
        //view.setLayout(null);
        view.setVisible(true);
        //view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*System.out.println(month);
        System.out.print(subName+" "+subCode+"\n");
        if(flag!=0){
            for(Student s:obj)
                if(s!=null)
                    System.out.println(s);
        }*/
    }
    public void setAttendance(Student obj,int ind){
        this.obj[ind]= new Student(obj);
    }

    public boolean check(String name, String pass){
        return subCode.equals(name) && pass.equals(subCode+"PSGTECH");
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
                if(rb1.isSelected()){
                    try {
                        StudentLogIn.getinfo(jt1, jt2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else if(rb2.isSelected()){
                    StaffLogin.getinfo(jt1, jt2);

                }
                else JOptionPane.showMessageDialog(null, "Select either Student or Staff");
            }
        });


        jf.setSize(1100, 500);
        jf.setLayout(null);
        jf.setVisible(true);
    }
}
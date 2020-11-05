package pack;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;





class Student{
    private String studName,studRollNo ;
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
        if(studName.equals(name) && studRollNo.equals(roll))
            return true;
        return false;

    }

    public String toString(){
        return studName+" "+studRollNo+" "+attend[0]+" "+attend[1]+" "+attend[2]+" "+attend[3];

    }
}
class StudentLogIn{
    static int studNo;

    static boolean checkMatch(String name, String roll){
        studNo = -1 ;
        for(Course sub:Readdata.course){
            if(sub.getInfoStud(name,roll)!=-1) {
                studNo = sub.getInfoStud(name, roll);
                System.out.println(sub.displayStud(studNo));
            }
        }
        if(studNo == -1)
            return false;
        else
            return true;
    }
}
class Course{
    public static int numberOfSubjects=0;

    private String subName ;
    private String subCode ;

    private String subName, subCode ;

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
                System.out.println(s.toString(1));

    }

    public Student getInfoStud(int ind){
        return obj[ind];
    }


    public int getInfoStud(String name, String rollnum){
        for(int i=0;i<Readdata.studno;i++)
            if(obj[i].check(name,rollnum))
                return i;

        return -1;
    }

    public Student displayStud(int ind){
        System.out.println("\n"+subName+" "+subCode);
        return obj[ind];


    public Student getInfoStud(String name, String rollnum){
        for(int i=0;i<100;i++)
            if(obj[i].check(name,rollnum))
                return obj[i];
        
        return null;

    }
}

class Readdata {
    static Course[] course;
    static int studno;

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
    static void readData() throws IOException{
        try{
            Course.setNumberOfSubjects();
        }
        catch (Exception e){
            System.out.println(e);
        }
        course = new Course[Course.numberOfSubjects];

        BufferedReader sc = new BufferedReader(new FileReader("C:\\Users\\Teja\\Downloads\\Attend.csv"));
        String line = sc.readLine();
        String[] data_lst;
        data_lst = line.split(",");

        //reading subject names form file to class and instantiating sub list

        int index = 0;
        for (String s : data_lst) {
            if (!s.isEmpty() && !s.equals("September 2020")) {
                course[index] = new Course(s);
                index++;
            }
        }

        sc.readLine();
        sc.readLine();
        sc.readLine();
        line = sc.readLine();
        studno = 0;
        int studno= 0;
        while (line != null) {
            data_lst = line.split(",");
            if (data_lst.length == 0)
                break;
            int[] temp = new int[4];
            int count = 0;
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
    static void writeData() throws  IOException,FileNotFoundException{
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
            String s = new String();
            while (count < Course.numberOfSubjects) {
                obj = course[count].getInfoStud(i);
                if(obj==null){
                    flag=1;
                    break;
                }
                if (count == 0)
                    s=s+obj.toString(1);
                else
                    s=s+obj.toString(0);
                count++;
            }
            if(flag==1)
                break;
            out.write(s+'\n');
        }
        inp.close();
        out.close();
    }
}

class Main{
    public static void main(String[] args)   throws IOException{
        Scanner in = new Scanner(System.in);

        Readdata.readData();
        //Readdata.course[0].display();
        Readdata.writeData();
        System.out.print("Enter Username :");
        String userName = in.nextLine();
        System.out.print("Enter Password :");
        String password = in.nextLine();
        while(!StudentLogIn.checkMatch(userName, password)){
                System.out.println("INVALID USERNAME PASSWORD");
            System.out.print("Enter Username :");
            userName = in.nextLine();
            System.out.print("Enter Password :");
            password = in.nextLine();
        }
    }

    }
    static void writeData() throws  IOException,FileNotFoundException{
        BufferedReader inp = new BufferedReader(new FileReader("C:\\Users\\Teja\\Downloads\\Attend.csv"));
        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Teja\\Downloads\\Attend_edit.csv"));
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
            String s = new String();
            while (count < Course.numberOfSubjects) {
                obj = course[count].getInfoStud(i);
                if(obj==null){
                    flag=1;
                    break;
                }
                if (count == 0)
                    s=s+obj.toString(1);
                else
                    s=s+obj.toString(0);
                count++;
            }
            if(flag==1)
                break;
            out.write(s+'\n');
        }
        inp.close();
        out.close();
    }
}

class Main{
    public static void main(String[] args)   throws IOException{
        Readdata.readData();
        Readdata.course[6].display();
        Readdata.writeData();
    }

}

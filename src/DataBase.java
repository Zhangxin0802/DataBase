import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static void main(String args[]) throws IOException {
        List<Student> studentList = inputStudentInfo(new File("student.txt"));
        List<Course> courseList = inputCourseInfo(new File("course.txt"));
        List<SC> scList = inputSCInfo(new File("sc.txt"));


        /*检索选修数据库原理课程的学生学号，姓名和成绩*/
        String info = "数据库原理";
        Course coursetemp = SelectCourseByCname(courseList, info).get(0);//得到课程号
        List<SC> sctemp = SelectSCByCno(scList, coursetemp.getCno());//得到学生学号信息


        System.out.println("选修数据库原理的学生信息");

//        List<Student> stutemp = new ArrayList<Student>();

        for (SC x : sctemp) {
            Student temp = SelectStuBySno(studentList, x.getSno()).get(0);

            System.out.println(x);
            System.out.println(temp);
            System.out.println();
        }

        /*检索刘明同学不学的课程号,课程名，学分*/
        String name = "刘明";
        /*检索学生学号*/
        String stunum = SelectSnoBySname(studentList, name);
        /*检索学生选修课程*/
        List<SC> SCTEMP = selectSCBySno(scList, stunum);

        System.out.println("刘明同学不学的课程信息如下");

        List<Course> cc = selectCourseBySC(courseList, SCTEMP);
        for (Course x : cc) {
            System.out.println(x);
            System.out.println();
        }

    }

    public static List<Student> inputStudentInfo(File file) throws IOException {

        /*将学生信息从txt文件中读入*/
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));

        List<Student> studentList = new ArrayList<Student>();

        for (int i = 0; ; i++) {
            String string = br.readLine();
            if (string == null || string.equals("")) {
                break;
            }
            String[] retval = string.split("\\s+");//将数据切割，按空格分开
            //将学生数据存入
            Student stu = new Student();//空指针问题

            stu.setSno(retval[0]);
            stu.setSname(retval[1]);
            stu.setAge(retval[2]);
            stu.setSex(retval[3]);
            stu.setSdept(retval[4]);

            studentList.add(stu);
        }
        br.close();
        return studentList;
    }

    public static List<Course> inputCourseInfo(File file) throws IOException {

        /*将课程信息从txt文件中读入*/
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));

        List<Course> courseList = new ArrayList<Course>();

        for (int i = 0; ; i++) {
            String string = br1.readLine();
            if (string == null || string.equals("")) {
                break;
            }
            String[] retval1 = string.split("\\s+");//将数据切割，按空格分开
            // 将课程数据存入
            Course course = new Course();
            course.setCno(retval1[0]);
            course.setCname(retval1[1]);
            course.setCredit(retval1[2]);

            courseList.add(course);
        }
//        System.out.println("共有课程数" + course_num);
        br1.close();
        return courseList;

    }

    public static List<SC> inputSCInfo(File file) throws IOException {

        /*将选课信息从txt文件中读入*/
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
        List<SC> scList = new ArrayList<SC>();
        for (int i = 0; ; i++) {
            String string = br2.readLine();
            if (string == null || string.equals("")) {
                break;
            }

            String[] retval2 = string.split("\\s+");//将数据切割，按空格分开
            //将学生选课数据存入
            SC sc = new SC();

            sc.setSno(retval2[0]);
            sc.setCno(retval2[1]);
            sc.setGrade(retval2[2]);

            scList.add(sc);
        }

//        System.out.println("选课记录有" + sc_num);
        br2.close();
        return scList;
    }

    public static List<Course> SelectCourseByCname(List<Course> courseList, String Cname) {

        /*通过课程名在课程信息表中找到课程号*/

        List<Course> temp = new ArrayList<Course>();


        for (Course x : courseList) {
            if (Cname.equals(x.getCname())) {
                temp.add(x);
            }
        }
        return temp;
    }

    public static List<SC> SelectSCByCno(List<SC> scList, String Cno) {

        /*通过课程号在选课信息表中找到选课的学生成绩*/

        List<SC> temp = new ArrayList<SC>();

        for (SC x : scList) {
            if (Cno.equals(x.getCno())) {
                temp.add(x);
            }
        }
        return temp;
    }

    public static List<Student> SelectStuBySno(List<Student> studentList, String Sno) {
        /*通过学号在学生信息表找到学生信息*/
        List<Student> temp = new ArrayList<Student>();

        for (Student x : studentList) {
            if (Sno.equals(x.getSno())) {
                temp.add(x);
            }
        }


        return temp;
    }

    public static String SelectSnoBySname(List<Student> studentList, String name) {
        /*通过学生姓名找到学生学号*/
        String temp = new String();
        for (Student x : studentList) {
            if (name.equals(x.getSname())) {
                temp = x.getSno();
                return temp;
            }
        }
        return null;
    }

    public static List<SC> selectSCBySno(List<SC> scList, String sno) {
        /*通过学生学号找到学生选修的课程*/
        List<SC> temp = new ArrayList<SC>();
        for (SC x : scList) {
            if (sno.equals(x.getSno())) {
                temp.add(x);
            }
        }
        return temp;
    }

    public static List<Course> selectCourseBySC(List<Course> courseList, List<SC> scList) {
        //遍历将已知学生选课表中课程与课程表中信息比较，将不同的课程（即学生未选的课）存入临时表中并返回
        // 把原表学了的去掉
        List<Course> temp = courseList.subList(0, courseList.size());

        for (int i = 0; i < courseList.size(); i++) {
            for (int j = 0; j < scList.size(); j++) {
                if (courseList.get(i).getCno().equals(scList.get(j).getCno())) {
                    temp.remove(courseList.get(i));
                }
            }
        }


        return temp;
    }
}

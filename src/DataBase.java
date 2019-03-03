import javax.lang.model.element.NestingKind;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.StringTokenizer;

public class DataBase {

    static Course[] course = new Course[5];
    static Student[] stu = new Student[34];
    static SC[] sc = new SC[9];

    public static void main(String args[]) throws IOException {
        /*将学生信息从txt文件中读入*/
//        FileReader fr = new FileReader(new File("student.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("student.txt"), "gbk"));
        String str = null;

//        Student[] stu = new Student[34];
        int student_num = 0;
        for (int i = 0; ; i++)
        {
            String string = br.readLine();
            if (string.equals(""))
            {
                break;
            }
            String[] retval = string.split("\\s+");//将数据切割，按空格分开
            //将学生数据存入
            stu[i] = new Student();//空指针问题
            stu[i].setSno(retval[0]);
            stu[i].setSname(retval[1]);
            stu[i].setAge(retval[2]);
            stu[i].setSex(retval[3]);
            stu[i].setSdept(retval[4]);
            student_num++;
        }


        /*将课程信息从txt文件中读入*/
//        FileReader fr1 = new FileReader(new File("course.txt"));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("course.txt"), "gbk"));
        String str1 = null;
//        Course[] course = new Course[5];
        int course_num = 0;//课程数目
        for (int i = 0; ; i++)
        {
            String string = br1.readLine();
            if (string.equals(""))
            {
                break;
            }
            String[] retval1 = string.split("\\s+");//将数据切割，按空格分开
            // 将课程数据存入
            course[i] = new Course();
            course[i].setCno(retval1[0]);
            course[i].setCname(retval1[1]);
            course[i].setCredit(retval1[2]);
            course_num++;
        }
        System.out.println("共有课程数" + course_num);


        /*将选课信息从txt文件中读入*/
//        FileReader fr2 = new FileReader(new File("c:/sc.txt"));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream("sc.txt"), "gbk"));
        String str2 = null;

//        SC[] sc = new SC[9];
        int sc_num = 0;
        for (int i = 0; ; i++)
        {
            String string = br2.readLine();
            if (string == null || string.equals(""))
            {
                break;
            }

            String[] retval2 = string.split("\\s+");//将数据切割，按空格分开
            //将学生选课数据存入
            sc[i] = new SC();
            sc[i].setSno(retval2[0]);
            sc[i].setCno(retval2[1]);
            sc[i].setGrade(retval2[2]);
            sc_num++;
        }
        System.out.println("选课记录有" + sc_num);
        /*检索选修数据库原理课程的学生学号，姓名和成绩*/
        String ifo = "数据库原理";
        Course coursetemp = SelectCourseByCname(ifo, course_num);//得到课程号
        String[] strtemp = SelectSCByCno(coursetemp.getCno(), sc_num);//得到学生学号信息
        String[] stemp = SelectGrade(coursetemp.getCno(), sc_num);
        System.out.println("选修数据库原理的学生信息");
        for (String ss : strtemp)
        {
            for (String aa : stemp)
            {
                Student stutemp = SelectStuBySno(ss, student_num);
                System.out.println("学号：" + stutemp.getSno() + "姓名：" + stutemp.getSname() + "成绩" + aa);
            }
        }
        /*检索刘明同学不学的课程号,课程名，学分*/
//        String name = "刘明";
//        int[] flag = new int[4];
//        int count = 0;
//        for (int i = 0; i < student_num; i++) {
//            if (name.equals(stu[i].getSname())) {
//                for (int j = 0; j < sc_num; j++) {
//                    if (stu[i].getSno().equals(sc[j].getSno())) {
//                        for (int k = 0; k < course_num; k++) {
//                            if (sc[j].getCno().equals(course[k].getCno())) {
//                                flag[i] = k;//记录下刘明选修的课程的下标
//                                count++;//记录刘明学的课程数
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        System.out.print("刘明不学的课程信息：");
//        for (int i = 0; i < course_num; i++) {
//            for (int j = 0; j < count; j++) {
//                if (flag[j] != i) {
//                    System.out.print("课程号：" + course[i].getCno() + "课程名：" + course[i].getCname() + "学分：" + course[i].getCredit());
//                    System.out.println();
//                } else {
//                    break;
//                }
//            }
//        }


//        fr.close();
        br.close();
//        fr1.close();
        br1.close();
//        fr2.close();
        br2.close();
    }

    public static Course SelectCourseByCname(String Cname, int course_num) {
        /*通过课程名在课程信息表中找到课程号*/
//        Course[] course = new Course[5];
        for (int i = 0; i < course_num; i++)
        {
//            course[i] = new Course();
            if (Cname.equals(course[i].getCname()))
            {
                return course[i];
            }
        }
        return null;
    }


    public static String[] SelectSCByCno(String Cno, int sc_num) {
        /*通过课程号在选课信息表中找到选课的学生学号*/
        if (Cno == null)
        {
            return null;
        } else
        {
//            SC[] sc = new SC[8];
            String[] str = new String[10];
            for (int i = 0; i < sc_num; i++)
            {
//                sc[i] = new SC();
                if (Cno.equals(sc[i].getCno()))
                {
                    str[i] = sc[i].getSno();//将学生学号保存到临时数组里
                } else
                {
                    return null;
                }
            }
            return str;
        }

    }

    public static String[] SelectGrade(String Cno, int sc_num) {
        /*通过课程号在选课信息表中找到选课的学生成绩*/
        SC[] sc = new SC[8];
        String[] str = new String[10];
        for (int i = 0; i < sc_num; i++)
        {
            sc[i] = new SC();
            if (Cno.equals(sc[i].getCno()))
            {
                str[i] = sc[i].getGrade();//将学生成绩保存到临时数组里
            } else
            {
                return null;
            }
        }
        return str;
    }

    public static Student SelectStuBySno(String Sno, int student_num) {
        Student[] stu = new Student[6];
        for (int i = 0; i < student_num; i++)
        {
            stu[i] = new Student();
            if (Sno.equals(stu[i].getSno()))
            {
                return stu[i];
            }
        }
        return null;
    }
}

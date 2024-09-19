package com.lilang.studentsystem;

import java.lang.invoke.CallSite;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentSystem {
    public static void startStuentSystem() {
        ArrayList<Student> list = new ArrayList<>();
        loop:
        while (true) {
            System.out.println("-----------------欢迎来到黑马学生管理系统-----------------");
            System.out.println("1:添加学生");
            System.out.println("2:删除学生");
            System.out.println("3:修改学生");
            System.out.println("4:查询学生");
            System.out.println("5:退出");
            System.out.println("请输入您的选择");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();
            switch (choose) {
                case "1" -> addStudent(list);
                case "2" -> deleteStudent(list);
                case "3" -> updateStudent(list);
                case "4" -> queryStudent(list);
                case "5" -> {
                    System.out.println("退出");
                    break loop;
                }
                default -> System.out.println("没有这个选项");


            }
        }

    }

    //添加学生的业务逻辑
    public static void addStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        Student stu = new Student();
        while (true) {
            //id
            System.out.println("请输入您学生的id");
            String id = sc.next();
            boolean flag = contains(list, id);
            if (flag) {
                System.out.println("id已经存在，请重新输入");
            } else {
                stu.setId(id);
                break;
            }
        }
        //姓名
        System.out.println("请输入您学生的姓名");
        String name = sc.next();
        stu.setName(name);
        //年龄
        System.out.println("请输入您学生的年龄");
        int age = sc.nextInt();
        stu.setAge(age);
        //家庭住址
        System.out.println("请输入您学生的住址");
        String adress = sc.next();
        stu.setAddress(adress);
        list.add(stu);


    }

    //删除学生
    public static void deleteStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要删除的id");
        String id = sc.next();
        int index = getIndex(list, id);
        //对index进行判断
        //如果大于等于0，表示存在，直接删除
        //如果-1，就表示不存在，结束方法，回到初始菜单
        if (index >= 0) {
            list.remove(index);
            System.out.println("id为：" + id + "的学生删除成功");
        } else {
            System.out.println("删除失败");
        }

    }

    //修改学生
    public static void updateStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要修改学生的id");
        String id = sc.next();

        int index = getIndex(list, id);

        if (index == -1) {
            System.out.println("要修改的id" + id + "不存在" + "请重新输入");
            return;
        }

        //当代码执行到这里，表示当前id存在。
        //获取要修改的学生对象
        Student stu = list.get(index);

        //输入其他的信息并修改
        System.out.println("请输入要修改的学生姓名");
        String newname = sc.next();
        stu.setName(newname);

        //请输入要修改的学生年龄
        System.out.println("请输入要修改的学生年龄");
        int newage = sc.nextInt();
        stu.setAge(newage);

        //请输入要修改的学生家庭住址
        String newadress = sc.next();
        stu.setAddress(newadress);

        System.out.println("学生信息修改成功");

    }

    //查询学生
    public static void queryStudent(ArrayList<Student> list) {
        if (list.size() == 0) {
            System.out.println("当前无学生信息，请查询后再录入");
        } else {
            System.out.println("id\t\t" + "姓名\t" + "年龄\t" + "家庭住址\t");
            for (int i = 0; i < list.size(); i++) {
                Student stu = list.get(i);
                System.out.println(stu.getId() + "\t" + stu.getName() + "\t" + stu.getAge() + "\t" + stu.getAddress());
            }
        }
    }

    //判断id是否存在
    public static boolean contains(ArrayList<Student> list, String id) {
        /*for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            String str = stu.getId();
            if(str.equals(id)){
                return true;
            }
        }
        return false;*/
        int index = getIndex(list, id);
        if (index >= 0) {
            return true;
        } else {
            return false;
        }
    }

    //通过id获取索引的方法
    public static int getIndex(ArrayList<Student> list, String id) {
        //遍历集合
        for (int i = 0; i < list.size(); i++) {
            //得到每一个学生对象
            Student stu = list.get(i);
            //得到每一个学生对象的id
            String sid = stu.getId();
            //拿着集合中的学生id跟要查询的id进行比较
            if (sid.equals(id)) {
                //如果一样，就返回索引
                return i;
            }
        }
        //当循环结束之后还没有找到，就表示不存在，返回-1
        return -1;
    }


}

package com.lilang.studentsystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<User> list = new ArrayList<>();
        while (true) {
            System.out.println("欢迎来到学生管理系统");
            System.out.println("请选择操作：1.登录 2.注册 3.忘记密码");
            String choose = sc.next();
            switch (choose) {
                case "1" -> login(list);
                case "2" -> regester(list);
                case "3" -> forgetPassword(list);
                case "4" -> {
                    System.out.println("谢谢使用，再见");
                    System.exit(0);//虚拟机结束运行
                }
                default -> System.out.println("没有这个选项");
            }
        }
    }

    private static void login(ArrayList<User> list) {
        //1.键盘录入用户名
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入用户名");
            String username = sc.next();
            //判断用户名是否存在
            boolean flag = contains(list, username);
            if (!flag) {
                System.out.println("用户名" + username + "未注册，请先注册再登录");
                return;
            }
            //2.键盘录入密码
            System.out.println("请输入密码");
            String password = sc.next();

            //3.键盘录入验证码
            while (true) {
                String rightCode = getCode();
                System.out.println("当前正确的验证码为：" + rightCode);
                System.out.println("请输入验证码");
                String code = sc.next();
                //忽略大小写比较
                if (code.equalsIgnoreCase(rightCode)) {
                    System.out.println("验证码正确");
                    break;
                } else {
                    System.out.println("验证码错误");
                }
            }
            //判断用户名和验证码是否正确
            //集合中是否包含用户名和密码
            //定义一个方法，验证用户名和密码是否正确

            //封装思想的应用
            //我们可以把一些零散的数据，封装成一个对象
            //以后传递参数的时候只要传递一个整体就好了，不需要管零散的数据
            User useInfo = new User(username, password, null, null);
            boolean result = checkUserIfo(list, useInfo);
            if (result) {
                System.out.println("登录成功，可以开始使用学生管理系统了");
                //创建对象，调用方法，启动学生管理系统
                StudentSystem ss = new StudentSystem();
                ss.startStuentSystem();
                break;
            } else {
                System.out.println("登录失败，用户名或密码错误");
                if (i == 2) {
                    System.out.println("当前账户" + username + "被锁定，请联系管理员：xxx-xxx");
                    //当前账号锁定之后，直接结束方法即可
                    return;
                } else {
                    System.out.println("用户名或密码错误，还剩下" + (2 - i) + "次机会");
                }
            }
        }

    }

    private static boolean checkUserIfo(ArrayList<User> list, User useInfo) {
        //判断用户是否存在，如果存在登录成功，如果不存在，登陆失败
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUsername().equals(useInfo.getUsername()) && user.getPassword().equals(useInfo.getPassword())) {
                return true;
            }
        }
        return false;

    }

    private static void forgetPassword(ArrayList<User> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名");
        String username = sc.next();
        boolean flag = contains(list, username);
        if (!flag) {
            System.out.println("该用户未注册");
            return;
        }
        System.out.println("请输入身份证号");
        String personID = sc.next();
        System.out.println("请输入手机号码");
        String phoneNumber = sc.next();
        //比较用户对象中的手机号码和身份证号码是否相同
        //需要把用户对象通过索引先获取出来
        int index = findIndex(list, username);
        User user = list.get(index);
        if (!(user.getPhoneNumber().equals(phoneNumber) && user.getPersonID().equalsIgnoreCase(personID))) {
            System.out.println("身份证号码或手机号码输入有误，不能修改密码");
            return;
        }
        //当代码执行到这里，代表所有的数据全部验证成功，直接修改即可
        String password;
        String againPassword;
        while (true) {
            System.out.println("请输入新的密码");
            password = sc.next();
            System.out.println("请再次输入密码");
            againPassword = sc.next();
            if(password.equals(againPassword)){
                System.out.println("两次密码输入一致");
                break;
            }else {
                System.out.println("两次密码输入不一致，请重新输入");
            }
        }

        //直接修改就可以了
        user.setPassword(password);
        System.out.println("密码修改成功");




    }

    private static int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    private static void regester(ArrayList<User> list) {
        //把用户名，密码，手机号码放到用户对象中
        //把用户对象添加到集合中
        String username;
        String password;
        String personID;
        String phoneNumber;
        //1.键盘录入用户名
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名");
            username = sc.next();
            //先验证格式是否正确，再验证是否唯一
            //因为以后所有数据都是存储在数据库中的，如果我们要校验，需要使用到网络资源。
            boolean flag1 = checkUsername(username);
            if (!flag1) {
                System.out.println("用户名格式不满足条件，请重新输入");
                continue;
            }
            //校验用户名唯一
            //username在集合中判断是否有存在的
            boolean flag2 = contains(list, username);
            if (flag2) {
                //当前用户名无法注册，请重新输入
                System.out.println("用户名已存在，请重新输入");
            } else {
                //不存在，可以继续录入下面的其他数据
                System.out.println("用户名可用" + username + "可用");
                break;
            }
        }

        //2.键盘录入密码
        //密码键盘输入两次，两次一致才可以进行注册
        while (true) {
            System.out.println("请输入要注册的密码");
            password = sc.next();
            System.out.println("请再次输入要输入的密码");
            String againPassword = sc.next();
            if (!password.equals(againPassword)) {
                System.out.println("两次密码输入不一致，请重新输入");
                continue;
            } else {
                System.out.println("两次密码一致，继续录入其他数据");
                break;
            }
        }

        //3.键盘录入身份证号码
        while (true) {
            System.out.println("请输入身份证号码");
            personID = sc.next();
            boolean flag = checkPersonID(personID);
            if (flag) {
                System.out.println("身份证号码满足要求");
                break;
            } else {
                System.out.println("身份证号码有误，请重新输入");
                continue;
            }
        }

        //键盘录入手机号码
        while (true) {
            System.out.println("请输入手机号码");
            phoneNumber = sc.next();
            boolean flag = checkPhoneNumber(phoneNumber);
            if (flag) {
                System.out.println("手机号码格式正确");
                break;
            } else {
                System.out.println("手机号码格式有误，请重新输入");
            }
        }

        //将用户名，身份证号码，手机号号码都放入用户对象之中
        User u = new User(username, password, personID, phoneNumber);
        //把用户对象添加到集合中
        list.add(u);
        System.out.println("注册成功");

        //遍历集合
        printList(list);

    }

    private static void printList(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            System.out.println(user.getUsername() + ", " + user.getPassword() + ", "
                    + user.getPersonID() + ", " + user.getPhoneNumber());
        }
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        //长度位11位
        if (phoneNumber.length() != 11) {
            return false;
        }
        //不能以0开头
        if (phoneNumber.startsWith("0")) {
            return false;
        }
        //必须都是数字
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        //循环结束只够表示每一个字符都在0到9之间
        return true;
    }

    private static boolean checkPersonID(String personID) {
        //长度为18
        if (personID.length() != 18) {
            return false;
        }
        //不能以0开头
        boolean flag = personID.startsWith("0");
        if (flag) {
            //如果以零开头，返回false
            return false;
        }
        //前17位必须是数字
        for (int i = 0; i < personID.length() - 1; i++) {
            char c = personID.charAt(i);
            //如果有一个数字不在0到9之间，直接返回false
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        //最后一位可以是数字也可以是大写或小写x
        char endChar = personID.charAt(personID.length() - 1);
        if (!((endChar >= '0' && endChar <= '9') || (endChar == 'x') || (endChar == 'X'))) {
            return false;
        }
        return true;

    }

    private static boolean contains(ArrayList<User> list, String username) {
        //循环遍历集合得到每一个用户对象，拿着用户对象中的用户名进行比较
        for (int i = 0; i < list.size(); i++) {
            //i 索引
            User user = list.get(i);
            String rightUsername = user.getUsername();
            if (rightUsername.equals(username)) {
                return true;
            }
        }
        //当循环结束了，表示集合当中的所有用户都表示完毕了，还没有一样的，则返回false
        return false;
    }

    private static boolean checkUsername(String username) {
        //用户名长度必须在3~15位之间
        int len = username.length();
        if (len < 3 || len > 15) {
            return false;
        }
        //当代码执行到这里，表示用户名长度符合要求
        //继续校验：只能能是字母加数字的组合
        //循环得到username里面的每一个字符，如果有一个字符不是字母或者数字，那么返回false
        for (int i = 0; i < username.length(); i++) {
            //i:索引
            char c = username.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }
        //当代码执行到这里,表示用户名满足两个要求：1长度满足 2内容也满足
        //不可以是纯数字
        //统计在用户名中，有多少个字母就可以了
        int count = 0;
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
                break;
            }
        }
        return count > 0;
    }

    private static String getCode() {
        //1.创建一个集合添加所有的大写和小写字母
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }
        //2.随机抽取四个字符
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            //获取随机索引
            int index = r.nextInt(list.size());
            //利用随机索引获取随机字符
            char c = list.get(index);
            sb.append(c);
        }
        //3.把一个随机数字添加到末尾
        int number = r.nextInt(10);
        sb.append(number);

        //4.如果要修改字符串中的内容
        //先把字符串变成字符数组，在数组中修改，然后再创建一个新的字符串
        char[] arr = sb.toString().toCharArray();
        //拿着最后一个索引跟随机索引进行交换
        int randomIndex = r.nextInt(arr.length);
        //拿着最大索引指向的元素，跟随即索引指向的元素进行交换
        char temp = arr[randomIndex];
        arr[randomIndex] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;
        return new String(arr);
    }


}

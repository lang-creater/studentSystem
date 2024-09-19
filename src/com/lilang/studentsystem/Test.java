package com.lilang.studentsystem;

import java.util.ArrayList;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        System.out.println(getCode());
    }

    //生成一个验证码
    private static String getCode(){
        //1.创建一个集合添加所有的大写和小写字母
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
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
        arr[randomIndex] = arr[arr.length-1];
        arr[arr.length-1] = temp;
        return new String(arr);


    }

}

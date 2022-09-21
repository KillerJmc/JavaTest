package com.test.regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jmc.lang.Outs.newLine;

public class RegexTest {
    public static void main(String[] args) {
        test01();
        test02();
        test03();
        test04();
    }

    //基础
    public static void test01() {
        //在这个字符串：asdfadf&233232 是否符合指定的正则表达式：\w+
        Pattern p = Pattern.compile("\\w+");

        //创建Matcher对象
        Matcher m = p.matcher("asdfadf2&33232");

        //尝试将整个字符序列与该模式匹配
        System.out.println(m.matches());

        //或者
        System.out.println(Pattern.matches("\\w+", "asdfadf233232"));

        newLine();

        //该方法扫描输入的序列，寻找与该模式匹配的下一个子序列，并打印
        /*System.out.println(m.find());
        System.out.println(m.group());
        System.out.println(m.find());
        System.out.println(m.group());
        System.out.println(m.find());*/

        while (m.find()) {
            //空和0均匹配整个表达式的子字符串
            System.out.println(m.group());
            System.out.println(m.group(0));
        }
        newLine();
    }

    //分组
    public static void test02() {
        Pattern p = Pattern.compile("([a-z]+)([0-9]+)");
        Matcher m = p.matcher("aa327**ssd399**sds233");

        while (m.find()) {
            System.out.println(m.group());
            System.out.println("分组1：" + m.group(1));
            System.out.println("分组2：" + m.group(2));
        }
        newLine();
    }

    //替换
    public static void test03() {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher("aa327ssd399sds233");

        //替换
        String str = m.replaceAll("#");
        System.out.println(str);
        //或者
        String str2 = "aa327ssd399sds233".replaceAll("[0-9]","#");
        System.out.println(str2);
        newLine();
    }

    //分割
    public static void test04() {
        String str = "a2345b244244c32423";
        //分割
        String[] split = str.split("\\d+");
        System.out.println(Arrays.toString(split));
    }
}

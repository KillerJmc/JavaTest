package com.test.scanner;

import java.util.Scanner;

public class ScannerTest {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // 当匹配到end时候跳出循环
        // 如果单纯while (in.hasNext()) 只有接受了EOF才能停止循环
        // 当键入ctrl + z + enter时系统会发送一个EOF给Scanner
        while (!in.hasNext("end")) {
            int n = in.nextInt();
            System.out.println(n);
        }
    }
}

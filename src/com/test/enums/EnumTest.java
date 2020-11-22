package com.test.enums;

import com.test.main.Main;

public class EnumTest {
    public static void main(String[] args) {
        System.out.println(Color.BLUE);
    }

    enum Color {
        RED("红色"), GREEN("绿色"), BLUE("蓝色"), WHITE, PURPLE, ORANGE, YELLOW;

        private String desc;

        Color() {

        }

        Color(String desc) {
            this.desc = desc;
        }

        public String desc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc != null ? this.desc() : super.toString();
        }
    }
}

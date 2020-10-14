/**
 * 题目源自小本练习册数列内容
 * 
 * @author Jmc
 * 
 */
package com.test.apply.math;

public class Selection {
	public static void main(String[] args) {
		calculate();
	}
	public static void calculate() {
		double[] selections = new double[] {
			(double)25 / 9, (double)26 / 9, (double)3, (double)28 / 9
		};  
		
		int n = 18;
		
		var a = new double[n];
		a[0] = 1; a[1] = 2;
		
		for (int i = 2; i < a.length; i++) {
			a[i] = (2 * i * a[i - 1] - (i - 1) * a[i - 2]) / (i + 1);
		}
		
		double result = a[n - 1];

		for (int i = 0; i < selections.length; i++) {
			if (result == selections[i])
				System.out.println("结果是：" + result
					+ "选第" + (i + 1) + "个选项");
		}
	}
}

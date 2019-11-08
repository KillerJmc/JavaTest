/**
 * 组合模式
 * @author Jmc
 * 
 */

package com.test.GOF23.StructuralPattern.Composite;

public class Client {
	public static void main(String[] args) {
		AbstractFile f1, f2, f3, f4, f5;
		
		var root = new Folder("我的收藏");
		f1 = new TextFile(".nomedia");
		root.add(f1);
		
		var d1 = new Folder("文件");
		f2 = new ImageFile("loli.jpg");
		f3 = new TextFile("hello.txt");
		d1.add(f2, f3);
		
		var d2 = new Folder("电影");
		f4 = new VideoFile("阿甘正传.rmvb");
		f5 = new VideoFile("神探夏洛克.mkv");
		d2.add(f4, f5);
		
		root.add(d1, d2);
		root.killVirus();
	}
}

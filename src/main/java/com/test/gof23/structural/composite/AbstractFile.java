package com.test.gof23.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Component
 */
public interface AbstractFile {
	void killVirus();
}

/**
 * Leaf
 */
class ImageFile implements AbstractFile {
	private String name;
	
	public ImageFile(String name) {
		this.name = name;
	}

	@Override
	public void killVirus() {
		System.out.println("	图像文件：" + name + "进行查杀！");
	}
}

class TextFile implements AbstractFile {
	private String name;
	
	public TextFile(String name) {
		this.name = name;
	}

	@Override
	public void killVirus() {
		System.out.println("	文本文件：" + name + "进行查杀！");
	}
}

class VideoFile implements AbstractFile {
	private String name;
	
	public VideoFile(String name) {
		this.name = name;
	}

	@Override
	public void killVirus() {
		System.out.println("	视频文件：" + name + "进行查杀！");
	}
}

/**
 * Composite
 */
class Folder implements AbstractFile {
	private String name;
	/**
	 * 容器，用来存放节点
	 */
	private List<AbstractFile> list = new ArrayList<>();
	
	public Folder(String name) {
		this.name = name;
	}

	public void add(AbstractFile... files) {
		for (var file : files) {
			list.add(file);
		}
	}
	
	public void remove(AbstractFile file) {
		list.remove(file);
	}
	
	public AbstractFile getChild(int index) {
		return list.get(index);
	}
	
	@Override
	public void killVirus() {
		System.out.println("---文件夹：" + name + "进行查杀！");
		list.forEach(AbstractFile::killVirus);
	}
}











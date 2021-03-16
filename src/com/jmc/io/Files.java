package com.jmc.io;

import com.jmc.lang.extend.Objs;
import com.jmc.lang.extend.Strs;
import com.jmc.lang.extend.Tries;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * <p>作者: Jmc
 * <p>时间: 2019.1.12
 * <p>功能: 文件操作
 * <p>更新:
 * <pre>
 *   2019.1.25     1.增加“源文件是否为文件夹的判断”
 *                 2.优化提示信息
 *   2019.1.27     1.优化删除文件(删除一个有2100长文件夹只需38秒)
 *                 2.添加多线程
 *   2019.1.28     添加随机存储
 *   2019.1.29     添加大文件通道(删除原有随机存储)
 *   2019.1.31     根据设备彻底优化，从最大程度避免出错
 *   2019.2.2      1.加入zip压缩和解压
 *                 2.加入文件的移动和重命名
 *   2019.2.3      1.加入基础提示布尔值
 *                 2.加入状态信息
 *   2019.2.17     加入输出和读取功能
 *   2019.2.27     读取改用FIS(BR末尾换行，故不采用)
 *   2019.2.28     输出功能加入追加模式
 *   2019.3.15     加入是否检查的布尔值
 *   2019.3.19     设置不抛出所有异常
 *   2019.3.23     复制功能加入追加模式
 *   2019.4.15     做到精准计算操作用时
 *   2019.5.3      通道均改为静态内部类方式
 *   2019.6.23     优化提示信息
 *   2019.7.3      加入输出字节码功能
 *   2019.8.22     1.改输入字节码功能为readToBytes
 *                 2.将readToBytes,outBytes作为输入输出主方法
 *   2019.8.23     1.优化文件压缩和解压，及时关闭文件流和清除缓冲区
 *                 2.删除out方法中检查文件完整性判断
 *   2019.8.24     1.添加文件搜索功能
 *                 2.优化文件信息统计
 *                 3.删除结果保存功能
 *                 4.删除多余成员变量
 *                 5.删除是否复制到sd卡判断
 *                 6.优化线程结束判断
 *   2019.8.25     1.高级搜索功能添加过滤器
 *                 2.调整流关闭顺序(先打开的后关闭，只需关闭Channel无需关闭文件流)
 *   2019.8.31     1.加入线程池
 *                 2.修改临时数组大小: 1024(1k) -> 8192(8k) 性能提升明显
 *   2019.9.13     1.加入文件编码修改功能
 *                 2.输入输出方法加入字符集参数
 *   2019.10.5     加入单文件搜索(findAny)方法
 *   2020.3.4      用非递归方式统计文件夹和搜索单文件，其中搜索单文件的速度明显加快
 *   2020.3.10     添加批量重命名方法
 *   2020.3.11     1.修复文件信息方法中的统计错误(将初始文件夹的大小：4KB也统计进去了)
 *                 2.添加文件长度转换方法
 *   2020.3.12     加入树状图统计文件夹功能
 *   2020.3.31     1.加入批量移动和批量删除功能
 *                 2.将批量方法集中并改名为寻找XX
 *                 3.添加寻找复制方法
 *                 4.添加findDO模板以统一化代码，并扩展findXX为orContains形式
 *   2020.4.1      1.升级findToMap方法，可以输入多个搜索条件
 *                 2.添加deletes方法
 *   2020.4.8      1.添加outStream方法
 *                 2.将所有out系列方法(outBytes, outStream)统一命名为out
 *                 3.压缩方法添加“储存模式”可选项
 *                 4.优化输出，使统计结果更加美观
 *   2020.4.9      将Streams类的流与文件方法移动到本类
 *   2020.4.17     添加按时间重命名方法
 *   2020.4.20     完善移动方法，使其能兼容不同分区的移动
 *   2020.8.7      1.添加findFiles和findDirs方法
 *                 2.将findToMap方法改名为findAll
 *                 3.删除findAll第一个参数为File的重载方法
 *   2020.8.18     1.将所有返回布尔值的方法改为返回void且出错时直接抛出运行时异常
 *                 2.添加deletes参数为文件列表的重载方法
 *   2020.12.27    对源码进行大幅度调整优化，并添加相应方法注释
 *   2021.3.15     更新大文件复制通道代码，用NIO: ByteBuffer提高性能
 *
 * </pre>
 */
public class Files
{
	/**
	 * 大文件指定界限
	 */
	private final static int LARGE_FILE_SIZE = 1024 * 1024 * 500;

	/**
	 * 最多正在操作文件数
	 */
	private final static int MAX_OPERATING_AMOUNT = 500;

	/**
	 * 线程池
	 */
    private static ExecutorService pool;

	/**
	 * 是否显示正在操作的提示信息
	 */
    public static boolean showOperatingDetails = false;

	/**
	 * 是否显示基础提示信息
	 */
    public static boolean basicDetails = true;

	/**
	 * 文件和文件夹存放集合
	 */
	private static Map<String, List<File>> map;

	/**
	 * 文件存放列表
	 */
    private static List<File> fileList;

	/**
	 * 文件夹存放列表
	 */
	private static List<File> dirList;

	/**
	 * 此类为工具类，不能被实例化
	 */
	private Files() {}

	/**
	 * 搜索路径下符合要求的所有文件和文件夹
	 * @param path 路径
	 * @param filter 文件过滤器
	 * @return 含结果文件和文件夹的集合
	 */
	public static Map<String,List<File>> findAll(String path, FileFilter filter) {
    	File src = new File(path);
    	if (!src.exists()) {
			System.out.println("路径不存在！");
			return null;
		}
    	if (src.isFile()) {
    		System.out.println("搜索父目录必须为文件夹！");
    		return null;
    	}
    	
    	map = new HashMap<>();
    	fileList = new LinkedList<>();
    	dirList = new LinkedList<>();
    	
    	map.put("file", fileList);
    	map.put("dir", dirList);
    	
    	findLoop(src, filter);
    	
    	return map;
    }

	/**
	 * 搜索路径下符合要求的所有文件和文件夹
	 * @param path 路径
	 * @param contains 文件或文件夹名称中包含的内容
	 * @return 含结果文件和文件夹的集合
	 */
	public static Map<String, List<File>> findAll(String path, String... contains) {
		if (contains.length == 0) {
			System.out.println("搜索参数不能为空！");
			return null;
		}
		
    	FileFilter filter = f -> Strs.orContains(f.getName() ,contains);
    	Files.findAll(path, filter);
		
		return map;
	}

	/**
	 * 搜索路径下符合要求的所有文件
	 * @param path 路径
	 * @param filter 文件过滤器
	 * @return 含结果文件的列表
	 */
	@SuppressWarnings("all")
    public static List<File> findFiles(String path, FileFilter filter) {
        return findAll(path, filter).get("file");
    }

	/**
	 * 搜索路径下符合要求的所有文件
	 * @param path 路径
	 * @param contains 文件名称中包含的内容
	 * @return 含结果文件的列表
	 */
	@SuppressWarnings("all")
	public static List<File> findFiles(String path, String... contains) {
        return findAll(path, contains).get("file");
    }

	/**
	 * 搜索路径下符合要求的所有文件夹
	 * @param path 路径
	 * @param filter 文件过滤器
	 * @return 含结果文件夹的列表
	 */
	@SuppressWarnings("all")
	public static List<File> findDirs(String path, FileFilter filter) {
        return findAll(path, filter).get("dir");
    }

	/**
	 * 搜索路径下符合要求的所有文件夹
	 * @param path 路径
	 * @param contains 文件夹名称中包含的内容
	 * @return 含结果文件的列表
	 */
	@SuppressWarnings("all")
	public static List<File> findDirs(String path, String... contains) {
        return findAll(path, contains).get("dir");
    }

	/**
	 * 递归搜索文件
	 * @param src 源文件
	 * @param filter 文件过滤器
	 */
    private static void findLoop(File src, FileFilter filter) {
    	File[] fs = src.listFiles();
		if (fs == null) return;

		for (File f : fs) {
			if (f.isFile()) {
				if (filter.accept(f)) fileList.add(f);
			} else {
				if (filter.accept(f)) dirList.add(f);
				findLoop(f,filter);
			}		
		}
    }

	/**
	 * 简单搜索文件和文件夹
	 * @param path 路径
	 * @param content 文件或文件夹包含内容
	 * @return 字符串形式的搜索结果
	 */
	public static String find(String path, String content) {
    	if (content == null) return null;
    	
    	long startTime = System.currentTimeMillis();
    	Files.findAll(path, content);
    	var sb = new StringBuilder();
		
		sb.append("文件夹：\n");
		for (File d : dirList) sb.append(d.getAbsolutePath()).append("\n");

		sb.append("\n文件：\n");
		for (File f : fileList) sb.append(f.getAbsolutePath()).append("\n");

		long endTime = System.currentTimeMillis();
		sb.append("\n共搜索到\n").append(dirList.size()).append("个文件夹\n")
		  .append(fileList.size()).append("个文件")
		  .append("\n本次搜索耗时").append((int) (endTime - startTime) / 1000).append("秒\n");
		
		return sb.toString();
    }

	/**
	 * 简单搜索文件和文件夹
	 * @param dirFile 文件夹的File对象
	 * @param content 文件或文件夹名称包含内容
	 * @return 字符串形式的搜索结果
	 */
	public static String find(File dirFile, String content) {
        return find(dirFile.getAbsolutePath(), content);
    }

	/**
	 * 搜索单个文件
	 * @param path 文件夹路径
	 * @param content 文件名称包含内容
	 * @return 搜索结果
	 */
    public static File findAny(String path, String content) {
		File src = new File(path);
		if (!src.exists()) {
			System.out.println("路径不存在!");
			return null;
		}
		
		if (src.isFile()) {
			System.out.println("搜索父目录必须为文件夹！");
			return null;
		}

		// 文件夹临时列表
		var temp = new ArrayList<File>() {{ add(src); }};

		while (!temp.isEmpty()) {
			var list = temp.remove(0).listFiles();
			if (list == null) return null;

			for (File f : list) {
				if (f.isDirectory()){
					temp.add(f);
				} else {
					if (f.getName().contains(content)) return f;
				}
			}
		}
		
    	return null;
    }

	/**
	 * 搜索单个文件
	 * @param dirFile 文件夹的File对象
	 * @param content 文件名称包含内容
	 * @return 搜索结果
	 */
	public static File findAny(File dirFile, String content) {
        return findAny(dirFile.getAbsolutePath(), content);
    }


	/**
	 * 寻找并做模板
	 * @param r 执行模块
	 * @param dirPath 文件夹路径
	 * @param orContains 文件或文件夹名称包含内容
	 */
	private static void findDo(Runnable r, String dirPath, String... orContains) {
		if (orContains.length == 0) {
			throw new RuntimeException("搜索字符不能为空！");
		}
		
		findAll(dirPath, f -> Strs.orContains(f.getName(), orContains));

		boolean flag = basicDetails;
		basicDetails = false;
		r.run();
		Files.basicDetails = flag;
	}

	/**
	 * 寻找并复制符合要求的文件
	 * @param dirPath 文件夹路径
	 * @param desPath 目标路径
	 * @param orContains 文件名称内包含内容
	 */
	public static void findCopies(String dirPath, String desPath, String... orContains) {
		findDo(() -> { for (File f : fileList) copy(f, desPath); }, dirPath, orContains);
	}

	/**
	 * 寻找并复制符合要求的文件
	 * @param dirFile 文件夹的File对象
	 * @param desPath 目标路径
	 * @param orContains 文件名称内包含内容
	 */
	public static void findCopies(File dirFile, String desPath, String... orContains) {
		findCopies(dirFile.getAbsolutePath(), desPath, orContains);
	}

	/**
	 * 寻找并移动符合要求的文件
	 * @param dirPath 文件夹路径
	 * @param desPath 目标路径
	 * @param orContains 文件名称内包含内容
	 */
	public static void findMoves(String dirPath, final String desPath, String... orContains) {
		findDo(() -> { for (File f : fileList) move(f, desPath); }, dirPath, orContains);
	}

	/**
	 * 寻找并移动符合要求的文件
	 * @param dirFile 文件夹的File对象
	 * @param desPath 目标路径
	 * @param orContains 文件名称内包含内容
	 */
	public static void findMoves(File dirFile, String desPath, String... orContains) {
        findMoves(dirFile.getAbsolutePath(), desPath, orContains);
	}

	/**
	 * 寻找并重命名符合要求的文件
	 * @param dirPath 文件夹路径
	 * @param newChar 新名称
	 * @param oldChars 旧文件的名称
	 */
	public static void findRenames(String dirPath, String newChar, String... oldChars) {
		findDo(() -> { for (File f : fileList) rename(f, Strs.orReplace(f.getName(), newChar, oldChars)); },
			dirPath, oldChars);
	}

	/**
	 * 寻找并重命名符合要求的文件
	 * @param dirFile 文件夹的File对象
	 * @param newChar 新名称
	 * @param oldChars 旧文件的名称
	 */
	public static void findRenames(File dirFile, String newChar, String... oldChars) {
		findRenames(dirFile.getAbsolutePath(), newChar, oldChars);
	}

	/**
	 * 寻找并重命名符合要求的文件
	 * @param dirPath 文件夹路径
	 * @param newChar 新名称
	 * @param oldChar 旧文件的名称
	 */
	public static void findRename(String dirPath, String oldChar, String newChar) {
		findRenames(dirPath, newChar, oldChar);
	}

	/**
	 * 寻找并重命名符合要求的文件
	 * @param dirFile 文件夹的File对象
	 * @param newChar 新名称
	 * @param oldChar 旧文件的名称
	 */
	public static void findRename(File dirFile, String oldChar, String newChar) {
		findRename(dirFile.getAbsolutePath(), oldChar, newChar);
	}

	/**
	 * 寻找并删除符合要求的文件
	 * @param dirPath 文件夹路径
	 * @param orContains 文件名称内包含内容
	 */
	@SuppressWarnings("all")
	public static void findDels(String dirPath, String... orContains) {
		findDo(() -> { for (File f : fileList) f.delete(); }, dirPath, orContains);
	}

	/**
	 * 寻找并删除符合要求的文件
	 * @param dirFile 文件夹的File对象
	 * @param orContains 文件名称内包含内容
	 */
	public static void findDels(File dirFile, String... orContains) {
		findDels(dirFile.getAbsolutePath(), orContains);
	}

	/**
	 * 按时间重命名
	 * @param dirFile 文件夹的File对象
	 * @param suffix 后缀（含小数点）
	 */
	public static void renamesByTime(File dirFile, String suffix) {
		Objs.throwsIfNullOrEmpty(dirFile, suffix);

		boolean flag = basicDetails;
		basicDetails = false;

		File[] fs = dirFile.listFiles(File::isFile);
		if (fs == null) return;

		Arrays.sort(fs, Comparator.comparingLong(File::lastModified));
		
		for (int i = 0; i < fs.length; i++) rename(fs[i], (i + 1) + suffix);

		Files.basicDetails = flag;
	}

	/**
	 * 按时间重命名
	 * @param dirPath 文件夹路径
	 * @param suffix 后缀（含小数点）
	 */
	public static void renamesByTime(String dirPath, String suffix) {
		renamesByTime(new File(dirPath), suffix);
	}

	/**
	 * 文件信息
	 * @param path 路径
	 * @return 包含文件信息的字符串
	 */
    public static String fileInfo(String path) {
        File src = new File(path);
        if (!src.exists()) {
			System.out.println("路径不存在！");
			return null;
		}
		
		// 文件夹临时列表
		List<File> temp = new ArrayList<>();

        // 文件总大小 文件个数 文件夹个数
        long length = 0, files = 0, dirs = 0;

        long startTime = System.currentTimeMillis();

        if (src.isDirectory()) {
			temp.add(src);
			while (!temp.isEmpty()){
				var list = temp.remove(0).listFiles();
				if (list == null) return null;

				for (File f : list) {
					if (f.isDirectory()){
						temp.add(f);
						dirs++;
					} else {
						length += f.length();
						files++;
					}
				}
			}
        } else {
			files = 1;
			length = src.length();
		}

		long endTime = System.currentTimeMillis();

		return String.format("""
      
		正在统计：%s
		
		共%d个文件
		%d个文件夹
		大小为%s (%s个字节)
		本次统计耗时%d秒
		""", src.getName(), files, dirs, lengthFormatter(length), new DecimalFormat().format(length), (endTime - startTime) / 1000);
    }

	/**
	 * 文件长度转换
	 * @param length 文件大小
	 * @return 格式化后的文件长度
	 */
	public static String lengthFormatter(long length) {
		long B = 1, KB = B * 1024, MB = KB * 1024,
			 GB = MB * 1024, TB = GB * 1024,
			 PB = TB * 1024, EB = PB * 1024;

		return length < KB ? length + "B" :
			   length < MB ? String.format("%.2f", (double) length / KB) + "KB" :
			   length < GB ? String.format("%.2f", (double) length / MB) + "MB" :
			   length < TB ? String.format("%.2f", (double) length / GB) + "GB" :
			   length < PB ? String.format("%.2f", (double) length / TB) + "TB" :
			   length < EB ? String.format("%.2f", (double) length / PB) + "PB" :
					         String.format("%.2f", (double) length / EB) + "EB";
	}

	/**
	 * 树状图统计文件夹
	 * @param dirFile 文件夹的File对象
	 * @param depth 搜寻深度
	 * @param MIN_MB_SIZE 结果中的文件夹最小多少MB
	 * @return 结果字符串
	 */
	public static String tree(File dirFile, int depth, double MIN_MB_SIZE) {
		if (!dirFile.exists()) {
			System.out.println("文件夹不存在");
			return null;
		}
		
		if (dirFile.isFile()) {
			System.out.println("统计的对象不能为文件！");
			return null;
		}
		
		long startTime = System.currentTimeMillis();
		var a = treeLoop(dirFile, 0, MIN_MB_SIZE, depth);
		if (a == null) return null;
		String result = a[0].toString();
		long endTime = System.currentTimeMillis();

		return String.format("""
		
		%s
		本次统计耗时%d秒		
		""", !result.isEmpty() ? result : "null\n", (endTime - startTime) / 1000);
	}

	/**
	 * 树状图统计文件夹
	 * @param dirPath 文件夹路径
	 * @param depth 搜寻深度
	 * @param MIN_MB_SIZE 结果中的文件夹最小多少MB
	 * @return 结果字符串
	 */
	public static String tree(String dirPath, int depth, double MIN_MB_SIZE) {
		File dirFile = new File(dirPath);
		return tree(dirFile, depth, MIN_MB_SIZE);
	}

	/**
	 * 递归创建树状图
	 * @param dirFile 文件夹的File对象
	 * @param currentDepth 当前搜寻深度
	 * @param MIN_MB_SIZE 结果中的文件夹最小多少MB
	 * @param depth 目标搜寻深度
	 * @return 包含当前已生成的结果字符串和当前文件夹大小这两个元素的Object数组
	 */
	private static Object[] treeLoop(File dirFile, int currentDepth, double MIN_MB_SIZE, int depth) {
		// 储存字符串
		var sb = new StringBuilder();
		// 文件夹名
		String fileName = dirFile.getName();
		// 最小文件夹长度
		long MIN_Length = (long) (MIN_MB_SIZE * 1024 * 1024);
		// 文件夹大小
		long length = 0;

		sb.append(" ".repeat(4 * currentDepth))
		  .append("├─").append(dirFile.getName()).append("\n");

		currentDepth++;

		var list = dirFile.listFiles();
		if (list == null) return null;

		for (File f : list) {
			if (f.isDirectory()) {
				Object[] os = treeLoop(f, currentDepth, MIN_MB_SIZE, depth);
				if (os == null) return null;

				if (currentDepth <= depth) sb.append(os[0]);
				// 将子文件夹大小加入父文件夹
				length += (long)os[1];
			} else {
				if (currentDepth <= depth && f.length() >= MIN_Length) {
					sb.append(" ".repeat(4 * currentDepth))
					  .append("├─").append(f.getName()).append("\n");

					length += f.length();
					// 在换行前插入文件大小
					sb.insert(sb.length() - 1, " " + lengthFormatter(f.length()));
				} else {
					length += f.length();
				}
			}
		}

		sb.insert(sb.indexOf(fileName) + fileName.length(),
				" " + lengthFormatter(length));

		return length >= MIN_Length ? new Object[] { sb, length } :
			new Object[] { "", length };
	}

	/**
	 * 普通树状图
	 * @param dirFile 文件夹的File对象
	 * @return 结果字符串
	 */
	public static String normalTree(File dirFile) {
		String result = tree(dirFile, 5, 50);
		if (result == null) return null;

		return !result.startsWith("\nnull") ? result :
				tree(dirFile, 5, 0);
	}

	/**
	 * 普通树状图
	 * @param dirPath 文件夹路径
	 * @return 结果字符串
	 */
	public static String normalTree(String dirPath) {
		File dirFile = new File(dirPath);
		return normalTree(dirFile);
	}

	/**
	 * 单层树状图
	 * @param dirFile 文件夹的File对象
	 * @return 结果字符串
	 */
	public static String singleTree(File dirFile) {
		String result = tree(dirFile, 1, 50);
		if (result == null) return null;

		return !result.startsWith("\nnull") ? result :
				tree(dirFile, 1, 0);
	}

	/**
	 * 单层树状图
	 * @param dirPath 文件夹路径
	 * @return 结果字符串
	 */
	public static String singleTree(String dirPath) {
		File dirFile = new File(dirPath);
		return singleTree(dirFile);
	}

	/**
	 * 完整树状图
	 * @param dirFile 文件夹的File对象
	 * @return 结果字符串
	 */
	public static String wholeTree(File dirFile) {
		return tree(dirFile, Integer.MAX_VALUE, 0);
	}

	/**
	 * 完整树状图
	 * @param dirPath 文件夹路径
	 * @return 结果字符串
	 */
	public static String wholeTree(String dirPath) {
		File dirFile = new File(dirPath);
		return wholeTree(dirFile);
	}

	/**
	 * 复制文件或文件夹
	 * @param source 源路径
	 * @param destination 目标路径
	 */
	@SuppressWarnings("all")
	public static void copy(String source,String destination) {
        // 检查路径正确性
        if (source == null || destination == null) {
            throw new RuntimeException("\n源路径和目标路径不能为空！\n");
        }       
        
        // 把反斜杠替换成斜杠
        source = source.replace("\\", "/");
        destination = destination.replace("\\", "/");
        
        // 按需要修改路径格式
        if (source.endsWith("/")) source = source.substring(0, source.length() - 1);
        if (!destination.endsWith("/")) destination = destination + "/";
        
        // 创建源文件
        File src = new File(source);
        
        // 深度检查路径正确性
        if (!src.exists()) throw new RuntimeException("\n源文件不存在，复制失败\n");

        // 若在复制文件夹就加上文件夹名称
        if (src.isDirectory()) destination += src.getName() + "/";
        
        // 创建目标路径
        File des = new File(destination);      
        // 目标路径不存在时就创建
        des.mkdirs();
        
        // 线程池
        pool = Executors.newFixedThreadPool(MAX_OPERATING_AMOUNT);
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();       
              
        // 判断是否为文件/文件夹
		if (basicDetails) System.out.printf("""
		
		正在复制%s 这个%s		
		
		""", src.getName(), src.isFile() ? "文件" : "文件夹");

        // 如果是文件
        if (src.isFile()) {
            // 目标文件
            des = new File(destination + src.getName());
            // 大文件通道速度快
            pool.execute(new LargeCopyThread(src, des));
        } else {
            // 递归复制文件
            copyLoop(src,source,destination);
        }
		pool.shutdown();
        
        // 等待执行完成
		Tries.tryThis(() -> { while (!pool.awaitTermination(100, TimeUnit.MILLISECONDS)); });

		// 统计时间
  		if (basicDetails) {
  			long endTime = System.currentTimeMillis();
			if (showOperatingDetails) System.out.println();
  			System.out.println("耗时" + (double)((endTime - startTime) / 1000) + "秒，已完成\n");
  		}
    }

	/**
	 * 复制文件或文件夹
	 * @param src 源文件
	 * @param destination 目标路径
	 */
	public static void copy(File src, String destination) {
		copy(src.getAbsolutePath(), destination);
	}

	/**
	 * 用追加模式复制
	 * @param source 源路径
	 * @param destination 目标路径
	 */
	public static void copyUsingAppendMode(String source, String destination) {
		//追加模式
		SmallCopyThread.appendMode = LargeCopyThread.appendMode = true;

		//开始复制
		copy(source, destination);

		//复制结束后关闭追加模式
		SmallCopyThread.appendMode = LargeCopyThread.appendMode = false;

	}

	/**
	 * 用追加模式复制
	 * @param src 源文件
	 * @param destination 目标路径
	 */
	public static void copyUsingAppendMode(File src, String destination) {
		copyUsingAppendMode(src.getAbsolutePath(),destination);
	}

	/**
	 * 递归复制文件
	 * @param f 文件
	 * @param source 源路径
	 * @param destination 目标路径
	 */
	@SuppressWarnings("all")
	private static void copyLoop(File f, String source, String destination) {
        File[] fs = f.listFiles();
        if (fs == null) return;

        for (File src : fs) {
            // 创建目标临时绝对路径字串符(删去括号内提供的长度)
            String temp_des_path = destination + src.getAbsolutePath().substring(source.length());
            // 创建这个文件/文件夹
            File des = new File(temp_des_path);
            // 若现在循环内的这个文件是个目录
            if (src.isDirectory()) {
                // 创建这个目录
                des.mkdir();
                // 递归复制
                copyLoop(src, source, destination);
            } else {
                // 如果该文件为大文件
                if (src.length() > LARGE_FILE_SIZE) {
                    // 大文件通道
                	pool.execute(new LargeCopyThread(src, des));
                } else {
                    // 小文件通道
					pool.execute(new SmallCopyThread(src, des));
                }
            }
        }
    }

	/**
	 * 移动文件或文件夹
	 * @param srcPath 源路径
	 * @param desPath 目标路径
	 */
	@SuppressWarnings("all")
	public static void move(String srcPath, String desPath) {
        // 创建源文件
        File src = new File(srcPath);
        // 判断源文件是否存在
        if (!src.exists()) {
            throw new RuntimeException("\n源文件不存在\n");
        }

        // 目标路径最后加上斜杠
        if (!desPath.endsWith("/")) desPath += "/";
        // 加上源文件名称
        desPath += src.getName();
        // 创建目标文件
        File des = new File(desPath);

        // 创建目标父目录
        File parent = new File(des.getParent());
        // 若没有就创建
        parent.mkdirs();

		// 移动文件，若移动失败就该用传统方式
		if (!src.renameTo(des)) {
			boolean flag = basicDetails;
			basicDetails = false;

			copy(srcPath, des.getParent());

			if (des.exists()) {
				delete(srcPath);
			} else {
                basicDetails = flag;
				throw new RuntimeException("\n移动失败！");
			}

			basicDetails = flag;
		}

        // 提示信息
        if (basicDetails) System.out.println("\n成功将 " + src.getName() + " 移动到 " + parent.getName() + " 文件夹!\n");
    }

	/**
	 * 移动文件或文件夹
	 * @param src 文件或文件夹的File对象
	 * @param desPath 目标路径
	 */
	public static void move(File src, String desPath) {
		move(src.getAbsolutePath(), desPath);
	}

	/**
	 * 重命名文件或文件夹
	 * @param filePath 路径
	 * @param newName 新名称
	 */
    public static void rename(String filePath, String newName) {
        // 创建源文件
        File file = new File(filePath);
        // 判断源文件是否存在
        if (!file.exists()) {
            throw new RuntimeException("\n源文件不存在\n");
        }
        // 判断新名称是否存在
        if (newName == null || "".equals(newName)) {
            throw new RuntimeException("\n新名称不存在\n");
        }

        // 创建目标路径(原路径)
        String desPath = file.getParent();

        // 创建新目标文件
        File newFile = new File(desPath + "/" + newName);

        // 重命名文件
        if (!file.renameTo(newFile)) {
			System.out.println("重命名失败！");
			return;
		}

        //提示信息
        if (basicDetails) System.out.println("\n成功将 \"" + file.getName() + "\" 重命名为 \"" + newName + "\"\n");
    }

	/**
	 * 重命名文件或文件夹
	 * @param src 源文件
	 * @param newName 新名称
	 */
	public static void rename(File src, String newName) {
		rename(src.getAbsolutePath(), newName);
	}

	/**
	 * 删除文件或文件夹
	 * @param path 路径
	 */
	public static void delete(String path) {
        File f = new File(path);

        // 判断要删除的文件是否存在
        if (!f.exists()) throw new RuntimeException("\n要删除的文件不存在！\n");

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 判断是否为文件/文件夹
        if (basicDetails) System.out.println("\n正在删除 " + f.getName()
        	+ " 这个" + (f.isFile() ? "文件" : "文件夹"));

        // 递归删除
        deleteLoop(f);

        // 统计时间
        long endTime = System.currentTimeMillis();

        if (basicDetails) System.out.println("\n耗时" + (double)((endTime - startTime) / 1000) + "秒，已完成\n");
    }

	/**
	 * 删除文件或文件夹
	 * @param src 源文件的File对象
	 */
	public static void delete(File src) {
		delete(src.getAbsolutePath());
	}

	/**
	 * 删除一个文件列表内所有文件
	 * @param list 文件列表
	 */
    public static void deletes(List<File> list) {
        deletes(list.toArray(new File[0]));
    }

	/**
	 * 删除多个文件或文件夹
	 * @param fs 文件后文件夹
	 */
	public static void deletes(File... fs) {
		boolean flag = basicDetails;
		basicDetails = false;

		for (File f : fs) delete(f);

		Files.basicDetails = flag;
	}

	/**
	 * 递归删除文件或文件夹
	 * @param f 文件
	 */
	@SuppressWarnings("all")
	private static void deleteLoop(File f) {
        // 如果不能直接删除
        if (!f.delete()){
            // 递归删除
            File[] fs = f.listFiles();
            if (fs == null) return;

            for (File del : fs) {
                deleteLoop(del);
            }
            //删除之前的非空文件夹
            f.delete();
        }
    }

	/**
	 * 压缩文件或文件夹
	 * @param srcPath 源路径
	 * @param zipPath zip路径
	 * @param storeMode 是否用储存式压缩
	 */
	@SuppressWarnings("all")
    public static void zip(String srcPath,String zipPath, boolean storeMode) {
        // 创建源文件
        File src = new File(srcPath);
        // 检查路径
        if (!src.exists()) {
            throw new RuntimeException("\n源文件不存在\n");
        }

        // 创建目标zip文件
        File zip = new File(zipPath);
        // 创建目标父目录
        File parent = new File(zip.getParent());
        // 若不存在就创建
        parent.mkdirs();

        // 创建zip输出流
		ZipOutputStream out;
        try {
			out = new ZipOutputStream(new FileOutputStream(zip));
		} catch (FileNotFoundException e) {
            throw new RuntimeException("创建zip输出流失败！");
		}
		// 按照用户需求设置是否为储存模式
		if (storeMode) out.setMethod(ZipOutputStream.STORED);

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 提示信息
        if (basicDetails) System.out.println("\n正在压缩 " + src.getName() + " 这个" + (src.isFile() ? "文件\n" : "文件夹\n"));

        // 递归创建zip
        zipLoop(out,src,src.getName(), storeMode);

        // 关闭zip输出流
        try {
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("zip输出流关闭失败！");
		}

  		if (basicDetails) {
  			long endTime = System.currentTimeMillis();
			if (showOperatingDetails) System.out.println();
  			System.out.println("耗时" + (double) ((endTime - startTime) / 1000) + "秒，已完成\n");
  		}
    }

	/**
	 * 压缩文件或文件夹
	 * @param src 源文件
	 * @param zipPath zip路径
	 * @param storeMode 是否用储存式压缩
	 */
	public static void zip(File src, String zipPath, boolean storeMode) {
		zip(src.getAbsolutePath(), zipPath, storeMode);
	}

	/**
	 * 在本目录下创建压缩文件
	 * @param srcPath 源路径
	 * @param storeMode 是否用储存式压缩
	 */
	public static void zip(String srcPath, boolean storeMode) {
        // 声明原路径
        File src = new File(srcPath);
        // zip生成路径为源路径
        String zipPath = src.getParent() + "/" + src.getName() + ".zip";
        // 开始创建
        zip(srcPath, zipPath, storeMode);
    }

	/**
	 * 在本目录下创建压缩文件
	 * @param src 源文件
	 * @param storeMode 是否用储存式压缩
	 */
	public static void zip(File src, boolean storeMode) {
		zip(src.getAbsolutePath(), storeMode);
	}

	/**
	 * 递归创建zip
	 * @param out zip输出流
	 * @param f 源文件
	 * @param root 根路径
	 * @param storeMode 是否用储存式压缩
	 */
    private static void zipLoop(ZipOutputStream out, File f, String root, boolean storeMode) {
        // 若f是一个文件夹
        if (f.isDirectory()) {
            File[] fs = f.listFiles();
            if (fs == null) return;

            if (fs.length == 0) {
                // 创建(放入)此文件夹
				ZipEntry entry = new ZipEntry(root + "/");
				if (storeMode) {
					entry.setCrc(0);
					entry.setSize(0);
				}
				Tries.tryThis(() -> out.putNextEntry(entry));
            } else {
                for (File src : fs) {
                    // 即将被复制文件的完整路径(root为根目录)
                    String filePath = root + "/" + src.getName();
                    // 递归创建
                    zipLoop(out, src, filePath, storeMode);
                }
            }
        } else {
			//提示信息
            if (showOperatingDetails) System.out.println("正在压缩: " + f.getAbsolutePath());
            //放入上文提到的完整路径
            try {
				ZipEntry entry = new ZipEntry(root);
				if (storeMode) {
					CRC32 crc = new CRC32();
					var in = new FileInputStream(f);
					byte[] b = new byte[8192];
					int i;
					while ((i = in.read(b)) != -1) {
						crc.update(b, 0, i);
					}
					in.close();

					entry.setCrc(crc.getValue());
					entry.setSize(f.length());
				}
				out.putNextEntry(entry);
			} catch (IOException e) {
				e.printStackTrace();
                throw new Error("重大异常！");
			}
			//输出文件到zip流
            out(f, out, false);
        }
    }

	/**
	 * 解压文件
	 * @param zipPath zip路径
	 * @param desPath 目标路径
	 */
	@SuppressWarnings("all")
	public static void unzip(String zipPath, String desPath) {
        // 创建源文件
        File src = new File(zipPath);
        // 判断是否存在
        if (!src.exists()) throw new RuntimeException("\nzip文件不存在!\n");

        // 在目标路径最后加上斜杠
        if (!desPath.endsWith("/")) desPath += "/";

        // 创建zip文件
        ZipFile zip;
		try {
			zip = new ZipFile(src);
		} catch (IOException e) {
			throw new RuntimeException("ZipFile对象创建失败！");
		}

        // 创建entries数组
        Enumeration<? extends ZipEntry> enumer = zip.entries();
        // 线程池
        pool = Executors.newFixedThreadPool(MAX_OPERATING_AMOUNT);

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 提示信息
        if (basicDetails) System.out.println("\n正在解压 " + src.getName() + " 这个压缩文件\n");

        // 遍历数组
        while (enumer.hasMoreElements()) {
            // 创建enrty
            var entry = enumer.nextElement();

            // 创建临时目标路径
            String temp_des_path = desPath + entry.getName();
            // 创建临时目标文件
            File des = new File(temp_des_path);

            //如果entry指向一个文件夹
            if (entry.isDirectory()) {
                //若不存在就创建文件夹
				des.mkdirs();
            } else {
				des.getParentFile().mkdirs();
				//多线程
                pool.execute(new ZipOutputThread(zip,des,entry));
            }
        }
        pool.shutdown();

		// 等待执行完成
		Tries.tryThis(() -> { while (!pool.awaitTermination(100, TimeUnit.MILLISECONDS)); });

        //关闭流
		try {
			zip.close();
		} catch (IOException e) {
            throw new RuntimeException("zipFile关闭时出错！");
		}

  		if (basicDetails) {
  			long endTime = System.currentTimeMillis();
			if (showOperatingDetails) System.out.println();
  			System.out.println("耗时" + (double) ((endTime - startTime) / 1000) + "秒，已完成\n");
  		}
    }

	/**
	 * 解压文件
	 * @param zip zip文件
	 * @param desPath 目标路径
	 */
	public static void unzip(File zip, String desPath) {
		unzip(zip.getAbsolutePath(), desPath);
	}

	/**
	 * 在本目录下解压文件
	 * @param zipPath zip文件路径
	 */
	public static void unzip(String zipPath) {
        //声明原zip路径
        File zip = new File(zipPath);
        //zip父目录为解压目录
        String desPath = zip.getParent();
        //zip开始解压
        unzip(zipPath, desPath);
    }

	/**
	 * 在本目录下解压文件
	 * @param zip zip文件
	 */
	public static void unzip(File zip) {
		unzip(zip.getAbsolutePath());
	}

	/**
	 * 读取到byte数组
	 * @param src 源文件
	 * @return 结果数组
	 */
	public static byte[] readToBytes(File src) {
		// 若文件不存在
		if (!src.exists()) {
			System.out.println("文件不存在！");
			return null;
		}

		return Tries.tryReturnsT(() -> Streams.read(new FileInputStream(src)));
	}

	/**
	 * 读取到byte数组
	 * @param path 源路径
	 * @return 结果数组
	 */
	public static byte[] readToBytes(String path) {
		// 创建源文件
		File src = new File(path);

		// 返回结果
		return readToBytes(src);
	}

	/**
	 * 读取文件到字符串
	 * @param src 源文件
	 * @param srcCharsetName 文件编码名称
	 * @return 结果字符串
	 */
	public static String read(File src, String srcCharsetName) {
    	//返回结果
		byte[] content = readToBytes(src);
		return content != null ? new String(content, Charset.forName(srcCharsetName)) : null;
	}

	/**
	 * 读取文件到字符串
	 * @param path 源文件路径
	 * @param srcCharsetName 文件编码名称
	 * @return 结果字符串
	 */
	public static String read(String path, String srcCharsetName) {
		// 创建源文件
		File src = new File(path);

		// 返回结果
		return read(src, srcCharsetName);
	}

	/**
	 * 默认系统编码读取
	 * @param src 源文件
	 * @return 结果字符串
	 */
	public static String read(File src) {
		return read(src, Charset.defaultCharset().name());
	}

	/**
	 * 默认系统编码读取
	 * @param path 源文件路径
	 * @return 结果字符串
	 */
	public static String read(String path) {
		// 创建源文件
		File src = new File(path);

		// 返回结果
		return read(src);
	}

	/**
	 * 输出文件到输出流
	 * @param f 文件
	 * @param out 输出流
	 * @param closeOut 是否关闭输出流
	 */
	public static void out(File f, OutputStream out, boolean closeOut) {
		Objs.throwsIfNullOrEmpty(f, out);
		Tries.tryThis(() -> Streams.out(new FileInputStream(f), out, closeOut));
	}

	/**
	 * 输出文件到输出流
	 * @param filePath 文件路径
	 * @param out 输出流
	 * @param closeOut 是否关闭输出流
	 */
	public static void out(String filePath, OutputStream out, boolean closeOut) {
		out(new File(filePath), out, closeOut);
	}

	/**
	 * 输入流输出到文件
	 * @param in 输入流
	 * @param f 文件
	 * @param appendMode 是否用追加模式
	 */
	@SuppressWarnings("all")
	public static void out(InputStream in, File f, boolean appendMode) {
		Objs.throwsIfNullOrEmpty(in, f, appendMode);
		f.getParentFile().mkdirs();

		Tries.tryThis(() -> Streams.out(in, new FileOutputStream(f, appendMode), true));
	}

	/**
	 * 输入流输出到文件
	 * @param in 输入流
	 * @param filePath 文件路径
	 * @param appendMode 是否用追加模式
	 */
	public static void out(InputStream in, String filePath, boolean appendMode) {
		out(in, new File(filePath), appendMode);
	}

	/**
	 * 把byte数组输出到文件
	 * @param b byte数组
	 * @param des 目标文件
	 * @param appendMode 是否用追加模式
	 */
	@SuppressWarnings("all")
	public static void out(byte[] b, File des, boolean appendMode) {
		if (b == null) throw new RuntimeException("数组为空！");

		// 创建目标文件父目录
		File parent = new File(des.getParent());

		// 若父目录不存在就创建
		parent.mkdirs();

		// 输出
		try (var out = new FileOutputStream(des, appendMode)) {
			// 写入
			out.write(b);
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException("输出流写入失败！");
		}
	}

	/**
	 * 把byte数组输出到文件
	 * @param b byte数组
	 * @param desPath 目标文件路径
	 * @param appendMode 是否用追加模式
	 */
	public static void out(byte[] b, String desPath, boolean appendMode) {
		// 创建目标文件
		File des = new File(desPath);

		// 开始输出
		out(b, des, appendMode);
	}

	/**
	 * 输出字符串到文件
	 * @param src 字符串
	 * @param des 目标文件
	 * @param desCharsetName 目标文件编码名称
	 * @param appendMode 是否用追加模式
	 */
	public static void out(String src, File des, String desCharsetName, boolean appendMode) {
		// 若字符串为空
		if (src == null | "".equals(src)) throw new RuntimeException("字符串为空！");

		// 开始输出
		out(src.getBytes(Charset.forName(desCharsetName)), des, appendMode);
	}

	/**
	 * 输出字符串到文件
	 * @param src 字符串
	 * @param desPath 目标文件路径
	 * @param desCharsetName 目标文件编码名称
	 * @param appendMode 是否用追加模式
	 */
	public static void out(String src, String desPath, String desCharsetName, boolean appendMode) {
		// 创建目标文件
		File des = new File(desPath);

		// 开始输出
		out(src, des, desCharsetName, appendMode);
	}

	/**
	 * 输出字符串到文件
	 * @param src 字符串
	 * @param des 目标文件
	 * @param appendMode 是否用追加模式
	 */
	public static void out(String src, File des, boolean appendMode) {
        out(src, des, Charset.defaultCharset().name(), appendMode);
	}

	/**
	 * 输出字符串到文件
	 * @param src 字符串
	 * @param desPath 目标文件路径
	 * @param appendMode 是否用追加模式
	 */
	public static void out(String src, String desPath, boolean appendMode) {
		//创建目标文件
		File des = new File(desPath);

		//开始输出
		out(src, des, appendMode);
	}

	/**
	 * 设置文件编码
	 * @param src 源文件
	 * @param oldCharsetName 旧编码名称
	 * @param newCharsetName 新编码名称
	 */
	public static void setEncoding(File src, String oldCharsetName, String newCharsetName) {
		byte[] srcBytes = readToBytes(src);
		if (srcBytes == null) throw new RuntimeException("设置编码失败！");
		byte[] bs = new String(srcBytes, Charset.forName(oldCharsetName))
				.getBytes(Charset.forName(newCharsetName));
		out(bs, src, false);
	}

	/**
	 * 设置文件编码
	 * @param path 源文件路径
	 * @param oldCharsetName 旧编码名称
	 * @param newCharsetName 新编码名称
	 */
	public static void setEncoding(String path, String oldCharsetName, String newCharsetName) {
		File src = new File(path);
		setEncoding(src, oldCharsetName, newCharsetName);
	}

	/**
	 * 大文件复制通道
	 */
	private static class LargeCopyThread implements Runnable {
		/**
		 * 要复制的文件
		 */
		private File src;

		/**
		 * 目标路径
		 */
		private File des;

		/**
		 * 追加模式
		 */
		private static boolean appendMode = false;

		private LargeCopyThread(File src, File des) {
			this.src = src;
			this.des = des;
			// 提示信息
			if (Files.showOperatingDetails) System.out.println("正在复制大文件: " + src.getAbsolutePath());
		}

		private LargeCopyThread() {}

		/**
		 * 复制文件
		 */
		public void run() {
			try (var in = new FileInputStream(src).getChannel();
			     var out = new FileOutputStream(des, appendMode).getChannel())
			{
				// 申请8M的堆外内存
				var buff = ByteBuffer.allocateDirect(8 * 1024 * 1024);
				while (in.read(buff) != -1) {
					// 改变buff为读模式
					buff.flip();
					out.write(buff);
					// buff的复位
					buff.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 小文件复制通道
	 */
	private static class SmallCopyThread implements Runnable {
		/**
		 * 要复制的文件
		 */
		private final File src;

		/**
		 * 目标路径
		 */
		private final File des;

		/**
		 * 追加模式
		 */
		private static boolean appendMode = false;

		public SmallCopyThread(File src, File des) {
			this.src = src;
			this.des = des;
			// 提示信息
			if (Files.showOperatingDetails) System.out.println("正在复制文件: " + src.getAbsolutePath());
		}

		/**
		 * 复制文件
		 */
		public void run() {
			try (var in = new FileInputStream(src);
			     var out = new FileOutputStream(des, appendMode))
			{
				in.transferTo(out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Zip输出通道
	 */
	@SuppressWarnings("all")
	private static class ZipOutputThread implements Runnable {
		/**
		 * zip文件
		 */
		private final ZipFile zip;

		/**
		 * 临时目标文件
		 */
		private final File des;

		/**
		 * zip节点
		 */
		private final ZipEntry entry;

		public ZipOutputThread(ZipFile zip, File des, ZipEntry entry) {
			this.zip = zip;
			this.des = des;
			this.entry = entry;
			// 提示信息
			if (Files.showOperatingDetails) System.out.println("正在解压: " + entry.getName());
		}

		public void run() {
			// 创建父目录
			File parent = new File(des.getParent());
			// 若不存在就创建
			parent.mkdirs();

			try (var in = zip.getInputStream(entry);
			     var out = new FileOutputStream(des))
			{
				in.transferTo(out);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

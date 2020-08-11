/**
 * 作者: Jmc
 * 时间: 2019.1.12 
 * 功能: 文件操作
 * 更新: 
 *       2019.1.25   1.增加“源文件是否为文件夹的判断”
 *                   2.优化提示信息
 *       2019.1.27   1.优化删除文件(删除一个有2100长文件夹只需38秒)
 *                   2.添加多线程
 *       2019.1.28   添加随机存储
 *       2019.1.29   添加大文件通道(删除原有随机存储)
 *       2019.1.31   根据设备彻底优化，从最大程度避免出错            
 *       2019.2.2    1.加入zip压缩和解压
 *                   2.加入文件的移动和重命名
 *       2019.2.3    1.加入基础提示布尔值
 *                   2.加入状态信息
 *		 2019.2.17   加入输出和读取功能
 *		 2019.2.27   读取改用FIS(BR末尾换行，故不采用)
 * 		 2019.2.28 	 输出功能加入追加模式
 *		 2019.3.15   加入是否检查的布尔值
 *		 2019.3.19   设置不抛出所有异常
 *		 2019.3.23   复制功能加入追加模式
 *		 2019.4.15   做到精准计算操作用时
 *		 2019.5.3    通道均改为静态内部类方式
 *		 2019.6.23   优化提示信息
 *		 2019.7.3    加入输出字节码功能
 *		 2019.8.22 	 1.改输入字节码功能为readToBytes
 *					 2.将readToBytes,outBytes作为输入输出主方法
 *		 2019.8.23   1.优化文件压缩和解压，及时关闭文件流和清除缓冲区
 * 		 			 2.删除out方法中检查文件完整性判断
 * 		 2019.8.24   1.添加文件搜索功能
 * 					 2.优化文件信息统计
 * 					 3.删除结果保存功能
 * 					 4.删除多余成员变量
 * 					 5.删除是否复制到sd卡判断
 * 					 6.优化线程结束判断
 *		 2019.8.25   1.高级搜索功能添加过滤器
 *					 2.调整流关闭顺序(先打开的后关闭，只需关闭Channel无需关闭文件流)
 *		 2019.8.31   1.加入线程池
 *					 2.修改临时数组大小: 1024(1k) -> 8192(8k) 性能提升明显
 *		 2019.9.13   1.加入文件编码修改功能
 *					 2.输入输出方法加入字符集参数
 *		 2019.10.5   加入单文件搜索(findAny)方法
 *		 2020.3.4    用非递归方式统计文件夹和搜索单文件，其中搜索单文件的速度明显加快
 *		 2020.3.10   添加批量重命名方法
 *		 2020.3.11 	 1.修复文件信息方法中的统计错误(将初始文件夹的大小：4KB也统计进去了)
 *					 2.添加文件长度转换方法
 *		 2020.3.12 	 加入树状图统计文件夹功能
 *		 2020.3.31   1.加入批量移动和批量删除功能
 *		 			 2.将批量方法集中并改名为寻找XX
 *					 3.添加寻找复制方法
 *					 4.添加findDO模板以统一化代码，并扩展findXX为orContains形式
 *		 2020.4.1    1.升级findToMap方法，可以输入多个搜索条件
 *					 2.添加deletes方法
 *		 2020.4.8    1.添加outStream方法
 *					 2.将所有out系列方法(outBytes, outStream)统一命名为out
 *					 3.压缩方法添加“储存模式”可选项
 *					 4.优化输出，使统计结果更加美观
 *		 2020.4.9    将Streams类的流与文件方法移动到本类
 *		 2020.4.17   添加按时间重命名方法
 *		 2020.4.20   完善移动方法，使其能兼容不同分区的移动
 *       2020.8.7    1.添加findFiles和findDirs方法
 *                   2.将findToMap方法改名为findAll
 *                   3.删除findAll第一个参数为File的重载方法
 *
 */

package com.jmc.io;
 
import com.jmc.lang.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.zip.*;

public class Files
{ 
    //大文件指定界线
    private final static int LARGE_FILE_SIZE = 1024 * 1024 * 500; 
    //最多正在操作文件数
    private final static int MAX_OPERATING_AMOUNT = 500;
    //线程池
    private static ExecutorService pool;
    
    //是否显示正在操作的提示信息
    public static boolean showOperatingDetails = false;
    //是否显示基础提示信息
    public static boolean basicDetails = true;
    
    //存放集合
    private static Map<String, List<File>> map;
    private static List<File> fileList;
    private static List<File> dirList;
	
    //高级搜索
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
    	
    	map = new HashMap<String, List<File>>();
    	fileList = new LinkedList<File>();
    	dirList = new LinkedList<File>();
    	
    	map.put("file", fileList);
    	map.put("dir", dirList);
    	
    	findLoop(src, filter);
    	
    	return map;
    }
	public static Map<String, List<File>> findAll(String path, final String... contains) {
		if (contains.length == 0) {
			System.out.println("搜索参数不能为空！");
			return null;
		}
		
    	FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File f) {
				return Strs.orContains(f.getName() ,contains);
			}
		};
    	Files.findAll(path, filter);
		
		return map;
	}
    
    public static List<File> findFiles(String path, FileFilter filter) {
        return findAll(path, filter).get("file");
    }
    public static List<File> findFiles(String path, String... contains) {
        return findAll(path, contains).get("file");
    }
    
    public static List<File> findDirs(String path, FileFilter filter) {
        return findAll(path, filter).get("dir");
    }
    public static List<File> findDirs(String path, String... contains) {
        return findAll(path, contains).get("dir");
    }
	
	//搜索文件循环
    private static void findLoop(File src, FileFilter filter) {
    	File[] fs = src.listFiles();
		if (fs == null) return;

		for (File f : fs) {
			if (f.isFile() && filter.accept(f)) {
				fileList.add(f);
			} else {
				if (filter.accept(f)) dirList.add(f);
				findLoop(f,filter);
			}		
		}
    }
	
    //简单搜索
    public static String find(String path, final String content) {
    	if (content == null) return null;
    	
    	long startTime = System.currentTimeMillis();
    	Files.findAll(path, content);
    	StringBuilder sb = new StringBuilder();
		
		sb.append("文件夹：\n");
		for (File d : dirList) {
			sb.append(d.getAbsolutePath());
			sb.append("\n");
		}
		
		sb.append("\n文件：\n");
		for (File f : fileList) {
			sb.append(f.getAbsolutePath());
			sb.append("\n");
		}
		
		long endTime = System.currentTimeMillis();
		sb.append("\n共搜索到\n" + dirList.size() + "个文件夹\n"
				  + fileList.size() + "个文件"
				  +"\n本次搜索耗时" + (int)(endTime - startTime) / 1000 + "秒\n");
		
		return sb.toString();
    }
    public static String find(File dirFile, String content) {
        return find(dirFile.getAbsolutePath(), content);
    }
	
	//搜索单个文件
    public static File findAny(String path, final String content) {
		//文件夹临时列表
		List<File> temp = new ArrayList<>();
		
		File src = new File(path);
		if (!src.exists()) {
			System.out.println("路径不存在!");
			return null;
		}
		
		if (src.isFile()) {
			System.out.println("搜索父目录必须为文件夹！");
			return null;
		} else {
			temp.add(src);
		}
		
		while (!temp.isEmpty()) {
			for (File f : temp.remove(0).listFiles()) {
				if (f.isDirectory()){
					temp.add(f);
				} else {
					if (f.getName().indexOf(content) != -1) return f;
				}
			}
		}
		
    	return null;
    }
    public static File findAny(File dirFile, String content) {
        return findAny(dirFile.getAbsolutePath(), content);
    }
	
	//“寻找并做”模板
	private static boolean findDO(Runnable r, String dirPath, final String... orContains) {
		if (orContains.length == 0) {
			System.out.println("搜索字符不能为空！");
			return false;
		}
		
		findAll(dirPath, new FileFilter() {
			@Override
			public boolean accept(File f) {
				return Strs.orContains(f.getName(), orContains);
			}
		});
		
		boolean modified = false;
		if (basicDetails) basicDetails = false;
		else modified = true;
		
		r.run();
		
		if (!modified) Files.basicDetails = true;
		
		return true;
	}
	
	//寻找复制
	public static boolean findCopies(String dirPath, final String desPath, String... orContains) {
		return findDO(new Runnable() {
			@Override
			public void run() {
				for (File f : fileList) copy(f, desPath);
			}
		}, dirPath, orContains);
	}
	public static boolean findCopies(File dirFile, String desPath, String... orContains) {
		return findRenames(dirFile.getAbsolutePath(), desPath, orContains);
	}
	
	//寻找移动
	public static boolean findMoves(String dirPath, final String desPath, String... orContains) {
		return findDO(new Runnable() {
			@Override
			public void run() {
				for (File f : fileList) move(f, desPath);
			}
		}, dirPath, orContains);
	}
	public static boolean findMoves(File dirFile, String desPath, String... orContains) {
		return findMoves(dirFile.getAbsolutePath(), desPath, orContains);
	}
	
	//寻找重命名
	public static boolean findRenames(String dirPath, final String newChar, final String... oldChars) {
		return findDO(new Runnable() {
			@Override
			public void run() {
				for (File f : fileList) rename(f, Strs.orReplace(f.getName(), newChar, oldChars));
			}
		}, dirPath, oldChars);
	}
	public static boolean findRenames(File dirFile, String newChar, String... oldChars) {
		return findRenames(dirFile.getAbsolutePath(), newChar, oldChars);
	}
	
	public static boolean findRename(String dirPath, String oldChar, String newChar) {
		return findRenames(dirPath, newChar, oldChar);
	}
	public static boolean findRename(File dirFile, String oldChar, String newChar) {
		return findRename(dirFile.getAbsolutePath(), oldChar, newChar);
	}
	
	//寻找删除
	public static boolean findDels(String dirPath, String... orContains) {
		return findDO(new Runnable() {
			@Override
			public void run() {
				for (File f : fileList) f.delete();
			}
		}, dirPath, orContains);
	}
	public static boolean findDels(File dirFile, String... orContains) {
		return findDels(dirFile.getAbsolutePath(), orContains);
	}
	
	//按时间重命名
	public static void renamesByTime(File dirFile, String suffix) {
		Objs.throwsIfNullOrEmpty(dirFile, suffix);
		
		Files.basicDetails = false;
		File[] fs = dirFile.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isFile();
			}
		});
		
		Arrays.sort(fs, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				return Long.compare(f1.lastModified(), f2.lastModified());
			}
		});
		
		for (int i = 0; i < fs.length; i++) {
			rename(fs[i], (i + 1) + suffix);
		}
		
		Files.basicDetails = true;
	}
	public static void renamesByTime(String dirPath, String suffix) {
		renamesByTime(new File(dirPath), suffix);
	}
	
	//文件信息
    public static String fileInfo(String path) {
        File src = new File(path);
        if (!src.exists()) {
			System.out.println("路径不存在！");
			return null;
		}
		
		//文件夹临时列表
		List<File> temp = new ArrayList<>();

        //文件总长度
        long length = 0;
        
		//统计
		long files = 0;
		long dirs = 0;

        long startTime = System.currentTimeMillis();

        //如果是个目录
        if (src.isDirectory()) {  
			temp.add(src);
			while (!temp.isEmpty()){
				for (File f : temp.remove(0).listFiles()) {
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

		return "\n正在统计: " + src.getName()
			+ "\n\n共" + files + "个文件\n" 
			+ dirs + "个文件夹"
			+ "\n大小为" + lengthFormatter(length)
			+ " (" + new DecimalFormat().format(length) + "字节)"
			+ "\n本次统计耗时" + (int)(endTime - startTime) / 1000 + "秒\n";
    }
	
	//文件长度转换
	public static String lengthFormatter(long length) {
		long B = 1, KB = B * 1024, MB = KB * 1024,
			GB = MB * 1024, TB = GB * 1024,
			PB = TB * 1024, EB = PB * 1024;

		double len = 0;
		String unit = null;

		if (length < KB) {
			return length + "B";
		} else if (length < MB) {
			len = (double)length / KB;
			unit = "KB";
		} else if (length < GB) {
			len = (double)length / MB;
			unit = "MB";
		} else if (length < TB) {
			len = (double)length / GB;
			unit = "GB";
		} else if (length < PB) {
			len = (double)length / TB;
			unit = "TB";
		} else if (length < EB) {
			len = (double)length / PB;
			unit = "PB";
		} else {
			len = (double)length / EB;
			unit = "EB";
		}
		return String.format("%.2f", len) + unit;
	}
	
    //树状图统计文件夹
	public static String tree(File dirFile, int level, double MIN_MB_SIZE) {
		if (!dirFile.exists()) {
			System.out.println("文件夹不存在");
			return null;
		}
		
		if (dirFile.isFile()) {
			System.out.println("统计的对象不能为文件！");
			return null;
		}
		
		long startTime = System.currentTimeMillis();
		String result = treeLoop(dirFile, 0, MIN_MB_SIZE, level)[0].toString();
		long endTime = System.currentTimeMillis();
		
		return "\n" + (!result.isEmpty() ? result : "null\n")  + "\n本次统计耗时" + (int)(endTime - startTime) / 1000 + "秒\n";
	}
	public static String tree(String dirPath, int level, double MIN_MB_SIZE) {
		File dirFile = new File(dirPath);
		return tree(dirFile, level, MIN_MB_SIZE);
	}
	
	//树状图创建循环
	private static Object[] treeLoop(File dirFile, int depth, double MIN_MB_SIZE, int level) {
		//储存字符串
		StringBuilder sb = new StringBuilder();
		//文件名
		String fileName = dirFile.getName();
		//最小文件夹长度
		Long MIN_Length = (long)(MIN_MB_SIZE * 1024 * 1024);
		//长度
		long length = 0;

		for (int i = 0; i < depth; i++) sb.append("    ");
		sb.append("├─" + dirFile.getName() + "\n");

		depth++;

		for (File f : dirFile.listFiles()) {
			if (f.isDirectory()) {
				Object[] os = treeLoop(f, depth, MIN_MB_SIZE, level);
				if (depth <= level) sb.append(os[0]);
				//将子文件夹大小加入父文件夹
				length += (long)os[1];
			} else {
				if (depth <= level && f.length() >= MIN_Length) {
					for (int i = 0; i < depth; i++) sb.append("    ");
					sb.append("├─" + f.getName() + "\n");

					length += f.length();
					//在换行前插入文件大小
					sb.insert(sb.length() - 1, " " + lengthFormatter(f.length()));
				} else {
					length += f.length();
				}
			}
		}

		sb.insert(sb.indexOf(fileName) 
				  + fileName.length(),
				  " " + lengthFormatter(length));

		return length >= MIN_Length ?
			new Object[] { sb, length }
			: new Object[] { "", length };
	}	
	
	//普通树状图
	public static String normalTree(File dirFile) {
		String result = tree(dirFile, 5, 50);
		if (result.startsWith("\nnull")) return tree(dirFile, 5, 0);
		return result;
	}
	public static String normalTree(String dirPath) {
		File dirFile = new File(dirPath);
		return normalTree(dirFile);
	}
	
	//单层树状图
	public static String singleTree(File dirFile) {
		String result = tree(dirFile, 1, 50);
		if (result.startsWith("\nnull")) return tree(dirFile, 1, 0);
		return result;
	}
	public static String singleTree(String dirPath) {
		File dirFile = new File(dirPath);
		return singleTree(dirFile);
	}

	//完整树状图
	public static String wholeTree(File dirFile) {
		return tree(dirFile, Integer.MAX_VALUE, 0);
	}
	public static String wholeTree(String dirPath) {
		File dirFile = new File(dirPath);
		return wholeTree(dirFile);
	}
	
    //复制文件
    public static boolean copy(String source,String destination) {           
        //检查路径正确性
        if (source == null || destination == null) {
            System.out.println("\n源路径和目标路径不能为空！\n");
            return false;
        }       
        
        //把反斜杠替换成斜杠
        source = source.replace("\\","/");
        destination = destination.replace("\\","/");
        
        //按需要修改路径格式
        if (source.endsWith("/")) source = source.substring(0,source.length() - 1);
        if (!destination.endsWith("/")) destination = destination + "/";
        
        //创建源文件
        File src = new File(source);
        
        //深度检查路径正确性
        if (!src.exists()) {
            System.out.println("\n源文件不存在，复制失败\n");
            return false;
        }             
                       
        //若在复制文件夹就加上文件夹名称
        if (src.isDirectory()) destination += src.getName() + "/";
        
        //创建目标路径
        File des = new File(destination);      
        //目标路径不存在时就创建
        if (!des.exists()) des.mkdirs();
        
        //线程池
        pool = Executors.newFixedThreadPool(MAX_OPERATING_AMOUNT);
        
        //记录开始时间
        long startTime = System.currentTimeMillis();       
              
        //判断是否为文件/文件夹
        if(basicDetails) System.out.println("\n正在复制 " + src.getName()
            + " 这个" + (src.isFile() ? "文件\n" : "文件夹\n"));                        
              
        //如果是文件
        if (src.isFile()) {
            //目标文件        
            des = new File(destination + src.getName());
            //大文件通道速度快
            pool.execute(new LargeCopyThread(src, des));
        } else {
            //开启复制文件循环
            copyLoop(src,source,destination);
        }
		pool.shutdown();
        
        //统计时间
        while (!pool.isTerminated()) {
  			try {
  				Thread.sleep(100);
  			} catch (InterruptedException e) {
  				return false;
  			}
  		}	
  		
  		if(basicDetails) {
  			long endTime = System.currentTimeMillis();
			if (showOperatingDetails) System.out.println();
  			System.out.println("耗时" + (double)((endTime - startTime) / 1000) + "秒，已完成\n");
  		}	
  		
		return true;
    }
	public static boolean copy(File src, String destination) {
		return copy(src.getAbsolutePath(),destination);
	}
	
	//用追加模式复制
	public static boolean copyUsingAppendMode(String source, String destination) {
		//追加模式
		SmallCopyThread.appendMode = true;
		LargeCopyThread.appendMode = true;
		
		//开始复制
		boolean flag = copy(source, destination);
		
		//复制结束后关闭追加模式
		SmallCopyThread.appendMode = false;
		LargeCopyThread.appendMode = false;
		
		//返回
		return flag;
	}
	public static boolean copyUsingAppendMode(File src, String destination) {
		return copyUsingAppendMode(src.getAbsolutePath(),destination);
	}
       
    //复制文件循环
    private static void copyLoop(File f, String source, String destination) { 
        File[] fs = f.listFiles();           
        //获取文件夹f内的所有文件/目录
        for (File src : fs) {
            //创建目标临时绝对路径字串符(删去括号内提供的长度)            
            String temp_des_path = destination
                + src.getAbsolutePath()
                .substring(source.length());
            //创建这个文件/文件夹
            File des = new File(temp_des_path);
            //若现在循环内的这个文件是个目录
            if (src.isDirectory()) {
                //创建这个目录
                des.mkdir();        
                //多重循环复制
                copyLoop(src, source, destination);
            } else {
                //如果该文件为大文件
                if (src.length() > LARGE_FILE_SIZE) {
                    //大文件通道
                	pool.execute(new LargeCopyThread(src, des));
                } else {                       
                    //小文件通道         
					pool.execute(new SmallCopyThread(src, des));
                }                  
            }                               
        }
    }
    
    //移动文件
    public static boolean move(String srcPath, String desPath) {
        //创建源文件
        File src = new File(srcPath);
        //判断源文件是否存在
        if (srcPath == null || !src.exists()) {
            System.out.println("\n源文件不存在\n");
            return false;
        }        
        
        //目标路径最后加上斜杠
        if(!desPath.endsWith("/")) desPath += "/";
        //加上源文件名称
        desPath += src.getName();
        //创建目标文件
        File des = new File(desPath);
        
        //创建目标父目录
        File parent = new File(des.getParent());
        //若没有就创建
        if(!parent.exists()) parent.mkdirs();
        
        //移动文件
        src.renameTo(des);
		
		//移动失败就该用传统方式
		if (!des.exists()) {
			boolean flag = basicDetails;
			basicDetails = false;
			copy(srcPath, des.getParent());
			if (des.exists()) {
				delete(srcPath);
			} else {
				System.out.println("\n移动失败！");
				basicDetails = flag;
				return false;
			}
			basicDetails = flag;
		}
        
        //提示信息
        if(basicDetails) {
            System.out.println("\n成功将 " + src.getName() 
                               + " 移动到 " + parent.getName() + " 文件夹!\n");
        }
       
		return true;
    }
	public static boolean move(File src, String desPath) {
		return move(src.getAbsolutePath(),desPath);
	}
	
    //重命名文件
    public static boolean rename(String filePath, String newName) {
        //创建源文件
        File file = new File(filePath);
        //判断源文件是否存在
        if (filePath == null || !file.exists()) {
            System.out.println("\n源文件不存在\n");
            return false;
        }
        //判断新名称是否存在
        if (newName == null || "".equals(newName)) {
            System.out.println("\n新名称不存在\n");
            return false;
        }
        
        //创建目标路径(原路径)
        String desPath = file.getParent();
        
        //创建新目标文件
        File newFile = new File(desPath + "/" + newName);
        
        //重命名文件
        file.renameTo(newFile);
        
        //提示信息
        if(basicDetails) {
            System.out.println("\n成功将 \"" + file.getName() + "\" 重命名为 \"" + newName + "\"\n");
        }
		
		return true;
    }
	public static boolean rename(File src, String newName) {
		return rename(src.getAbsolutePath(),newName);
	}
    
    //删除文件
    public static boolean delete(String path) {
        File f = new File(path);
		
        //判断要删除的文件是否存在
        if (!f.exists()) {
            System.out.println("\n要删除的文件不存在！\n");
            return false;
        }
		
        //记录开始时间
        long startTime = System.currentTimeMillis();
		
        //判断是否为文件/文件夹
        if(basicDetails) System.out.println("\n正在删除 " + f.getName()
        	+ " 这个" + (f.isFile() ? "文件" : "文件夹"));     
		
        //开启删除循环
        deleteLoop(f);
		
        //统计时间
        long endTime = System.currentTimeMillis();
		
        if(basicDetails) {
            System.out.println("\n耗时" + (double)((endTime - startTime) / 1000) + "秒，已完成\n");
        }
		
		return true;
    }
	public static boolean delete(File src) {
		return delete(src.getAbsolutePath());
	}
	public static boolean deletes(File... fs) {
		boolean modified = false;
		if (basicDetails) basicDetails = false;
		else modified = true;

		for (File f : fs) delete(f);

		if (!modified) Files.basicDetails = true;
		
		return true;
	}
	
    //删除文件循环
    private static void deleteLoop(File f) {
        //如果不能直接删除
        if(!f.delete()){
            //多重循环删除
            File[] fs = f.listFiles();
            for (File del : fs) {
                deleteLoop(del);        
            }
            //删除之前的非空文件夹        
            f.delete();
        }   
    }
	
    //压缩文件(zip)
    public static boolean zip(String srcPath,String zipPath, boolean storeMode) {    
		//返回的布尔值
		boolean flag = true;
        //创建源文件
        File src = new File(srcPath);
        //检查路径
        if (srcPath == null || !src.exists()) {
            System.out.println("\n源文件不存在\n");
            return false;
        }
        
        //创建目标zip文件
        File zip = new File(zipPath);
        //创建目标父目录
        File parent = new File(zip.getParent());
        //若不存在就创建
        if(!parent.exists()) parent.mkdirs();       
        
        //创建zip输出流
		ZipOutputStream out = null;
        try {
			out = new ZipOutputStream(
				new FileOutputStream(zip));
		} catch (FileNotFoundException e) {
			return false;
		}
		//按照用户需求设置是否为储存模式
		if (storeMode) out.setMethod(ZipOutputStream.STORED);
		
        //记录开始时间
        long startTime = System.currentTimeMillis();
        
        //提示信息
        if(basicDetails) System.out.println("\n正在压缩 " + src.getName()
            + " 这个" + (src.isFile() ? "文件\n" : "文件夹\n"));
            
        //开始zip创建循环
        flag = zipLoop(out,src,src.getName(), storeMode);       
        
        //关闭zip输出流
        try {
			out.close();
		} catch (IOException e) {
			return false;
		}

  		if(basicDetails) {
  			long endTime = System.currentTimeMillis();
			if (showOperatingDetails) System.out.println();
  			System.out.println("耗时" + (double)((endTime - startTime) / 1000) + "秒，已完成\n");
  		}	
		
		//返回
		return flag;
    }
	public static boolean zip(File src, String zipPath, boolean storeMode) {
		return zip(src.getAbsolutePath(), zipPath, storeMode);
	}
    
    //压缩文件创建到本目录
    public static boolean zip(String srcPath, boolean storeMode) {
        //声明原路径
        File src = new File(srcPath);
        //zip生成路径为源路径
        String zipPath = src.getParent()
            + "/" + src.getName() + ".zip";
        //zip开始创建
        return zip(srcPath, zipPath, storeMode);
    }
	public static boolean zip(File src, boolean storeMode) {
		return zip(src.getAbsolutePath(), storeMode);
	}

    //zip创建循环
    private static boolean zipLoop(ZipOutputStream out, File f, String root, boolean storeMode) {
        //若f是一个文件夹
        if (f.isDirectory()) {
            //展开f
            File[] fs = f.listFiles();
            //如果f为空
            if (fs.length == 0) {
                //创建(放入)此文件夹
				try {
					ZipEntry entry = new ZipEntry(root + "/");
					if (storeMode) {
						entry.setCrc(0);
						entry.setSize(0);
					}
					out.putNextEntry(entry);
				} catch (IOException e) {
					e.printStackTrace();
				}
            } else {
                //获取f内所有文件/文件夹
                for (File src : fs) {
                    //即将被复制文件的完整路径(root为根目录)
                    String filePath = root + "/" + src.getName();
                    //循环创建
                    zipLoop(out, src, filePath, storeMode);
                }
            }
        } else {
			//提示信息
            if(showOperatingDetails) System.out.println("正在压缩: " + f.getAbsolutePath());
            //放入上文提到的完整路径
            try {
				ZipEntry entry = new ZipEntry(root);
				if (storeMode) {
					CRC32 crc = new CRC32();
					FileInputStream in = new FileInputStream(f);
					byte[] b = new byte[8192];
					int i = 0;
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
				return false;
			}
			//输出文件到zip流
            out(f, out, false);
        }
		return true;
    }
    
    //解压文件(zip)
    public static boolean unzip(String zipPath, String desPath) {
        //创建源文件
        File src = new File(zipPath);
        //判断是否存在
        if (!src.exists()) {
            System.out.println("\nzip文件不存在!\n");
            return false;
        }
        
        //在目标路径最后加上斜杠
        if(!desPath.endsWith("/")) desPath += "/";
        
        //创建zip文件
        ZipFile zip = null;
		try {
			zip = new ZipFile(src);
		} catch (IOException e) {
			return false;
		}

        //创建entries数组
        Enumeration<? extends ZipEntry> enumer = zip.entries();
        //声明一个entry
        ZipEntry entry;
        //线程池
        pool = Executors.newFixedThreadPool(MAX_OPERATING_AMOUNT);
        
        //记录开始时间
        long startTime = System.currentTimeMillis();
        
        //提示信息
        if(basicDetails) System.out.println("\n正在解压 " + src.getName() + " 这个压缩文件\n");
        
        //遍历数组
        while (enumer.hasMoreElements()) {
            //创建enrty
            entry = enumer.nextElement();
            
            //创建临时目标路径
            String temp_des_path = desPath + entry.getName();
            //创建临时目标文件
            File des = new File(temp_des_path);
            
            //如果entry指向一个文件夹
            if (entry.isDirectory()) {
                //若不存在就创建文件夹
                if(!des.exists()) des.mkdirs();
            } else {
				if (!des.getParentFile().exists()) des.getParentFile().mkdirs();
				//多线程
                pool.execute(new ZipOutputThread(zip,des,entry));
            }
        }
        pool.shutdown();
        
        //统计时间
        while (!pool.isTerminated()) {
  			try {
  				Thread.sleep(100);
  			} catch (InterruptedException e) {
  				return false;
  			}
  		}	
        
        //关闭流
		try {
			zip.close();
		} catch (IOException e) {
			return false;
		}
		
  		if(basicDetails) {
  			long endTime = System.currentTimeMillis();
			if (showOperatingDetails) System.out.println();
  			System.out.println("耗时" + (double)((endTime - startTime) / 1000) + "秒，已完成\n");
  		}
       
		return true;
    }
	public static boolean unzip(File zip, String desPath) {
		return unzip(zip.getAbsolutePath(), desPath);
	}
    
    //解压文件到本目录
    public static boolean unzip(String zipPath) {
        //声明原zip路径
        File zip = new File(zipPath);
        //zip父目录为解压目录
        String desPath = zip.getParent();
        //zip开始创建
        return unzip(zipPath,desPath);
    }
	public static boolean unzip(File zip) {
		return unzip(zip.getAbsolutePath());
	}
	
	//读取到byte数组
	public static byte[] readToBytes(File src) {
		//若文件不存在
		if (!src.exists()) {
			System.out.println("文件不存在！");
			return null;
		}
		
		try {
			return Streams.read(new FileInputStream(src));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static byte[] readToBytes(String path) {
		//创建源文件
		File src = new File(path);

		//返回结果
		return readToBytes(src);
	}
	
	//读取
	public static String read(File src, String srcCharsetName) {
		//开始读取
		String result = new String(
			readToBytes(src), Charset.forName(srcCharsetName)
		);

		//返回结果
		return result;
	}
	public static String read(String path, String srcCharsetName) {
		//创建源文件
		File src = new File(path);
		
		//返回结果
		return read(src, srcCharsetName);
	}
	
	//默认读取
	public static String read(File src) {
		return read(src, Charset.defaultCharset().name());
	}
	public static String read(String path) {
		//创建源文件
		File src = new File(path);
				
		//返回结果
		return read(src);
	}
	
	//流与文件
	public static void out(File f, OutputStream out, boolean closeOut) {
		Objs.throwsIfNullOrEmpty(f, out);
		try {
			Streams.out(new FileInputStream(f), out, closeOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void out(String filePath, OutputStream out, boolean closeOut) {
		out(new File(filePath), out, closeOut);
	}

	public static void out(InputStream in, File f, boolean appendMode) {
		Objs.throwsIfNullOrEmpty(in, f, appendMode);
		if (!f.getParentFile().exists()) f.getParentFile().mkdirs();

		try {
			Streams.out(in, new FileOutputStream(f, appendMode), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void out(InputStream in, String filePath, boolean appendMode) {
		out(in, new File(filePath), appendMode);
	}
	
	//输出byte数组
	public static boolean out(byte[] b, File des, boolean appendMode) {
		if (b == null) {
			System.out.println("数组为空！");
			return false;
		}
		
		//创建目标文件父目录
		File parent = new File(des.getParent());

		//若父目录不存在就创建
		if(!parent.exists()) parent.mkdirs();

		//输出
		try {
			FileOutputStream out = new FileOutputStream(des, appendMode);
			//写入
			out.write(b);
			out.flush();
			//关闭流
			out.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	public static boolean out(byte[] b, String desPath, boolean appendMode) {
		//创建目标文件
		File des = new File(desPath);

		//开始输出
		return out(b, des, appendMode);
	}
	
	//输出字串符
	public static boolean out(String src, File des, String desCharsetName, boolean appendMode) {
		//若字符串为空
		if (src == null | "".equals(src)) {
			System.out.println("字符串为空！");
			return false;
		}
		
		//开始输出
		return out(src.getBytes(Charset.forName(desCharsetName)), des, appendMode);
	}
	public static boolean out(String src, String desPath, String desCharsetName, boolean appendMode) {
		//创建目标文件
		File des = new File(desPath);
		
		//开始输出
		return out(src, des, desCharsetName, appendMode);
	}
	
	//默认输出
	public static boolean out(String src, File des, boolean appendMode) {
		return out(src, des, Charset.defaultCharset().name(), appendMode);
	}
	public static boolean out(String src, String desPath, boolean appendMode) {
		//创建目标文件
		File des = new File(desPath);
		
		//开始输出
		return out(src, des, appendMode);
	}
    
	//设置文件编码
	public static void setEncoding(File src, String oldCharsetName, String newCharsetName) {
		byte[] bs = new String(
			readToBytes(src), Charset.forName(oldCharsetName)
		).getBytes(Charset.forName(newCharsetName));
		out(bs, src, false);
	}
	public static void setEncoding(String path, String oldCharsetName, String newCharsetName) {
		File src = new File(path);
		setEncoding(src, oldCharsetName, newCharsetName);
	}
	
	//大文件复制通道
	private static class LargeCopyThread implements Runnable {
		//要复制的文件
		private File src;
		//目标路径
		private File des;
		//两个通道
		private FileChannel in;
		private FileChannel out;
		//追加模式
		private static boolean appendMode = false;

		//构造方法
		private LargeCopyThread(File src, File des) {
			this.src = src;
			this.des = des;
			//提示信息
			if(Files.showOperatingDetails) System.out.println("正在复制大文件: " + src.getAbsolutePath());       
		}

		private LargeCopyThread() {}

		//复制文件
		@SuppressWarnings("resource")
		public void run() {
			try {
				in = new FileInputStream(src).getChannel();
				out = new FileOutputStream(des,appendMode).getChannel();
				
				int position = 0;
				long size = in.size();
				//保证完整复制
				while (size > 0) {
					long count = in.transferTo(position,size,out);
					if (count > 0) {
						position += count;
						size -= count;
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {           
				try {
					in.force(true);
					out.close();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//小文件复制通道
	private static class SmallCopyThread implements Runnable {   
		//要复制的文件
		private File src;
		//目标路径
		private File des;
		//两个文件流
		private FileInputStream in;
		private FileOutputStream out;
		//临时数组
		private byte[] b = new byte[8192];
		//追加模式
		private static boolean appendMode = false;

		//构造方法
		public SmallCopyThread(File src, File des) {
			this.src = src;
			this.des = des;
			//提示信息
			if(Files.showOperatingDetails) System.out.println("正在复制文件: " + src.getAbsolutePath());
		}

		//复制文件
		public void run() {
			try {
				in = new FileInputStream(src);
				out = new FileOutputStream(des,appendMode);
				int i = 0;
				while ((i = in.read(b)) != -1) {
					out.write(b,0,i);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.flush();
					out.close();
					in.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} 
	}
	
	//Zip输出通道
	private static class ZipOutputThread implements Runnable {
		//zip文件
		private ZipFile zip;
		//临时目标文件
		private File des;
		//entry
		private ZipEntry entry;
		//两个缓冲文件流
		private InputStream in;
		private FileOutputStream out;

		public ZipOutputThread(ZipFile zip, File des, ZipEntry entry) {
			this.zip = zip;
			this.des = des;
			this.entry = entry;
			//提示信息
			if(Files.showOperatingDetails) System.out.println("正在解压: " + entry.getName());
		}

		public void run() {
			//创建父目录
			File parent = new File(des.getParent());
			//若不存在就创建
			if(!parent.exists()) parent.mkdirs();  

			try {
				//初始化文件缓冲流
				in = zip.getInputStream(entry);
				out = new FileOutputStream(des);
				byte[] b = new byte[8192];
				int i = 0;
				while ((i = in.read(b)) != -1) {
					out.write(b,0,i);
				}
				out.flush();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null && out != null) {
						out.close();
						in.close();
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

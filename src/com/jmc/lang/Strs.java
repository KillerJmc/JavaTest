/*
 * 作者: Jmc
 *
 * 时间: 2020.3.11   加入重复方法
 *		2020.3.24   添加高级重复（以吊状图精简代码为灵感开发了此功能）
 *		2020.3.26   添加or系列方法
 *		2020.4.1    添加orEquals系列方法和substrings系列方法
 *		2020.4.10   添加removeAll方法
 *		2020.4.12   1.添加getLines和getLine方法
 *					2.removeAll方法改为StringBuilder编辑提高效率
 *      2020.5.1    开创stream模式
 *      2020.6.20   解决stream模式方法名称歧义
 *      2020.8.6    添加encrypt和decrypt方法
 *      2020.8.30   添加linesToStr方法
 *      2020.9.5    添加collectAll方法
 *
 * 功能: String类扩展
 *
 */
 
package com.jmc.lang;

import java.util.*;

public class Strs 
{
	private static StringBuilder sb = new StringBuilder();
	private static final Strs instance = new Strs();
    
    private Strs() {
        
    }
    
    public static Strs stream(String src) {
        sb.append(src);
        return instance;
    }
    
    public String get() {
        String result = sb.toString();
        sb = new StringBuilder();
        return result;
    }
    public void print() {
        System.out.print(get());
    }
    public void println() {
        System.out.println(get());
	}
	
    public static String append(String src, String str, long times) {
        Objs.throwsIfNullOrEmpty(src, str, times);
        StringBuilder sb = new StringBuilder(src);

        while (times > 0) {
            sb.append(str);
            times--;
        }

        return sb.toString();
    }
    
    public Strs _append(String str, long times) {
        Objs.throwsIfNullOrEmpty(str, times);
        while (times > 0) {
            sb.append(str);
            times--;
        }

        return instance;
    }
    
    public Strs _append(String str) {
        return _append(str, 1);
    }

    public Strs _repeat(long times) {
        sb.append(repeat(sb.toString(), times - 1));
        return instance;
	}
    
    public static String repeat(String src, long times) {
        Objs.throwsIfNullOrEmpty(src, times);
        StringBuilder sb = new StringBuilder();

        while (times > 0) {
            sb.append(src);
            times--;
        }

        return sb.toString();
	}
    
    public Strs _orReplace(String newChar, String... oldChars) {
        sb = new StringBuilder(orReplace(sb.toString(), newChar, oldChars));
        return instance;
    }
	
    public static String orReplace(String src, String newChar, String... oldChars) {
        Objs.throwsIfNullOrEmpty(src, newChar);

        for (String oldChar : oldChars) src = src.replace(oldChar, newChar);

        return src;
    }
    
    public Strs _removeAll(String startsWith, String endsWith) {
        sb = new StringBuilder(removeAll(sb.toString(), startsWith, endsWith));
        return instance;
    }

    public static String removeAll(String src, String startsWith, String endsWith) {
        StringBuilder sb = new StringBuilder(src);
        while (true) {
            int startIdx = sb.indexOf(startsWith);
            int endIdx = sb.indexOf(endsWith, startIdx);
            if (startIdx != -1 && endIdx != -1) {
                sb.delete(startIdx, endIdx + endsWith.length());
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public static List<String> collectAll(String src, String startsWith, String endsWith) {
        List<String> rList = new ArrayList<>();
        for (String t : collectAllContains(src, startsWith, endsWith)) {
            rList.add(t.replace(startsWith, "").replace(endsWith, ""));
        }
        return rList;
    }

    public static List<String> collectAllContains(String src, String startsWith, String endsWith) {
        StringBuilder sb = new StringBuilder(src);
        List<String> rList = new ArrayList<>();
        while (true) {
            int startIdx = sb.indexOf(startsWith);
            int endIdx = sb.indexOf(endsWith, startIdx);
            if (startIdx != -1 && endIdx != -1) {
                rList.add(sb.substring(startIdx, endIdx + endsWith.length()));
                sb.delete(startIdx, endIdx + endsWith.length());
            } else {
                break;
            }
        }
        return rList;
    }


    public Strs _substrings(String beginStr, String endStr) {
        sb = new StringBuilder(substrings(sb.toString(), beginStr, endStr));
        return instance;
    }

    public static String substrings(String src, String beginStr, String endStr) {
        int beginIdx = src.indexOf(beginStr);
        int endIdx = src.indexOf(endStr, beginIdx);

        return src.substring(beginIdx + beginStr.length(), endIdx);
    }
    
    public Strs _substringsContains(String beginStr, String endStr) {
        sb = new StringBuilder(substringsContains(sb.toString(), beginStr, endStr));
        return instance;
    }

    public static String substringsContains(String src, String beginStr, String endStr) {
        return beginStr + substrings(src, beginStr, endStr) + endStr;
	}
    
    public boolean _OrEquals(String... contains) {
        return orEquals(sb.toString(), contains);
    }
    
	public static boolean orEquals(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);
		
		for (String s : contains) if (src.equals(s)) return true;
		
		return false;
	}
    
    public boolean _orEqualsIgnoreCase(String... contains) {
        return orEqualsIgnoreCase(sb.toString(), contains);
	}
	
	public static boolean orEqualsIgnoreCase(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);

		for (String s : contains) if (src.equalsIgnoreCase(s)) return true;

		return false;
	}
	
    public boolean _orContains(String... contains) {
        return orContains(sb.toString(), contains);
    }
    
	public static boolean orContains(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);
		
		for (String s : contains) if (src.contains(s)) return true;
		
		return false;
	}
    
    public boolean _orStartsWith(String... contains) {
        return orStartsWith(sb.toString(), contains);
    }
	
	public static boolean orStartsWith(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);

		for (String s : contains) if (src.startsWith(s)) return true;

		return false;
	}
	
    public boolean _orEndsWith(String... contains) {
        return orEndsWith(sb.toString(), contains);
	}
    
	public static boolean orEndsWith(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);

		for (String s : contains) if (src.endsWith(s)) return true;
	
		return false;
	}
	
	public static List<String> getLines(String src, String... contains) {
		List<String> l = new ArrayList<>();
		String[] split = src.split("\n");
		for (String s : split) for (String cs : contains) if (s.contains(cs)) l.add(s);
		return l;
	}
	
	public static List<String> getLines(String src) {
		return getLines(src, "");
	}
    
    public static String linesToStr(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String t : list) {
            sb.append(t).append("\n");
        }
        return sb.toString();
    }
    
	public static String getLine(String src, int line) {
		return getLines(src).get(line - 1);
	}
    
    public static String xor(String src, int key) {
        byte[] bs = src.getBytes();
        for (int i = 0; i < bs.length; i++) bs[i] ^= key;
        return new String(bs);
    }
}

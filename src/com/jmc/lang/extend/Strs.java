package com.jmc.lang.extend;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 作者: Jmc
 *
 * <p> 时间:
 * <pre>
 *      2020.3.11   加入重复方法
 *      2020.3.24   添加高级重复（以吊状图精简代码为灵感开发了此功能）
 *      2020.3.26   添加or系列方法
 *      2020.4.1    添加orEquals系列方法和substrings系列方法
 *      2020.4.10   添加removeAll方法
 *      2020.4.12   1.添加getLines和getLine方法
 *                  2.removeAll方法改为StringBuilder编辑提高效率
 *      2020.5.1    开创stream模式
 *      2020.6.20   解决stream模式方法名称歧义
 *      2020.8.6    添加encrypt和decrypt方法（之后改名为xor：2021.1.30补充）
 *      2020.8.30   添加linesToStr方法
 *      2020.9.5    添加collectAll方法
 *      2020.11.18  添加format方法
 *      2021.1.22   修改substrings方法，并改名为sub
 *      2021.1.30   移除stream模式，删除getLine系列，linesToStr，format方法
 * </pre>
 * <p> 功能: String类扩展
 *
 */
public class Strs
{
    public static String sub(String src, String startsInclusive) {
        return src.substring(src.indexOf(startsInclusive));
    }

    public static String sub(String src, String startsInclusive, String endsInclusive) {
        int beginIdx = src.indexOf(startsInclusive);
        int endIdx = src.indexOf(endsInclusive, beginIdx);

        return src.substring(beginIdx, endIdx + endsInclusive.length());
    }

    public static String xor(String src, byte key) {
        byte[] bs = src.getBytes();
        Bytes.xor(bs, key);
        return new String(bs);
    }


    /**
     * 交换字符串中的两个子串
     * @param src 字符串
     * @param subA 子串a
     * @param subB 子串b
     * @return 结果字符串
     */
    public static String swap(String src, String subA, String subB) {
        return subA.equals(subB) ? src : src.replace(subA, "" + Character.MAX_VALUE).replace(subB, subA).replace("" + Character.MAX_VALUE, subB);
    }

    public static List<String> collectAll(String src, String startsInclusive, String endsInclusive) {
        StringBuilder sb = new StringBuilder(src);
        List<String> rList = new ArrayList<>();
        while (true) {
            int startIdx = sb.indexOf(startsInclusive);
            int endIdx = sb.indexOf(endsInclusive, startIdx);
            if (startIdx != -1 && endIdx != -1) {
                rList.add(sb.substring(startIdx, endIdx + endsInclusive.length()));
                sb.delete(startIdx, endIdx + endsInclusive.length());
            } else {
                break;
            }
        }
        return rList;
    }

    public static String removeAll(String src, String startsInclusive, String endsInclusive) {
        StringBuilder sb = new StringBuilder(src);
        while (true) {
            int startIdx = sb.indexOf(startsInclusive);
            int endIdx = sb.indexOf(endsInclusive, startIdx);
            if (startIdx != -1 && endIdx != -1) {
                sb.delete(startIdx, endIdx + endsInclusive.length());
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public static String orReplace(String src, String newChar, String... oldChars) {
        Objs.throwsIfNullOrEmpty(src, newChar);

        for (String oldChar : oldChars) src = src.replace(oldChar, newChar);

        return src;
    }

	public static boolean orEquals(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);
		
		for (String s : contains) if (src.equals(s)) return true;
		
		return false;
	}
	
	public static boolean orContains(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);
		
		for (String s : contains) if (src.contains(s)) return true;
		
		return false;
	}
    
	public static boolean orStartsWith(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);

		for (String s : contains) if (src.startsWith(s)) return true;

		return false;
	}
	
	public static boolean orEndsWith(String src, String... contains) {
		Objs.throwsIfNullOrEmpty(src);

		for (String s : contains) if (src.endsWith(s)) return true;
	
		return false;
	}
}

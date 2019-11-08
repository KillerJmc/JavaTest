/**
 * 爬虫测试
 */
package com.test.Regex;

import com.jmc.io.Streams;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSpiderTest {
    public static void main(String[] args) throws IOException {
        String content = getURLContent("http://www.163.com/", "GBK");

        /*<a href="http://sitemap.163.com/">网站地图</a>
        取到整个超链接的内容：<a[\\s\\S]+?</a>*/
        var map = getMatchedMap("href=\"(http[\\w\\s\\-\\./:]+?)\">([\\S]+?)</a>", content);

        //还可以遍历map.keySet()来继续循环获取链接，如果该网站没防护，即可将所有链接都爬下来
        map.forEach((K, V) -> System.out.println(K + "：" + V));
    }

    /**
     * 获得网页源码内容
     */
    public static String getURLContent(String url, String charsetName) throws IOException {
        return new String(Streams.read(new URL(url).openStream()), Charset.forName(charsetName));
    }

    /**
     * 获取匹配的内容
     */
    public static Map<String, String> getMatchedMap(String regex, String content) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        Map<String, String> map = new LinkedHashMap<>();

        while (m.find()) {
            //内容-网址
            map.put(m.group(2), m.group(1));
        }
        return map;
    }
}

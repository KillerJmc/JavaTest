package com.test.apply.spring.ioc.util;

import com.jmc.lang.Strs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Xml读取工具类
 * @author Jmc
 */
public class XmlReader {
    /**
     * xml内容
     */
    private final String xmlContent;

    public XmlReader(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    /**
     * 获取指定标签下所有属性内容
     * @param labelName 标签名
     * @param argNames 所有参数名
     * @return 指定标签下所有属性内容（属性名 -> 内容）
     */
    public List<Map<String, String>> getProperties(String labelName, String... argNames) {
        var resList = new ArrayList<Map<String, String>>();

        int currIdx = 0;
        while ((currIdx = xmlContent.indexOf("<" + labelName +  " ", currIdx)) != -1) {
            // 跳过’<label ‘代表长度
            currIdx += labelName.length() + 2;

            // 下一个标签的下标
            var nextLabelIdx = xmlContent.indexOf("<" + labelName +  " ", currIdx);

            var map = new HashMap<String, String>();

            for (var argName : argNames) {
                // 如果这个参数在下一个标签之后出现，就不记录（说明现在的标签没有这个参数）
                if (nextLabelIdx != -1 && xmlContent.indexOf(argName + "=\"", currIdx) > nextLabelIdx) {
                    continue;
                }

                // argName="value"中提取value
                var value = Strs.subExclusive(xmlContent, argName + "=\"", "\"", currIdx);
                map.put(argName, value);
            }
            resList.add(map);
        }

        return resList;
    }
}

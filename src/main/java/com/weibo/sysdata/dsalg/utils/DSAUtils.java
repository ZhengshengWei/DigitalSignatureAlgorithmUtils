package com.weibo.sysdata.dsalg.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhengsheng on 2017/1/4.
 */
public class DSAUtils {

    /**
     * 连接字符参数，并判断是否需要排序
     * @param parameters  字符参数
     * @param isSort  是否排序
     * @return  拼接结果
     */
    public static String concatenate(List<String> parameters, boolean isSort) {
        String result = "";
        if(isSort) {
            Arrays.sort(parameters.toArray());
        }
        for (String paramter : parameters) {
            result += paramter;
        }
        return result;
    }
}

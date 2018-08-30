package com.qimiaochong.common.util;

public class TopicUtil {

    /**
     * 文章点赞数格式转换
     * 0~999       240=>240
     * 1000~9999   2435=>2.4K
     * >9999       43241=>43K
     * @param likeCount 点赞数
     * @return
     */
    public static String likeCountFormat(Integer likeCount){
        if (likeCount<1000){
            return likeCount.toString();
        }
        double count=(double)likeCount/1000;
        if (count<10){
            String value= String.format("%.1f",count)+"K";
            char decimal=value.charAt(value.indexOf(".") + 1);
            if (decimal=='0'){
                return String.format("%.0f",count)+"K";
            }
            return value;
        }
        return String.format("%.0f",count)+"K";
    }


    public static void main(String[] args){
        String s=likeCountFormat(1960);
        System.out.println(s);
    }

}

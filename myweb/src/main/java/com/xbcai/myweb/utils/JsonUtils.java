package com.xbcai.myweb.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xbcai.myweb.vo.StudentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author Administrator
 * @classname JsonUtils
 * @description 封装Jackson的工具类
 * @date 2019/5/14 21:20
 */
@Slf4j
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        //忽略在json字符串中存在，但在java对象中不存在对应属性的情况，防止出错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    /**
     * 将对象转为json字符串输出
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T obj){
        if(obj==null){
            return null;
        }
        try {
            return obj instanceof String ?(String) obj:objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("对象转json字符串解析错误",e);
            return null;
        }
    }

    /**
     * 将对象转为json，并格式化的输出
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2StringPretty(T obj){
        if(obj==null){
            return null;
        }
        try {
            return obj instanceof String ?(String) obj:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("对象转json字符串解析错误",e);
            return null;
        }
    }

    /**
     * 将json字符串转为java对象（适用单个对象使用，返回值就是单个对象）
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str)||clazz==null){
            return null;
        }
        try {
            return clazz.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("json字符串转对象解析错误",e);
            return null;
        }
    }

    /**
     * 将json字符串转为java对象 （这里适用容器存储多个对象的时候使用，返回值是容器对象例如：List<Object></>）
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str)||typeReference==null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)?str:objectMapper.readValue(str,typeReference));
        } catch (IOException e) {
            log.warn("json字符串转对象解析错误",e);
            return null;
        }
    }

    public static void main(String[] args) {
        StudentVO vo1 = new StudentVO("张三","25","http://baidu.com");
        StudentVO vo2 = new StudentVO("李四","29","http://126.com");
        String str = JsonUtils.obj2String(vo1);
        //{"name":"张三","age":"25","image":"http://baidu.com"}
        System.out.println(str);
        System.out.println("+++++++++++++++++++++++++++++++++++");
        /**
         * {
         *   "name" : "张三",
         *   "age" : "25",
         *   "image" : "http://baidu.com"
         * }
         */
        str = JsonUtils.obj2StringPretty(vo1);
        System.out.println(str);
        Map<String,StudentVO> map = new HashMap<>();
        map.put("A",vo1);
        map.put("B",vo2);
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        /**
         * {
         *   "A" : {
         *     "name" : "张三",
         *     "age" : "25",
         *     "image" : "http://baidu.com"
         *   },
         *   "B" : {
         *     "name" : "李四",
         *     "age" : "29",
         *     "image" : "http://126.com"
         *   }
         * }
         */
        System.out.println(JsonUtils.obj2StringPretty(map));
        Map<String, List<Integer>> map2 = new HashMap<>();
        map2.put("A", Arrays.asList(1,2,3,4,5));
        map2.put("B",Arrays.asList(7,8,9,10));
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        /**
         * {
         *   "A" : [ 1, 2, 3, 4, 5 ],
         *   "B" : [ 7, 8, 9, 10 ]
         * }
         */
        System.out.println(JsonUtils.obj2StringPretty(map2));
        String json = "{\"name\":\"张三\",\"age\":\"25\",\"image\":\"http://baidu.com\"}";
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        //StudentVO(name=张三, age=25, image=http://baidu.com)
        System.out.println(JsonUtils.string2Obj(json,StudentVO.class));
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        List<StudentVO> list = new ArrayList<>();
        list.add(vo1);
        list.add(vo2);
        /**
         * [ {
         *   "name" : "张三",
         *   "age" : "25",
         *   "image" : "http://baidu.com"
         * }, {
         *   "name" : "李四",
         *   "age" : "29",
         *   "image" : "http://126.com"
         * } ]
         */
        System.out.println(JsonUtils.obj2StringPretty(list));
        String str2="[{\"name\":\"张三\",\"age\":\"25\",\"image\":\"http://baidu.com\"},{\"name\":\"李四\",\"age\":\"29\",\"image\":\"http://126.com\"}]";
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        //[{name=张三, age=25, image=http://baidu.com}, {name=李四, age=29, image=http://126.com}]
        System.out.println(JsonUtils.string2Obj(str2,List.class));
        List<StudentVO> list1 = JsonUtils.string2Obj(str2,new TypeReference<List<StudentVO>>(){});
        for (StudentVO vo:list1) {
            System.out.println(vo.getName());
        }

    }
}

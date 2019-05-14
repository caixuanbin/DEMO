package com.xbcai.myweb.vo;

import lombok.Data;

/**
 * @author Administrator
 * @classname StudentVO
 * @description TODO
 * @date 2019/5/14 21:38
 */
@Data
public class StudentVO {
    private String name;
    private String age;
    private String image;
    public StudentVO() {
    }

    public StudentVO(String name, String age, String image) {
        this.name = name;
        this.age = age;
        this.image = image;
    }
}

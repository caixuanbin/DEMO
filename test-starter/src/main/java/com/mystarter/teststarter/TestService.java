package com.mystarter.teststarter;

/**
 * @author Administrator
 * @classname TestService
 * @description TODO
 * @date 2019/2/18 14:01
 */
public class TestService {
    private String ip;
    private int port;

    public TestService(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    public void printConfInfo(){
        System.out.println("IP:"+ip+",PORT:"+port);
    }
}

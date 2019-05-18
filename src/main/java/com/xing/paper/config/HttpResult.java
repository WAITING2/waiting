package com.xing.paper.config;

public class HttpResult {



    private int code;



    private String body;

    public  HttpResult(int code,String body){

        this.body = body;
        this.code = code;

    }

    public HttpResult(){

    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
 

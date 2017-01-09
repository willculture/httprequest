package com.wutongwei.httprequest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String body = HttpRequest.get("http://www.baidu.com").body();
        System.out.println(body);
    }
}

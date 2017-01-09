package com.wutongwei.httprequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class HttpRequest {

  static HttpClient client;
  HttpClientBuilder build;
  static HttpResponse response;
  static HttpRequest request;
  static HttpPost post;
  static HttpGet get;
  MultipartEntityBuilder entity$build;
  UrlEncodedFormEntity entity$form;
  List<NameValuePair> parameters;

  private HttpRequest() {
    build = HttpClientBuilder.create();
    client = build.build();
    entity$build = MultipartEntityBuilder.create();

    parameters = new ArrayList<NameValuePair>();
    try {
      entity$form = new UrlEncodedFormEntity(parameters);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * get请求
   * 
   * @param url
   * @return
   */
  public static HttpRequest get(String url) {
    request = new HttpRequest();
    get = new HttpGet(url);
    try {
      response = client.execute(get);
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return request;
  }


  /**
   * 创建post请求
   * 
   * @param url
   * @return
   */
  public static HttpRequest post(String url) {
    request = new HttpRequest();
    post = new HttpPost(url);
    return request;
  }

  /**
   * 添加表单数据
   * 
   * @param key
   * @param value
   * @return
   */
  public HttpRequest form(String key, String value) {
    parameters.add(new BasicNameValuePair(key, value));
    return request;
  }

  /**
   * 发送表单数据请求
   * 
   * @return
   */
  public HttpRequest create() {
    post.setEntity(entity$form);
    try {
      response = client.execute(post);
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return request;
  }

  /**
   * 在post请求中加入请求数据
   * 
   * @param key
   * @param value
   * @return
   */
  public HttpRequest addPart(String key, Object value) {
    if (value instanceof File) {
      entity$build.addBinaryBody(key, (File) value);
    }
    if (value instanceof String) {
      entity$build.addTextBody(key, value.toString());
    }
    if (value instanceof byte[]) {
      entity$build.addBinaryBody(key, (byte[]) value);
    }

    if (value instanceof InputStream) {
      entity$build.addBinaryBody(key, (InputStream) value);
    }
    return request;
  }

  /**
   * 发送post请求
   * 
   * @return
   */
  public HttpRequest send() {
    post.setEntity(entity$build.build());
    try {
      response = client.execute(post);
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return request;
  }


  /**
   * 返回字符串内容
   * 
   * @return
   */
  public String body(String chartype) {
    try {
      return EntityUtils.toString(response.getEntity(), chartype);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 返回JSON格式数据
   * 
   * @return
   */
  public JSONObject json() {
    String body = body(CharType.UTF8);
    return JSONObject.fromObject(body);
  }

  /**
   * 获取请求状态码
   * 
   * @return
   */
  public int code() {
    return response.getStatusLine().getStatusCode();
  }



  public static class CharType {
    public static final String UTF8 = "UTF-8";
    public static final String GBK = "gbk";
  }



  public String body() {

    return body(CharType.UTF8);
  }


}

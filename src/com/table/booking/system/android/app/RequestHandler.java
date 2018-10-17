package com.table.booking.system.android.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
 
import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestHandler {

  private ObjectMapper objectMapper = new ObjectMapper();
  private static final String AUTH_TOKEN_HEADER = "Authorization";
  
  public String sendLoginRequest(UserCredentials credentials) {
    String token = null;
    HttpURLConnection connection = null;
    try {
      URL url = new URL(URLs.URL_LOGIN);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      String credsAsString = objectMapper.writeValueAsString(credentials);
      if (null != credsAsString) {
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-length", credsAsString.getBytes().length + "");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(credsAsString.getBytes("UTF-8"));
        outputStream.close();
      }
      connection.connect();
      int status = connection.getResponseCode();
      if (HttpURLConnection.HTTP_OK == status) {
        token = connection.getHeaderField(AUTH_TOKEN_HEADER);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch(SocketTimeoutException e) {
      //Handles URL access timeout.
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      connection.disconnect();
    }
    return token;
  }

  public User getUserData(String token) {
    User user = new User();
    return user;
  }
  
  //this method will send a post request to the specified url 
  //in this app we are using only post request 
  //in the hashmap we have the data to be sent to the server in keyvalue pairs
  public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
      URL url;

      StringBuilder sb = new StringBuilder();
      try {
          url = new URL(requestURL);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setReadTimeout(15000);
          conn.setConnectTimeout(15000);
          conn.setRequestMethod("POST");
          conn.setDoInput(true);
          conn.setDoOutput(true);

          OutputStream os = conn.getOutputStream();

          BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
          writer.write(getPostDataString(postDataParams));

          writer.flush();
          writer.close();
          os.close();
          int responseCode = conn.getResponseCode();

          if (responseCode == HttpsURLConnection.HTTP_OK) {

              BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
              sb = new StringBuilder();
              String response;

              while ((response = br.readLine()) != null) {
                  sb.append(response);
              }
          }

      } catch (Exception e) {
          e.printStackTrace();
      }
      return sb.toString();
  }


  //this method is converting keyvalue pairs data into a query string as needed to send to the server 
  private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
      StringBuilder result = new StringBuilder();
      boolean first = true;
      for (Map.Entry<String, String> entry : params.entrySet()) {
          if (first)
              first = false;
          else
              result.append("&");

          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          result.append("=");
          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
      }

      return result.toString();
  }

}

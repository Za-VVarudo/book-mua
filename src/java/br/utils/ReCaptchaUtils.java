/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import br.com.google.constants.ReCaptchaConstants;
import org.json.JSONObject;

/**
 *
 * @author Death
 */
public class ReCaptchaUtils {
    public static synchronized boolean isCaptchaValid(String reCaptchaCode) throws IOException {
        boolean valid = false;
        OutputStream out = null;
        InputStream in = null;
        BufferedReader br = null;
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify";
            String parameters = "secret=" + ReCaptchaConstants.RECAPTCHA_SECRET_KEY + "&response=" + reCaptchaCode;
            URL httpURL = new URL(url);
            HttpURLConnection http = (HttpURLConnection) httpURL.openConnection();
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            out = http.getOutputStream();
            out.write(parameters.getBytes("UTF-8"));
            out.flush();

            in = http.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));            
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = br.read()) != -1) {
                sb.append((char) c);
            }
            //System.out.println(sb.toString());
            JSONObject json = new JSONObject(sb.toString());
            valid = json.getBoolean("success");
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out!=null) out.close();
            if (br!=null) br.close();
            if (in!=null) in.close();
        }
        return valid;
    }
}

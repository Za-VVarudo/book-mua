/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utils;

import java.io.IOException;
import br.com.google.constants.LoginGoogleConstants;
import br.dats.User;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;

/**
 *
 * @author Death
 */
public class LoginWithGoogleUtils {

    public static String getToken(final String code) throws IOException {
        String accessToken = null;
        try {
            String response = Request.Post(LoginGoogleConstants.GET_TOKEN_LINK)
                    .bodyForm(Form.form().add("client_id", LoginGoogleConstants.CLIENT_ID)
                            .add("client_secret", LoginGoogleConstants.CLIENT_SECRET)
                            .add("redirect_uri", LoginGoogleConstants.REDIRECT_URI).add("code", code)
                            .add("grant_type", LoginGoogleConstants.GRANT_TYPE).build())
                    .execute().returnContent().asString();
            JSONObject json = new JSONObject(response);
            accessToken = json.get("access_token").toString().replaceAll("\"", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static User getLoginGoogle(final String accessToken) {
        User user =null;
        try {
            String link = LoginGoogleConstants.GET_USER_INFO_LINK + accessToken;
            String response = Request.Get(link).execute().returnContent().asString();
            JSONObject json = new JSONObject(response);
            String email = json.getString("email");
            String id = json.getString("id");
            user = new User();
            user.setEmail(email);
            user.setUserID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}

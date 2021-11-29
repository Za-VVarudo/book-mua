/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.google.constants;

/**
 *
 * @author Death
 */
public class LoginGoogleConstants {

    public static final String CLIENT_ID = "402260881799-uehfudcls2ojdmh07513sg37p3a3316d.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "rnQ42_4f30RK3ucTebUhBpK4";
    public static final String REDIRECT_URI = "http://localhost:8080/BookMua/MainController?action=LoginWithGoogle";
    public static final String GET_TOKEN_LINK = "https://accounts.google.com/o/oauth2/token";
    public static final String GET_USER_INFO_LINK = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String LOGIN_GOOGLE_LINK = "https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/BookMua/MainController?action=LoginWithGoogle&response_type=code"
    +"&client_id=402260881799-uehfudcls2ojdmh07513sg37p3a3316d.apps.googleusercontent.com&approval_prompt=force";
}

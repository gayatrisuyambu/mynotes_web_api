package com.myprojects.interceptor;

import com.myprojects.customexceptions.UserUnAuthorizedException;
import com.myprojects.service.IUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransactionInterceptor implements HandlerInterceptor {

    @Autowired
    private IUser iUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Your business logic goes here
        if (request.getCookies() != null && request.getCookies().length > 0) {
            boolean isCookieAvail = false;
            JSONObject userDetails = null;
            Cookie requestCookie[] = request.getCookies();
            for (int i = 0; i < requestCookie.length; i++) {
                if (requestCookie[i].getName().equals("mynotes_ui")) {
                    isCookieAvail = true;
                    userDetails = new JSONObject(requestCookie[i].getValue());
                    break;
                }
            }

            if (isCookieAvail) {
                if (userDetails != null) {
                    int userId=iUser.validatingUser(userDetails.getString("userName"),userDetails.getString("emailId"),userDetails.getString("token"));
                    if(userId!=0){
                        request.setAttribute("userid", userId);
                        return true;
                    }else {
                        return false;
                    }
                }else{
                    throw new UserUnAuthorizedException();
                }
            } else {
                throw new UserUnAuthorizedException();
            }
        }else{
            return false;
        }
        // return true or false depending on whether you want the controller to handle the request or terminate request processing.
    }
}

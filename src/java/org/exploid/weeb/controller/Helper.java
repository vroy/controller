package org.exploid.weeb.controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author vince
 */
public class Helper {

    public HttpServletRequest request;
    public HttpServletResponse response;
    public HttpSession session;
    public HashMap<String, String> params;

    public Helper() {
    }

    public Helper(HttpServletRequest req, HttpServletResponse res, HttpSession ses, HashMap<String, String> params) {
        this.request = req;
        this.response = res;
        this.session = ses;
        this.params = params;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}

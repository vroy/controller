package org.exploid.weeb.controller;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author vince
 * 
 * Wrapper class to be extented by users who wants to use controllers.
 * 
 * This will be used to make the request, response and parameters available to the
 * user without him having to do anything.
 * 
 * This will also provide convenient methods to be used in his controller.
 */
public class WeebHelper extends Helper {

    public WeebHelper(HttpServletRequest req, HttpServletResponse res, HttpSession ses, HashMap<String, String> params) {
        super(req, res, ses, params);
    }

    public WeebHelper() {
    }

    /**
     * Convenient method to select a jsp view for the action.
     * 
     * @param path path to the jsp.
     */
    public void view(String path) {
        try {
            request.getRequestDispatcher(path).include(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Convenient method to redirect the user inside the scope of the dispatcher.
     * 
     * If the first arguments starts with a /, it will be redirected to this path directly.
     * Else it will build a path with all the arguments and start to build a query string when
     * an argument containing an = sign is found.
     * 
     * @param args one string or multiple strings.
     * @return true if the user was redirected and false if there was an error.
     */
    public boolean redirect(String... args) {
        String path = request.getContextPath() + "/d";

        boolean queryString = false;

        if (args[0].startsWith("/")) {
            path += args[0];
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i].contains("=")) {
                    queryString = true;
                    path += "?";
                }

                path += (queryString) ? args[i] + "&" : "/" + args[i];
            }
        }

        try {
            response.sendRedirect(path);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Convenient method to redirect the user outside the scope of the dispatcher.
     * 
     * @param path to the page where you want to redirect the user.
     * @return true if the user was redirected and false if there was an error.
     */
    public boolean redirectOut(String path) {
        try {
            response.sendRedirect(path);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Convenient method to get a parameter in the controller.
     * 
     * @param key key of the parameter you want to have.
     * @return a String with the value of the parameter.
     */
    public String param(String key) {
        return params.get(key);
    }

    /**
     * Convenient method to set an attribute on the request, those attributes are accessible
     * in jsp views.
     * 
     * @param key Key of the attribute.
     * @param value Value of the attribute.
     */
    public void attr(String key, String value) {
        request.setAttribute(key, value);
    }
    
    /**
     * Convenient method to get a String from the session.
     * Note: user getAttribute to manipulate objects.
     * 
     * @param key key of the object you need.
     * @return a String associated with the key.
     */
    public String session(String key) {
        return (String) session.getAttribute(key);
    }
    
    /**
     * Convenient method to set an Object in the session.
     * 
     * @param key String to represent the object in the session.
     * @param value Object to be placed in the session.
     */
    public void session(String key, Object value) {
        session.setAttribute(key, value);
    }
    
    
}

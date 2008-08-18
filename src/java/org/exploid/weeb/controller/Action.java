package org.exploid.weeb.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.exploid.weeb.util.StringUtils;

/**
 * @author vince
 */
public class Action {

    private Class<?> klass;
    private Method method;
    private Object instance;

    public Action() {
    }

    public Action(Class<?> klass, Method method) {
        this.klass = klass;
        this.method = method;
    }

    public void call(HttpServletRequest request, HttpServletResponse response, Object... args) throws ActionNotFoundException {
        try {
            instance = klass.newInstance();
            setup(request, response);
            method.invoke(instance, args);
        } catch (InstantiationException ex) {
            throw new ActionNotFoundException();
        } catch (IllegalAccessException ex) {
            throw new ActionNotFoundException();
        } catch (IllegalArgumentException ex) {
            throw new ActionNotFoundException();
        } catch (InvocationTargetException ex) {
            throw new ActionNotFoundException();
        }
    }

    /**
     * Convenient method to check if the action supports the httpmethod given by the request.
     * 
     * @param request Http Request containing the HttpMethod
     * @return true if the Method supports the HttpMethod
     */
    public boolean supports(HttpServletRequest request) {
        String annotationClassName = StringUtils.capitalize(request.getMethod());

        Class<? extends Annotation> methodAnnotation = null;
        try {
            methodAnnotation = (Class<? extends Annotation>) Class.forName("org.exploid.weeb.controller." + annotationClassName);
        } catch (ClassNotFoundException ex) {
            methodAnnotation = null;
        }

        return method.isAnnotationPresent(methodAnnotation);
    }

    /**
     * Convenient method to set up the request, response, session and params in the controller instance.
     * 
     * @param request
     * @param response
     */
    private void setup(HttpServletRequest request, HttpServletResponse response) {
        set("request", request);
        set("response", response);
        set("session", request.getSession());
        set("params", getParameters(request));
    }

    /**
     * Convenient method to set a public field in the instance to a new value while ignoring errors.
     * 
     * @param field String containing the name of the field in the instance object.
     * @param value new value for the field.
     */
    //TODO: Maybe try a setter if the field is null.
    private void set(String field, Object value) {
        try {
            klass.getField(field).set(instance, value);
        } catch (IllegalArgumentException ex) {
        } catch (IllegalAccessException ex) {
        } catch (NoSuchFieldException ex) {
        } catch (SecurityException ex) {
        }

    }

    /**
     * Convenient method to transform the Parameters in an HashMap<String, String>
     * 
     * @param request HttpServletRequest to take the parameters from.
     * @return an HashMap<String, String> representing the parameters from the request.
     */
    private HashMap<String, String> getParameters(HttpServletRequest request) {

        HashMap<String, String> params = new HashMap<String, String>();

        Enumeration parameters = request.getParameterNames();

        while (parameters.hasMoreElements()) {
            String key = (String) parameters.nextElement();
            params.put(key, request.getParameter(key));
        }

        return params;
    }
}

package org.exploid.weeb.controller;

import javax.servlet.ServletException;

/**
 * @author vince
 */
public class ActionNotFoundException extends ServletException {

    public ActionNotFoundException(Throwable cause) {
        super(cause);
    }

    public ActionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionNotFoundException(String message) {
        super(message);
    }

    public ActionNotFoundException() {
    }
}

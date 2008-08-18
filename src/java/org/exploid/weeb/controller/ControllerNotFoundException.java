package org.exploid.weeb.controller;

import javax.servlet.ServletException;

/**
 * @author vince
 */
public class ControllerNotFoundException extends ServletException {

    public ControllerNotFoundException(Throwable cause) {
        super(cause);
    }

    public ControllerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerNotFoundException(String message) {
        super(message);
    }

    public ControllerNotFoundException() {
    }
}

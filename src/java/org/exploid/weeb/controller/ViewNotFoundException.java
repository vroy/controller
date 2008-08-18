package org.exploid.weeb.controller;

import javax.servlet.ServletException;

/**
 * @author vince
 */
class ViewNotFoundException extends ServletException{

    public ViewNotFoundException(Throwable cause) {
        super(cause);
    }

    public ViewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewNotFoundException(String message) {
        super(message);
    }

    public ViewNotFoundException() {
    }


}

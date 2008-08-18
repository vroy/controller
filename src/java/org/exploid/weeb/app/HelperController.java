/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.exploid.weeb.app;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.exploid.weeb.controller.Controller;
import org.exploid.weeb.controller.Get;
import org.exploid.weeb.controller.WeebHelper;

/**
 *
 * @author vince
 */
@Controller("helper")
public class HelperController {

    // Define them if they are not defined in an extended helper.
    public HttpServletRequest request;
    public HttpServletResponse response;
    public HttpSession session;
    public HashMap<String, String> params;
    WeebHelper h = new WeebHelper(request, response, session, params);
    
    //Exception thrown from here will need to extend ServletException or IOException
    @Get
    public void index() {
        h.attr("foo", "bar");
        h.view("/prop.jsp");
    }
    
}

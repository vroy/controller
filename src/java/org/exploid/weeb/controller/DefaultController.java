package org.exploid.weeb.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vince
 */
@Controller
public class DefaultController {
    
    //Define those only if you need them.
    //public HttpServletRequest request;
    //public HttpSession session;
    //public HashMap<String, String> params;
    
    public HttpServletResponse response;

    @Get
    public void index() throws IOException {
        String msg = "Create a mapping for / in web.xml and its value should be a class that extends Controller.";
        response.getWriter().write(msg);
    }
}

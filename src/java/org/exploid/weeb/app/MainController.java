package org.exploid.weeb.app;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.exploid.weeb.controller.Controller;
import org.exploid.weeb.controller.Get;

/**
 * @author vince
 */
@Controller("")
public class MainController {

    public HttpServletResponse response;
    
    @Get
    public void index() throws IOException {
        response.getWriter().write("This is my main controller.");
    }
}

package org.exploid.weeb.app;

import org.exploid.weeb.controller.Controller;
import org.exploid.weeb.controller.Get;
import org.exploid.weeb.controller.WeebHelper;

/**
 * @author vince
 */
@Controller({"extend", "extends"})
public class ExtendController extends WeebHelper {

    
    //Exception thrown from here will need to extend ServletException or IOException
    @Get
    public void index() {
        //throw new UserDefinedException("foo");
        attr("foo", "bar");
        view("/prop.jsp");
    }

}

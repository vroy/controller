package org.exploid.weeb.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.exploid.weeb.util.FileUtils.*;

/**
 * @author vince
 * 
 * Note: It does not take <code>ClassController</code> as an argument to make
 * cleaner code and it should always be a class representing <code>Controller</code>
 * because this is made in <code>init()</code>.
 */
public class Dispatcher extends HttpServlet {

    private HashMap<String, Action> actions;

    //TODO: Ajouter la securiter avec annotation @Role({"roles","goes","here"}) either au helper ou dispatcher
    //TODO: Log exceptions instead of only printStackTrace();

    //TODO: document init, doGet, doPost, doPut, doDelete and dispatch
    @Override
    public void init(ServletConfig config) {
        String path = config.getServletContext().getRealPath("/WEB-INF/classes");
        initActions(path);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> args = parseRequest(request);

        String controllerName = args.get(0);

        String methodName = (args.size() > 1) ? args.get(1) : "index";

        Object[] params = (args.size() > 2) ? args.subList(2, args.size()).toArray() : null;

        // Try to find action on the default controller first with urls like /index, /about
        // Init defines the controller like so: controllerName + "/" + methodName so if the
        // controller url is "" it will make urls like "/action"
        Action action = actions.get("/" + controllerName);

        // if the action was not found on the default controller, try to find one with the controller specified.
        if (action == null) {
            action = actions.get(controllerName + "/" + methodName);
        }

        if (action == null) {
            throw new ControllerNotFoundException();
        }

        try {
            if (action.supports(request)) {
                action.call(request, response, params);
            } else {
                throw new ActionNotFoundException("Http Method Not Supported.");
            }
        } catch (ActionNotFoundException ex) {
            throw ex;
        }
    }

    private String[] getControllerNames(Class<?> controllerClass) {
        Controller controller = controllerClass.getAnnotation(Controller.class);

        String controllerNames[] = new String[controller.value().length];

        if ("__DEFAULT__".equals(controller.value()[0])) {
            controllerNames[0] = getDefaultControllerUrl(controllerClass);
        } else {
            for (int i = 0; i < controller.value().length; i++) {
                controllerNames[i] = controller.value()[i];
            }
        }
        return controllerNames;
    }

    private Action getDefaultAction() {
        try {
            return new Action(DefaultController.class, DefaultController.class.getMethod("index"));
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (SecurityException ex) {
            return null;
        }
    }

    private String getDefaultControllerUrl(
            Class<?> klass) {
        return klass.getSimpleName().replaceAll("Controller", "").toLowerCase();
    }

    private void initActions(String path) {
        actions = new HashMap<String, Action>();

        // Setup the index action of DefaultController as the fallback if the user doesn't define a default controller.
        actions.put("/index", getDefaultAction());

        List<String> klasses = getClassNames(new ArrayList<String>(), new File(path), "");

        for (String name : klasses) {
            Class<?> controllerClass = getControllerClass(name);

            // If the class is a controller, add all the methods annotated as Http Methods to the actions HashMap
            if (controllerClass != null && controllerClass.isAnnotationPresent(Controller.class)) {
                for (Method method : controllerClass.getMethods()) {
                    if (isAction(method)) {
                        Action action = new Action(controllerClass, method);

                        // For every controller name specified by the Controller annotation, add a reference to the action in the hashmap.
                        for (String controllerName : getControllerNames(controllerClass)) {
                            actions.put(controllerName + "/" + method.getName(), action);
                        }
                    }
                }
            }
        }
    }

    private boolean isAction(Method method) {
        return (method.isAnnotationPresent(Get.class) || method.isAnnotationPresent(Post.class) || method.isAnnotationPresent(Put.class) || method.isAnnotationPresent(Delete.class));
    }

    /**
     * Convenient method to get the <code>Controller</code> class specified by the
     * arguments located in the url.
     * 
     * @param controller A String which is a key of the controllers hashmap.
     * @return A <code>Controller</code> class
     * @throws javax.servlet.ServletException
     */
    private Class<?> getControllerClass(String controller) {
        try {
            return Class.forName(controller);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    /**
     * Convenient method to parse the request info and put this in an array
     * 
     * @param request Request to get info on for the request parsing.
     * @return A List of Strings where the first string is the controller name,
     * the second string is the method name and third+ is parameters
     */
    private List<String> parseRequest(HttpServletRequest request) {
        String[] args = request.getPathInfo().substring(1).split("/");

        List<String> params = new LinkedList<String>();

        params.add(args[0]); // controller

        params.add((args.length > 1) ? args[1] : "index"); // method

        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                params.add(args[i]);
            }
        }

        return params;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        dispatch(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        dispatch(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response);
    }
}

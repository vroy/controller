Well this is the Controller part of MVC. I got inspired to do this because when I did my internship I was forced to use crappy frameworks like spring and jsf and I didn't like it at all.

== Introduction
You first need to add org.exploid.weeb.controller.Dispatcher as a Servlet in your web.xml.

A controller class needs to be annotated by @Controller and the argument is the url that will be used after the first /, example, UserController could be annotated by @Controller("users", "user") so you can access the page by going to /d/user/ or /d/users/


== Helpers
If you want to create custom helpers I suggest extending org.exploid.weeb.controller.Helper

== Examples

I put up two small examples:
* one that extends the default weeb helper class which is WeebsHelper. This example is at org.exploid.weeb.app.ExtendController.
* one that only has the helper class has an instance variable so you can extend what you want instead of extending the helper class. This example is at org.exploid.weeb.app.HelperController
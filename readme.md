Spring version 4.3.2

# Spring dispatcher

see [dispatcherServlet](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/DispatcherServlet.java). The process of handling a URL request, for example ```/register``` is:

1.
  The ```@RequestMapping``` in [IndexController](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/controller/IndexController.java) intercepts this URL, and returns ```view/register```

2.
  The ```viewResolver``` bean adds ```/static/``` as a prefix and ```.html``` as suffix for the return result ```view/register``` from IndexController.

3.
  The ```addResourceHandlers``` gets the whole result ```/static/view/register.html``` from viewResolver bean, then maps it to location ```/angularjs/app```.

# Spring and Mongodb

use this lib for mongo in java, read [installation guide](http://mongodb.github.io/mongo-java-driver/3.2/driver/getting-started/installation-guide/) carefully, the download link is at the bottom of the page.
> mongo-java-driver-3.2.2.jar

The official says this is for older version, so if later this jar cannot be used, download these three jars from the same website above
> mongo-driver.jar  
  mongo-driver-core.jar  
  bson.jar

Use Maven to install these libs for [spring-data-mongo](http://projects.spring.io/spring-data-mongodb/) module 
> spring-data-commons-1.12.2.RELEASE.jar  
  spring-data-mongodb-1.9.2.RELEASE.jar

configure files
> [mongo-properties](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/mongo.properties)  
  [MongoContext.java](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/MongoContext.java)

The mongoContext.java is imported in the last line in [ApplicationConfig](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/ApplicationConfig.java)

Use a @Document tag on the model class to declare this is a mongodb collection. The default collection name will be the same as the model class but all letters in lower case. In order to change the collection name, use ```@Document(collection = "name")```. See [User](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/model/User.java).

Other operations related with mongodb can be seen in DAO directory.

# Spring RESTFUL API

Put a @RequestBody tag in front of the parameter in the controller, see ```saveUser``` function in [UserController](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/controller/UserController.java). This tag needs to use three [Jackson](http://wiki.fasterxml.com/JacksonHome) libs, download [here](http://repo1.maven.org/maven2/com/fasterxml/jackson/core/)
> jackson-annotations-2.8.0.jar  
  jackson-core-2.8.0.jar  
  jackson-databind-2.8.0.jar  

Besides, this tag requires the parameter in json string format. ATTENTION: not JS object format, must be json string. ```JSON.stringify(JS object)``` can convert a JS object to json string in a JS file. See```$scope.save``` function in [UserController.js](https://github.com/marcusgao94/spring/blob/master/web/angularjs/app/controller/UserController.js)

# Spring security 3.2.5

add [SecurityConfig](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/SecurityConfig.java)

Create [SecurityWebApplicationInitializer](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/SecurityWebApplicationInitializer.java) extends ```AbstractSecurityWebApplicationInitializer``` which will load the ```springSecurityFilterChain``` automatically

load the spring security config file in ```rootContext.register(ApplicationConfig.class, SecurityConfig.class);``` in [MyWebApplicationInitializer](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/MyWebApplicationInitializer.java)

### login and authenticate process

If client visits a url without authenticating, the [MyAuthenticationEntryPoint](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/MyAuthenticationEntryPoint.java) is called. Response contains a 403 error and a message. In [SecurityConfig](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/SecurityConfig.java) this line ```.and().exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint())``` configs the EntryPoint.

To login using a form containing username and password, need to create a class [MyUsernamePasswordAuthenticationFilter](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/MyUsernamePasswordAuthenticationFilter.java). In this class I override the method Authentication in which I parse a json using [Jackson](http://wiki.fasterxml.com/JacksonHome) to get username and password. Add it in this way ```.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)``` in [SecurityConfig](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/SecurityConfig.java). It must be added before the default ```UsernamePasswordAuthenticationFilter```

Then the ```getAuthenticationManager().authenticate(token);``` method use the token containing username and password to authenticate. The authenticationManager is configged in [SecurityConfig](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/SecurityConfig.java) like this:

```
@Autowired
public void configureAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(securityUserDetailService);
}
```

```securityUserDetailService``` is a [SecurityUserDetailService](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/service/SecurityUserDetailService.java) class. This class implements the ```UserDetailsServic``` interface and need to override ```loadUserByUsername(String username)``` method. The username parameter is got from the token. The service checks this username in database and returns a ```org.springframework.security.core.userdetails.User``` object with only username and password. Spring security automatically compares the ```org.springframework.security.core.userdetails.User``` object's password with the token's password. If the password is the same, spring security calls [MyAuthenticationSuccessHandler](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/MyAuthenticationSuccessHandler.java). If is not, it calls the default ```SimpleUrlAuthenticationFailureHandler```

The method ```public void configure(HttpSecurity http) throws Exception``` in [SecurityConfig](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/SecurityConfig.java) configs for the normal ```UsernamePasswordAuthenticationFilter```. I need to apply these configuration for [MyUsernamePasswordAuthenticationFilter](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/security/MyUsernamePasswordAuthenticationFilter.java) in this way:

```
@Bean
public MyUsernamePasswordAuthenticationFilter getAuthenticationFilter() throws Exception {
    MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager());
    filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/signin"));
    filter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
    filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
    return filter;
}
```


# AngularJS $resource

need [ngResource](https://docs.angularjs.org/api/ngResource) module 

$resource is a non-synchronized method, so use ```$promise.then``` to wait until it finished processing. See ```$scope.save``` function in [UserController.js](https://github.com/marcusgao94/spring/blob/master/web/angularjs/app/controller/UserController.js)

# AngularJS ngRoute

need [ngRoute](https://docs.angularjs.org/api/ngRoute) module

see [app.js](https://github.com/marcusgao94/spring/blob/master/web/angularjs/app/app.js) for config example

There must be only one ```ng-view``` in the whole app, generally it is in [index.html](https://github.com/marcusgao94/spring/blob/master/web/angularjs/app/index.html)

The ```$routeProvider``` maps a URL to a correspond templateUrl, which would send to the server. After analyzing this templateUrl by controller and the [dispatcherServlet](https://github.com/marcusgao94/spring/blob/master/src/com/myapp/config/DispatcherServlet.java) mechanism, the server renders an html page to the client. Finally this html will be shown in the position of ng-view in [index.html](https://github.com/marcusgao94/spring/blob/master/web/angularjs/app/index.html)


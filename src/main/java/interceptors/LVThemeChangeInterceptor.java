package interceptors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LVThemeChangeInterceptor extends HandlerInterceptorAdapter {

    /**
     * Default name of the theme specification parameter: "group".
     */
    public static final String DEFAULT_PARAM_NAME = "group";

	private static final String DEFAULT_THEME = "lv";

    private String paramName = DEFAULT_PARAM_NAME;


    /**
     * Set the name of the parameter that contains a theme specification
     * in a theme change request. Default is "theme".
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * Return the name of the parameter that contains a theme specification
     * in a theme change request.
     */
    public String getParamName() {
        return this.paramName;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException {

        ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(request);
        if (themeResolver == null) {
            throw new IllegalStateException("No ThemeResolver found: not in a DispatcherServlet request?");
        }
        
        String theme = request.getRequestURI();

        if (theme.contains("/css") || theme.contains("/js") || theme.contains("/images")) {
        	return true;
        }
        
        System.out.println("Look here:" + theme);
        
        
        if (theme.contains("/nw/")) {
        	theme = "nw";
        }
        else {
            theme = DEFAULT_THEME;
        }
        
        String userAgent = request.getHeader("User-Agent"); 
        String newChannel = "";
        
        if (theme != null && userAgent != null) {
            
            if (userAgent.contains("Firefox")){
                newChannel = "browser";
            } else if (userAgent.contains("MSIE")){
                newChannel = "ie";
            }
            
            String strToSet = theme.concat(newChannel);
            
            themeResolver.setThemeName(request, response, strToSet);
            
            System.out.println("Theme Resolver: " + themeResolver);
            System.out.println("Theme : " + strToSet);
        }
        // Proceed in any case.
        return true;
    }

    
}

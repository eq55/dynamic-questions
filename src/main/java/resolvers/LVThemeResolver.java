package resolvers;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;


public class LVThemeResolver {
    
    private ResourceBundleMessageSource themes;
    
    public LVThemeResolver(ResourceBundleMessageSource themes){
        this.setThemes(themes);
    }
   
    public void setThemes(ResourceBundleMessageSource themes) {
        this.themes = themes;
    }

    public String resolve(String name, HttpServletRequest request) {
        ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(request);
        Locale locale = new Locale(themeResolver.resolveThemeName(request));
        return themes.getMessage(name, null, locale);
    }
}

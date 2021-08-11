package com.demxs.tdm.common.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: wuliepeng
 * Date: 2017-11-01
 * Time: 上午11:26
 */
@Controller
public class LanguageController {
    @Autowired
    CookieLocaleResolver resolver;


    /**
     * 语言切换
     */
    @RequestMapping("language/{lang}")
    public ModelAndView language(HttpServletRequest request, HttpServletResponse response, @PathVariable("lang") String lang){

        lang=lang.toLowerCase();
        if(lang==null||lang.equals("")){
            return new ModelAndView("redirect:/a");
        }else{
            if("zh".equals(lang)){
                resolver.setLocale(request, response, Locale.CHINA);
            }else if("en".equals(lang)){
                resolver.setLocale(request, response, Locale.US);
            }else{
                resolver.setLocale(request, response, Locale.CHINA);
            }
        }
        return new ModelAndView("redirect:/a");
    }
}

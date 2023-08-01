package grouped.vehiclesdetails.controller;

import grouped.vehiclesdetails.config.CustomProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static grouped.vehiclesdetails.service.ServiceConstants.NONE;
import static grouped.vehiclesdetails.service.ServiceConstants.TOKEN;

@Controller
public class RedirectionController {
    private final CustomProperties properties;

    public RedirectionController(CustomProperties customProperties){
        this.properties = customProperties;
    }

    @GetMapping("/")
    public ModelAndView redirectToHomePage(){
        return new ModelAndView(new RedirectView(properties.getVehiclesFront()));
    }

    /**
     * Redirect the user to the login page
     * @return Redirect the user to the login page
     */
    @GetMapping("/login")
    public ModelAndView redirectToLogin(){
        return new ModelAndView(new RedirectView(properties.getLogin()));
    }

    /**
     * Redirect the user to the register page
     * @return Redirect the user to the register page
     */
    @GetMapping("/signin")
    public ModelAndView redirectToRegister(){
        return new ModelAndView(new RedirectView(properties.getRegister()));
    }

    /**
     * Log the current user out
     * @param response HTTP Response
     * @return redirect user to the root page
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response){
        Cookie cookie = new Cookie(TOKEN, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ModelAndView(new RedirectView(properties.getVehiclesFront()));
    }

    /**
     * @return Redirect the user to his account page
     */
    @GetMapping("/account")
    public ModelAndView redirectToAccount(){
        return new ModelAndView(new RedirectView(properties.getAccount()));
    }

    /**
     * @return Redirect to the cart page
     */
    @GetMapping("/cart/{id}")
    public ModelAndView redirectToCart(@PathVariable("id") int id){
        return new ModelAndView(new RedirectView(properties.getCartFront()+"/"+id));
    }
}

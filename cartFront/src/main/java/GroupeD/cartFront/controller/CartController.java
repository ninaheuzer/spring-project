package GroupeD.cartFront.controller;


import GroupeD.cartFront.config.CustomProperties;
import GroupeD.cartFront.model.Cart;
import GroupeD.cartFront.model.CartDTO;
import GroupeD.cartFront.model.User;
import GroupeD.cartFront.model.Vehicle;
import GroupeD.cartFront.service.GatewayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CartController {
    private final GatewayService gatewayService;
    private final static String TOKEN = "Authorization";
    public CartController(GatewayService gatewayService){
        this.gatewayService = gatewayService;
    }



    @GetMapping("/{id}")
    public ModelAndView cart(@CookieValue(value=TOKEN, defaultValue = "none") String token, Model model, @PathVariable("id") int id){
        if (token != null && !token.equals("none")){
            int idToken = gatewayService.verifyToken(token);
            if (idToken != -1){
                if (id == idToken){
                    List<Cart> cart = gatewayService.findByIdUser(token, id);
                    List<CartDTO> cartDTO = new ArrayList<CartDTO>();
                    if(cart.isEmpty()){
                        model.addAttribute("cart", cartDTO);
                        return new ModelAndView("cart");
                    }
                    User user = gatewayService.findUserById(cart.get(0).getIdUser());
                    double totalPrice = 0;

                    for(Cart c: cart){
                        Vehicle vehicle = gatewayService.findVehicleById(c.getIdProduct());
                        CartDTO cDTO = new CartDTO();
                        cDTO.setProductPrice(vehicle.getPrice());
                        cDTO.setProductName(vehicle.getName());
                        cDTO.setAddress(user.getAddress());
                        cDTO.setId(c.getId());
                        cDTO.setIdProduct(c.getIdProduct());
                        cDTO.setIdUser(c.getIdUser());
                        cDTO.setQuantity(c.getQuantity());
                        cartDTO.add(cDTO);
                        totalPrice += cDTO.getProductPrice()*cDTO.getQuantity();
                    }
                    model.addAttribute("cart", cartDTO);
                    Cart updateCart = new Cart();
                    model.addAttribute("updateCart", updateCart);
                    model.addAttribute("totalPrice", totalPrice);
                    return new ModelAndView("cart");
                }
            }
        }
        return new ModelAndView(new RedirectView("http://localhost:4200/"));
    }

    @GetMapping("/validationOrder/{id}")
    public ModelAndView validationOrder(@CookieValue(value=TOKEN, defaultValue = "none") String token, Model model, @PathVariable("id") int id){
        if (token != null && !token.equals("none")) {
            int idToken = gatewayService.verifyToken(token);
            if (idToken != -1) {
                User user = gatewayService.findUserById(id);
                model.addAttribute("address", user.getAddress());
                gatewayService.deleteAllCart(token, user.getId());
                return new ModelAndView("validationOrder");
            }
        }
        return new ModelAndView(new RedirectView("http://localhost:4200/"));
    }

    @PostMapping("/{id}")
    public ModelAndView createCart(@CookieValue(value=TOKEN, defaultValue = "none") String token, @ModelAttribute Cart cart, @PathVariable("id") int id){
        if (token != null && !token.equals("none")){
            int idToken = gatewayService.verifyToken(token);
            if (idToken != -1){
                if (id == idToken) {
                    if (!gatewayService.checkQuantityCart(cart)) {
                        return new ModelAndView("redirect:/", "cart", cart);
                    }
                    gatewayService.saveCart(token, cart);
                    return new ModelAndView("redirect:/");
                }
            }
        }
        return new ModelAndView(new RedirectView("http://localhost:4200/"));
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteCart(@PathVariable("id") int id, @CookieValue(value = TOKEN, defaultValue = "none") String token){
        if (token != null && !token.equals("none")){
            int idToken = gatewayService.verifyToken(token);
            if (idToken !=-1){
                gatewayService.deleteCart(token, id);
                return new ModelAndView("redirect:/"+idToken);
            }
        }
        return new ModelAndView(new RedirectView("http://localhost:4200"));
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateCart(@PathVariable("id") int id, @ModelAttribute Cart cart, @CookieValue(value = TOKEN, defaultValue = "none") String token){
        if (token != null && !token.equals("none")){

            int idToken = gatewayService.verifyToken(token);
            if (idToken != -1){
                gatewayService.updateCart(token, id, cart);
                return new ModelAndView("redirect:/"+idToken);
            }
        }
        return new ModelAndView(new RedirectView("http://localhost:4200"));
    }


}

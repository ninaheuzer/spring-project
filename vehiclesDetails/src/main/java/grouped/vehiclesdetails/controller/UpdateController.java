package grouped.vehiclesdetails.controller;

import grouped.vehiclesdetails.model.Cart;
import grouped.vehiclesdetails.model.Comment;
import grouped.vehiclesdetails.model.User;
import grouped.vehiclesdetails.model.Vehicle;
import grouped.vehiclesdetails.service.GatewayService;
import grouped.vehiclesdetails.service.ServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static grouped.vehiclesdetails.service.ServiceConstants.*;

@Controller
public class UpdateController {
    private GatewayService gatewayService;

    public UpdateController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }


    /******** Vehicle **********/
    @GetMapping("/vehicle/{id}/edit")
    public String getVehicleEditPage(@PathVariable("id") int id, @CookieValue(value = TOKEN, defaultValue = NONE) String token, Model model) {
        return gatewayService.handleGetVehicleEditPage(id, token, model);
    }

    @PostMapping("/vehicle/{id}/edit")
    public String updateVehicle(@PathVariable("id") int id, @ModelAttribute Vehicle vehicle, @CookieValue(value = TOKEN, defaultValue = NONE) String token) {
        return gatewayService.handleEditVehicle(id, vehicle, token);
    }

    /******** Comment **********/
    @GetMapping("/comment/{id}/edit")
    public String getCommentEditPage(@PathVariable("id") int id, @CookieValue(value = TOKEN, defaultValue = NONE) String token, Model model) {
        return gatewayService.handleGetCommentEditPage(id, token, model);
    }

    @PostMapping("/comment/{id}/edit")
    public String updateComment(@PathVariable("id") int commentId, @ModelAttribute Comment comment, @CookieValue(value = TOKEN, defaultValue = NONE) String token) {
        System.out.println(commentId);
        return gatewayService.handleEditComment(commentId, comment, token);
    }

    @PostMapping("/cart")
    public String updateCart(@ModelAttribute Cart cart, @CookieValue(value = TOKEN, defaultValue = NONE) String token){
        return gatewayService.handleUpdateCart(cart, token);
    }
}

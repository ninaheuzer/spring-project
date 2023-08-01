package grouped.vehiclesdetails.controller;

import grouped.vehiclesdetails.model.Comment;
import grouped.vehiclesdetails.model.User;
import grouped.vehiclesdetails.service.GatewayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static grouped.vehiclesdetails.service.ServiceConstants.NONE;
import static grouped.vehiclesdetails.service.ServiceConstants.TOKEN;

@Controller
public class CreateController {
    private GatewayService gatewayService;

    public CreateController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping("/comment")
    public String createComment(@ModelAttribute Comment comment, @CookieValue(value = TOKEN, defaultValue = NONE) String token){
        if(!gatewayService.canPostComment(token)) return "redirect:/vehicle/"+ comment.getVehicleId() + "/details#comments";
        gatewayService.handleCreateComment(comment, token);
        return "redirect:/vehicle/" + comment.getVehicleId() + "/details#comments";
    }
}

package grouped.vehiclesdetails.controller;

import grouped.vehiclesdetails.service.GatewayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static grouped.vehiclesdetails.service.ServiceConstants.NONE;
import static grouped.vehiclesdetails.service.ServiceConstants.TOKEN;

@Controller
public class DetailsController {
    private GatewayService gatewayService;

    public DetailsController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    /**
     * @param id vehicleId
     * @param model webpage model
     * @param token JWT token
     * @return a detailed page for a specific vehicle
     */
    @GetMapping("/vehicle/{id}/details")
    public String detailPage(@PathVariable("id") int id, Model model, @CookieValue(value = TOKEN, defaultValue = NONE) String token){
        gatewayService.loadDetailsPage(id, model, token);
        return "detailPage";
    }
}

package grouped.vehiclesdetails.controller;

import grouped.vehiclesdetails.model.Comment;
import grouped.vehiclesdetails.model.User;
import grouped.vehiclesdetails.service.GatewayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static grouped.vehiclesdetails.service.ServiceConstants.*;

@Controller
public class DeleteController {

    private GatewayService gatewayService;

    public DeleteController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    /***
     * Delete a specific vehicle
     * Must be Admin
     * @param id vehicleId
     * @param token JWT token
     * @return redirect to the vehicle list page
     */
    @GetMapping("/vehicle/{id}/delete")
    public ModelAndView deleteVehicle(@PathVariable("id") int id, @CookieValue(value = TOKEN, defaultValue = NONE) String token){
        gatewayService.handleDeleteVehicle(id, token);
        return new ModelAndView(ROOT_REDIRECT);
    }

    /**
     * Delete a specific comment
     * Must be Admin or the author of the comment
     * @param id commentId
     * @param token JWT token
     * @return redirect to vehicles list page or to the detailed page of the removed comment
     */
    @GetMapping("/comment/{id}/delete")
    public ModelAndView deleteComment(@PathVariable("id") int id, @CookieValue(value = TOKEN, defaultValue = NONE) String token){
        return gatewayService.handleDeleteComment(id, token);
    }
}

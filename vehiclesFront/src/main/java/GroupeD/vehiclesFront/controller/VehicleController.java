package GroupeD.vehiclesFront.controller;

import GroupeD.vehiclesFront.model.User;
import GroupeD.vehiclesFront.model.Vehicle;
import GroupeD.vehiclesFront.model.VehicleDTO;
import GroupeD.vehiclesFront.service.GatewayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import static GroupeD.vehiclesFront.service.ServiceConstants.NONE;
import static GroupeD.vehiclesFront.service.ServiceConstants.TOKEN;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VehicleController {

    private GatewayService service;

    public VehicleController(GatewayService service) {this.service = service; }


    @GetMapping("/")
    public String vehicles(@RequestParam(required = false) String category,
                           @RequestParam(required = false) Double minPrice,
                           @RequestParam(required = false) Double maxPrice,
                           Model model, @ModelAttribute VehicleDTO vehicleDTO,
                           @CookieValue(value = TOKEN, defaultValue = NONE) String token) {
        User user = service.getUserFromToken(token);
        if (user != null) {
            service.addConnectionInModel(model, Integer.toString(user.getId()));
            if (service.isUserAdmin(token)){
                service.addAdminConnectionInModel(model, Integer.toString(user.getId()));
            }
        }
        model.addAttribute("vehicles", service.vehiclesSearch(vehicleDTO));
        model.addAttribute("vehicle", new Vehicle( "", "", "", 0, ""));
        return "listVehicles";
    }

    @PostMapping("/")
    public ModelAndView createVehicle(@ModelAttribute Vehicle v, @CookieValue(value = TOKEN, defaultValue = NONE) String token) {
        if (token.equals(NONE) || !service.isUserAdmin(token)) {
            return new ModelAndView("redirect:/");
        }
        else {
            service.createVehicle(v, token);
        }
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/{id}")
    public ModelAndView deleteVehicle(@PathVariable("id") int id, @CookieValue(value = TOKEN, defaultValue = NONE) String token) {
        if (token.equals(NONE) || !service.isUserAdmin(token)) {
            return new ModelAndView("redirect:/");
        }
        else {
            service.deleteVehicle(id, token);
        }
        return new ModelAndView("redirect:/");
    }
}

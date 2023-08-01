package grouped.vehiclesdetails.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import grouped.vehiclesdetails.config.CustomProperties;
import com.auth0.jwt.algorithms.Algorithm;
import grouped.vehiclesdetails.model.*;
import grouped.vehiclesdetails.proxy.GatewayProxy;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static grouped.vehiclesdetails.service.ServiceConstants.*;

@Service
public class GatewayService {
    private final CustomProperties properties;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier verifier;
    private final GatewayProxy proxy;

    public GatewayService(GatewayProxy gatewayProxy, CustomProperties customProperties) {
        this.proxy = gatewayProxy;
        this.properties = customProperties;
        this.jwtAlgorithm = Algorithm.HMAC256(properties.getJWTSecret());
        this.verifier = JWT.require(jwtAlgorithm).withIssuer("auth0").build();
    }

    /******** Vehicles **********/
    public Vehicle findVehicleById(int id) {
        return proxy.findVehicleById(id);
    }

    public double findAverageForVehicle(int id) {
        return proxy.findAverageForVehicle(id);
    }

    public Vehicle deleteVehicle(int id, String token) {
        return proxy.deleteVehicle(id, token);
    }

    public String handleGetVehicleEditPage(int id, String token, Model model) {
        if (token.equals(NONE) || !isUserAdmin(token)) return "redirect:/vehicle/" + id + "/details";

        try {
            model.addAttribute("current_user", getUserFromToken(token));
            model.addAttribute("vehicle", findVehicleById(id));
        } catch (Exception e) {
            return "redirect:/vehicle/" + id + "/details";
        }
        return "updateVehiclePage";
    }

    public String handleEditVehicle(int id, Vehicle vehicle, String token) {
        if (token.equals(NONE) || !isUserAdmin(token)) return "redirect:/vehicle/" + id + "/details";

        try {
            updateVehicle(id, vehicle, token);
        } catch (Exception e) {
            return "redirect:/vehicle/" + id + "/details";
        }
        return "redirect:/vehicle/" + id + "/details";
    }

    public void updateVehicle(int id, Vehicle vehicle, String token) {
        if (vehicle.getId() != id) throw new IllegalArgumentException("id and vehicle id don't match");
        proxy.updateVehicle(id, vehicle, token);
    }

    public void handleDeleteVehicle(int id, String token) {
        if(isUserAdmin(token)){
            try{
                deleteVehicle(id, token);
            } catch(Exception e){
            }
        }
    }

    /******** Users **********/
    public User findUserById(int id) {
        return proxy.findUserById(id);
    }

    public User getUserFromToken(String token) {
        if (token == null || token.equals(NONE)) return null;
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            User user = findUserById(decodedJWT.getClaim(USER).asInt());
            user.setAdmin(user.getUserType() == User.USER_TYPES.ADMIN);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isUserAdmin(String token) {
        if (token == null || token.equals(NONE)) return false;
        User user = getUserFromToken(token);
        if (user == null) return false;
        return user.getUserType() == User.USER_TYPES.ADMIN;
    }

    /******** Comments **********/
    public Iterable<Comment> findAllCommentsByVehicle(int id) {
        return proxy.findAllCommentsByVehicle(id);
    }

    public Comment findCommentById(int id) {
        return proxy.findCommentById(id);
    }

    public Comment deleteComment(int id, Comment comment, String token) {
        return proxy.deleteComment(id, comment, token);
    }

    public String handleGetCommentEditPage(int id, String token, Model model) {
        if (canEditComment(id, token)) {
            Comment comment;
            User user;
            try {
                comment = findCommentById(id);
                user = getUserFromToken(token);
                model.addAttribute("comment", comment);
                model.addAttribute("current_user", user);
            } catch (Exception e) {
                return ROOT_REDIRECT;
            }
            return "updateCommentPage";
        }
        return ROOT_REDIRECT;
    }

    public String handleEditComment(int commentId, Comment comment, String token) {
        if(!canEditComment(commentId, token)) return "redirect:/vehicle/" + comment.getVehicleId() + "/details#comments";
        try{
            updateComment(commentId, comment, token);
        } catch(Exception e){
            return "redirect:/vehicle/" + comment.getVehicleId() + "/details#comments";
        }
        return "redirect:/vehicle/" + comment.getVehicleId() + "/details#comments";
    }

    public boolean canEditComment(int commentId, String token) {
        try {
            Comment comment = findCommentById(commentId);
            if (comment == null) return false;
            User user = getUserFromToken(token);
            if (user == null) return false;
            return comment.getUserId() == user.getId();
        } catch (Exception e) {
            return false;
        }
    }

    public void updateComment(int id, Comment comment, String token) {
        try {
            proxy.updateComment(id, comment, token);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public void handleCreateComment(Comment comment, String token) {
        User currentUser = getUserFromToken(token);
        comment.setUserId(currentUser.getId());
        createComment(comment, token);
    }

    public void createComment(Comment comment, String token){
        try{
            comment.setCreationDate(LocalDate.now());
            proxy.createComment(comment, token);
        } catch(Exception e){
        }
    }

    public boolean canPostComment(String token) {
        if (token == null && token.equals(NONE)) return false;
        User currentUser = getUserFromToken(token);
        return currentUser != null;
    }

    public ModelAndView handleDeleteComment(int id, String token) {
        if(token.equals(NONE)) return new ModelAndView(ROOT_REDIRECT);
        User user;
        try{
            user = getUserFromToken(token);
        } catch(Exception e){
            return new ModelAndView(ROOT_REDIRECT);
        }
        Comment deletedComment = findCommentById(id);

        int idVehicle = -1;
        if(user.isAdmin() || user.getId() == deletedComment.getUserId()) {
            idVehicle = deleteCommentById(id, token);
            if(idVehicle == -1){
                return new ModelAndView(ROOT_REDIRECT);
            }
            return new ModelAndView("redirect:/vehicle/"+idVehicle+"/details#comments");
        }
        return new ModelAndView(ROOT_REDIRECT);
    }

    public int deleteCommentById(int id, String token) {
        int idVehicle;
        Comment comment;
        try {
            comment = findCommentById(id);
            comment.setState("supprimé");
            idVehicle = comment.getVehicleId();
        } catch (Exception e) {
            return -1;
        }
        try {
            deleteComment(id, comment, token);
        } catch (Exception e) {
        }
        return idVehicle;
    }

    /******** Cart **********/
    public String handleUpdateCart(Cart cart, String token) {
        if(token == null || token.equals(NONE)) return "redirect:/vehicle/" + cart.getIdProduct() + "/details";
        updateCart(token, cart);
        int idUser = getUserFromToken(token).getId();
        return "redirect:/cart/"+idUser;
    }

    public void updateCart(String token, Cart cart){
        if(token == null || token.equals(NONE)) return;
        try{
            User currentUser = getUserFromToken(token);
            proxy.createCart(currentUser.getId(), cart, token);//The create method can also update the cart
        } catch(Exception e){
        }
    }

    /******** Other **********/
    public void loadDetailsPage(int id, Model model, String token) {
        Vehicle vehicle;
        ArrayList<CommentDTO> commentDTOS = new ArrayList<>();
        double noteAverage;
        User currentUser = getUserFromToken(token);
        Comment newComment = new Comment();
        Cart cart = new Cart();

        try {
            vehicle = findVehicleById(id);
        } catch (Exception e) {
            vehicle = null;
        }
        try {
            for (Comment comment1 : findAllCommentsByVehicle(id)) {
                User user = findUserById(comment1.getUserId());
                CommentDTO newDTO = new CommentDTO();
                newDTO.setId(comment1.getId());
                newDTO.setPseudo(user.getPseudo());
                newDTO.setUserId(comment1.getUserId());
                newDTO.setCreationDate(comment1.getCreationDate());
                newDTO.setRating(comment1.getRating());
                newDTO.setState(comment1.getState());
                newDTO.setText(comment1.getText());
                commentDTOS.add(newDTO);
            }

            List<CommentDTO> usersComment = commentDTOS.stream()
                    .filter(comment -> comment.getState().equals("valide"))
                    .filter(comment -> comment.getUserId() == currentUser.getId())
                    .sorted(Comparator.comparing(CommentDTO::getCreationDate).reversed())
                    .collect(Collectors.toList());
            List<CommentDTO> commentsWithoutUser = commentDTOS.stream()
                    .filter(comment -> comment.getState().equals("valide"))
                    .filter(comment -> comment.getUserId() != currentUser.getId())
                    .sorted(Comparator.comparing(CommentDTO::getCreationDate).reversed())
                    .collect(Collectors.toList());
            commentDTOS.clear();

            commentDTOS.addAll(usersComment);
            commentDTOS.addAll(commentsWithoutUser);
        } catch (Exception e) {
            commentDTOS = new ArrayList<>();
        }
        noteAverage = findAverageForVehicle(id);
        try {
            ArrayList<Cart> existingCart = (ArrayList<Cart>) proxy.getCart(currentUser.getId(), token);
            Optional<Cart> currentCart = existingCart.stream().filter((c) -> c.getId() == id).findFirst();
            if(currentCart.isPresent()){
                cart.setQuantity(currentCart.get().getQuantity());
            }
        } catch (Exception e){
            System.out.println(e);
        }
        if(currentUser != null){
            newComment.setVehicleId(id);
            newComment.setUserId(currentUser.getId());
            newComment.setState("valide");
            newComment.setRating(5);
            cart.setIdUser(currentUser.getId());
            cart.setIdProduct(id);
            cart.setQuantity(1);
        }

        model.addAttribute("cart",cart);
        model.addAttribute("new_comment", newComment);
        model.addAttribute("current_user", currentUser);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("note_average", noteAverage);
    }
}

package grouped.usersfront.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import grouped.usersfront.config.CustomProperties;
import grouped.usersfront.model.User;
import grouped.usersfront.model.UserDTO;
import grouped.usersfront.proxy.UsersFrontProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static grouped.usersfront.service.ServiceConstants.*;


@Service
public class UsersFrontService {

    private final UsersFrontProxy proxy;
    private final CustomProperties properties;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier verifier;

    public UsersFrontService(UsersFrontProxy proxy, CustomProperties props) {
        this.proxy = proxy;
        this.properties = props;
        this.jwtAlgorithm = Algorithm.HMAC256(properties.getJWTSecret());
        this.verifier = JWT.require(jwtAlgorithm).withIssuer(properties.getJWTIssuer()).build();
    }

    public List<User> findAll(String token) {
        return proxy.findAll(token);
    }

    public User findById(int id) {
        return proxy.findById(id);
    }

    public User findByEmail(String email) {
        return proxy.findByEmail(email);
    }

    public User createUser(User user) {
        return proxy.createUser(user);
    }

    public User updateUser(String token, int id, User user) {
        return proxy.updateUser(token, id, user);
    }

    public User deleteUser(String token, int id) {
        return proxy.deleteUser(token, id);
    }

    /**
     * Decode and extract the given token.
     * If the token is invalid or the user is not found, null is returned.
     *
     * @param token the token
     * @return User the user or null
     */
    public User getUserFromToken(String token) {
        if (token == null || token.equals(NONE)) {
            return null;
        }
        User user;
        try {
            DecodedJWT decodedToken = verifier.verify(token);
            user = proxy.findById(decodedToken.getClaim(USER).asInt());
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    /**
     * Check if a user is valid with its email and password.
     * Returning the user id or -1 if something went wrong.
     *
     * @param user a user containing at least the email and the password
     * @return -1 or the user id
     */
    public int checkUser(User user) {
        User u;
        try {
            u = findByEmail(user.getEmail());
        } catch (Exception e) {
            return -1;
        }
        if (u == null) return -1;
        return checkUserPassword(user.getPassword(), u.getPassword()) ? u.getId() : -1;
    }

    public boolean checkUserPassword(String password1, String password2) {
        return new BCryptPasswordEncoder().matches(password1, password2);
    }

    public void insertCookieAfterLogin(HttpServletResponse response, int userId) {
        Cookie cookie = new Cookie(TOKEN, createToken(userId));
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public String createToken(int userId) {
        Date tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24)); // => 24h
        return JWT.create().withIssuer(properties.getJWTIssuer()).withClaim(USER, userId).withExpiresAt(tomorrow).sign(jwtAlgorithm);
    }

    public ModelAndView errorOnSignin(@ModelAttribute UserDTO user, RedirectAttributes redirectAttributes, String s) {
        setDefaultPasswords(user);
        redirectAttributes.addFlashAttribute(USER, user);
        redirectAttributes.addFlashAttribute(MESSAGE, s);
        return new ModelAndView("redirect:/signin");
    }

    public ModelAndView messageOnUpdateAccount(UserDTO user, RedirectAttributes redirectAttributes, String target, String message) {
        setDefaultPasswords(user);
        redirectAttributes.addFlashAttribute(USER, user);
        redirectAttributes.addFlashAttribute(target, message);
        return new ModelAndView("redirect:/account");
    }

    public void addUserInModel(Model model) {
        if (!model.containsAttribute(USER)) {
            UserDTO user = new UserDTO();
            setDefaultPasswords(user);
            user.setAddress("");
            user.setEmail("");
            user.setFirstName("");
            user.setLastName("");
            user.setPseudo("");
            model.addAttribute(USER, user);
        }
    }

    public void addUserInModel(Model model, User user) {
        if (!model.containsAttribute(USER)) {
            UserDTO userDTO = new UserDTO();
            setDefaultPasswords(userDTO);
            userDTO.setAddress(user.getAddress());
            userDTO.setEmail(user.getEmail());
            userDTO.setPseudo(user.getPseudo());
            model.addAttribute(USER, userDTO);
        }
    }

    public void addConnectionInModel(Model model, String value) {
        if (!model.containsAttribute("connected_id"))
            model.addAttribute("connected_id", value);
    }

    public void setDefaultPasswords(UserDTO user) {
        user.setPassword("");
        user.setPasswordConfirm("");
    }

    public ModelAndView redirectToProductPage() {
        return new ModelAndView(new RedirectView(properties.getVehiclesFront()));
    }
}

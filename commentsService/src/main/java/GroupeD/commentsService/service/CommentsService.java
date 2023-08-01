package GroupeD.commentsService.service;

import GroupeD.commentsService.config.CustomProperties;
import GroupeD.commentsService.model.Comment;
import GroupeD.commentsService.repository.CommentRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentsService {

    private final CommentRepository repo;
    private final CustomProperties properties;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier verifier;


    public CommentsService(CommentRepository repo, CustomProperties props) {
        this.repo = repo;
        this.properties = props;
        this.jwtAlgorithm = Algorithm.HMAC256(properties.getJWTSecret());
        this.verifier = JWT.require(jwtAlgorithm).withIssuer(properties.getJWTIssuer()).build();
    }

    public Iterable<Comment> findByUserId(int id) {
        return repo.findByUserIdOrderByCreationDateAsc(id);
    }

    public Iterable<Comment> findByVehicleId(int id) {
        return repo.findByVehicleIdOrderByCreationDateAsc(id);
    }

    public Iterable<Comment> findByVehicleIdAndUserId(int vehicleId, int userId) {
        return repo.findByVehicleIdAndUserIdOrderByCreationDateAsc(vehicleId, userId);
    }

    public Iterable<Comment> findByVehicleIdExceptUserId(int vehicleId, int userId) {
        return repo.findByVehicleIdAndUserIdNotOrderByCreationDateAsc(vehicleId, userId);
    }

    public Comment saveComment(Comment comment) {
        Comment c = null;
        try {
            c = repo.save(comment);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot add comment", e);
        }
        return c;
    }

    public Comment updateComment(int id, Comment comment, String token) {
        Comment c = findById(id);
        int userId = getUserIdFromToken(token);

        if(userId != c.getUserId())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        c.setText(comment.getText());
        c.setState(comment.getState());

        try {
            c = repo.save(c);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot update comment", e);
        }
        return c;
    }

    public Iterable<Comment> findAll() {
        return repo.findByOrderByCreationDateAsc();
    }

    public Comment findById(int id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found comment"));
    }

    public void delete(int id) {
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot delete comment", e);
        }
    }

    public long countByVehicleId(int id) {
        return repo.countByVehicleId(id);
    }

    public double averageByVehicleId(int id) {
        double note;
        try {
            note = repo.averageByVehicleId(id);
        } catch(Exception e){
            return -1;
        }
        return note;
    }

    public int getUserIdFromToken(String token) {
        if (token == null || token.equals("none")) {
            return -1;
        }
        try {
            DecodedJWT decodedToken = verifier.verify(token);
            return decodedToken.getClaim("user").asInt();
        } catch (Exception e) {
            return -1;
        }
    }
}

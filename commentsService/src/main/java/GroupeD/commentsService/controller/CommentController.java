package GroupeD.commentsService.controller;

import GroupeD.commentsService.model.Comment;
import GroupeD.commentsService.service.CommentsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentsService commentsService;

    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/user/{id}")
    public Iterable<Comment> getCommentsByUser(@PathVariable("id") int id) {
        return commentsService.findByUserId(id);
    }

    @GetMapping("/vehicle/{id}")
    public Iterable<Comment> getCommentsByVehicle(@PathVariable("id") int id) {
        return commentsService.findByVehicleId(id);
    }

    @GetMapping("/vehicle/{vId}/withuser/{uId}")
    public Iterable<Comment> getCommentsByVehicleAndUser(@PathVariable("vId") int vId, @PathVariable("uId") int uId) {
        return commentsService.findByVehicleIdAndUserId(vId, uId);
    }

    @GetMapping("/vehicle/{vId}/exceptuser/{uId}")
    public Iterable<Comment> getCommentsByVehicleExceptUser(@PathVariable("vId") int vId, @PathVariable("uId") int uId) {
        return commentsService.findByVehicleIdExceptUserId(vId, uId);
    }

    @GetMapping("/vehicle/{vId}/count")
    public long countCommentsByVehicleId(@PathVariable("vId") int vId) {
        return commentsService.countByVehicleId(vId);
    }

    @GetMapping("/vehicle/{vId}/average")
    public double averageByVehicleId(@PathVariable("vId") int vId) {
        return commentsService.averageByVehicleId(vId);
    }


    // CRUD //
    @GetMapping("/{id}")
    public Comment commentById(@PathVariable("id") int id) {
        return commentsService.findById(id);
    }

    @PostMapping("/secure")
    public Comment addComment(@RequestHeader(name="Authorization") String token, @RequestBody Comment comment) {
        return commentsService.saveComment(comment);
    }

    @PutMapping("/secure/{id}")
    public Comment editComment(@RequestHeader(name="Authorization") String token, @RequestBody Comment comment, @PathVariable("id") int id) {
        return commentsService.updateComment(id, comment, token);
    }

    @DeleteMapping("/secure/{id}")
    public void deleteById(@RequestHeader(name="Authorization") String token, @PathVariable("id") int id) {
        commentsService.delete(id);
    }

    // Not asked but may be useful later //
    @GetMapping
    public Iterable<Comment> findAll() {
        return commentsService.findAll();
    }


}

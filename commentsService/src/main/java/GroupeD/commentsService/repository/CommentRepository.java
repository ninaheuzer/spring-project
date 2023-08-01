package GroupeD.commentsService.repository;

import GroupeD.commentsService.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    List<Comment> findByOrderByCreationDateAsc();



    List<Comment> findByUserIdOrderByCreationDateAsc(int userId);

    List<Comment> findByVehicleIdOrderByCreationDateAsc(int vehicleId);

    List<Comment> findByVehicleIdAndUserIdOrderByCreationDateAsc(int vehicleId, int userId);

    List<Comment> findByVehicleIdAndUserIdNotOrderByCreationDateAsc(int vehicleId, int userId);

    long countByVehicleId(int vehicleId);

    @Query("select avg(c.rating) from comments c where c.vehicleId = ?1")
    double averageByVehicleId(int vehicleId);
}

package GroupeD.commentsService.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "comments")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    private int rating;

    @CreationTimestamp
    private LocalDate creationDate;

    private int userId;

    private int vehicleId;

    private String state;

    public enum States {
        SUPPRIME("supprimé"), VALIDE("valide");
        String name;

        States(String name) {
            this.name = name;
        }
    }

    public void setRating(int rating) {
        if (rating < 0 || rating > 5) throw new IllegalArgumentException("Bad rating value");
        this.rating = rating;
    }

    public void setState(String state) {
        if (state == null || state.toLowerCase().equals(States.VALIDE.name))
            this.state = States.VALIDE.name;
        else if (state.toLowerCase().equals(States.SUPPRIME.name))
            this.state = States.SUPPRIME.name;
        else
            throw new IllegalArgumentException("Invalid state");
    }
}

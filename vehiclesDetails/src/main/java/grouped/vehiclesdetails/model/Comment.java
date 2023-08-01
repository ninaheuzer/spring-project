package grouped.vehiclesdetails.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Comment {
    private int id;
    private String text;
    private int rating;
    private LocalDate creationDate;
    private int userId;
    private int vehicleId;
    private String state;
}

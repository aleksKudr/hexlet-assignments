package exercise.dto;

import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
public class CommentDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String body;
}
// END

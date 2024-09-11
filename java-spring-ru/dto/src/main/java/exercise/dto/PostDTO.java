package exercise.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Setter
@Getter
public class PostDTO {
    long id;
    String title;
    String body;
    List<CommentDTO> comments;
}
// END

package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.model.Comment;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    @GetMapping
    public  List<PostDTO> index() {
        return postRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDTO show(@PathVariable long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found", id)));
        return toDto(post);
    }



    private PostDTO toDto(Post post) {
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        List<CommentDTO> commentsDTO = comments.stream().map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setBody(comment.getBody());
                    commentDTO.setId(comment.getId());
                    return commentDTO;
                }
        ).toList();
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(post.getBody());
        postDTO.setId(post.getId());
        postDTO.setComments(commentsDTO);
        postDTO.setTitle(post.getTitle());
        return postDTO;
    }
}
// END

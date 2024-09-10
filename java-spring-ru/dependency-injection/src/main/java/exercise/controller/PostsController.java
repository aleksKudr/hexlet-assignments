package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @GetMapping
    public List<Post> index() {
        return postRepository.findAll();
    }
    @GetMapping("/{id}")
    public Post show(@PathVariable long id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found", id)));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create (@RequestBody Post post) {
        return postRepository.save(post);
    }
    @PutMapping("/{id}")
    public Post update(@PathVariable long id, @RequestBody Post data) {
        Post udatedPost =  postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %s not found", id)));
        udatedPost.setBody(data.getBody());
        udatedPost.setTitle(data.getTitle());

        return postRepository.save(udatedPost);
    }
    @DeleteMapping("/{id}")
    public void destroy(@PathVariable long id) {
        if(postRepository.existsById(id)) {
            postRepository.deleteById(id);
            commentRepository.deleteByPostId(id);
        } else {
            throw new ResourceNotFoundException("doesn`t fined with id " + id);
        }
    }
}
// END

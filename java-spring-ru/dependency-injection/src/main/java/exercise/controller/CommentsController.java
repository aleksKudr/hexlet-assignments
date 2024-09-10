package exercise.controller;

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

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> index() {
        return commentRepository.findAll();
    }
    @GetMapping("/{id}")
    public Comment show(@PathVariable long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("doesn`t fined with id " + id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }
    @PutMapping("/{id}")
    public Comment update(@PathVariable long id, @RequestBody Comment data) {
        Comment updatedComment = commentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("doesn`t fined with id " + id));
        updatedComment.setBody(data.getBody());
        return commentRepository.save(updatedComment);
    }
    @DeleteMapping("/{id}")
    public void destroy(@PathVariable long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("doesn`t fined with id " + id);
        }
    }
}
// END

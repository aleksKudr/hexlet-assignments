package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    public BookMapper bookMapper;
    public List<BookDTO> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(book -> bookMapper.map(book))
                .toList();
    }
    public BookDTO create(BookCreateDTO dto) {
        var book = bookMapper.map(dto);
        bookRepository.save(book);
        return bookMapper.map(book);
    }
    public BookDTO findById(long id) {
        var book = bookRepository.findById(id).orElseThrow(( ) -> new ResourceNotFoundException("Not found"));
        return bookMapper.map(book);
    }
    public BookDTO update(BookUpdateDTO dto, long id) {
        var book = bookRepository.findById(id).orElseThrow(( ) -> new ResourceNotFoundException("Not found"));
        bookMapper.update(dto, book);
        bookRepository.save(book);
        return bookMapper.map(book);
    }
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
    // END
}

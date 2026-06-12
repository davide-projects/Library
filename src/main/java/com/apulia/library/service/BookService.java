    package com.apulia.library.service;

    import com.apulia.library.exception.BookNotFoundException;
    import com.apulia.library.exception.SearchException;
    import com.apulia.library.model.Book;
    import com.apulia.library.repository.BookRepository;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Map;

    @Service
    public class BookService {

        private final BookRepository bookRepository;

        public BookService(BookRepository bookRepository){
            this.bookRepository = bookRepository;
        }

        public List<Book> getAllBooks() {
            return bookRepository.findAll();
        }

        public Book getBookById(Integer id) {
            return bookRepository.findById(id)
                    .orElseThrow(() -> new BookNotFoundException(id));
        }

        public Book addBook(Book book) {
            return bookRepository.save(book);
        }

        public Book updateBook(int id, Book book) {
            Book existingBook = getBookById(id);
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPublisher(book.getPublisher());
            return bookRepository.save(existingBook);
        }

        public Book patchBook(int id, Map<String, Object> updates) {

            Book existingBook = getBookById(id);

            updates.forEach((key, value) -> {

                if (value == null) {
                    throw new SearchException("Il campo '" + key + "' non può essere null");
                }

                String stringValue = value.toString().trim();
                switch (key) {
                    case "title" -> {
                        if (stringValue.isBlank()) {
                            throw new SearchException("Il titolo non può essere vuoto");
                        }
                        existingBook.setTitle(stringValue);
                    }
                    case "author" -> {
                        if (stringValue.isBlank()) {
                            throw new SearchException("L'autore non può essere vuoto");
                        }
                        existingBook.setAuthor(stringValue);
                    }
                    case "publisher" -> {
                        if (stringValue.isBlank()) {
                            throw new SearchException("L'editore non può essere vuoto");
                        }
                        existingBook.setPublisher(stringValue);
                    }
                    default -> throw new SearchException("Campo '" + key + "' non supportato");
                }
            });
            return bookRepository.save(existingBook);
        }

        public void deleteBook(int id) {
            Book existingBook = getBookById(id);
            bookRepository.delete(existingBook);
        }

        public List<Book> searchByAuthorAndTitle(String author, String title) {

            boolean hasAuthor = (author != null && !author.isBlank());
            boolean hasTitle  = (title != null && !title.isBlank());

            // Nessun parametro valido
            if (!hasAuthor && !hasTitle) {
                throw new SearchException("Devi specificare almeno 'author' oppure 'title'");
            }
            List<Book> results;
            // Solo autore
            if (hasAuthor && !hasTitle) {
                results = bookRepository.findByAuthorContainingIgnoreCase(author);
            }
            // Solo titolo
            else if (!hasAuthor) {
                results = bookRepository.findByTitleContainingIgnoreCase(title);
            }
            // Entrambi presenti
            else {
                results = bookRepository
                        .findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(author, title);
            }
            // Nessun risultato
            if (results.isEmpty()) {
                throw new SearchException("Nessun libro trovato con i criteri forniti");
            }
            return results;
        }
    }
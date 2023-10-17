package web.bookshop.repository.impl;

import web.bookshop.exception.DataProcessingException;
import web.bookshop.exception.EntityNotFoundException;
import web.bookshop.model.Book;
import web.bookshop.repository.BookRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        try {
            sessionFactory.inTransaction(sessionFactory -> sessionFactory.persist(book));
            return book;
        } catch (Exception e) {
            throw new DataProcessingException("Can't save book to the DB, book: " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't get all books from DB", e);
        }
    }
}

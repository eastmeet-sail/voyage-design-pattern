package iterator;

import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IteratorTest {

    @Test
    @DisplayName("")
    void iteratorTest() {
        BookShelf bookShelf = new BookShelf(4);
        bookShelf.appendBook(new Book("On Liberty"));
        bookShelf.appendBook(new Book("Don Quixote"));
        bookShelf.appendBook(new Book("Walden"));
        bookShelf.appendBook(new Book("Silent Spring"));

        // Iterator 사용
        Iterator<Book> it = bookShelf.iterator();

        System.out.println("========== while 문 사용 ==========");
        while (it.hasNext()) {
            Book book = it.next();
            System.out.println("book.getName() = " + book.getName());
        }

        System.out.println("========== for 문 사용 ==========");
        for (Book book : bookShelf) {
            System.out.println("book.getName() = " + book.getName());
        }
    }

}
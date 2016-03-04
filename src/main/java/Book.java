import org.sql2o.*;
import java.util.List;

public class Book {
  private int id;
  private String title;

  public Book (String title) {
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public boolean equals(Object otherBook){
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle());
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO books (title) VALUES (:title)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("title", this.title)
         .executeUpdate()
         .getKey();
    }
  }

  //READ
  public static List<Book> all() {
    String sql = "SELECT * FROM books;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public static Book find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE id=:id;";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Book.class);
    }
  }

  public List<Author> getAuthors() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT authors.* FROM books JOIN authors_books ON (books.id = authors_books.book_id) JOIN authors ON (authors_books.author_id = authors.id) WHERE books.id=:id;";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Author.class);
    }
  }

  public List<Copy> allCopiesOf() {
    String sql = "SELECT * FROM copies WHERE id=:id;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Copy.class);
    }
  }

//UPDATE
  public void update(String newTitle) {
  this.title = newTitle;
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE books SET title=:newTitle;";
      con.createQuery(sql)
        .addParameter("newTitle", newTitle)
        .executeUpdate();
    }
  }

  public void addAuthor(int authorId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors_books (book_id, author_id) VALUES (:bookId, :authorId)";
        con.createQuery(sql)
          .addParameter("bookId", id)
          .addParameter("authorId", authorId)
          .executeUpdate();
    }
  }

  // DELETE
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM books WHERE id=:id;";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    }
  }
}

import org.sql2o.*;
import java.util.List;

public class Author{
  private int id;
  private String author_name;


  public Author(String author_name) {
    this.author_name = author_name;
  }

  public int getId() {
    return id;
  }

  public String getAuthorName() {
    return author_name;
  }

  @Override
  public boolean equals(Object otherAuthor){
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getAuthorName().equals(newAuthor.getAuthorName());
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO authors (author_name) VALUES (:author_name)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("author_name", this.author_name)
         .executeUpdate()
         .getKey();
    }
  }

  //READ
  public static List<Author> all() {
    String sql = "SELECT * FROM authors;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  public static Author find(int id) {
    String sql = "SELECT * FROM authors WHERE id=:id;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Author.class);
    }
  }

  //UPDATE
  public void update(String newName) {
  this.author_name = newName;
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE authors SET author_name=:newName;";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .executeUpdate();
    }
  }

  // DELETE
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM authors WHERE id=:id;";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    }
  }


  public List<Book> getBooks() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT books.* FROM authors JOIN authors_books ON (authors.id = authors_books.author_id) JOIN books ON (authors_books.book_id = books.id) WHERE authors.id=:id;";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Book.class);
    }
  }
}

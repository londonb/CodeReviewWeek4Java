import org.sql2o.*;
import java.util.List;
import java.util.Calendar;

public class Copy {
  private int id;
  private int book_id;
  private boolean checkout;
  private Calendar due_date;
  private int patron_id;


  public Copy(int bookId) {
    book_id = bookId;
    checkout = false;
  }

  public int getId() {
    return id;
  }

  public int getBookId() {
    return book_id;
  }

  public boolean getOut() {
    return checkout;
  }

  public Calendar getDueDate() {
    return due_date;
  }

  public int getPatronId() {
    return patron_id;
  }

  @Override
  public boolean equals(Object otherCopy){
    if (!(otherCopy instanceof Copy)) {
      return false;
    } else {
      Copy newCopy = (Copy) otherCopy;
      return  this.getBookId() == (newCopy.getBookId()) &&
              this.getId() == (newCopy.getId());
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO copies (checkout, book_id) VALUES (:out, :book_id)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("out", this.checkout)
         .addParameter("book_id", book_id)
         .executeUpdate()
         .getKey();
    }
  }

  //READ
  public static List<Copy> all() {
    String sql = "SELECT * FROM copies;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Copy.class);
    }
  }

  public static Copy find(int id) {
    String sql = "SELECT * FROM copies WHERE id=:id;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Copy.class);
    }
  }

  public Book getBook() {
    String sql = "SELECT * FROM books WHERE id=:book_id;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("book_id", book_id)
      .executeAndFetchFirst(Book.class);
    }
  }

  public String getBookTitle() {
    Book myBook = this.getBook();
    return myBook.getTitle();
  }

  //UPDATE

  // public void checkout() {
  //   checkout = true;
  //   Calendar due_date = Calendar.getInstance();
  //   due_date.add(Calendar.DAY_OF_MONTH, 30);
  //   String sql = ("UPDATE copies SET checkout=:checkout, due_date=:due_date;");
  //   try (Connection con = DB.sql2o.open()) {
  //     return con.createQuery(sql)
  //     .addParameter("out", checkout)
  //     .addParameter("due_date", due_date)
  //     .executeUpdate();
  //   }
  // }

 //  http://www.java2s.com/Tutorial/Java/0040__Data-Type/SimpleDateFormat.htm

  // DELETE
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM copies WHERE id=:id;";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    }
  }
}

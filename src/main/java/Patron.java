import org.sql2o.*;
import java.util.List;

public class Patron {
  private int id;
  private String patron_name;


  public Patron(String patron_name) {
    this.patron_name = patron_name;
  }

  public int getId() {
    return id;
  }

  public String getPatronName() {
    return patron_name;
  }

  @Override
  public boolean equals(Object otherPatron){
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getPatronName().equals(newPatron.getPatronName());
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO patrons (patron_name) VALUES (:patron_name)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("patron_name", this.patron_name)
         .executeUpdate()
         .getKey();
    }
  }

  //READ
  public static List<Patron> all() {
    String sql = "SELECT * FROM patrons;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  public static Patron find(int id) {
    String sql = "SELECT * FROM patrons WHERE id=:id;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Patron.class);
    }
  }

  //UPDATE
  public void update(String newName) {
  this.patron_name = newName;
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE patrons SET patron_name=:newName;";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .executeUpdate();
    }
  }

  // DELETE
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM patrons WHERE id=:id;";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    }
  }
}

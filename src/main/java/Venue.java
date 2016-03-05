import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Venue {
  private String name;
  private int id;

  public Venue(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static  List<Venue> all() {
    String sql = "SELECT * FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
      this.getId() == newVenue.getId();
    }
  }

  public void save() {
    String sql = "INSERT INTO venues (name) VALUES (:name)";
     try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    String sql = "SELECT id, name FROM venues WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Venue venue = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }
  //
  public void updateName(String name) {
    String sql ="UPDATE venues SET name = :name WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    String sqlJoin ="DELETE FROM shows WHERE venue_id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sqlJoin)
        .addParameter("id", id)
        .executeUpdate();
    }
    String sql ="DELETE FROM venues WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
  public void addBand (Band band) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO shows (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
      .addParameter("venue_id", this.getId())
      .addParameter("band_id", band.getId())
      .executeUpdate();
    }
  }

  public List<Band> getBands() {
    try(Connection con = DB.sql2o.open()) {

      String sql = "SELECT bands.* FROM venues " +
      "JOIN shows ON (venues.id = venue_id) " +
      "JOIN bands ON (band_id = bands.id) " +
      "WHERE venues.id = :id";
      List<Band> bands = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Band.class);
      return bands;
    }
  }

  public static void deleteAll() {
    String sqlJoin ="DELETE FROM shows";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sqlJoin)
        .executeUpdate();
    }
    String sql ="DELETE FROM venues ";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .executeUpdate();
    }
  }

}

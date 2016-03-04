import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Band {
  private int id;
  private String band_name;

  public int getId() {
    return id;
  }

  public String getBandName() {
    return band_name;
  }

  public Band(String band_name) {
    this.band_name = band_name;
  }

  public static List<Band> all() {
  String sql = "SELECT * FROM bands";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Band.class);
  }
 }

  @Override
  public boolean equals(Object otherBand){
   if (!(otherBand instanceof Band)) {
     return false;
   } else {
     Band newBand = (Band) otherBand;
     return this.getBandName().equals(newBand.getBandName());
   }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(band_name) VALUES (:band_name)";
      this.id = (int) con.createQuery(sql,true)
      .addParameter("band_name", this.band_name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id= :id";
      Band band = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void update(String newName) {
    this.band_name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET band_name=:newName WHERE id =:id";
        con.createQuery(sql)
          .addParameter("newName", newName)
          .addParameter("id", id)
          .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM bands WHERE id =:id";
      con.createQuery(sql)
      .addParameter("id",id)
      .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    String sql = "SELECT venues.* FROM bands JOIN shows ON (bands.id = shows.band_id) JOIN venues ON (shows.venue_id = venues.id) WHERE bands.id=:id;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Venue.class);
    }
  }


  public void addVenue(int venue_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO shows (band_id, venue_id) VALUES (:band_id, :venue_id)";
        con.createQuery(sql)
          .addParameter("band_id", this.getId())
          .addParameter("venue_id", venue_id)
          .executeUpdate();
    }
  }
}

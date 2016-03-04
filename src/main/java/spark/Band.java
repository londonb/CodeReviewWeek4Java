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
      String sql = "UPDATE bands SET band_name=:newName";
        con.createQuery(sql)
          .addParameter("newName", newName)
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
}

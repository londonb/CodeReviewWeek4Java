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
  String sql = "SELECT id, band_name FROM bands";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Band.class);
  }
 }
}

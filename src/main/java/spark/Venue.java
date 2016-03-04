import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Venue {

  private int id;
  private String venue_name;

  public int getId() {
    return id;
  }

  public String getVenueName() {
    return venue_name;
  }

  public Venue(String venue_name) {
    this.venue_name = venue_name;
  }

  public static List<Venue> all() {
  String sql = "SELECT * FROM venues";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Venue.class);
  }
 }
}

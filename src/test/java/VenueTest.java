import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfVenueAretheSame() {
    Venue firstVenue = new Venue("40 Watt");
    Venue secondVenue = new Venue("40 Watt");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesVenueIntoDatabase_true() {
    Venue myVenue = new Venue("40 Watt");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void find_findsVenueInDatabase_true() {
    Venue myVenue = new Venue("Go Go Boogie");
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }
}

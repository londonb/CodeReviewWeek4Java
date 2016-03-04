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
}

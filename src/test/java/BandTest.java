import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Band firstBand = new Band("Go Go Boogie");
    Band secondBand = new Band("Go Go Boogie");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Band myBand = new Band("Go Go Boogie");
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }

  @Test
  public void find_findsBandInDatabase_true() {
    Band myBand = new Band("Go Go Boogie");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void updateName_updatesBandNameInDatabase() {
    Band newBand = new Band ("Goo Goo Boogie");
    newBand.save();
    newBand.updateName("Go Go Boogie");
    assertEquals(Band.all().get(0).getName(),("Go Go Boogie"));
  }

  @Test
  public void delete_deletesABandFromDatabase() {
    Band myBand = new Band ("Go Go Boogie");
    myBand.save();
    myBand.delete();
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void getVenues_returnsVenueWithId() {
    Band newBand = new Band("Go Go Boogie");
    newBand.save();
    Venue newVenue = new Venue("40 Watt");
    newVenue.save();
    newBand.addVenue(newVenue);
    List savedVenues = newBand.getVenues();
    assertEquals(savedVenues.size(), 1);
  }
}

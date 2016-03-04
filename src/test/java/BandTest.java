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
  public void update_updatesBandNameInDatabase() {
    Band newBand = new Band ("Goo Goo Boogie");
    newBand.save();
    newBand.update("Go Go Boogie");
    assertEquals(newBand.getBandName(), "Go Go Boogie");
  }

  @Test
  public void delete_deletesABandFromDatabase() {
    Band myBand = new Band ("Go Go Boogie");
    myBand.save();
    myBand.delete();
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void getBands_returnsVenueWithId() {
    Band newBand = new Band("Go Go Boogie");
    Venue newVenue = new Venue("40 Watt");
    newBand.save();
    newVenue.save();
    newBand.addVenue(newVenue.getId());
    assertTrue(newVenue.getBands().contains(newBand));
  }
}

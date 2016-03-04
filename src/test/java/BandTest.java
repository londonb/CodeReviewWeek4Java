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
}

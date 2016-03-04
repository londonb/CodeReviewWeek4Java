import org.junit.*;
import static org.junit.Assert.*;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void book_instantiatesCorrectly_true() {
    Patron newPatron = new Patron("Cheryl's Story");
    assertTrue(newPatron instanceof Patron);
  }

  @Test
  public void equals_returnsTrueIfPatronsTitlesAreTheSame() {
    Patron newPatron = new Patron("Bobby");
    Patron newPatronToo = new Patron("Bobby");
    assertTrue(newPatron.equals(newPatronToo));
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void save() {
    Patron newPatron = new Patron("Bobby");
    newPatron.save();
    assertTrue(Patron.all().contains(newPatron));
  }

  @Test
  public void update_updatesPatronTitle() {
    Patron newPatron = new Patron("History");
    newPatron.save();
    newPatron.update("Charles");
    assertEquals("Charles", newPatron.getPatronName());
  }

  @Test
  public void delete_removesPatron() {
    Patron newPatron = new Patron("Literature");
    newPatron.save();
    assertTrue(Patron.all().contains(newPatron));
    newPatron.delete();
    assertFalse(Patron.all().contains(newPatron));
  }

  @Test
  public void find_returnesPatronWithId() {
    Patron newPatron = new Patron("Literature");
    newPatron.save();
    assertEquals(Patron.find(newPatron.getId()), newPatron);
  }
}

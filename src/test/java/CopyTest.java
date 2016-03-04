import org.junit.*;
import static org.junit.Assert.*;

public class CopyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void copy_instantiatesCorrectly_true() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    assertTrue(newCopy instanceof Copy);
  }

  @Test
  public void equals_returnsTrueIfCopiesTitlesAreTheSame() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    Copy newCopyToo = new Copy(newBook.getId());
    assertTrue(newCopy.equals(newCopyToo));
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Copy.all().size(), 0);
  }

  @Test
  public void save() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertTrue(Copy.all().contains(newCopy));
  }

  // @Test
  // public void update_updatesPropertiesOfCopy() {
  //   Book newBook = new Book("The Great Gatsby");
  //   newBook.save();
  //   Copy newCopy = new Copy(newBook.getId());
  //   newCopy.save();
  //   newCopy.update(true, );
  //   assertEquals("Charles", newCopy.getTitle());
  // }

  @Test
  public void delete_removesCopy() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertTrue(Copy.all().contains(newCopy));
    newCopy.delete();
    assertFalse(Copy.all().contains(newCopy));
  }

  @Test
  public void find_returnesCopyWithId() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertEquals(Copy.find(newCopy.getId()), newCopy);
  }

  @Test
  public void getBook_returnesBookForSpecificCopy() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertEquals(newBook, newCopy.getBook());
  }

  @Test
  public void getBookTitle_returnesBookTitleForSpecificCopy() {
    Book newBook = new Book("The Great Gatsby");
    newBook.save();
    Copy newCopy = new Copy(newBook.getId());
    newCopy.save();
    assertEquals(newBook.getTitle(), newCopy.getBookTitle());
  }
}

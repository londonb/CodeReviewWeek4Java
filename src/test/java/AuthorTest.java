import org.junit.*;
import static org.junit.Assert.*;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void author_instantiatesCorrectly_true() {
    Author newAuthor = new Author("Cheryl Story");
    assertTrue(newAuthor instanceof Author);
  }

  @Test
  public void equals_returnsTrueIfAuthorNameAreTheSame() {
    Author newAuthor = new Author("Bobby Bob");
    Author newAuthorToo = new Author("Bobby Bob");
    assertTrue(newAuthor.equals(newAuthorToo));
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void save() {
    Author newAuthor = new Author("Bobby Bob");
    newAuthor.save();
    assertTrue(Author.all().contains(newAuthor));
  }

  @Test
  public void update_updatesAuthorName() {
    Author newAuthor = new Author("History");
    newAuthor.save();
    newAuthor.update("Charles");
    assertEquals("Charles", newAuthor.getAuthorName());
  }

  @Test
  public void delete_removesAuthor() {
    Author newAuthor = new Author("Bobby Bob");
    newAuthor.save();
    assertTrue(Author.all().contains(newAuthor));
    newAuthor.delete();
    assertFalse(Author.all().contains(newAuthor));
  }

  @Test
  public void find_returnesAuthorWithId() {
    Author newAuthor = new Author("Literature");
    newAuthor.save();
    assertEquals(Author.find(newAuthor.getId()), newAuthor);
  }


  @Test
  public void getBooks_returnesAuthorWithId() {
    Book newBook = new Book("Literature");
    Author newAuthor = new Author("Bobby Bob");
    newBook.save();
    newAuthor.save();
    newBook.addAuthor(newAuthor.getId());
    assertTrue(newAuthor.getBooks().contains(newBook));
  }
}

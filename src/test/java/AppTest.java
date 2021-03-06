import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

    @Test
    public void rootTest() {
        goTo("http://localhost:4567/");
        assertThat(pageSource()).contains("Welcome to the Concert Database");
    }

    @Test
    public void bandIsCreated() {
      goTo("http://localhost:4567/bands");
      fill("#band_name").with("Go Go Boogie");
      submit("#addBand");
      assertThat(pageSource()).contains("Go Go Boogie");
    }

    @Test
    public void venueIsCreated() {
      goTo("http://localhost:4567/venues");
      fill("#venueName").with("40 Watt");
      submit("#addVenue");
      assertThat(pageSource()).contains("40 Watt");
    }

    @Test
    public void addVenueToBand() {
      Venue newVenue = new Venue("Fox Teater");
      newVenue.save();
      Band newBand = new Band("Go Go Boogie");
      newBand.save();
      String bandPath = String.format("http://localhost:4567/bands/%d", newBand.getId());
      goTo(bandPath);
      assertThat(pageSource()).contains("Fox Teater");
      assertThat(pageSource()).contains("Go Go Boogie");
    }
}

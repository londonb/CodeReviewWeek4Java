
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

      get("/", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/bands", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("bands", Band.all());
        model.put("template", "templates/bands.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/venues", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("venues", Venue.all());
        model.put("template", "templates/venues.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/bands/deleteAll", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Band.deleteAll();
        response.redirect("/bands");
        return null;
      });

      post("/bands/:id/update", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params("id"));
        Band band = Band.find(id);
        String bandName = request.queryParams("updateBandName");
        band.updateName(bandName);
        response.redirect("/bands/" + id);
        return null;
      });

      post("/venues/:id/update", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params("id"));
        Venue venue = Venue.find(id);
        String venueName = request.queryParams("updateVenueName");
        venue.updateName(venueName);
        response.redirect("/venues/" + id);
        return null;
      });

      get("/bands/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params("id"));
        Band band = Band.find(id);
        model.put("band", band);
        model.put("allVenues", Venue.all());
        model.put("template", "templates/band.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/venues/deleteAll", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Venue.deleteAll();
        response.redirect("/venues");
        return null;
      });

      get("/venues/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params("id"));
        Venue venue = Venue.find(id);
        model.put("venue", venue);
        model.put("allBands", Band.all());
        model.put("template", "templates/venue.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/bands", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String band_name = request.queryParams("band_name");
        Band newBand = new Band(band_name);
        newBand.save();
        response.redirect("/bands");
        return null;
      });

      post("/bands/:id/delete", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int bandId = Integer.parseInt(request.queryParams("bandId"));
        Band band = Band.find(bandId);
        band.delete();
        response.redirect("/bands");
        return null;
      });

      post("/bands/:id", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();
       int bandId = Integer.parseInt(request.queryParams("band_id"));
       int venueId = Integer.parseInt(request.queryParams("venueName"));
       Venue venue = Venue.find(venueId);
       Band band = Band.find(bandId);
       band.addVenue(venue);
       response.redirect("/bands/" + bandId);
       return null;
     });

     post("/venues/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venueId = Integer.parseInt(request.queryParams("venue_id"));
      int bandId = Integer.parseInt(request.queryParams("band_id"));
      Band band = Band.find(bandId);
      Venue venue = Venue.find(venueId);
      venue.addBand(band);
      response.redirect("/venues/" + venueId);
      return null;
    });

    post("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String venueName = request.queryParams("venueName");
      Venue newVenue = new Venue(venueName);
      newVenue.save();
      response.redirect("/venues");
      return null;
    });

    post("/venues/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venueId = Integer.parseInt(request.queryParams("venueId"));
      Venue venue = Venue.find(venueId);
      venue.delete();
      response.redirect("/venues");
      return null;
    });
  }
}

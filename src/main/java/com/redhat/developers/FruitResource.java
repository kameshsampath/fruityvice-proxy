package com.redhat.developers;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/fruit")
public class FruitResource {

  @Inject
  @Named("trust-all")
  FruityViceService fruityViceService;

  @GET
  @Path("/all")
  @Produces(MediaType.APPLICATION_JSON)
  public List<FruityVice> all() {
    return fruityViceService.allFruits();
  }

  @GET
  @Path("{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public FruityVice getFruitInfoByName(@PathParam("name") String name) {
    return fruityViceService.getFruitByName(name);
  }

}

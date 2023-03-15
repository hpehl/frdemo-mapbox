package org.cajun.navy.map;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/directions/v5/mapbox/driving")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class DrivingDirectionsService {

    public static final int PAYLOADS = 10;

    @Inject
    Logger log;

    @GET
    @Path("{coordinates}")
    public Response fakeDirections(@PathParam("coordinates") String coordinates) {
        try {
            int index = new Random().nextInt(PAYLOADS);
            String payload = String.format("/payload%d.json", index);
            URL resource = getClass().getResource(payload);
            if (resource != null) {
                String json = Files.readString(Paths.get(resource.toURI()));
                String bytes = humanReadableByteCount(json.getBytes(Charset.defaultCharset()).length, true);
                log.infof("MapBox request for: %s. Choosing %s: %s", coordinates, payload, bytes);
                return Response.ok(json).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {return bytes + " B";}
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}

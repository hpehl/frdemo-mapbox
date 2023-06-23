package org.cajun.navy.map;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.stream.Collectors;

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
    @Path("available")
    public Response available() {
        return Response.ok().build();
    }

    @GET
    @Path("{coordinates}")
    public Response fakeDirections(@PathParam("coordinates") String coordinates) {
        try {
            int index = new Random().nextInt(PAYLOADS);
            String payload = String.format("/payload%d.json", index);
            InputStream stream = getClass().getResourceAsStream(payload);
            if (stream != null) {
                String json = new BufferedReader(new InputStreamReader(stream))
                        .lines().parallel().collect(Collectors.joining("\n"));
                String bytes = humanReadableByteCount(json.getBytes(Charset.defaultCharset()).length, true);
                log.infof("MapBox request for: %s. Choosing %s: %s", coordinates, payload, bytes);
                return Response.ok(json).build();
            } else {
                log.errorf("Payload $s not found!");
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            log.errorf(e, "Internal server error: %s", e.getMessage());
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

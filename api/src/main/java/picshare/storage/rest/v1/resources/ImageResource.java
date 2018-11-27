package picshare.storage.rest.v1.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

@Path("image")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ImageResource {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @GET
    @Produces("image/png")
    public Response returnImage() {
        log.info("AHAHAHAHAHAH");
        File fi = new File("test.png");
        final byte[] image;
        try {
            image = Files.readAllBytes(fi.toPath());

            return Response.ok().entity(new StreamingOutput(){
                @Override
                public void write(OutputStream output)
                        throws IOException, WebApplicationException {
                    output.write(image);
                    output.flush();
                }
            }).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }
}

package picshare.storage.rest.v1.resources;

import io.swagger.oas.annotations.Parameter;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Path("image")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ImageResource {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @GET
    @Path("/{userId}/{albumId}/{imageId}")
    @Produces("image/png")
    public Response returnImage(@PathParam("userId") Integer userId, @PathParam("albumId") Integer albumId, @PathParam("imageId") Integer imageId) {
        String dir = System.getProperty("user.dir");
        //System.out.println("current dir = " + dir);
        final byte[] image;
        try {
            File fi = new File(dir+"/images/"+userId+"-"+albumId+"-"+imageId+".png");
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
            log.info("No image found");
            return Response.status(404).entity(null).build();
        }

    }
}

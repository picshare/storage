package picshare.storage.rest.v1.resources;

import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import picshare.storage.config.StorageConfig;
import picshare.storage.entitete.business.newImage;
import picshare.storage.entitete.business.updateImage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.logging.Logger;

@Path("image")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ImageResource {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Inject
    private StorageConfig sc;

    @Inject
    @Metric(name = "number-of-images-accepted")
    private Counter counter;

    @GET
    @Path("/{userId}/{albumId}/{imageId}")
    @Produces("image/png")
    public Response returnImage(@PathParam("userId") Integer userId, @PathParam("albumId") Integer albumId, @PathParam("imageId") Integer imageId) {
        final byte[] image;
        try {
            if(sc.isDisablestorage()) {
                throw new Exception("Storage folder disabled for maintainance");
            }
            File fi = new File(sc.getImagefolder() +"/images/"+userId+"-"+albumId+"-"+imageId+".png");
            image = Files.readAllBytes(fi.toPath());

            return Response.ok().entity(new StreamingOutput(){
                @Override
                public void write(OutputStream output)
                        throws IOException, WebApplicationException {
                    output.write(image);
                    output.flush();
                }
            }).build();
        } catch (Exception e) {
            log.info("No image found");
            return Response.status(404).entity(null).build();
        }
    }

    @POST
    public Response saveImage(newImage image) {
        try {
            if(sc.isDisablestorage()) {
                throw new Exception("Storage folder disabled for maintainance");
            }
            final byte[] decodedImage = Base64.getDecoder().decode(image.getEncodedImage());
            File outputfile = new File(sc.getImagefolder()+"/images/"+image.getUserId()+"-"+image.getAlbumId()+"-"+image.getImageId()+".png");
            FileUtils.writeByteArrayToFile(outputfile, decodedImage);
            counter.inc();
            return Response.ok().build();
        } catch (Exception e) {
            log.info("No image found");
            return Response.status(404).entity(null).build();
        }
    }

    @PUT
    public Response updateImage(updateImage image) {
        try {
            if(sc.isDisablestorage()) {
                throw new Exception("Storage folder disabled for maintainance");
            }
            File file = new File(sc.getImagefolder()+"/images/"+image.getUserId()+"-"+image.getOldAlbumId()+"-"+image.getImageId()+".png");
            File file2 = new File(sc.getImagefolder()+"/images/"+image.getUserId()+"-"+image.getAlbumId()+"-"+image.getImageId()+".png");
            if (file2.exists()) {
                throw new Exception("File already exists");
            }
            boolean success = file.renameTo(file2);

            if (!success) {
                throw new Exception("Failes writing to new file");
            }

            return Response.ok().build();
        } catch (Exception e) {
            log.info(e.toString());
            return Response.status(404).entity(null).build();
        }
    }

    @DELETE
    @Path("/{userId}/{albumId}/{imageId}")
    public Response removeImage(@PathParam("userId") Integer userId, @PathParam("albumId") Integer albumId, @PathParam("imageId") Integer imageId) {
        try {
            if(sc.isDisablestorage()) {
                throw new Exception("Storage folder disabled for maintainance");
            }
            File file = new File(sc.getImagefolder()+"/images/"+userId+"-"+albumId+"-"+imageId+".png");
            if(!file.delete()) {
                throw new Exception("Image failed to delete");
            }

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            log.info(e.toString());
            return Response.status(404).entity(null).build();
        }
    }
}

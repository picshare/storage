package picshare.storage.entitete.business;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class updateImage {
    @XmlElement
    private String userId;
    @XmlElement
    private String albumId;
    @XmlElement
    private String imageId;
    @XmlElement
    private String oldAlbumId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getOldAlbumId() {
        return oldAlbumId;
    }

    public void setOldAlbumId(String oldAlbumId) {
        this.oldAlbumId = oldAlbumId;
    }
}

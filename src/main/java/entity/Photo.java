package entity;

import static image.ImageServlet.IMAGE_SERVLET_PATH;
import java.nio.file.Paths;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Photo {
 String fullSizeUrl, thumbUrl;

    public Photo() {
    }

    public Photo(String fullSizeUrl, String thumbUrl) {
        this.fullSizeUrl = fullSizeUrl;
        this.thumbUrl = thumbUrl;
    }

    public String getFullSizeUrl() {
        return fullSizeUrl;
    }

    public void setFullSizeUrl(String fullSizeUrl) {
        this.fullSizeUrl = fullSizeUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    @Override
    public String toString() {
        return "Photo{" + "fullSizeUrl=" + fullSizeUrl + ", thumbUrl=" + thumbUrl + '}';
    }
 
}

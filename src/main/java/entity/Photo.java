package entity;

import static image.ImageConstants.IMAGE_SERVLET_PATH;
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
        this.fullSizeUrl = getImageUrl(fullSizeUrl);
        this.thumbUrl = getImageUrl(thumbUrl);
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

    String getImageUrl(String fileName) {
        return IMAGE_SERVLET_PATH + fileName;
    }
}

package entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class IlllustratedData {

    String firstName, lastName;
    List<Photo> photos ;

    public IlllustratedData() {
    }

    public IlllustratedData(String firstName, String lastName, List<Photo> urls) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photos = urls;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "IlllustratedData{" + "firstName=" + firstName + ", lastName=" + lastName + ", urls=" + photos + '}';
    }

}

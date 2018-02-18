package entity;

import java.util.List;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class IlllustratedData {

    Integer id;
    String firstName, lastName;
    List<Photo> photos;

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

    public Integer getId() {
        if (id == null) {
            id = new Random().nextInt();
        }
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IlllustratedData{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", photos=" + photos + '}';
    }
}

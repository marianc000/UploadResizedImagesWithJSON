package entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class IlllustratedData {

    String firstName, lastName;
    List<String> photos;

    public IlllustratedData() {
    }

    public IlllustratedData(String firstName, String lastName, List<String> urls) {
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        System.out.println(">setPhotos");
        for (String s : photos) {
            System.out.println(s);
        }
        System.out.println("<setPhotos");
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "IlllustratedData{" + "firstName=" + firstName + ", lastName=" + lastName + ", urls=" + photos + '}';
    }

}

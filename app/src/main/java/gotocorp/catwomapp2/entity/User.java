package gotocorp.catwomapp2.entity;

import com.google.android.gms.maps.model.LatLng;

/**
 * Un utilisateur (servira a afficher qui est derrière les alertes, qui se déplace sur la map))
 */
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private LatLng location;

    public User() {
    }

    public void initFromWebService() {
        String email = "test@email.test";
        String firstname = "John";
        String lastname = "Doe";
        Integer id = 1337;
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void refreshPositionFromWebService() {
         this.location = new LatLng(48.05, 12.85);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public LatLng getLocation() {
        return location;
    }
}

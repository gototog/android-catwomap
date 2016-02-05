package gotocorp.catwomapp2.entity;

/**
 * Created by goto on 04/12/15.
 */
public class Alert {
    private int id;
    private String name;
    private String mapCoordinate;

    public Alert() {
        this.name = name;
        this.mapCoordinate = "inconnu";
    }
    public Alert(String name, String mapCoordinate) {
        this.name = name;
        this.mapCoordinate = mapCoordinate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapCoordinate() {
        return mapCoordinate;
    }

    public void setMapCoordinate(String mapCoordinate) {
        this.mapCoordinate = mapCoordinate;
    }
}

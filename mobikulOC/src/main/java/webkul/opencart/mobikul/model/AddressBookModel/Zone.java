package webkul.opencart.mobikul.model.AddressBookModel;

/**
 * Created by manish.choudhary on 18/8/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Zone{

    @SerializedName("zone_id")
    @Expose
    private String zoneId;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public Zone() {
    }

    /**
     *
     * @param name
     * @param zoneId
     */
    public Zone(String zoneId, String name) {
        super();
        this.zoneId = zoneId;
        this.name = name;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

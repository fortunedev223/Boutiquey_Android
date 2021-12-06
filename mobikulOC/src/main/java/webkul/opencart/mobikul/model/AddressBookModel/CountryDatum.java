package webkul.opencart.mobikul.model.AddressBookModel;

/**
 * Created by manish.choudhary on 18/8/17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDatum {

    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("zone")
    @Expose
    private List<Zone> zone = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public CountryDatum() {
    }

    /**
     *
     * @param countryId
     * @param name
     * @param zone
     */
    public CountryDatum(String countryId, String name, List<Zone> zone) {
        super();
        this.countryId = countryId;
        this.name = name;
        this.zone = zone;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Zone> getZone() {
        return zone;
    }

    public void setZone(List<Zone> zone) {
        this.zone = zone;
    }

}

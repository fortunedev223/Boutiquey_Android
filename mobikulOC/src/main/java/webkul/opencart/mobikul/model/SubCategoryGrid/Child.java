package webkul.opencart.mobikul.model.SubCategoryGrid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Child {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("children")
    @Expose
    private List<Object> children = null;
    @SerializedName("column")
    @Expose
    private String column;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("icon")
    @Expose
    private String icon;

    public Child(String name, List<Object> children, String column, String path, String image, String thumb, String icon) {
        this.name = name;
        this.children = children;
        this.column = column;
        this.path = path;
        this.image = image;
        this.thumb = thumb;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}

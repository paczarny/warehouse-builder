package pl.krakow.uek.wzisn2.etl.advert;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Advert {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
    private String revision;

    @JsonProperty
    private String url;

    @JsonProperty
    private Long price;

    @JsonProperty
    private float area;

    @JsonProperty
    private String username;

    public Advert() {
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getRevision() {
        return revision;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getArea() { return area; }

    public void setArea(float area) { this.area = area; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }
}

package pl.krakow.uek.wzisn2.etl.advert;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonProperty
    private String market;

    @JsonProperty
    private String constructionType;

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

    public String getMarket() { return market; }

    public void setMarket(String market) { this.market = market; }

    public String getConstructionType() { return constructionType; }

    public void setConstructionType(String constructionType) { this.constructionType = constructionType; }

    @JsonIgnore
    public String[] getAttributesArray() {
        return new String[]{
                this.id,
                Long.toString(this.price),
                this.revision,
                Float.toString(this.area),
                this.market,
                this.constructionType,
                this.username
        };
    }
}

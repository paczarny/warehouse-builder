package pl.krakow.uek.wzisn2.etl.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailPageScrapper {
    private Document doc;

    public DetailPageScrapper(String url) throws IOException {
        WebsiteDownloader web = new WebsiteDownloader();
        doc = web.getDocument(url);
    }

    public String getId() {
        Elements details = doc.getElementsByClass("offer-titlebox__details");
        Elements small = details.select("small");
        String[] strings = small.text().split(":");
        return strings[strings.length - 1].trim();
    }

    public Long getPrice() {
        String priceText = doc.getElementsByClass("price-label").select("strong").text();
        String pureNumber = priceText.replaceAll("[^0-9]", "");
        return Long.valueOf(pureNumber);
    }

    public float getArea() {
        Elements table = doc.getElementsByClass("details fixed marginbott20 margintop5 full")
                .select("tbody > tr > td > table > tbody > tr");
        String areaText = this.getValueFromTableRow(table, "Powierzchnia");
        String pureNumber = areaText.replaceAll("[^0-9|^.]", "");
        return Float.parseFloat(pureNumber);
    }

    private String getValueFromTableRow(Elements elements, String thName) {
        String val = "";
        for(var element : elements) {
            if(element.select("th").text().equals(thName)) {
                val = element.select("td > strong").text();
                break;
            }
        }
        return val;
    }
}

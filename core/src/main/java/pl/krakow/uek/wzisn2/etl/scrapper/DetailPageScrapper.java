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
        Elements table = doc.getElementsByClass("details fixed marginbott20 margintop5 full");
        String areaText = this.getValueFromTableRow(table, "Powierzchnia");
        String number = areaText.replaceAll("[^0-9|^,]", "");
        String pureNumber = number.replaceAll("[,]", ".");
        return Float.parseFloat(pureNumber);
    }

    public String getMarket() {
        Elements table = doc.getElementsByClass("details fixed marginbott20 margintop5 full");
        return this.getValueFromTableRow(table, "Rynek");
    }

    private String getValueFromTableRow(Elements table, String thName) {
        Elements elements = table.select("tbody > tr > td > table > tbody > tr");
        String val = "";
        for(var element : elements) {
            if(element.select("th").text().equals(thName)) {
                val = element.select("td > strong").text();
                return val;
            }
        }
        return val;
    }
}

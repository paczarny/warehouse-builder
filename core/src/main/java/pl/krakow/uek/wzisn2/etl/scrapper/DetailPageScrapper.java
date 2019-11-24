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
}

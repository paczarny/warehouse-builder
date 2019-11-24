package pl.krakow.uek.wzisn2.etl.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListPageScrapper {
    private Document doc;

    public ListPageScrapper(String url) throws IOException {
        WebsiteDownloader web = new WebsiteDownloader();
        doc = web.getDocument(url);
    }

    public List<String> getAdvertUrls() {
        Elements links = doc.getElementsByAttributeValue("data-cy", "listing-ad-title");
        return links.stream().map(e -> e.attr("href")).collect(Collectors.toList());
    }
}

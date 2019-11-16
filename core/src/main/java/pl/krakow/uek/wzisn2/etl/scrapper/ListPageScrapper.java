package pl.krakow.uek.wzisn2.etl.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.krakow.uek.wzisn2.etl.WebSiteDownloader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListPageScrapper {
    private Document doc;

    public ListPageScrapper(String url) throws IOException {
        WebSiteDownloader web = new WebSiteDownloader(url);
        doc = web.getDocument();
    }

    public List<String> getAdvertUrls() {
        Elements links = doc.getElementsByAttributeValue("data-cy", "listing-ad-title");
        return links.stream().map(e -> e.attr("href")).collect(Collectors.toList());
    }
}

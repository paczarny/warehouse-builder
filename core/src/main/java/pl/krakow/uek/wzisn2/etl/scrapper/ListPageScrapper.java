package pl.krakow.uek.wzisn2.etl.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class representing page containing advertisements
 */
public class ListPageScrapper {
    private Document doc;

    public ListPageScrapper(String url) throws IOException {
        WebsiteDownloader web = new WebsiteDownloader();
        doc = web.getDocument(url);
    }

    /**
     * Scraps URLs from page containing various URLs
     *
     * @return list of scrapped URLS
     */
    public List<String> getAdvertUrls() {
        Elements links = doc.getElementsByAttributeValue("data-cy", "listing-ad-title");
        return links.stream().map(e -> e.attr("href")).collect(Collectors.toList());
    }

    /**
     * Recognizes last available page
     *
     * @return number of last page
     */
    public Integer getLastPage() {
        var lastLink = doc.getElementsByClass("pager").select("a[data-cy='page-link-last']").last().attr("href");
        var pageNumber = lastLink.substring(lastLink.lastIndexOf("=") + 1);
        return Integer.valueOf(pageNumber);
    }
}

package pl.krakow.uek.wzisn2.etl.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krakow.uek.wzisn2.etl.scrapper.DetailPageScrapper;
import pl.krakow.uek.wzisn2.etl.scrapper.ListPageScrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExtractService {
    private final Logger log = LoggerFactory.getLogger(ExtractService.class);
    private final String URL;

    public ExtractService(String url) {
        URL = url;
    }

    public List<DetailPageScrapper> extractAllAdverts() throws IOException {
        log.info("Preparing URLs");
        Integer lastPage = getLastPage(URL);
        List<String> advertUrls = getAdvertUrls(lastPage);

        log.info("Extracting adverts");
        return extractAdverts(advertUrls);
    }

    private List<DetailPageScrapper> extractAdverts(List<String> advertUrls) {
        List<DetailPageScrapper> pages = new ArrayList<>();
        for (int i = 0; i < advertUrls.size(); i++) {
            DetailPageScrapper page = new DetailPageScrapper(advertUrls.get(i));
            pages.add(page);
            log.info("Extracted advert {}/{}", i + 1, advertUrls.size());
        }
        return pages;
    }

    private List<String> getAdvertUrls(Integer lastPage) throws IOException {
        List<String> advertUrls = new ArrayList<>();

        for (int i = 1; i < lastPage; i++) {
            advertUrls.addAll(getAdvertUrls(URL + "?page=" + i));
            log.info("URLs scrapped from page " + i);
        }
        log.info("{} URLs scrapped", advertUrls.size());
        return advertUrls;
    }

    private List<String> getAdvertUrls(String url) throws IOException {
        log.info("Processing list with URL [{}]", url);
        ListPageScrapper listPageScrapper = new ListPageScrapper(url);
        return listPageScrapper.getAdvertUrls()
                .stream()
                .filter(s -> s.startsWith("https://www.olx.pl"))    // remove another providers
                .collect(Collectors.toList());
    }

    private Integer getLastPage(String url) throws IOException {
        ListPageScrapper listPageScrapper = new ListPageScrapper(url);
        return listPageScrapper.getLastPage();
    }

}

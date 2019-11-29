package pl.krakow.uek.wzisn2.etl.advert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krakow.uek.wzisn2.etl.db.DatabaseConnector;
import pl.krakow.uek.wzisn2.etl.scrapper.DetailPageScrapper;
import pl.krakow.uek.wzisn2.etl.scrapper.ListPageScrapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdvertService {
    private final Logger logger = LoggerFactory.getLogger(AdvertService.class);
    private final AdvertRepository advertisementRepository;

    public AdvertService(DatabaseConnector connector) {
        this.advertisementRepository = new AdvertRepository(connector.getDb());
    }

    @Deprecated
    public void extractAllPages(String url) throws IOException {
        logger.info("Processing all pages for URL [{}]", url);
        Integer lastPage = getLastPage(url);

        for (int i = 1; i < lastPage; i++) {
            scrapListPage(url + "?page=" + i);
        }
    }

    public Integer getLastPage(String url) throws IOException {
        ListPageScrapper listPageScrapper = new ListPageScrapper(url);
        return listPageScrapper.getLastPage();
    }

    public void scrapListPage(String url) throws IOException {
        logger.info("Processing list with URL [{}]", url);
        ListPageScrapper listPageScrapper = new ListPageScrapper(url);
        List<String> advertUrls = listPageScrapper.getAdvertUrls()
                .stream()
                .filter(s -> s.startsWith("https://www.olx.pl"))    // remove another providers
                .collect(Collectors.toList());

        for (String link : advertUrls) {
            createOrUpdateAdvertDetail(link);
        }
    }

    private void createOrUpdateAdvertDetail(String url) throws IOException {
        logger.info("Processing advert with URL [{}]", url);
        DetailPageScrapper pageScrapper = new DetailPageScrapper(url);
        String id = pageScrapper.getId();

        Advert advert = new Advert();
        advert.setId(id);
        advert.setUrl(url);
        advert.setPrice(pageScrapper.getPrice());

        if (advertisementRepository.contains(id)) {
            Advert existingAdv = advertisementRepository.get(id);
            advert.setRevision(existingAdv.getRevision());
            advertisementRepository.update(advert);
        } else {
            advertisementRepository.add(advert);
        }
    }

}

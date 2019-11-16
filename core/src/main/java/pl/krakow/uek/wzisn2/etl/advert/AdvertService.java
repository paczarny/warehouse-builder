package pl.krakow.uek.wzisn2.etl.advert;

import pl.krakow.uek.wzisn2.etl.db.DatabaseConnector;
import pl.krakow.uek.wzisn2.etl.scrapper.DetailPageScrapper;
import pl.krakow.uek.wzisn2.etl.scrapper.ListPageScrapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdvertService {
    private final AdvertRepository advertisementRepository;

    public AdvertService(DatabaseConnector connector) {
        this.advertisementRepository = new AdvertRepository(connector.getDb());
    }

    public void scrapListPage(String url) throws IOException {
        ListPageScrapper listPageScrapper = new ListPageScrapper(url);
        List<String> advertUrls = listPageScrapper.getAdvertUrls()
                .stream()
                .filter(s -> s.startsWith("https://www.olx.pl"))    // remove another providers
                .collect(Collectors.toList());

        advertUrls.forEach(this::createOrUpdateAdvertDetail);
    }

    public void createOrUpdateAdvertDetail(String url) {
        DetailPageScrapper pageScrapper = null;
        try {
            pageScrapper = new DetailPageScrapper(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

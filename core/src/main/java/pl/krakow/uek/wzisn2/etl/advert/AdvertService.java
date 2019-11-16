package pl.krakow.uek.wzisn2.etl.advert;

import pl.krakow.uek.wzisn2.etl.db.DatabaseConnector;
import pl.krakow.uek.wzisn2.etl.scrapper.PageScrapper;

import java.io.IOException;

public class AdvertService {
    private final AdvertRepository advertisementRepository;

    public AdvertService(DatabaseConnector connector) {
        this.advertisementRepository = new AdvertRepository(connector.getDb());
    }

    public Advert createOrUpdateAdvertForPage(String url) throws IOException {
        PageScrapper pageScrapper = new PageScrapper(url);
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
        return advert;
    }

}

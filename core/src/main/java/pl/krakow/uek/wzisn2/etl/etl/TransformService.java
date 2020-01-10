package pl.krakow.uek.wzisn2.etl.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krakow.uek.wzisn2.etl.advert.Advert;
import pl.krakow.uek.wzisn2.etl.scrapper.DetailPageScrapper;

import java.util.ArrayList;
import java.util.List;

public class TransformService {
    private final Logger log = LoggerFactory.getLogger(TransformService.class);

    /**
     * Transforms adverts from raw page to advert entity
     *
     * @param pages pages to process
     * @return list of processed adverts
     */
    public List<Advert> transformPages(List<DetailPageScrapper> pages) {
        List<Advert> adverts = new ArrayList<>();

        for (int i = 0; i < pages.size(); i++) {
            Advert advert = transformAdvert(pages.get(i));
            adverts.add(advert);
            log.info("Advert {}/{} transformed", i + 1, adverts.size());
        }
        log.info("All adverts transformed");
        return adverts;
    }

    private Advert transformAdvert(DetailPageScrapper pageScrapper) {
        Advert advert = new Advert();
        advert.setId(pageScrapper.getId());
        advert.setUrl(pageScrapper.getUrl());
        advert.setPrice(pageScrapper.getPrice());
        advert.setArea(pageScrapper.getArea());
        advert.setMarket(pageScrapper.getMarket());
        advert.setConstructionType(pageScrapper.getConstructionType());
        advert.setUsername(pageScrapper.getUsername());

        return advert;
    }
}

package pl.krakow.uek.wzisn2.etl.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krakow.uek.wzisn2.etl.advert.Advert;
import pl.krakow.uek.wzisn2.etl.advert.AdvertRepository;

import java.util.List;

public class LoadService {
    private final Logger log = LoggerFactory.getLogger(LoadService.class);
    private final AdvertRepository advertisementRepository;

    public LoadService(AdvertRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    /**
     * Loads adverts into database
     *
     * @param adverts list of adverts to load
     */
    public void load(List<Advert> adverts) {
        for (int i = 0; i < adverts.size(); i++) {
            loadAdvert(adverts.get(i));
            log.info("Advert {}/{} processed", i + 1, adverts.size());
        }
        log.info("All adverts loaded");
    }

    private void loadAdvert(Advert advert) {
        String id = advert.getId();
        if (advertisementRepository.contains(id)) {
            Advert existingAdv = advertisementRepository.get(id);
            advert.setRevision(existingAdv.getRevision());
            advertisementRepository.update(advert);
            log.info("Advert " + id + " updated");
        } else {
            advertisementRepository.add(advert);
            log.info("Advert " + id + " added");
        }
    }
}

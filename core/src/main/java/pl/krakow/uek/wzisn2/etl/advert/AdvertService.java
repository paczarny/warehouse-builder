package pl.krakow.uek.wzisn2.etl.advert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krakow.uek.wzisn2.etl.helper.AdvertCsvWriter;

import java.io.File;
import java.util.List;

public class AdvertService {
    private final Logger logger = LoggerFactory.getLogger(AdvertService.class);
    private final AdvertRepository advertisementRepository;
    private AdvertCsvWriter csvWriter;

    public AdvertService(AdvertRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
        this.csvWriter = new AdvertCsvWriter();
    }

    public List<Advert> findAll() {
        return advertisementRepository.getAll();
    }

    public void delete(Advert advert) {
        advertisementRepository.remove(advert);
    }

    public void exportAllData(String[] header, String path_) throws Exception {
        File file = new File(path_);
        this.csvWriter.writeHeader(header, path_);
        this.csvWriter.writeAll(this.advertisementRepository.getAll(), file.getPath());
    }

    public void exportSingly(String[] header, String path_) throws Exception {
        List<Advert> adverts = this.findAll();
        String name = "";
        for (Advert advert : adverts) {
            name = path_ + advert.getId() + ".csv";
            this.csvWriter.writeHeader(header, name);
            this.csvWriter.writeOne(advert.getAttributesArray(), name);
        }
    }
}

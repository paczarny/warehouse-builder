package pl.krakow.uek.wzisn2.etl.helper;

import com.opencsv.CSVWriter;
import pl.krakow.uek.wzisn2.etl.advert.Advert;
import java.io.FileWriter;
import java.util.List;

public class AdvertCsvWriter {
    public void writeHeader(String[] stringArray, String path) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(path));
        writer.writeNext(stringArray);
        writer.close();
    }

    public void writeOne(String[] advert, String path) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(path, true));
        writer.writeNext(advert);
        writer.close();
    }

    public void writeAll(List<Advert> advertArray, String path) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(path, true));
        for (var advert : advertArray) {
            writer.writeNext(advert.getAttributesArray());
        }
        writer.close();
    }
}

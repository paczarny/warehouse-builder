package pl.krakow.uek.wzisn2.etl;

import pl.krakow.uek.wzisn2.etl.advert.AdvertService;
import pl.krakow.uek.wzisn2.etl.db.DatabaseConnector;

import java.io.IOException;

public class ModuleRunner {

    public void run() throws IOException {
        DatabaseConnector connector = new DatabaseConnector();
        connector.start();

        String url = "https://www.olx.pl/oferta/kamienica-kazimierz-CID3-IDyikSE.html#766a1c8d8c;promoted";
        new AdvertService(connector).createOrUpdateAdvertForPage(url);
    }
}

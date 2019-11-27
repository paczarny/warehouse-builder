package pl.krakow.uek.wzisn2.etl;

import pl.krakow.uek.wzisn2.etl.advert.AdvertService;
import pl.krakow.uek.wzisn2.etl.db.DatabaseConnector;

import java.io.IOException;
import java.net.MalformedURLException;

public class ModuleRunner {
    private DatabaseConnector connector;

    public ModuleRunner() throws MalformedURLException {
        connector = new DatabaseConnector();
        connector.start();
    }

    public void run() throws IOException {
        String url = "https://www.olx.pl/nieruchomosci/domy/krakow/";
        var advertService = new AdvertService(connector);
        advertService.extractAllPages(url);

        System.out.println("End");
    }
}

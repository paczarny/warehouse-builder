package pl.krakow.uek.wzisn2.etl.scrapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebsiteDownloader {

    public Document getDocument(String address) throws IOException {
        Connection.Response response = this.prepareConnection(address);
        if (response.statusCode() >= 300) return null;
        return response.parse();
    }

    private Connection.Response prepareConnection(String address) throws IOException {
        return Jsoup.connect(address).execute();
    }
}

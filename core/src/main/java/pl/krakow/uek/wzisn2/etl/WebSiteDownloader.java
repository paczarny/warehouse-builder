package pl.krakow.uek.wzisn2.etl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebSiteDownloader {
    private final String address;
    private URL url;
    private InputStream is = null;
    private BufferedReader br;

    public WebSiteDownloader(String address) {
        this.address = address;
    }

    StringBuilder getHTML() {
        StringBuilder html = new StringBuilder();
        try {
            this.url = new URL(address);
            this.is = url.openStream();
            this.br = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = this.br.readLine()) != null) {
                if(!line.isBlank()) {
                    html.append(line);
                    html.append(System.getProperty("line.separator"));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (this.is != null) {
                try {
                    this.is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return html;
    }

    public Document getDocument() throws IOException {
        Document doc = this.prepareConnection(this.address).get();
        return doc;
    }

    private Connection prepareConnection(String address) {
        Connection conn = Jsoup.connect(address);
        return conn;
    }
}

package pl.krakow.uek.wzisn2.etl;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;


import java.io.IOException;

import static org.junit.Assert.*;

public class WebSiteDownloaderTest {

    @Test
    public void getDocument() throws IOException {
        WebSiteDownloader web = new WebSiteDownloader("https://www.olx.pl");
        Document doc = web.getDocument();
        assertNotNull(doc.title());
    }
}
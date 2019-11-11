package pl.krakow.uek.wzisn2.etl;

import org.jsoup.nodes.Document;
import java.io.IOException;

public class ModuleRunner {

    public void run() throws IOException {
        WebSiteDownloader web = new WebSiteDownloader("https://www.olx.pl/nieruchomosci/mieszkania/wynajem/krakow/");
        Document doc = web.getDocument();
    }
}

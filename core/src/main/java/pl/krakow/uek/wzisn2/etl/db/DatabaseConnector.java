package pl.krakow.uek.wzisn2.etl.db;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

public class DatabaseConnector {
    private final static Logger log = LoggerFactory.getLogger(DatabaseConnector.class);
    private CouchDbConnector db;

    public void start() throws MalformedURLException {
        log.info("Connecting to DB");
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        db = new StdCouchDbConnector("scrapper_db", dbInstance);
        db.createDatabaseIfNotExists();
        log.debug("Connected successfully");
    }

    public CouchDbConnector getDb() {
        return db;
    }
}

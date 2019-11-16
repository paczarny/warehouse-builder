package pl.krakow.uek.wzisn2.etl.advert;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class AdvertRepository extends CouchDbRepositorySupport<Advert> {
    public AdvertRepository(CouchDbConnector connector) {
        super(Advert.class, connector);
    }
}

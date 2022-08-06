package org.tyniest.shortner.facade;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.tyniest.shortner.store.Store;
import org.tyniest.shortner.utils.UuidHelper;

import io.smallrye.mutiny.Uni;

@Path("/_")
public class HttpFacade {

    private final Store<String> store;

    public HttpFacade(final Store<String> store) {
        this.store = store;
    }

    @POST
    public Uni<String> createKey(@QueryParam("url") final String url) {
        final var key = UuidHelper.getCompactUUID4();
        return store.set(key, url, 0, true)
            .map(oldKey -> oldKey == null ? key : oldKey);
    }

    @DELETE
    public Uni<Void> deleteUrl(@QueryParam("url") final String url) {
        return store.remove(url);
    }
}

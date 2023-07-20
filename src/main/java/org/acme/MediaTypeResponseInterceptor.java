package org.acme;

import io.quarkus.logging.Log;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

@Priority(5000)
@Provider
public class MediaTypeResponseInterceptor implements WriterInterceptor {

    @Context
    Request request;

    @Context
    UriInfo uriInfo;

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        Log.infof(String.format("Request: %s - %s", request.getMethod(), uriInfo.getPath()));
        Log.infof("Response: %s%n", context.getMediaType());
        // TODO Where to get Response statusCode from?
        if (context.getMediaType() == null) {
            throw new IOException("Oh! MediaType is null");
        }
        context.proceed();
    }
}
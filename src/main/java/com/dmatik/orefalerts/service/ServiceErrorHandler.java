package com.dmatik.orefalerts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class ServiceErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return new DefaultResponseErrorHandler().hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        if (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            // handle 5xx errors
            // raw http status code e.g `500`
            //System.out.println(response.getRawStatusCode());
            log.error(String.valueOf(response.getRawStatusCode()));

            // http status code e.g. `500 INTERNAL_SERVER_ERROR`
            log.error(String.valueOf(response.getStatusCode()));

        } else if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle 4xx errors
            // raw http status code e.g `404`
            log.error(String.valueOf(response.getRawStatusCode()));

            // http status code e.g. `404 NOT_FOUND`
            log.error(String.valueOf(response.getStatusCode()));

            // get response body
            log.error(String.valueOf(response.getBody()));
        }
    }
}

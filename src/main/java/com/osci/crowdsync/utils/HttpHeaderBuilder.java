package com.osci.crowdsync.utils;

import lombok.Builder;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpHeaderBuilder {

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {

        private HttpHeaders httpHeaders;

        public Builder() {
            this.httpHeaders = new HttpHeaders();
        }

        public Builder cacheControl(String cacheControl) {
            this.httpHeaders.setCacheControl(cacheControl);
            return this;
        }

        public Builder mediaType(MediaType mediaType) {
            this.httpHeaders.setContentType(mediaType);
            return this;
        }

        public Builder basicAuth(String username, String password) {
            this.httpHeaders.setBasicAuth(username, password);
            return this;
        }

        public Builder add(String headerName, String headerValue) {
            this.httpHeaders.add(headerName, headerValue);
            return this;
        }

        public HttpHeaders build() {
            return this.httpHeaders;
        }
    }
}

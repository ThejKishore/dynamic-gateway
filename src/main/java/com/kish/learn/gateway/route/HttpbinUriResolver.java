package com.kish.learn.gateway.route;

import com.kish.learn.gateway.route.model.RoutesItem;
import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.web.servlet.function.ServerRequest;

import java.net.URI;
import java.util.function.Function;

public class HttpbinUriResolver implements Function<ServerRequest,ServerRequest> {

    private final RoutesItem routesItem;

    public HttpbinUriResolver(RoutesItem routesItem) {
        this.routesItem = routesItem;
    }

    @Override
    public ServerRequest apply(ServerRequest serverRequest) {
        URI uri = URI.create(serverRequest.path());
        MvcUtils.setRequestUrl(serverRequest,uri);
        return serverRequest;
    }
}

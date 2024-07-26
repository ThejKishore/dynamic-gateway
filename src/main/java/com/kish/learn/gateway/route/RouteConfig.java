package com.kish.learn.gateway.route;

import com.kish.learn.gateway.route.model.FilterItem;
import com.kish.learn.gateway.route.model.RoutesItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.List;

import static org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions.removeResponseHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.header;
import static org.springframework.web.servlet.function.RequestPredicates.*;

@Configuration
public class RouteConfig {

    @Autowired
    private RouteRepo routeRepo;

    @Bean
    @RefreshScope
    public RouterFunction<ServerResponse> routes() {
        if (routeRepo.findAll().isEmpty()) {
            RoutesItem routesItem = new RoutesItem();
            routesItem.setPath("/httpbin/get");
            routesItem.setRouteId("httpbin");
            routesItem.setMethod(HttpMethod.GET);
            routesItem.setUri("https://httpbin.org");
            routesItem.setFilters(List.of(new FilterItem()));
            routeRepo.save(routesItem);
        }

        RouterFunctions.Builder route = route();
        routeRepo.findAll().stream()
                .map(this::createRoute)
                .forEach(route::add);
        return route.build();
    }

    private RouterFunction<ServerResponse> createRoute(RoutesItem routesItem) {
        return getRouterFunctionSetBeforeAndAfterFilter(routesItem);
    }

    private RouterFunction<ServerResponse> getRouterFunctionSetBeforeAndAfterFilter(RoutesItem routesItem) {
        return setRoutePatternAndRequestPredicate(routesItem)
                .before(BeforeFilterFunctions.stripPrefix(1))
                .after(removeResponseHeader("X-Response-foo"))
                .build();
    }

    private RouterFunctions.Builder setRoutePatternAndRequestPredicate(RoutesItem routesItem) {
        String identifier = routesItem.getRouteId();
        return route(identifier)
                .route(setRequestPredicate(routesItem),http(URI.create(routesItem.getUri())));
    }

    private RequestPredicate setRequestPredicate(RoutesItem routesItem) {
        return method(routesItem.getMethod())
                .and(path(routesItem.getPath()));
    }

}

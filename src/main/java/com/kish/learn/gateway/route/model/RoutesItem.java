package com.kish.learn.gateway.route.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;

@Document(collection = "routes")
@Data
@Builder
public class RoutesItem{
	@JsonProperty(value = "id")
	private String routeId;
	@Id
	private String mongoId;
	@Singular
	private List<PredicatesItem> predicates;
	@Singular
	private List<FilterItem> filters;
	private String uri;
	private String path;
	private Metadata metadata;
	private HttpMethod method;
}
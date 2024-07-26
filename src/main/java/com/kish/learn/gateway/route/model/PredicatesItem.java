package com.kish.learn.gateway.route.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PredicatesItem{
	private Map<String,String> args;
	private String name;
	private PredicateType predicateType;
}
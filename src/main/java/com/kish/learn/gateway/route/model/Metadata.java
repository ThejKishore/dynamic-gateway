package com.kish.learn.gateway.route.model;

import lombok.Data;

import java.util.Map;

@Data
public class Metadata{
	private Map<String,String> compositeObject;
	private String optionName;
	private int iAmNumber;
}
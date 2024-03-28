package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		// MappingJacksonValue

		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		MappingJacksonValue mappingJacksonValue = filteringLogic(someBean);
		return mappingJacksonValue;
	}

	private MappingJacksonValue filteringLogic(SomeBean someBean) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		filteringLogic(mappingJacksonValue);
		return mappingJacksonValue;
	}

	private void filteringLogic(MappingJacksonValue mappingJacksonValue) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		FilterProvider filters=new SimpleFilterProvider().addFilter("SomeBeanFilter", filter ) ;
		mappingJacksonValue.setFilters(filters);
	}

	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		List<SomeBean> asList = Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value4", "value5", "value6"));
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(asList);
		filteringLogic(mappingJacksonValue);
		return mappingJacksonValue;
	}
}

package com.cuponera.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class LookBookFilters extends BaseModel {
	
	@JsonProperty("FiltersLastUpdate")
	private Date filtersLastUpdate;
	
	@JsonProperty("Filters")
	private List<LookBookFilter> filters;

	public List<LookBookFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<LookBookFilter> filters) {
		this.filters = filters;
	}

	public Date getFiltersLastUpdate() {
		return filtersLastUpdate;
	}

	public void setFiltersLastUpdate(Date filtersLastUpdate) {
		this.filtersLastUpdate = filtersLastUpdate;
	}
	
	
}

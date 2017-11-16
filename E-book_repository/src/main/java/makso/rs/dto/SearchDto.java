package makso.rs.dto;

import java.util.List;

import makso.rs.udd.searcher.SearchField;

public class SearchDto {
	
	private List<SearchField> fields;

	public List<SearchField> getFields() {
		return fields;
	}

	public void setFields(List<SearchField> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "SearchDto [fields=" + fields + "]";
	}

	

}

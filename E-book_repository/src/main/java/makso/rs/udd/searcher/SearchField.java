package makso.rs.udd.searcher;

public class SearchField {

	private String type;
	private String field;
	private String value;
	private String occur;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOccur() {
		return occur;
	}
	public void setOccur(String occur) {
		this.occur = occur;
	}
	@Override
	public String toString() {
		return "SearchField [type=" + type + ", field=" + field + ", value=" + value + ", occur=" + occur + "]";
	}
	
	
	
}

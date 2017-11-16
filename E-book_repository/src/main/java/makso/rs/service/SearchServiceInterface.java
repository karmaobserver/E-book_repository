package makso.rs.service;

import java.util.List;

import org.apache.lucene.search.BooleanQuery;

import makso.rs.dto.SearchDto;
import makso.rs.model.Ebook;
import makso.rs.udd.model.RequiredHighlight;
import makso.rs.udd.searcher.SearchField;

import org.apache.lucene.search.BooleanClause.Occur;



public interface SearchServiceInterface {
	
	public List<Ebook> search(SearchDto searchDto);
	public Occur getOccur(String value);
	void addToQuery(BooleanQuery bquery, SearchField f);
	void addToQuery(BooleanQuery bquery, List<RequiredHighlight> requiredHighlights,SearchField f);

}

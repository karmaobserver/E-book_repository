package makso.rs.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause.Occur;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import makso.rs.dto.SearchDto;
import makso.rs.model.Ebook;
import makso.rs.udd.model.RequiredHighlight;
import makso.rs.udd.model.SearchType;
import makso.rs.udd.query.QueryBuilder;
import makso.rs.udd.searcher.InformationRetriever;
import makso.rs.udd.searcher.SearchField;

@Service
public class SearchService implements SearchServiceInterface {
	
	private static Log4JLogger logger = new Log4JLogger(SearchService.class.getName());

	@Override
	public List<Ebook> search(SearchDto searchDto) {
		
		BooleanQuery bquery = new BooleanQuery();
		List<RequiredHighlight> requiredHighlights = new ArrayList<>();
		
		searchDto.getFields()
		.stream()
		.forEach(f ->  addToQuery(bquery,requiredHighlights,f));

		List<Ebook> results = InformationRetriever.getData(bquery,requiredHighlights);
		
		logger.info(results);
		
		return results;
	}

	@Override
	public Occur getOccur(String value) {
		
		if(value.equals("MUST")){
			return Occur.MUST;
		}else if(value.equals("MUST NOT")){
			return Occur.MUST_NOT;
		}else{
			return Occur.SHOULD;
		}
	}

	@Override
	public void addToQuery(BooleanQuery bquery, SearchField f) {
		
		if(!(f.getValue() == null || f.getValue().equals(""))){
			Query query = null;
			try {
				query = QueryBuilder.buildQuery(SearchType.getType(f.getType()), f.getField(), f.getValue());
			} catch (IllegalArgumentException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bquery.add(query, getOccur(f.getOccur()));
		}
		
	}

	@Override
	public void addToQuery(BooleanQuery bquery, List<RequiredHighlight> requiredHighlights, SearchField f) {
		
		if(!(f.getValue() == null || f.getValue().equals(""))){
			Query query = null;
			RequiredHighlight rh = new RequiredHighlight(f.getField(),f.getValue(),null);
			try {
				query = QueryBuilder.buildQuery(SearchType.getType(f.getType()), f.getField(), f.getValue());
			} catch (IllegalArgumentException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bquery.add(query, getOccur(f.getOccur()));
			requiredHighlights.add(rh);
		}
		
	}

}

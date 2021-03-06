package makso.rs.udd.query;

import java.util.StringTokenizer;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

import makso.rs.udd.analyzer.SerbianAnalyzer;
import makso.rs.udd.analyzer.filer.CyrillicLatinConverter;
import makso.rs.udd.model.SearchType;
import makso.rs.udd.model.SearchType.Type;


public class QueryBuilder {
	
	private static final Version matchVersion = Version.LUCENE_4_9;
	private static SerbianAnalyzer analyzer = new SerbianAnalyzer(matchVersion);
	private static int maxEdits = 2;
	
	public static int getMaxEdits(){
		return maxEdits;
	}
	
	public static void setMaxEdits(int maxEdits){
		QueryBuilder.maxEdits = maxEdits;
	}
	
	public static Query buildQuery(SearchType.Type queryType, String field, String value) throws IllegalArgumentException, ParseException{
		QueryParser parser = new QueryParser(matchVersion, field, analyzer);
		String errorMessage = "";
		if(field == null || field.equals("")){
			errorMessage += "Field not specified";
		}
		if(value == null){
			if(!errorMessage.equals("")) errorMessage += "\n";
			errorMessage += "Value not specified";
		}
		if(!errorMessage.equals("")){
			throw new IllegalArgumentException(errorMessage);
		}
		
		Term term = null;
		
//		(Fuzzy, Wildcard, Prefix, Regex, Range) are not analyzed, which can be problematic when indexing with a stemmer.
		if(queryType.equals(Type.fuzzy)){
			term = new Term(field, CyrillicLatinConverter.cir2lat(value.trim()));
		}else{
			term = new Term(field, value.trim());
		}
		
		Query query = null;
		if(queryType.equals(Type.regular)){
			query = new TermQuery(term);
		}else if(queryType.equals(Type.fuzzy)){
			query = new FuzzyQuery(term, maxEdits);
		}else if(queryType.equals(Type.prefix)){
			query = new PrefixQuery(term);
		}else if(queryType.equals(Type.range)){
			StringTokenizer st = new StringTokenizer(value.trim());
			if(st.countTokens() < 2){
				query = new TermQuery(new Term(field, value.trim()));
			}else{
				query = new TermRangeQuery(field, new BytesRef(st.nextToken()), new BytesRef(st.nextToken()), true, true);
			}
		}else{
			StringTokenizer st = new StringTokenizer(value.trim());
			PhraseQuery pq = new PhraseQuery();
			while(st.hasMoreTokens()){
				pq.add(new Term(field, st.nextToken()));
			}
			query = pq;
		}
		
		return parser.parse(query.toString(field));
	}
	

}

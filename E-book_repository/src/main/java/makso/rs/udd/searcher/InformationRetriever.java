package makso.rs.udd.searcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import makso.rs.model.Ebook;
import makso.rs.repository.CategoryRepository;
import makso.rs.repository.LanguageRepository;
import makso.rs.udd.analyzer.SerbianAnalyzer;
import makso.rs.udd.indexer.IndexManager;
import makso.rs.udd.indexer.UDDIndexer;
import makso.rs.udd.model.RequiredHighlight;


@Component
public class InformationRetriever {

	private static CategoryRepository categoryRepository;
	private static LanguageRepository languageRepository;
	
	private static int maxHits = 10;
	private static final Version matchVersion = Version.LUCENE_4_9;
	private static Analyzer analyzer = new SerbianAnalyzer(matchVersion);

	@Autowired
	private CategoryRepository categoryRepository0;

	@Autowired
	private LanguageRepository languageRepository0;

	@PostConstruct
	public void initStaticRepo() {
		categoryRepository = this.categoryRepository0;
		languageRepository = this.languageRepository0;
	}

	public static List<Ebook> getData(Query query) {
		if (query == null)
			return null;
		List<Document> docs = ResultRetriever.getResults(query);
		List<Ebook> results = new ArrayList<Ebook>();

		String temp;
		Ebook data;

		// DirectoryReader reader;
		// reader =
		// DirectoryReader.open(IndexManager.getIndexer().getIndexDir());
		for (Document doc : docs) {
			data = new Ebook();
			String[] allKeywords = doc.getValues("keyword");
			temp = "";
			for (String keyword : allKeywords) {
				temp += keyword + " ";
			}
			if (!temp.equals("")) {
				temp = temp.substring(0, temp.length() - 1);
			}
			data.setKeywords(temp);

			data.setTitle(doc.get("title"));
			data.setAuthor(doc.get("author"));
			data.setPublicationYear(Integer.valueOf(doc.get("publicationYear")));
			data.setFileName(doc.get("filename"));
			data.setMime(doc.get("mime"));
			data.setCategory(categoryRepository.findCategoryByName(doc.get("cateogry")));
			data.setLanguage(languageRepository.findLanguageByName(doc.get("language")));
	

			String stringId = doc.get("ebookId");
			Long longId = (stringId == null) ? 0 : Long.valueOf(stringId);
			data.setId(longId);

			temp = "";

			results.add(data);
		}
		// reader.close();
		return results;
		// throw new IllegalArgumentException("U prosledjenom direktorijumu ne
		// postoje indeksi ili je direktorijum zakljucan");
	}

	public static List<Ebook> getData(Query query, List<RequiredHighlight> requiredHighlights) {
		if (query == null)
			return null;
		List<Document> docs = ResultRetriever.getResults(query);
		List<Ebook> results = new ArrayList<Ebook>();

		String temp;
		Ebook data;
		Highlighter hl;
		
		DirectoryReader reader;
		try {
			reader = DirectoryReader.open(IndexManager.getIndexer().getIndexDir());
			for (Document doc : docs) {
				data = new Ebook();
				String[] allKeywords = doc.getValues("keyword");
				temp = "";
				for (String keyword : allKeywords) {
					temp += keyword + " ";
				}
				if (!temp.equals("")) {
					temp = temp.substring(0, temp.length() - 1);
				}
				data.setKeywords(temp);

				data.setTitle(doc.get("title"));
				data.setAuthor(doc.get("author"));
				data.setPublicationYear(Integer.valueOf(doc.get("publicationYear")));
				data.setFileName(doc.get("filename"));
				data.setMime(doc.get("mime"));
				data.setCategory(categoryRepository.findCategoryByName(doc.get("cateogry")));
				data.setLanguage(languageRepository.findLanguageByName(doc.get("language")));

				String stringId = doc.get("ebookId");
				Long longId = (stringId == null) ? 0 : Long.valueOf(stringId);
				data.setId(longId);

				temp = "";
				if (requiredHighlights != null) {
					for (RequiredHighlight rh : requiredHighlights) {
						try {
							hl = new Highlighter(new QueryScorer(query, reader, rh.getFieldName()));
							File docFile = new File(doc.get("location"));
							String value = UDDIndexer.getHandler(docFile).getDocument(docFile).get(rh.getFieldName());
							String tempHL = hl.getBestFragment(analyzer, rh.getFieldName(), value);
							//tempHL = tempHL.replace("B>", "b>");
							if (tempHL != null) {
								temp += rh.getFieldName() + ": " + tempHL.trim() + " ... ";
							}
						} catch (Exception e) {

						}
					}
				}
				data.setHighlight(temp);

				results.add(data);
			}
			reader.close();
			return results;
		} catch (IOException e) {

		}

		throw new IllegalArgumentException(
				"U prosledjenom direktorijumu ne postoje indeksi ili je direktorijum zakljucan");
	}

}

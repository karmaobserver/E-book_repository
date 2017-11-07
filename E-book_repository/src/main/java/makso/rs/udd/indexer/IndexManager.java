package makso.rs.udd.indexer;

public class IndexManager {
	
	//true po default-u udd
	private static boolean restartIndex = false;
	private static UDDIndexer indexer = new UDDIndexer(true);
	
	public static UDDIndexer getIndexer(){
		if(indexer == null){
			indexer = new UDDIndexer(restartIndex);
		}
		return indexer;
	}
	
	public static void restart(){
		indexer.restart();
	}

}

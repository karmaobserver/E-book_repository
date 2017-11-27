package makso.rs.udd.indexer;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

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
	
	/*public static void IndexInit() {
		String path = ResourceBundle.getBundle("index").getString("index");
		File indexDirPath = new File(path);
		Directory indexDir;
		try {
			indexDir = new SimpleFSDirectory(indexDirPath);
			DirectoryReader reader = DirectoryReader.open(indexDir);
		} catch (IOException e) {
			getIndexer();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}

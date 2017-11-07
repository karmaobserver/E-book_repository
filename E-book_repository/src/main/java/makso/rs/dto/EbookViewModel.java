package makso.rs.dto;

import org.springframework.web.multipart.MultipartFile;

import makso.rs.model.Ebook;

public class EbookViewModel {

	private MultipartFile file;
	private Ebook ebook;
	
	

	public Ebook getEbook() {
		return ebook;
	}

	public void setEbook(Ebook ebook) {
		this.ebook = ebook;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
		
}

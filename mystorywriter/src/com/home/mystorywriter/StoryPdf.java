package com.home.mystorywriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

public class StoryPdf {
	
	 /** Path to the resulting PDF file. */
    private String title;
	private String begin;
	private String middle;
	private String end;
	
	public StoryPdf(String title, String begin, String middle, String end){
		this.title = title;
		this.begin = begin;
		this.middle = middle;
		this.end = end;
	}
	
	public void createPdf(String filename, String imagestring ){
		
 try {
		File oldfile = new File(filename);
		if(oldfile.exists() && oldfile.isFile()) {
			oldfile.delete();
		}
		
		File newfile = new File(filename);
		FileOutputStream fileout = new FileOutputStream(newfile);
		Document document = new Document();
		Phrase phrase = new Phrase();
        Paragraph paragraph = new Paragraph();
        Paragraph paragraph1 = new Paragraph();
		PdfWriter.getInstance(document, fileout);
		document.addAuthor("Me");
		document.addTitle(title);
		float indent = 20;
        document.open();
         
		// Add story title, begin, middle and end 
		Chunk chunk = new Chunk(title);
		Font font = new Font(Font.FontFamily.HELVETICA,26,Font.UNDERLINE);
	    chunk.setFont(font);
		phrase.add(chunk);
		paragraph.add(phrase);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.add("\n\n\n");
		document.add(paragraph);
		paragraph1.setAlignment(Element.ALIGN_JUSTIFIED);
		paragraph1.setIndentationLeft(indent);
		paragraph1.add(begin);
		paragraph1.add("\n\n");
		paragraph1.add(middle);
		paragraph1.add("\n\n");
		paragraph1.add(end);
		paragraph1.add("\n\n");
		document.add(paragraph1);
		
		//Add image
		Image image1 = Image.getInstance(imagestring);
		//if you would have a chapter indentation
		image1.setAlignment(Image.LEFT);
		image1.scaleAbsolute(150f, 150f);
	   
		document.add(image1);
		
		//closing the document will flush out the content to the file.No more edits possible after this.
		document.close();
				        
		} catch (DocumentException e1) {
			
			e1.printStackTrace();
		}catch (FileNotFoundException e1){
			
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
	
	


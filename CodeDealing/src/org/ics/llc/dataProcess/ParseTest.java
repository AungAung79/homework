package org.ics.llc.dataProcess;

import java.io.IOException;
import java.text.ParseException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ParseTest {

	public static void main(String[] args) throws SAXException, IOException, ParseException {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		
		reader.setContentHandler(new PostsHandler4Words());
		reader.parse("raw-data/posts.xml");
		
		System.out.println(PostsHandler4Words.total+"\t");
		System.out.println(PostsHandler4Words.code);
		
//		BufferedReader brtrain = new BufferedReader(new InputStreamReader(new FileInputStream("posts.xml")));
//		String line;
//		int linenum=0;
//		while((line=brtrain.readLine()) != null)
//		{
//			System.out.println(line);
//			linenum++;
//			if(linenum>400)
//				break;
//		}
	}
}

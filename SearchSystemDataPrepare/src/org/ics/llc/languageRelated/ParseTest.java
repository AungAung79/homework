package org.ics.llc.languageRelated;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ParseTest {

	public static void main(String[] args) throws SAXException, IOException, ParseException {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		
		reader.setContentHandler(new PostsHandler4Words());
		reader.parse("/Users/jimmy/StackOverflow/raw-data/posts.xml");
		
		System.out.print(PostsHandler4Words.total+"\t"+ PostsHandler4Words.languageNum + "\t" + PostsHandler4Words.code + "\t");
		System.out.println(PostsHandler4Words.languageCode);
		
		HashMap<LanguageType, Integer> lanTemp = PostsHandler4Words.lanCount;
		Iterator<Entry<LanguageType, Integer>> iterator = lanTemp.entrySet().iterator();
		while(iterator.hasNext())
		{
			Map.Entry<LanguageType, Integer> entry = iterator.next();
			LanguageType ty = entry.getKey();
			String dir = ty.toString();
			int in = entry.getValue();
			System.out.println(dir + ":" + in);
		}
		
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

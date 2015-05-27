package org.ics.llc.dataProcess;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ParseTest {

	public static void main(String[] args) throws SAXException, IOException, ParseException {
//		XMLReader reader = XMLReaderFactory.createXMLReader();
//		
//		reader.setContentHandler(new PostsHandler4Words());
//		reader.parse("raw-data/posts.xml");
		
		BufferedReader brtrain = new BufferedReader(new InputStreamReader(new FileInputStream("parsed-data/CountTags.txt")));
		FileOutputStream outCount = new FileOutputStream("parsed-data/Count.txt");
		PrintStream psCount = new PrintStream(outCount);
		String line = brtrain.readLine();
		while((line = brtrain.readLine()) != null)
		{
			String[] lStrings = line.split("\t");
			if(lStrings.length == 8)
				psCount.println(lStrings[1] + "\t" + lStrings[5] + "\t" + lStrings[6] + "\t" + lStrings[7]);
		}
	}
}

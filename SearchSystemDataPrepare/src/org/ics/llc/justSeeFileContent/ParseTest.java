package org.ics.llc.justSeeFileContent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class ParseTest {

	public static void main(String[] args) throws IOException {
		
		BufferedReader brtrain = new BufferedReader(new InputStreamReader(
				new FileInputStream("/Users/jimmy/StackOverflow/raw-data/posts.xml")));
		
		PrintWriter pw = new PrintWriter("justSeeSee.xml");
		String line;
		int linenum=0;
		while((line=brtrain.readLine()) != null)
		{
			linenum++;
			if(linenum < 14000)
				continue;
			pw.println(line);
			//System.out.println(line);
			if(linenum > 25000)
				break;
		}
	}
}

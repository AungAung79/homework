package org.ics.llc.TokenRelevance;

import java.io.PrintWriter;
import java.util.HashSet;

public class PythonKeyword {
	HashSet<String> keyword = new HashSet<String>();
	
	public PythonKeyword()
	{
		String word = "and elif is global as in if from raise for except finally print import pass return "
				+"exec else break not with class assert yield try while continue del or def lambda "
				+"nonlocal|10 None True False";
		
		String built_in = "Ellipsis NotImplemented";
		
		String other = "def class";
		
		String[] sp = word.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			keyword.add(sp[i].toLowerCase());
		}
		
		sp = built_in.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			keyword.add(sp[i].toLowerCase());
		}
		
		sp = other.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			keyword.add(sp[i].toLowerCase());
		}
	}
	
	public int getRelevance(String s)
	{
		String[] sp = s.split(" ");
		int relevance = 0;
		for(int i = 0; i < sp.length; i++)
		{
			if(keyword.contains(sp[i]))
				relevance++;
		}
		return relevance;
	}
	
	public void printKeyword()
	{
		try {
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Token/pythonkeyword.txt");
			for (String s : keyword) {
				pw.println(s);
			}
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

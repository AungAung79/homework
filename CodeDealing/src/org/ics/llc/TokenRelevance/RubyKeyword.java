package org.ics.llc.TokenRelevance;

import java.io.PrintWriter;
import java.util.HashSet;

public class RubyKeyword {
	HashSet<String> keyword = new HashSet<String>();
	
	public RubyKeyword()
	{
		String word = "and false then defined module in return redo if BEGIN retry end for true self when "
				+"next until do begin unless END rescue nil else break undef not super class case "
				+"require yield alias while ensure elsif or include attr_reader attr_writer attr_accessor";
		
		String other = "class module def";
		
		String[] sp = word.split(" ");
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
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Token/rubykeyword.txt");
			for (String s : keyword) {
				pw.println(s);
			}
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

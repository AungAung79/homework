package org.ics.llc.TokenRelevance;

import java.io.PrintWriter;
import java.util.HashSet;

public class CSharpKeyword {
	HashSet<String> keyword = new HashSet<String>();
	
	public CSharpKeyword()
	{
		String word = "abstract as base bool break byte case catch char checked const continue decimal dynamic "
				+"default delegate do double else enum event explicit extern false finally fixed float "
				+"for foreach goto if implicit in int interface internal is lock long null when "
				+"object operator out override params private protected public readonly ref sbyte "
				+"sealed short sizeof stackalloc static string struct switch this true try typeof "
				+"uint ulong unchecked unsafe ushort using virtual volatile void while async "
				+"protected public private internal "
				+"ascending descending from get group into join let orderby partial select set value var "
				+"where yield";
		
		String other = "if else elif endif define undef warning error line region endregion pragma checksum class interface namespace new return throw await";
		
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
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Token/csharpkeyword.txt");
			for (String s : keyword) {
				pw.println(s);
			}
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

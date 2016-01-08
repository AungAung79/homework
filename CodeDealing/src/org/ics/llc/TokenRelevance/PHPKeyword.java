package org.ics.llc.TokenRelevance;

import java.io.PrintWriter;
import java.util.HashSet;

public class PHPKeyword {
	HashSet<String> keyword = new HashSet<String>();
	
	public PHPKeyword()
	{
		String word = "and include_once list abstract global private echo interface as static endswitch "
				+"array null if endwhile or const for endforeach self var while isset public "
				+"protected exit foreach throw elseif include __FILE__ empty require_once do xor "
				+"return parent clone use __CLASS__ __LINE__ else break print eval new "
				+"catch __METHOD__ case exception default die require __FUNCTION__ "
				+"enddeclare final try switch continue endfor endif declare unset true false "
				+"trait goto instanceof insteadof __DIR__ __NAMESPACE__ "
				+"yield finally";
		
		String other = "php function class interface namespace use";
		
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
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Token/phpkeyword.txt");
			for (String s : keyword) {
				pw.println(s);
			}
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

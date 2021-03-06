package org.ics.llc.languageRelated;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Statistic {
	static HashMap<LanguageType, Integer> lanCount = new HashMap<LanguageType, Integer>();

	public LanguageType decideType(String s)
	{
		if(s.equals("c#"))
		{
			return LanguageType.CSharp;
		}
		else if(s.equals("c++"))
		{
			return LanguageType.CPlusPlus;
		}
		else if(s.equals("c"))
		{
			return LanguageType.C;
		}
		else if(s.equals("css"))
		{
			return LanguageType.CSS;
		}
		else if(s.equals("html"))
		{
			return LanguageType.HTML;
		}
		else if(s.equals("java"))
		{
			return LanguageType.Java;
		}
		else if(s.equals("javascript"))
		{
			return LanguageType.JavaScript;
		}
		else if(s.equals("objective-c"))
		{
			return LanguageType.ObjectiveC;
		}
		else if(s.equals("php"))
		{
			return LanguageType.PHP;
		}
		else if(s.equals("perl"))
		{
			return LanguageType.Perl;
		}
		else if(s.equals("python"))
		{
			return LanguageType.Python;
		}
		else if(s.equals("ruby"))
		{
			return LanguageType.Ruby;
		}
		else if(s.equals("sql"))
		{
			return LanguageType.SQL;
		}
		else {
			return null;
		}
	}
	
	public void done()
	{
		BufferedReader brtrain;
		lanCount.put(LanguageType.C, 0);
		lanCount.put(LanguageType.CPlusPlus, 0);
		lanCount.put(LanguageType.CSharp, 0);
		lanCount.put(LanguageType.CSS, 0);
		lanCount.put(LanguageType.HTML, 0);
		lanCount.put(LanguageType.Java, 0);
		lanCount.put(LanguageType.JavaScript, 0);
		lanCount.put(LanguageType.ObjectiveC, 0);
		lanCount.put(LanguageType.Perl, 0);
		lanCount.put(LanguageType.PHP, 0);
		lanCount.put(LanguageType.Python, 0);
		lanCount.put(LanguageType.Ruby, 0);
		lanCount.put(LanguageType.SQL, 0);
		try {
			brtrain = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/jimmy/StackOverflow/parsed-data/QuestionsLanguageType.txt")));
			String line;
			int linenum=0;
			while((line=brtrain.readLine()) != null)
			{
				linenum++;
				LanguageType typeTemp = decideType(line);
				if(typeTemp != null)
				{
					lanCount.put(typeTemp, lanCount.get(typeTemp) + 1);
				}
			}
			System.out.println(linenum);
			Iterator<Entry<LanguageType, Integer>> iterator = lanCount.entrySet().iterator();
			while(iterator.hasNext())
			{
				Map.Entry<LanguageType, Integer> entry = iterator.next();
				LanguageType ty = entry.getKey();
				String dir = ty.toString();
				int in = entry.getValue();
				System.out.println(dir + ":" + in);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void choose()
	{
		BufferedReader brtrain;
		lanCount.put(LanguageType.C, 0);
		lanCount.put(LanguageType.CPlusPlus, 0);
		lanCount.put(LanguageType.CSharp, 0);
		lanCount.put(LanguageType.CSS, 0);
		lanCount.put(LanguageType.HTML, 0);
		lanCount.put(LanguageType.Java, 0);
		lanCount.put(LanguageType.JavaScript, 0);
		lanCount.put(LanguageType.ObjectiveC, 0);
		lanCount.put(LanguageType.Perl, 0);
		lanCount.put(LanguageType.PHP, 0);
		lanCount.put(LanguageType.Python, 0);
		lanCount.put(LanguageType.Ruby, 0);
		lanCount.put(LanguageType.SQL, 0);
		try {
			brtrain = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/jimmy/StackOverflow/parsed-data/QuestionsLanguageType.txt")));
			String line;
			int linenum=0;
			while((line=brtrain.readLine()) != null)
			{
				linenum++;
				if(linenum <= (459393 - (int)(459393 * 0.1)))
					continue;
				LanguageType typeTemp = decideType(line);
				if(typeTemp != null)
				{
					lanCount.put(typeTemp, lanCount.get(typeTemp) + 1);
				}
			}
			System.out.println(linenum);
			Iterator<Entry<LanguageType, Integer>> iterator = lanCount.entrySet().iterator();
			while(iterator.hasNext())
			{
				Map.Entry<LanguageType, Integer> entry = iterator.next();
				LanguageType ty = entry.getKey();
				String dir = ty.toString();
				int in = entry.getValue();
				System.out.println(dir + ":" + in);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Statistic st = new Statistic();
//		st.done();
		st.choose();
	}
}

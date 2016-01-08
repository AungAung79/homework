package org.ics.llc.TokenRelevance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageDecide {
	String path = "/Users/jimmy/StackOverflow/parsed-data/code/";
	CKeyword ck = new CKeyword();
	CPPKeyword cppk = new CPPKeyword();
	CSharpKeyword csk = new CSharpKeyword();
	CSSKeyword cssk = new CSSKeyword();
	HtmlKeyword htmlk = new HtmlKeyword();
	JavaKeyword javak = new JavaKeyword();
	JavaScriptKeyword jsk = new JavaScriptKeyword();
	ObjectiveCKeyword objck = new ObjectiveCKeyword();
	PerlKeyword perlk = new PerlKeyword();
	PHPKeyword phpk = new PHPKeyword();
	PythonKeyword pyk = new PythonKeyword();
	RubyKeyword rubyk = new RubyKeyword();
	SQLKeyword sqlk = new SQLKeyword();
	
	HashMap<String, Integer> LanScore = new HashMap<String, Integer>();
	HashMap<Integer, String> LanDecide = new HashMap<Integer, String>();
	
	int fileNum = 0;
	int fileRightNum = 0;
	
	public void getFile()
	{
		try {
			File dec = new File(path);
			if(dec.isDirectory())
			{
				File[] files = dec.listFiles();
				for(int i = 0; i < files.length; i++)
				{
					File temp = files[i];
					String name = temp.getName();
					if(!name.startsWith("Questions_"))
						continue;
					fileNum++;
					int firstIndex = name.indexOf("Questions_");
					int lastIndex = name.indexOf("_code.txt");
					int num = Integer.parseInt(name.substring(firstIndex + 10, lastIndex));
//					if(num != 2)
//						continue;
				
					BufferedReader br = new BufferedReader(new FileReader(temp));
					String code = "";
					String line;
					while((line = br.readLine()) != null)
					{
						code += " " + line;
					}
					br.close();
					code.replaceAll("\t", " ");
					LanScore.put("c", ck.getRelevance(code));
					LanScore.put("c++", cppk.getRelevance(code));
					LanScore.put("c#", csk.getRelevance(code));
					LanScore.put("css", cssk.getRelevance(code));
					LanScore.put("html", htmlk.getRelevance(code));
					LanScore.put("java", javak.getRelevance(code));
					LanScore.put("javascript", jsk.getRelevance(code));
					LanScore.put("objective-c", objck.getRelevance(code));
					LanScore.put("perl", perlk.getRelevance(code));
					LanScore.put("php", phpk.getRelevance(code));
					LanScore.put("python", pyk.getRelevance(code));
					LanScore.put("ruby", rubyk.getRelevance(code));
					LanScore.put("sql", sqlk.getRelevance(code));
							
					String lanDe = decide(LanScore);
//					System.out.println(lanDe);
//					if(num == 2)
//						return;
					
					LanDecide.put(num, lanDe);
				}
			}
			
			System.out.println(LanDecide.size());
			
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Method1/PredictLanguageType.txt");
			for(int i = 1; i <= fileNum; i++)
			{
				pw.println(LanDecide.get(i));
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String decide(HashMap<String, Integer> score)
	{
		List<Map.Entry<String, Integer>> infos = new ArrayList<Map.Entry<String,Integer>>(score.entrySet());
		
		Collections.sort(infos, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
				return (o2.getValue().compareTo(o1.getValue()));
			}
		});
		
		//System.out.println(infos.get(0).getKey() + ":" + infos.get(0).getValue());
		String de = infos.get(0).getKey();
		return de;
	}
	
	public void printKeyword()
	{
		ck.printKeyword();
		cppk.printKeyword();
		csk.printKeyword();
		cssk.printKeyword();
		htmlk.printKeyword();
		javak.printKeyword();
		jsk.printKeyword();
		objck.printKeyword();
		perlk.printKeyword();
		phpk.printKeyword();
		pyk.printKeyword();
		rubyk.printKeyword();
		sqlk.printKeyword();
	}
	
	public static void main(String[] args)
	{
		LanguageDecide ld = new LanguageDecide();
		ld.printKeyword();
		ld.getFile();
		System.out.println(ld.fileNum + "\t" + ld.fileRightNum);
	}
}

package org.ics.llc.dataProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class dataFilterForTagNum {
	HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
	HashMap<String, Integer> wordIndex = new HashMap<String, Integer>();
	
	HashSet<String> tagSet = new HashSet<String>();
	HashSet<Integer> missLine = new HashSet<Integer>();
	
	public void dealTag()
	{
		HashMap<String, Integer> countHashMap = new HashMap<String, Integer>();
		File file = new File("parsed-data/QuestionsTags.txt");
		PrintWriter pw;
		try{
			//count tags
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null)
			{
				String[] sp = line.split("\t");
				for(int i = 0; i < sp.length; i++)
				{
					if(countHashMap.containsKey(sp[i]))
					{
						countHashMap.put(sp[i], countHashMap.get(sp[i])+1);
					}
					else {
						countHashMap.put(sp[i], 1);
					}
				}
			}
			br.close();
			
			//sort counts
			List<Map.Entry<String, Integer>> infos = new ArrayList<Map.Entry<String, Integer>>(countHashMap.entrySet());
			Collections.sort(infos, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2){
					return (o2.getValue().compareTo(o1.getValue()));
				}
			});
			
			pw = new PrintWriter("parsed-data/countTag.csv");
			
			for(int i = 0; i < infos.size(); i++)
			{
				String idString= infos.get(i).getKey();
				int count = infos.get(i).getValue();
				pw.println(idString+","+count);
			}
			pw.close();
			
			System.out.println(infos.size());
			
			//remove tags whose counts <= 300
			for(int i = infos.size() - 1; i >= 0; i--)
			{
				int num = infos.get(i).getValue();
				if(num <= 1000)
					infos.remove(i);
			}
			System.out.println(infos.size());
			
			for(int i = 0; i < infos.size(); i++)
			{
				String idString= infos.get(i).getKey();
				tagSet.add(idString);
			}
			System.out.println(tagSet.size());
			
			//remove missed tags and build the missLine set
			br = new BufferedReader(new FileReader(file));
			pw = new PrintWriter("parsed-data/QuestionsTags_removeLowNumTags.txt");
			int linenum = 0;
			while((line = br.readLine()) != null)
			{
				linenum++;
				boolean flag = false;
				String newLine = "";
				String[] sp = line.split("\t");
				for(int i = 0; i < sp.length; i++)
				{
					if(tagSet.contains(sp[i]))
					{
						flag = true;
						newLine += sp[i] + "\t";
					}
				}
				if(flag)
				{
					pw.println(newLine);
				}
				else
				{
					missLine.add(linenum);
				}
			}
			System.out.println(missLine.size());
			br.close();
			pw.close();
			
			//according to the missLine set, remove the questions
			file = new File("parsed-data/QuestionsBody_NLTK.txt");
			br = new BufferedReader(new FileReader(file));
			pw = new PrintWriter("parsed-data/QuestionsBody_removeLowNumTags.txt");
			linenum = 0;
			while((line = br.readLine()) != null)
			{
				linenum++;
				if(!missLine.contains(linenum))
				{
					pw.println(line);
				}
			}
			br.close();
			pw.close();
			
			file = new File("parsed-data/QuestionsTitles_NLTK.txt");
			br = new BufferedReader(new FileReader(file));
			pw = new PrintWriter("parsed-data/QuestionsTitles_removeLowNumTags.txt");
			linenum = 0;
			while((line = br.readLine()) != null)
			{
				linenum++;
				if(!missLine.contains(linenum))
				{
					pw.println(line);
				}
			}
			br.close();
			pw.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		dataFilterForTagNum tn = new dataFilterForTagNum();
		tn.dealTag();
	}
}

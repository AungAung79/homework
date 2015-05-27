package ics.llc.dataProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Process {
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
			
			//remove tags whose counts <= 60
			for(int i = infos.size() - 1; i >= 0; i--)
			{
				int num = infos.get(i).getValue();
				if(num <= 60)
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
			pw = new PrintWriter("parsed-data/QuestionsTagsDealed.txt");
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
			file = new File("parsed-data/QuestionsBody.txt");
			br = new BufferedReader(new FileReader(file));
			pw = new PrintWriter("parsed-data/QuestionsBodyDealed.txt");
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
			
			file = new File("parsed-data/QuestionsTitles.txt");
			br = new BufferedReader(new FileReader(file));
			pw = new PrintWriter("parsed-data/QuestionsTitlesDealed.txt");
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
	
	public void countTag()
	{
		HashMap<String, Integer> countHashMap = new HashMap<String, Integer>();
		File file = new File("parsed-data/QuestionsTagsDealed.txt");
		try {
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
			
			List<Map.Entry<String, Integer>> infos = new ArrayList<Map.Entry<String, Integer>>(countHashMap.entrySet());
			Collections.sort(infos, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2){
					return (o2.getValue().compareTo(o1.getValue()));
				}
			});
			
			PrintWriter pw = new PrintWriter("result/TagDic.txt");
			
			for(int i = 0; i < infos.size(); i++)
			{
				String idString= infos.get(i).getKey();
				//int num = infos.get(i).getValue();
				pw.println(idString);
			}
			pw.close();
			
			System.out.println(infos.size());
			
			for(int i = 0; i < infos.size(); i++)
			{
				String idString= infos.get(i).getKey();
				tagIndex.put(idString, i);
			}
			boolean[] tag = new boolean[infos.size()];
			
			pw = new PrintWriter("result/Doc_Tag.txt");
			
			br = new BufferedReader(new FileReader(file));
			int linenum = 0;
			while((line = br.readLine()) != null)
			{
				linenum++;
				if(linenum % 10000 == 0)
					System.out.println(linenum);
				for(int i = 0; i < infos.size(); i++)
					tag[i] = false;
				String[] sp = line.split("\t");
				for(int i = 0; i < sp.length; i++)
				{
					if(tagIndex.containsKey(sp[i]))
					{
						int index = tagIndex.get(sp[i]);
						tag[index] = true;
					}
				}
				for(int i = 1; i <= infos.size(); i++)
				{
					if(tag[i - 1])
					{
						pw.println(linenum+","+i+",1");
					}
				}
			}
			br.close();
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void countTF()
	{
		HashMap<String, Integer> countHashMap = new HashMap<String, Integer>();
		File file = new File("parsed-data/QuestionsBodyDealed.txt");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			int linenum = 0;
			while((line = br.readLine()) != null)
			{
				linenum++;
				if(linenum % 10000 == 0)
				{
					System.out.println(linenum+" "+countHashMap.size());
				}
				String[] sp = line.split(" ");
				for(int i = 0; i < sp.length; i++)
				{
					if(Stopwords.isStopword(sp[i]) || sp[i].length() >= 10 || !sp[i].matches("[A-Za-z]*"))
						continue;
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
			
			file = new File("parsed-data/QuestionsTitlesDealed.txt");
			br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			linenum = 0;
			while((line = br.readLine()) != null)
			{
				linenum++;
				if(linenum % 10000 == 0)
				{
					System.out.println(linenum+" "+countHashMap.size());
				}
				String[] sp = line.split(" ");
				for(int i = 0; i < sp.length; i++)
				{
					if(Stopwords.isStopword(sp[i]) || sp[i].length() >= 10 || !sp[i].matches("[A-Za-z]*"))
						continue;
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
			
			List<Map.Entry<String, Integer>> infos = new ArrayList<Map.Entry<String, Integer>>(countHashMap.entrySet());
			Collections.sort(infos, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2){
					return (o2.getValue().compareTo(o1.getValue()));
				}
			});
			
			for(int i = infos.size() - 1; i >= 0; i--)
			{
				int num = infos.get(i).getValue();
				if(num <= 100)
					infos.remove(i);
			}
			System.out.println(infos.size());
			
			PrintWriter pw = new PrintWriter("result/WordDic.txt");
			
			for(int i = 0; i < infos.size(); i++)
			{
				String idString= infos.get(i).getKey();
				pw.println(idString);
			}
			pw.close();
			
			System.out.println(infos.size());
			
			for(int i = 0; i < infos.size(); i++)
			{
				String idString= infos.get(i).getKey();
				wordIndex.put(idString, i);
			}
			int[] word = new int[infos.size()];
			
			pw = new PrintWriter("result/Doc_Word.txt");
			
			File file1 = new File("parsed-data/QuestionsBodyDealed.txt");
			File file2 = new File("parsed-data/QuestionsTitlesDealed.txt");
			BufferedReader br1 = new BufferedReader(new FileReader(file1));
			BufferedReader br2 = new BufferedReader(new FileReader(file2));
			String line1;
			String line2;
			linenum = 0;
			while((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null)
			{
				linenum++;
				if(linenum % 10000 == 0)
					System.out.println(linenum);
				for(int i = 0; i < infos.size(); i++)
					word[i] = 0;
				String[] sp1 = line1.split(" ");
				for(int i = 0; i < sp1.length; i++)
				{
					if(wordIndex.containsKey(sp1[i]))
					{
						int index = wordIndex.get(sp1[i]);
						word[index] = word[index] + 1;
					}
				}
				String[] sp2 = line2.split(" ");
				for(int i = 0; i < sp2.length; i++)
				{
					if(wordIndex.containsKey(sp2[i]))
					{
						int index = wordIndex.get(sp2[i]);
						word[index] = word[index] + 1;
					}
				}
				
				for(int i = 1; i <= infos.size(); i++)
				{
					if(word[i - 1] != 0)
					{
						pw.println(linenum+","+i+","+word[i - 1]);
					}
				}
			}
			br1.close();
			br2.close();
			pw.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Process pr = new Process();
		pr.dealTag();
		pr.countTag();
		pr.countTF();
	}
	
}

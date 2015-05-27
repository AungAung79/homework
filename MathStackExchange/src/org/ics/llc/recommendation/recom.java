package org.ics.llc.recommendation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.ics.llc.lda.PathConfig;

public class recom {
	String tag[];
	int Num;
	
	int K;
	int No;
	int index[];
	double sim[];
	
	int RecNum = 20;
	
	HashMap<String, Double> tagPro = new HashMap<String, Double>();
	HashSet<String> tagOrig = new HashSet<String>();
	
	String resPath = PathConfig.LdaResultsPath;
	
	public void init() throws Exception
	{
		BufferedReader tagReader = new BufferedReader(new InputStreamReader(new FileInputStream("parsed-data/QuestionsTags_removeLowNumTags.txt")));
		String line;
		int linenum=0;
		while((line=tagReader.readLine()) != null)
		{
			linenum++;
		}
		Num = linenum;
		tag = new String[Num+1];
		
		tagReader = new BufferedReader(new InputStreamReader(new FileInputStream("parsed-data/QuestionsTags_removeLowNumTags.txt")));
		linenum=1;
		while((line=tagReader.readLine()) != null)
		{
			tag[linenum] = line;
			linenum++;
		}
		
		BufferedReader indexreader = new BufferedReader(new InputStreamReader(new FileInputStream(resPath+"index.txt")));
		linenum=0;
		while((line=indexreader.readLine()) != null)
		{
			String[] lineParts = line.split(":");
			String[] parts = lineParts[1].split("\t");
			K = parts.length;
			linenum++;
		}
		index = new int[K];
		sim = new double[K];
	}
	
	public void Print()
	{
		System.out.println(K);
		System.out.println(Num);
		System.out.println(tag.length);
		System.out.println(tag[4]);
	}
	
	public void deal() throws Exception
	{
		BufferedReader indexreader = new BufferedReader(new InputStreamReader(new FileInputStream(resPath+"index.txt")));
		BufferedReader simreader = new BufferedReader(new InputStreamReader(new FileInputStream(resPath+"sim.txt")));
		BufferedWriter writer = new BufferedWriter(new FileWriter(resPath + "tagRecommended.txt"));
		double globelpercentage = 0;
		double globelrecall = 0;
		double globelfmeasure = 0;
		int recommendNum = 0;
		String line;
		while((line=indexreader.readLine()) != null)
		{
			String[] lineParts = line.split(":");
			No = Integer.valueOf(lineParts[0]);
			String[] parts = lineParts[1].split("\t");
			for(int temp = 0; temp < parts.length; temp++)
			{
				index[temp] = Integer.valueOf(parts[temp]);
			}
			
			line=simreader.readLine();
			lineParts = line.split(":");
			parts = lineParts[1].split("\t");
			for(int temp = 0; temp < parts.length; temp++)
			{
				sim[temp] = Double.valueOf(parts[temp]);
			}
			
			writer.write(No+":");
			//System.out.println(No);
			
			tagPro.clear();
			tagOrig.clear();
			String tagor = tag[No];
			String[] splitParts = tagor.split("\t");
			for(int sptemp = 0; sptemp < splitParts.length; sptemp++)
			{
				String part = splitParts[sptemp];
				if(!tagOrig.contains(part))
					tagOrig.add(part);
			}
			int hitnum = 0;
			
			for(int temp = 0; temp < index.length; temp++)
			{
				String tagString = tag[index[temp]];
//				if(No == 183083)
//				{
//					System.out.print(index[temp]+":");
//					System.out.println(tagString);
//				}
				String[] tagParts = tagString.split("\t");
				for(int ptemp = 0; ptemp < tagParts.length; ptemp++)
				{
					String part = tagParts[ptemp];
					if(tagPro.containsKey(part))
					{
						tagPro.put(part, (tagPro.get(part)+1.0-sim[temp]));
					}
					else {
						tagPro.put(part, (1.0-sim[temp]));
					}
				}
			}
			
			List<Map.Entry<String,Double>> infos = new ArrayList<Map.Entry<String, Double>>(tagPro.entrySet());
			
//			if(No == 183083)
//			{
//				System.out.println(tagPro.size());
//				for(int i=0; i<infos.size();i++)
//				{
//					String idString= infos.get(i).toString();
//					System.out.println(idString);
//				}
//				System.out.println("-----------------------");
//			}
			
			Collections.sort(infos, new Comparator<Map.Entry<String, Double>>() {
				public int compare(Map.Entry<String, Double> o1,Map.Entry<String, Double> o2){
					return (o2.getValue().compareTo(o1.getValue()));
				}
			});
			
			for(int i=0; i<RecNum && i<infos.size();i++)
			{
				String idString= infos.get(i).getKey();
				//System.out.println(idString);
				if(tagOrig.contains(idString))
					hitnum++;
				writer.write(idString+ "\t");
			}
			double hitpercentage = (double)hitnum/RecNum*100.0;
			double hitrecall = (double)hitnum/(tagOrig.size())*100;
			double f_measure;
			if(hitpercentage!=0 || hitrecall!=0)
				f_measure = 2*hitpercentage*hitrecall/(hitpercentage+hitrecall);
			else {
				f_measure = 0;
			}
			globelpercentage += hitpercentage;
			globelrecall += hitrecall;
			globelfmeasure += f_measure;
			recommendNum++;
			writer.write(Double.toString(hitpercentage)+"%\t");
			writer.write(Double.toString(hitrecall)+"%\t");
			writer.write(Double.toString(f_measure));
			writer.write("\n");
		}
		System.out.println(recommendNum);
		globelpercentage = globelpercentage / recommendNum;
		globelrecall = globelrecall / recommendNum;
		globelfmeasure = globelfmeasure / recommendNum;
		writer.write("Average percentage: "+Double.toString(globelpercentage)+"%\t");
		writer.write("Average recall: "+Double.toString(globelrecall)+"%\t");
		writer.write("Average fscore: "+Double.toString(globelfmeasure));
		writer.write("\n");
		
		writer.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		long a=System.currentTimeMillis();
		recom re = new recom();
		re.init();
		re.Print();
		re.deal();
		long b=System.currentTimeMillis();
		System.out.println((b-a)/1000);
	}
}

package recommendation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

public class recom {
	File trainResultFile = new File("train_and_test/topic-term-distributions.csv");
	File termIndex = new File("train_and_test/term-index.txt");
	File labelIndex = new File("train_and_test/label-index.txt");
	
	File testFile = new File("train_and_test/test.csv");
	File testTag = new File("train_and_test/testTag.csv");
	
	HashMap<Integer, String> label = new HashMap<Integer, String>();
	HashMap<String, Integer> term = new HashMap<String, Integer>();
	HashMap<Integer, HashMap<Integer, Double>> distribution = new HashMap<Integer, HashMap<Integer, Double>>();
	
	HashSet<String> missSet = new HashSet<String>();
	
	int RecNum = 10;
	
	public void read()
	{
		String line;
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(labelIndex)));
			line=reader.readLine();
			int linenum=0;
			while(line != null)
			{
				label.put(linenum, line);
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			System.out.println("file over!");
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(termIndex)));
			line=reader.readLine();
			linenum=0;
			while(line != null)
			{
				term.put(line, linenum);
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			System.out.println("file over!");
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(trainResultFile)));
			line=reader.readLine();
			linenum=0;
			while(line != null)
			{
				String[] spStrings = line.split(",");
				for(int i = 0; i < spStrings.length; i++)
				{
					if(distribution.containsKey(i))
					{
						HashMap<Integer, Double> temp = distribution.get(i);
						temp.put(linenum, Double.parseDouble(spStrings[i]));
					}
					else {
						HashMap<Integer, Double> temp = new HashMap<Integer, Double>();
						temp.put(linenum, Double.parseDouble(spStrings[i]));
						distribution.put(i, temp);
					}
				}
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			System.out.println("file over!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void rec()
	{
		String line;
		String lineTag;
		HashSet<String> tagPro = new HashSet<String>();
		double globelpercentage = 0;
		double globelrecall = 0;
		double globelfmeasure = 0;
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(testFile)));
			BufferedReader readerTag=new BufferedReader(new InputStreamReader(new FileInputStream(testTag)));
			BufferedWriter writer = new BufferedWriter(new FileWriter("train_and_test/tagRecommended.txt"));
			line=reader.readLine();
			lineTag=readerTag.readLine();
			int linenum=0;
			while(line != null)
			{
				HashMap<Integer, Double> weight = new HashMap<Integer, Double>();
				for(int i = 0; i < label.size(); i++)
					weight.put(i, 0.0);
				
				tagPro.clear();
				String[] tagStrings = lineTag.split(" ");
				for(int i = 0; i < tagStrings.length; i++)
				{
					tagPro.add(tagStrings[i]);
				}
				int hitnum = 0;
				
				String[] spStrings = line.split(",");
				String textString = spStrings[2];
				int mindex = textString.indexOf("\"");
				int nindex = textString.indexOf("\"",1);
				String actualTextString = textString.substring(mindex+1, nindex);
				String[] sp = actualTextString.split(" ");
				for(int i = 0; i < sp.length; i++)
				{
					String str = sp[i].toLowerCase();
					if(term.containsKey(str))
					{
						int index = term.get(str);
						HashMap<Integer, Double> temp = distribution.get(index);
						for(int j = 0; j < label.size(); j++)
						{
							weight.put(j, weight.get(j)+temp.get(j));
						}
					}
					else {
						System.out.println(str);
						missSet.add(str);
					}
				}
				
				List<Map.Entry<Integer,Double>> infos = new ArrayList<Map.Entry<Integer, Double>>(weight.entrySet());
				
				Collections.sort(infos, new Comparator<Map.Entry<Integer, Double>>() {
					public int compare(Map.Entry<Integer, Double> o1,Map.Entry<Integer, Double> o2){
						return (o2.getValue().compareTo(o1.getValue()));
					}
				});
				
				for(int i=0; i<RecNum && i<infos.size();i++)
				{
					Integer id= infos.get(i).getKey();
					String tag = label.get(id);
					if(tagPro.contains(tag))
						hitnum++;
					writer.write(tag+ "\t");
				}
				
				double hitpercentage = (double)hitnum/RecNum;
				double hitrecall = (double)hitnum/(tagPro.size());
				double f_measure;
				if(hitpercentage!=0 || hitrecall!=0)
					f_measure = 2*hitpercentage*hitrecall/(hitpercentage+hitrecall);
				else {
					f_measure = 0;
				}
				globelpercentage += hitpercentage;
				globelrecall += hitrecall;
				globelfmeasure += f_measure;
				writer.write(Double.toString(hitpercentage)+"\t");
				writer.write(Double.toString(hitrecall)+"\t");
				writer.write(Double.toString(f_measure));
				writer.write("\n");
				
				line=reader.readLine();
				lineTag=readerTag.readLine();
				linenum++;
			}
			reader.close();
			readerTag.close();
			System.out.println("file over!");
			System.out.println(linenum);
			System.out.println((double)missSet.size()/(double)term.size());
			globelpercentage = globelpercentage / linenum;
			globelrecall = globelrecall / linenum;
			globelfmeasure = globelfmeasure / linenum;
			writer.write("Average percentage: "+Double.toString(globelpercentage)+"\t");
			writer.write("Average recall: "+Double.toString(globelrecall)+"\t");
			writer.write("Average fscore: "+Double.toString(globelfmeasure));
			writer.write("\n");
			
			writer.write(term.size()+"\t"+missSet.size()+"\t"+((double)missSet.size()/(double)term.size())+"\n");
			
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		recom rec = new recom();
		rec.read();
		rec.rec();
	}
}

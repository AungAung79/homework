package dataProcess;

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
import java.util.Set;

public class dataSelect {
	Set<Integer> ranSet;
	HashMap<String, Integer> countHashMap = new HashMap<String, Integer>();
	HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
	
	public dataSelect(){
		ranSet = new HashSet<Integer>();
	}
	
	public void initran()
	{
		while(ranSet.size()<20000)
		{
			int temp = (int)(Math.random() * 200000);
			if(!ranSet.contains(temp))
			{
				ranSet.add(temp);
			}
		}
	}
	
	public void countTags()
	{
		File file = new File("data/QuestionsTags.txt");
		
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
			
			PrintWriter pw = new PrintWriter("train_and_test/countTag.csv");
			
			for(int i=0; i<infos.size();i++)
			{
				String idString= infos.get(i).getKey();
				int count = infos.get(i).getValue();
				if(count >= 200)
					tagIndex.put(idString, i);
				pw.println(idString+","+count);
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean judge(String line)
	{
		String[] sp = line.split("\t");
		boolean flag = false;
		for(int i = 0; i < sp.length; i++)
		{
			String tagTemp = sp[i];
			if(tagIndex.containsKey(tagTemp))
				flag = true;
		}
		return flag;
	}
	
	public void deal()
	{
		File file1=new File("data/QuestionsBothProcessed.txt");
		File file2=new File("data/QuestionsTags.txt");
		File trainFile = new File("train_and_test/train.csv");
		File testFile = new File("train_and_test/test.csv");
		File testTag = new File("train_and_test/testTag.csv");
		String line1;
		String line2;
		try {
			BufferedReader reader1=new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
			BufferedReader reader2=new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
			PrintWriter pWriter1=new PrintWriter(trainFile);
			PrintWriter pWriter2=new PrintWriter(testFile);
			PrintWriter pWriter3=new PrintWriter(testTag);
			line1=reader1.readLine();
			line2=reader2.readLine();
			int linenum=1;
			int trainNo=1;
			int testNo=1;
			while(line1 != null && line2 != null)
			{
				if(linenum%10000==0)
				{
					System.out.println(linenum);
				}
				if(ranSet.contains(linenum))
				{
					if(judge(line2))
					{
						pWriter2.print(testNo+","+linenum+",\"");
						String[] spline1 = line1.split(" ");
						for(int i = 0; i < spline1.length; i++)
						{
							if(!Stopwords.isStopword(spline1[i]))
								pWriter2.print(spline1[i]+" ");
						}
						pWriter2.println("\"");
						testNo++;
						String[] sp = line2.split("\t");
						for(int i = 0; i < sp.length; i++)
						{
							String tagTemp = sp[i];
							if(tagIndex.containsKey(tagTemp))
								pWriter3.print(tagIndex.get(tagTemp)+" ");
						}
						pWriter3.println();
					}
				}
				else {
					if(judge(line2))
					{
						pWriter1.print(trainNo+",");
						String[] sp = line2.split("\t");
						if(tagIndex.containsKey(sp[0]))
							pWriter1.print(tagIndex.get(sp[0]));
						for(int i = 1; i < sp.length; i++)
						{
							String tagTemp = sp[i];
							if(tagIndex.containsKey(tagTemp))
								pWriter1.print(" "+tagIndex.get(tagTemp));
						}
						pWriter1.print(","+linenum+",\"");
						String[] spline1 = line1.split(" ");
						for(int i = 0; i < spline1.length; i++)
						{
							if(!Stopwords.isStopword(spline1[i]))
								pWriter1.print(spline1[i]+" ");
						}
						pWriter1.println("\"");
						trainNo++;
					}
				}
				
				line1=reader1.readLine();
				line2=reader2.readLine();
				linenum++;
			}
			reader1.close();
			reader2.close();
			pWriter1.close();
			pWriter2.close();
			pWriter3.close();
			System.out.println("file over!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		dataSelect dSelect = new dataSelect();
		dSelect.initran();
		dSelect.countTags();
		dSelect.deal();
	}
}

package org.ics.llc.dataProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class dataSelect {
	Set<Integer> ranSet;
	
	public dataSelect(){
		ranSet = new HashSet<Integer>();
	}
	
	public void initran()
	{
		while(ranSet.size()<200000)
		{
			int temp = (int)(Math.random() * 2012348);
			if(!ranSet.contains(temp))
			{
				ranSet.add(temp);
			}
		}
	}
	
	public void titlescan()
	{
		File file=new File("processed-data/QuestionsTitlesProcessed.txt");
		File stat=new File("selected-data/QuestionsTitlesProcessed.txt");
		String line=new String();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			PrintWriter pWriter=new PrintWriter(stat);
			line=reader.readLine();
			int linenum=1;
			while(line!=null)
			{
				if(linenum%10000==0)
				{
					System.out.println(linenum);
				}
				
				if(ranSet.contains(linenum))
					pWriter.println(line);
				
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			pWriter.close();
			System.out.println("file over!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void bodyscan()
	{
		File file=new File("processed-data/QuestionsBodyProcessed.txt");
		File stat=new File("selected-data/QuestionsBodyProcessed.txt");
		String line=new String();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			PrintWriter pWriter=new PrintWriter(stat);
			line=reader.readLine();
			int linenum=1;
			while(line!=null)
			{
				if(linenum%10000==0)
				{
					System.out.println(linenum);
				}
				
				if(ranSet.contains(linenum))
					pWriter.println(line);
				
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			pWriter.close();
			System.out.println("file over!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void bothscan()
	{
		File file=new File("processed-data/QuestionsBothProcessed.txt");
		File stat=new File("selected-data/QuestionsBothProcessed.txt");
		String line=new String();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			PrintWriter pWriter=new PrintWriter(stat);
			line=reader.readLine();
			int linenum=1;
			while(line!=null)
			{
				if(linenum%10000==0)
				{
					System.out.println(linenum);
				}
				
				if(ranSet.contains(linenum))
					pWriter.println(line);
				
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			pWriter.close();
			System.out.println("file over!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tagscan()
	{
		File file=new File("parsed-data/QuestionsTags.txt");
		File stat=new File("selected-data/QuestionsTags.txt");
		String line=new String();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			PrintWriter pWriter=new PrintWriter(stat);
			line=reader.readLine();
			int linenum=1;
			while(line!=null)
			{
				if(linenum%10000==0)
				{
					System.out.println(linenum);
				}
				
				if(ranSet.contains(linenum))
					pWriter.println(line);
				
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			pWriter.close();
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
		dSelect.titlescan();
		dSelect.bodyscan();
		dSelect.bothscan();
		dSelect.tagscan();
	}
}

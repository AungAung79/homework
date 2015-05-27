package org.ics.llc.dataProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class selected {
	Set<Integer> ranSet;
	
	public selected(){
		ranSet = new HashSet<Integer>();
	}
	
	public void initran()
	{
		while(ranSet.size()<15000)
		{
			int temp = (int)(Math.random() * 1942249);
			if(!ranSet.contains(temp))
			{
				ranSet.add(temp);
			}
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
		File file=new File("parsed-data/QuestionsTags_removeLowNumTags.txt");
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
		selected dSelect = new selected();
		dSelect.initran();
		dSelect.bothscan();
		dSelect.tagscan();
	}
}

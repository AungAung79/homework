package org.ics.llc.dataProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class dataFilterForBody {
	private ArrayList<String> dic=new ArrayList<String>();
	private HashMap<String,Integer> dicHashSet=new HashMap<String,Integer>();
	
	private void add(String s)
	{
		dic.add(s);
		dicHashSet.put(s,1);
	}
	
	private boolean check(String s)
	{
		if(dicHashSet.containsKey(s)){
			return true;
		}
		else
			return false;
	}
	
	public void scan()
	{
		File file=new File("parsed-data/QuestionsBody.txt");
		String line=new String();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			line=reader.readLine();
			int linenum=1;
			while(line!=null)
			{
				StringTokenizer st = new StringTokenizer( line," " );
				String word;
				//System.out.print(word);
				if(linenum%10000==0)
				{
					System.out.println(linenum+" "+dic.size());
				}
				while(st.hasMoreTokens())
				{
					word=st.nextToken();
					if(!check(word))
						add(word);
					else {
						dicHashSet.put(word, dicHashSet.get(word)+1);
					}
				}
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			System.out.println("file over!");
			
			File stat=new File("processed-data/wordFreForBody.txt");
			PrintWriter pWriter=new PrintWriter(stat);
			for(int i=0;i<dic.size();i++)
			{
				pWriter.println(dic.get(i)+" "+dicHashSet.get(dic.get(i)));
			}
			pWriter.close();
			
			for(int i=dic.size()-1;i>=0;i--)
			{
				String heheString=dic.get(i);
				if(dicHashSet.get(heheString)<=50)
				{
					dic.remove(i);
					dicHashSet.remove(heheString);
					System.out.println(dic.size());
				}
			}
			
			stat=new File("processed-data/wordFreRemoved50ForBody.txt");
			pWriter=new PrintWriter(stat);
			for(int i=0;i<dic.size();i++)
			{
				pWriter.println(dic.get(i)+" "+dicHashSet.get(dic.get(i)));
			}
			pWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rescan()
	{
		File file=new File("parsed-data/QuestionsBody.txt");
		File pro=new File("processed-data/QuestionsBodyProcessed.txt");
		String line=new String();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			PrintWriter pWriter=new PrintWriter(pro);
			line=reader.readLine();
			int linenum=1;
			while(line!=null)
			{
				StringTokenizer st = new StringTokenizer( line," " );
				String word;
				//System.out.print(word);
				if(linenum%10000==0)
				{
					System.out.println(linenum+" "+dic.size());
				}
				while(st.hasMoreTokens())
				{
					word=st.nextToken();
					if(check(word))
						pWriter.print(word+" ");
				}
				pWriter.println();
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
		dataFilterForBody df=new dataFilterForBody();
		df.scan();
		df.rescan();
	}
}

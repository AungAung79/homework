package org.ics.llc.countCodeTags;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;

public class test {
	public void hehe()
	{
		File file=new File("raw-data/posts.xml");
		String line=new String();
		PrintStream psTemp = null;
		FileOutputStream outTemp = null;
		try
		{
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			outTemp = new FileOutputStream("FileTemp.txt");
			psTemp = new PrintStream(outTemp);
			line=reader.readLine();
			int linenum=1;
			while(linenum <= 500)
			{
				System.out.println(line);
				psTemp.println(line);
				line=reader.readLine();
				linenum++;
			}
			reader.close();
			System.out.println("file over!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void count()
	{
		File file=new File("parsed-data/QuestionsCodeIndex.txt");
		String line=new String();
		HashSet<Integer> index = new HashSet<Integer>();
		HashSet<String> tagOrig = new HashSet<String>();
		int csharpcount = 0;
		int javacount = 0;
		int cppcount = 0;
		int ccount = 0;
		int pycount = 0;
		int rubycount = 0;
		int phpcount = 0;
		int jscount = 0;
		int perlcount = 0;
		try
		{
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while((line=reader.readLine()) != null)
			{
				int temp = Integer.parseInt(line);
				index.add(temp);
			}
			reader.close();
			System.out.println("file over!");
			
			file = new File("parsed-data/QuestionsTags.txt");
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int linenum = 0;
			while((line=reader.readLine()) != null)
			{
				String[] splitParts = line.split("\t");
				linenum++;
				tagOrig.clear();
				for(int sptemp = 0; sptemp < splitParts.length; sptemp++)
				{
					String part = splitParts[sptemp];
					if(!tagOrig.contains(part))
						tagOrig.add(part);
				}
				if(index.contains(linenum))
				{
					if(tagOrig.contains("c#"))
						csharpcount++;
					if(tagOrig.contains("java"))
						javacount++;
					if(tagOrig.contains("c++"))
						cppcount++;
					if(tagOrig.contains("c"))
						ccount++;
					if(tagOrig.contains("python"))
						pycount++;
					if(tagOrig.contains("ruby"))
						rubycount++;
					if(tagOrig.contains("php"))
						phpcount++;
					if(tagOrig.contains("javascript"))
						jscount++;
					if(tagOrig.contains("perl"))
						perlcount++;
				}
			}
			reader.close();
			System.out.println("C#\t"+csharpcount);
			System.out.println("Java\t"+javacount);
			System.out.println("C++\t"+cppcount);
			System.out.println("C\t"+ccount);
			System.out.println("Python\t"+pycount);
			System.out.println("Ruby\t"+rubycount);
			System.out.println("Php\t"+phpcount);
			System.out.println("JavaScript\t"+jscount);
			System.out.println("Perl\t"+perlcount);
			int count = csharpcount + javacount + cppcount + ccount + pycount + rubycount + phpcount + jscount + perlcount;
			System.out.println("total:\t"+count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void JavaCount()
	{
		File file=new File("parsed-data/QuestionsCodeIndex.txt");
		File file2 = new File("JavaResult.txt");
		String line;
		String line2;
		HashMap<Integer, Double> index = new HashMap<Integer, Double>();
		HashSet<String> tagOrig = new HashSet<String>();
		int decide0value0 = 0;
		int decide1value0 = 0;
		int decide0value1 = 0;
		int decide1value1 = 0;
		try
		{
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			BufferedReader reader2=new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
			while((line=reader.readLine()) != null && (line2=reader2.readLine()) != null)
			{
				int temp = Integer.parseInt(line);
				double temp2 = Double.parseDouble(line2);
				index.put(temp, temp2);
			}
			reader.close();
			reader2.close();
			System.out.println("file over!");
			
			file = new File("parsed-data/QuestionsTags.txt");
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int linenum = 0;
			while((line=reader.readLine()) != null)
			{
				String[] splitParts = line.split("\t");
				linenum++;
				tagOrig.clear();
				for(int sptemp = 0; sptemp < splitParts.length; sptemp++)
				{
					String part = splitParts[sptemp];
					if(!tagOrig.contains(part))
						tagOrig.add(part);
				}
				if(index.containsKey(linenum))
				{
					if(tagOrig.contains("java") && index.get(linenum) > 0)
						decide1value1++;
					if(tagOrig.contains("java") && index.get(linenum) <= 0)
						decide0value1++;
					if(!tagOrig.contains("java") && index.get(linenum) > 0)
						decide1value0++;
					if(!tagOrig.contains("java") && index.get(linenum) <= 0)
						decide0value0++;
				}
			}
			reader.close();
			System.out.println("decide0value0\t"+decide0value0);
			System.out.println("decide0value1\t"+decide0value1);
			System.out.println("decide1value0\t"+decide1value0);
			System.out.println("decide1value1\t"+decide1value1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void PythonCount()
	{
		File file=new File("parsed-data/QuestionsCodeIndex.txt");
		File file2 = new File("PythonResult.txt");
		String line;
		String line2;
		HashMap<Integer, Double> index = new HashMap<Integer, Double>();
		HashSet<String> tagOrig = new HashSet<String>();
		int decide0value0 = 0;
		int decide1value0 = 0;
		int decide0value1 = 0;
		int decide1value1 = 0;
		try
		{
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			BufferedReader reader2=new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
			while((line=reader.readLine()) != null && (line2=reader2.readLine()) != null)
			{
				int temp = Integer.parseInt(line);
				double temp2 = Double.parseDouble(line2);
				index.put(temp, temp2);
			}
			reader.close();
			reader2.close();
			System.out.println("file over!");
			
			file = new File("parsed-data/QuestionsTags.txt");
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int linenum = 0;
			while((line=reader.readLine()) != null)
			{
				String[] splitParts = line.split("\t");
				linenum++;
				tagOrig.clear();
				for(int sptemp = 0; sptemp < splitParts.length; sptemp++)
				{
					String part = splitParts[sptemp];
					if(!tagOrig.contains(part))
						tagOrig.add(part);
				}
				if(index.containsKey(linenum))
				{
					if(tagOrig.contains("python") && index.get(linenum) > 0)
						decide1value1++;
					if(tagOrig.contains("python") && index.get(linenum) <= 0)
						decide0value1++;
					if(!tagOrig.contains("python") && index.get(linenum) > 0)
						decide1value0++;
					if(!tagOrig.contains("python") && index.get(linenum) <= 0)
						decide0value0++;
				}
			}
			reader.close();
			System.out.println("decide0value0\t"+decide0value0);
			System.out.println("decide0value1\t"+decide0value1);
			System.out.println("decide1value0\t"+decide1value0);
			System.out.println("decide1value1\t"+decide1value1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		test t = new test();
		t.JavaCount();
		t.PythonCount();
	}
}

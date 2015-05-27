package org.ics.llc.dataProcess;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PostsHandler4Words extends DefaultHandler {
	PrintStream psTitle = null;
	FileOutputStream outTitle = null;
	
	PrintStream psTag = null;
	FileOutputStream outTag = null;
	
	PrintStream psBody = null;
	FileOutputStream outBody = null;
	
	PrintStream psCount = null;
	FileOutputStream outCount = null;
	
	boolean row = false;
	boolean Body = false;
	boolean Title = false;
	boolean Tags = false;
	
	HashMap<String, Integer> tagNum = new HashMap<String, Integer>();
	HashMap<String, Integer> tagTitleNum = new HashMap<String, Integer>();
	HashMap<String, Integer> tagBodyNum = new HashMap<String, Integer>();
	HashMap<String, Integer> tagTotalNum = new HashMap<String, Integer>();

	public boolean filt(String s)
	{
		if(s.length() < 2)
			return false;
		if(s.contains("<") || s.contains(">") || s.contains("-") || s.contains("'") || s.contains(".") || s.contains("/")
				|| s.contains("@") || s.contains("$") || s.contains("_") || s.contains(":") || s.contains(",")
				|| s.contains("\\") || s.contains("*") || s.contains("`") || s.contains(";") || s.contains("?") 
				|| s.contains("(") || s.contains(")") || s.contains("[") || s.contains("]") || s.contains("{")
				|| s.contains("}") || s.contains("\"") || s.contains("&") || s.contains("!") || s.contains("=")
				|| s.contains("#") || s.contains("|") || s.contains("%") || s.contains("^") || s.contains("��"))
			return false;
		if(s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5")
				|| s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9"))
			return false;
		return true;
	}
	
	@Override
	public void startDocument() throws SAXException {
		try {
			System.setProperty("entityExpansionLimit", "99999");
			
			outTag = new FileOutputStream("parsed-data/QuestionsTags.txt");
			psTag = new PrintStream(outTag);
			
			outCount = new FileOutputStream("parsed-data/CountTags.txt");
			psCount = new PrintStream(outCount);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("row")) {
			row = true;
		} else {
			return;
		}
		
		if(Integer.parseInt(attributes.getValue(1)) == 1) {// questions
			for (int i = 0; i < attributes.getLength(); i++) {
				String name = attributes.getQName(i);
				if(name.equalsIgnoreCase("Body"))
					Body = true;
				else if(name.equalsIgnoreCase("Title"))
					Title = true;
				else if(name.equalsIgnoreCase("Tags"))
					Tags = true;
			}
			
			// these information are mandatory
			if(Tags == false || Body == false || Title == false) {
				//System.out.println("delete question");
				return;
			}
			
			// PostId AcceptedAnswerId CreationDate Score ViewCount BodyLength (Owner)UserId TitleLength AnswerCount CommentCount FavoriteCount
			for (int i = 0; i < attributes.getLength(); i++) {
				String name = attributes.getQName(i);
				if(name.equalsIgnoreCase("Title"))
				{
					try {
						outTitle = new FileOutputStream("parsed-data/QuestionsTitles.txt");
						psTitle = new PrintStream(outTitle);
						psTitle.print(attributes.getValue(i).toLowerCase());
						psTitle.close();
						outTitle.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(name.equalsIgnoreCase("Tags"))
				{
					String titleString = "";
					String bodyString = "";
					try {
						BufferedReader brTitle = new BufferedReader(new FileReader(new File("parsed-data/QuestionsTitles.txt")));
						String temp = brTitle.readLine();
						while(temp != null)
						{
							titleString = titleString + " " + temp;
							temp = brTitle.readLine();
						}
						brTitle.close();
						BufferedReader brBody = new BufferedReader(new FileReader(new File("parsed-data/QuestionsBody.txt")));
						temp = brBody.readLine();
						while(temp != null)
						{
							bodyString = bodyString + " " + temp;
							temp = brBody.readLine();
						}
						brBody.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String temp = attributes.getValue(i);
					String[] sp1 = temp.split("<");
					for(int m = 1; m < sp1.length; m++)
					{
						String[] sp2 = sp1[m].split(">");
						String tagTemp = sp2[0];
						if(tagNum.containsKey(tagTemp))
						{
							tagNum.put(tagTemp, tagNum.get(tagTemp) + 1);
						}
						else {
							tagNum.put(tagTemp, 1);
							tagTitleNum.put(tagTemp, 0);
							tagBodyNum.put(tagTemp, 0);
							tagTotalNum.put(tagTemp, 0);
						}
						
						if(tagTemp.contains("-"))
						{
							String[] tagSplit = tagTemp.split("-");
							boolean titleBoolean = false;
							boolean bodyBoolean = false;
							for(int index = 0; index < tagSplit.length; index++)
							{
								if(!titleString.contains(tagSplit[index]))
								{
									titleBoolean = true;
								}
								if(!bodyString.contains(tagSplit[index]))
								{
									bodyBoolean = true;
								}
							}
							if(!titleBoolean)
							{
								tagTitleNum.put(tagTemp, tagTitleNum.get(tagTemp) + 1);
							}
							if(!bodyBoolean)
							{
								tagBodyNum.put(tagTemp, tagBodyNum.get(tagTemp) + 1);
							}
							if(!titleBoolean || !bodyBoolean)
							{
								tagTotalNum.put(tagTemp, tagTotalNum.get(tagTemp) + 1);
							}
						}
						else {
							if(titleString.contains(tagTemp))
							{
								tagTitleNum.put(tagTemp, tagTitleNum.get(tagTemp) + 1);
							}
							
							if(bodyString.contains(tagTemp))
							{
								tagBodyNum.put(tagTemp, tagBodyNum.get(tagTemp) + 1);
							}
							if(titleString.contains(tagTemp) || bodyString.contains(tagTemp))
							{
								tagTotalNum.put(tagTemp, tagTotalNum.get(tagTemp) + 1);
							}
						}
						
						psTag.print(tagTemp+ "\t");
					}
					psTag.println();
					
				}
				if(name.equalsIgnoreCase("Body")) {
					try {
						outBody = new FileOutputStream("parsed-data/QuestionsBody.txt");
						psBody = new PrintStream(outBody);
						psBody.print(attributes.getValue(i).toLowerCase());
						psBody.close();
						outBody.close();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(attributes.getValue(i));
						System.exit(-1);
					}
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (row) {
			row = false;
			Tags = false;
			Body = false;
			Title = false;
		}
	}
	
	@Override
	public void endDocument() throws SAXException 
	{
		psCount.println("Tag\ttagOccurNum\ttagTitleOccurNum\ttagBodyOccurNum\ttitleRatio\tbodyRatio\ttotalRatio");
		double totalT = 0;
		double totalB = 0;
		double totalTo = 0;
		double removeTotalT = 0;
		double removeTotalB = 0;
		double removeTotalTo = 0;
		int removeNum = 0;
		for(Map.Entry<String, Integer> entry : tagNum.entrySet())
		{
			String keyString = entry.getKey();
			int num = entry.getValue();
			int titleNum = tagTitleNum.get(keyString);
			int bodyNum = tagBodyNum.get(keyString);
			int totalNum = tagTotalNum.get(keyString);
			double titleRatio = (double)titleNum / (double)num;
			double bodyRatio = (double)bodyNum / (double)num;
			double totalRatio = (double)totalNum / (double)num;
			if(num >= 100)
			{
				removeTotalT += titleRatio;
				removeTotalB += bodyRatio;
				removeTotalTo += totalRatio;
				removeNum++;
			}
			totalT += titleRatio;
			totalB += bodyRatio;
			totalTo += totalRatio;
			psCount.println(keyString + "\t" + num + "\t" + titleNum + "\t" + bodyNum + "\t" + totalNum + "\t" + titleRatio + "\t" + bodyRatio + "\t" + totalRatio);
		}
		double averageTitleRatio = totalT / (double)tagNum.size();
		double averageBodyRatio = totalB / (double)tagNum.size();
		double averageTotalRatio = totalTo / (double)tagNum.size();
		double averageRemoveTitleRatio = removeTotalT / (double)removeNum;
		double averageRemoveBodyRatio = removeTotalB / (double)removeNum;
		double averageRemoveTotalRatio = removeTotalTo / (double)removeNum;
		psCount.println("averageRatio\t" + averageTitleRatio + "\t" + averageBodyRatio + "\t" + averageTotalRatio);
		psCount.println("averageRatio(removeLowerThan100)\t" + averageRemoveTitleRatio + "\t" + averageRemoveBodyRatio + "\t" + averageRemoveTotalRatio);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (row) {
			row = false;
		}
	}

}
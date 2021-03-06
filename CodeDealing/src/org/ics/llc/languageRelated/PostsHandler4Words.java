package org.ics.llc.languageRelated;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class PostsHandler4Words extends DefaultHandler {
	PrintStream psTag = null;
	FileOutputStream outTag = null;
	
	PrintStream psOtherTag = null;
	FileOutputStream outOtherTag = null;
	
	PrintStream psLanTag = null;
	FileOutputStream outLanTag = null;
	
	PrintStream psAll = null;
	FileOutputStream outAll = null;
	
	boolean row = false;
	boolean Body = false;
	boolean Title = false;
	boolean Tags = false;
	
	String titleString = "";
	String bodyString = "";
	String tagString = "";
	HashSet<String> tagSet = new HashSet<String>();
	String codeString = "";
	HashSet<LanguageType> typeSet = new HashSet<LanguageType>();
	boolean codeFlag = false;
	
	static int total = 0;
	static int code = 0;
	static int languageNum = 0;
	static int languageCode = 0;
	static HashMap<LanguageType, Integer> lanCount = new HashMap<LanguageType, Integer>();

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
	
	public LanguageType decideType(String s)
	{
		if(s.equals("c#"))
		{
			return LanguageType.CSharp;
		}
		else if(s.equals("c++"))
		{
			return LanguageType.CPlusPlus;
		}
		else if(s.equals("c"))
		{
			return LanguageType.C;
		}
		else if(s.equals("css"))
		{
			return LanguageType.CSS;
		}
		else if(s.equals("html"))
		{
			return LanguageType.HTML;
		}
		else if(s.equals("java"))
		{
			return LanguageType.Java;
		}
		else if(s.equals("javascript"))
		{
			return LanguageType.JavaScript;
		}
		else if(s.equals("objective-c"))
		{
			return LanguageType.ObjectiveC;
		}
		else if(s.equals("php"))
		{
			return LanguageType.PHP;
		}
		else if(s.equals("perl"))
		{
			return LanguageType.Perl;
		}
		else if(s.equals("python"))
		{
			return LanguageType.Python;
		}
		else if(s.equals("ruby"))
		{
			return LanguageType.Ruby;
		}
		else if(s.equals("sql"))
		{
			return LanguageType.SQL;
		}
		else {
			return null;
		}
	}
	
	@Override
	public void startDocument() throws SAXException {
		try {
			System.setProperty("entityExpansionLimit", "99999");
			
			outTag = new FileOutputStream("/Users/jimmy/StackOverflow/parsed-data/QuestionsTags.txt");
			psTag = new PrintStream(outTag);
			
			outLanTag = new FileOutputStream("/Users/jimmy/StackOverflow/parsed-data/QuestionsLanguageType.txt");
			psLanTag = new PrintStream(outLanTag);
			
			outOtherTag = new FileOutputStream("/Users/jimmy/StackOverflow/parsed-data/QuestionsOtherTags.txt");
			psOtherTag = new PrintStream(outOtherTag);
			
			lanCount.put(LanguageType.C, 0);
			lanCount.put(LanguageType.CPlusPlus, 0);
			lanCount.put(LanguageType.CSharp, 0);
			lanCount.put(LanguageType.CSS, 0);
			lanCount.put(LanguageType.HTML, 0);
			lanCount.put(LanguageType.Java, 0);
			lanCount.put(LanguageType.JavaScript, 0);
			lanCount.put(LanguageType.ObjectiveC, 0);
			lanCount.put(LanguageType.Perl, 0);
			lanCount.put(LanguageType.PHP, 0);
			lanCount.put(LanguageType.Python, 0);
			lanCount.put(LanguageType.Ruby, 0);
			lanCount.put(LanguageType.SQL, 0);
			
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
					titleString = attributes.getValue(i).toLowerCase();
				}
				if(name.equalsIgnoreCase("Tags"))
				{
					String temp = attributes.getValue(i);
					String[] sp1 = temp.split("<");
					for(int m = 1; m < sp1.length; m++)
					{
						String[] sp2 = sp1[m].split(">");
						LanguageType typeTemp = decideType(sp2[0]);
						if(typeTemp != null)
						{
							typeSet.add(typeTemp);
							lanCount.put(typeTemp, lanCount.get(typeTemp) + 1);
						}
						tagString += sp2[0]+ "\t";
						tagSet.add(sp2[0]);
					}
					if(typeSet.size() != 0)
						languageNum++;
				}
				if(name.equalsIgnoreCase("Body")) {
					bodyString = attributes.getValue(i).toLowerCase();
					if(bodyString.contains("</code></pre>"))
					{
						code++;
						codeFlag = true;
						
						int startIndex = 0;
						int index1;
						int index2;
						while(bodyString.indexOf("<pre><code>",startIndex) > -1 && bodyString.indexOf("</code></pre>",startIndex) > -1)
						{
							index1 = bodyString.indexOf("<pre><code>",startIndex);
							index2 = bodyString.indexOf("</code></pre>",startIndex);
							if(index1 < index2)
							{
								String codestr = bodyString.substring(index1+11, index2);
								codestr = codestr.replaceAll("&lt;", "<");
								codestr = codestr.replaceAll("&gt;", ">");
								codestr = codestr.replaceAll("&amp;lt;", "<=");
								codestr = codestr.replaceAll("&amp;gt;", ">=");
								codestr = codestr.replaceAll("&amp;", "=");
								codestr = codestr.replaceAll("<br>", "\n");
								codeString += codestr + "\n";
							}
							startIndex = index2+13;
						}
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
		if(typeSet.size() == 1 && tagSet.size() >= 2)
		{
			if(codeFlag)
			{
				languageCode++;
				total++;
				Iterator<LanguageType> iterator = typeSet.iterator();
				LanguageType ty = iterator.next();
				String lan = ty.toString();
				
				psTag.println(tagString);
				psLanTag.println(lan);
				
				Iterator<String> iterator2 = tagSet.iterator();
				while(iterator2.hasNext())
				{
					String t = iterator2.next();
					if(!lan.equals(t))
						psOtherTag.print(t + "\t");
				}
				psOtherTag.println();
				
				try {
					File filetemp = new File("/Users/jimmy/StackOverflow/parsed-data/text/");
					if(!filetemp.exists())
						filetemp.mkdir();
					outAll = new FileOutputStream("/Users/jimmy/StackOverflow/parsed-data/text/Questions_"+total+".txt");
					psAll = new PrintStream(outAll);
					psAll.println(titleString);
					psAll.println();
					psAll.println(bodyString);
					psAll.close();
					
					filetemp = new File("/Users/jimmy/StackOverflow/parsed-data/code/");
					if(!filetemp.exists())
						filetemp.mkdir();
					outAll = new FileOutputStream("/Users/jimmy/StackOverflow/parsed-data/code/Questions_"+total+"_code.txt");
					psAll = new PrintStream(outAll);
					psAll.println(codeString);
					psAll.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		typeSet.clear();
		codeFlag = false;
		tagSet.clear();
		tagString = "";
		codeString = "";
		if(total % 100000 == 0)
		{
			System.out.print("total:" + total + "\tlanguage:" + languageNum + "\tcode:" + code + "\tlanguageAndCode:");
			System.out.println(languageCode);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (row) {
			row = false;
		}
	}

}
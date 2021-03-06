package org.ics.llc.dataProcess;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class PostsHandler4Words extends DefaultHandler {
	PrintStream psTitle = null;
	FileOutputStream outTitle = null;
	
	PrintStream psTag = null;
	FileOutputStream outTag = null;
	
	PrintStream psBody = null;
	FileOutputStream outBody = null;
	
	PrintStream psTemp = null;
	FileOutputStream outTemp = null;
	
	boolean row = false;
	boolean Body = false;
	boolean Title = false;
	boolean Tags = false;

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
			
			outTitle = new FileOutputStream("parsed-data/QuestionsTitles.txt");
			psTitle = new PrintStream(outTitle);
			
			outTag = new FileOutputStream("parsed-data/QuestionsTags.txt");
			psTag = new PrintStream(outTag);
			
			outBody = new FileOutputStream("parsed-data/QuestionsBody.txt");
			psBody = new PrintStream(outBody);
			
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
						outTemp = new FileOutputStream("parsed-data/FileTemp.txt");
						psTemp = new PrintStream(outTemp);
						psTemp.print(attributes.getValue(i).toLowerCase());
						PTBTokenizer ptbt;
						ptbt = new PTBTokenizer(new FileReader("parsed-data/FileTemp.txt"),new CoreLabelTokenFactory(),"");
						for(CoreLabel label; ptbt.hasNext();)
						{
							label = (CoreLabel)ptbt.next();
							if(filt(label.toString()))
								psTitle.print(label + " ");
						}
						psTitle.println();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if(name.equalsIgnoreCase("Tags"))
				{
					String temp = attributes.getValue(i);
					String[] sp1 = temp.split("<");
					for(int m = 1; m < sp1.length; m++)
					{
						String[] sp2 = sp1[m].split(">");
						psTag.print(sp2[0]+ "\t");
					}
					psTag.println();
				}
				if(name.equalsIgnoreCase("Body")) {
					try {
						outTemp = new FileOutputStream("parsed-data/FileTemp.txt");
						psTemp = new PrintStream(outTemp);
						psTemp.print(attributes.getValue(i).toLowerCase());
						PTBTokenizer ptbt;
						ptbt = new PTBTokenizer(new FileReader("parsed-data/FileTemp.txt"),new CoreLabelTokenFactory(),"");
						for(CoreLabel label; ptbt.hasNext();)
						{
							label = (CoreLabel)ptbt.next();
							if(filt(label.toString()))
								psBody.print(label + " ");
						}
						psBody.println();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
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
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (row) {
			row = false;
		}
	}

}
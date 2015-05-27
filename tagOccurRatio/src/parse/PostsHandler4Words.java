package parse;
import java.io.File;
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
	
	static int total = 0;

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
			
			//outTitle = new FileOutputStream("parsed-data/QuestionsTitles.txt");
			//psTitle = new PrintStream(outTitle);
			
			outTag = new FileOutputStream("/Users/jimmy/StackOverflow/parsed-data/QuestionsTags.txt");
			psTag = new PrintStream(outTag);
			
			//outBody = new FileOutputStream("parsed-data/QuestionsBody.txt");
			//psBody = new PrintStream(outBody);
			
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
			
			total++;
			int dir = total / 100000 + 1;
			if(total % 10000 == 0)
				System.out.println(total + " " + dir);
			try {
				File filetemp = new File("/Users/jimmy/StackOverflow/parsed-data/text/"+dir);
				if(!filetemp.exists())
					filetemp.mkdir();
				outBody = new FileOutputStream("/Users/jimmy/StackOverflow/parsed-data/text/"+dir+"/Questions_"+total+".txt");
				psBody = new PrintStream(outBody);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			String titleString = "";
			String bodyString = "";
			
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
						psTag.print(sp2[0]+ "\t");
					}
					psTag.println();
				}
				if(name.equalsIgnoreCase("Body")) {
					bodyString = attributes.getValue(i).toLowerCase();
				}
			}
			
			psBody.println(titleString);
			psBody.println();
			psBody.println();
			psBody.println(bodyString);
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
			psBody.close();
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
package org.ics.llc.TextDeal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class DealText {
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
	
	public void deal()
	{
		try {
			PrintWriter pw = new PrintWriter(new File("/Users/jimmy/StackOverflow/parsed-data/Method4/text.txt"));
			PrintWriter pw2 = new PrintWriter(new File("/Users/jimmy/StackOverflow/parsed-data/Method5/text.txt"));
			File f1 = new File("/Users/jimmy/StackOverflow/parsed-data/QuestionsLanguageType.txt");
			BufferedReader br = new BufferedReader(new FileReader(f1));
			int index = 0;
			String linete;
			while((linete = br.readLine()) != null)
			{
				index++;
				if(index % 10000 == 0)
					System.out.println(index);
				String filename = "/Users/jimmy/StackOverflow/parsed-data/text/Questions_"+index+".txt";
				PTBTokenizer ptbt = new PTBTokenizer(new FileReader(filename),new CoreLabelTokenFactory(),"");
				for(CoreLabel label; ptbt.hasNext();)
				{
					label = (CoreLabel)ptbt.next();
					if(filt(label.toString()))
					{
						pw.print(label + " ");
						pw2.print(label + " ");
					}
				}
				pw.println();
				pw2.println();
			}
			pw.close();
			pw2.close();
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		DealText dt = new DealText();
		dt.deal();
	}
}

package parse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class feature {
	public static void main(String[] args)
	{
		try {
			FileOutputStream outFea = new FileOutputStream("/Users/jimmy/StackOverflow/feature-data/feature1.txt");
			PrintStream psFea = new PrintStream(outFea);
			for(int i = 1; i <= 2012348; i++)
			{
				int dir = i / 100000 + 1;
				if(i % 10000 == 0)
					System.out.println(i + " " + dir);
				BufferedReader brtrain = new BufferedReader(new InputStreamReader(
						new FileInputStream("/Users/jimmy/StackOverflow/parsed-data/text/"+dir+"/Questions_"+i+".txt")));
				String fileString = "";
				String temp;
				while((temp = brtrain.readLine()) != null)
				{
					fileString += temp;
				}
				brtrain.close();
				int length = fileString.length();
				int numberOfCodeSeg = 0;
				int startIndex = 0;
				while(fileString.indexOf("<pre><code>",startIndex) > -1 && fileString.indexOf("</code></pre>",startIndex) > -1)
				{
					int index1 = fileString.indexOf("<pre><code>",startIndex);
					int index2 = fileString.indexOf("</code></pre>",startIndex);
					if(index1 < index2)
						numberOfCodeSeg++;
					startIndex = index2 + 13;
				}
				int numberOfAHref = 0;
				startIndex = 0;
				while(fileString.indexOf("a href",startIndex) > -1)
				{
					int index1 = fileString.indexOf("a href",startIndex);
					numberOfAHref++;
					startIndex = index1 + 6;
				}
				int numberOfUrl = 0;
				startIndex = 0;
				while(fileString.indexOf("http",startIndex) > -1)
				{
					int index1 = fileString.indexOf("http",startIndex);
					numberOfUrl++;
					startIndex = index1 + 4;
				}
				int numberOfHtmlTag = 0;
				startIndex = 0;
				while(fileString.indexOf(">",startIndex) > -1)
				{
					int index1 = fileString.indexOf(">",startIndex);
					numberOfHtmlTag++;
					startIndex = index1 + 1;
				}
				psFea.println(length + "," + numberOfCodeSeg + "," + numberOfAHref + "," + numberOfUrl + "," + numberOfHtmlTag);
			}
			psFea.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

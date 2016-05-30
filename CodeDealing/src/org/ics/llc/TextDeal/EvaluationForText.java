package org.ics.llc.TextDeal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class EvaluationForText {
	String origin = "/Users/jimmy/StackOverflow/parsed-data/QuestionsLanguageType.txt";
	String predict = "/Users/jimmy/StackOverflow/parsed-data/Method4/PredictedLabel2_new.txt";
	int fileNum = 0;
	
	public void judgeMatrix()
	{
		int check = 0;
		try {
			HashMap<String, Integer> index = new HashMap<String, Integer>();
			index.put("c", 2);
			index.put("c++", 1);
			index.put("c#", 0);
			index.put("css", 3);
			index.put("html", 4);
			index.put("java", 5);
			index.put("javascript", 6);
			index.put("objective-c", 7);
			index.put("perl", 9);
			index.put("php", 8);
			index.put("python", 10);
			index.put("ruby", 11);
			index.put("sql", 12);
			
			int[][] confusionMatrix = new int[13][13];
			for(int i = 0; i < 13; i++)
				for(int j = 0; j < 13; j++)
					confusionMatrix[i][j] = 0;
			
			File ori = new File(origin);
			File pre = new File(predict);
			BufferedReader bro = new BufferedReader(new FileReader(ori));
			BufferedReader brp = new BufferedReader(new FileReader(pre));
			String lineo;
			String linep;
			while((lineo = bro.readLine()) != null)
			{
				fileNum++;
				if(fileNum <= (459393 - (int)(459393 * 0.1)))
					continue;
				
				linep = brp.readLine();
				int indexo = index.get(lineo);
				int indexp = (int)(Double.parseDouble(linep));
				confusionMatrix[indexo][indexp]++;
				
				if(indexp == 3 && lineo.equals("sql"))
					check++;
			}
			bro.close();
			brp.close();
			
			System.out.println("=== Confusion Matirx ===");
			System.out.println("    a    b    c    d    e    f    g    h    i    j    k    l    m    <--- classified as");
			int correct = 0;
			int sum = 0;
			for(int i = 0; i < 13; i++)
			{
				correct += confusionMatrix[i][i];
				for(int j = 0; j < 13; j++)
				{
					System.out.print(confusionMatrix[i][j] + "\t");
					sum += confusionMatrix[i][j];
				}
				System.out.println(" |");
			}
			
			System.out.println(check);
			
			System.out.println(correct + "\t" + sum + "\t" + ((double)correct / (double)sum));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		EvaluationForText et = new EvaluationForText();
		et.judgeMatrix();
	}
}

package org.ics.llc.TokenRelevance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Evaluation {
	String origin = "/Users/jimmy/StackOverflow/parsed-data/QuestionsLanguageType.txt";
	String predict = "/Users/jimmy/StackOverflow/parsed-data/Method1/PredictLanguageType.txt";
	int fileNum = 0;
	int fileRightNum = 0;
	
	public void judge()
	{
		try {
			File ori = new File(origin);
			File pre = new File(predict);
			BufferedReader bro = new BufferedReader(new FileReader(ori));
			BufferedReader brp = new BufferedReader(new FileReader(pre));
			String lineo;
			String linep;
			while((lineo = bro.readLine()) != null && (linep = brp.readLine()) != null)
			{
				fileNum++;
				if(lineo.equals(linep))
					fileRightNum++;
			}
			bro.close();
			brp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
			while((lineo = bro.readLine()) != null && (linep = brp.readLine()) != null)
			{
				fileNum++;
				if(fileNum <= (459393 - (int)(459393 * 0.1)))
					continue;
				
				int indexo = index.get(lineo);
				int indexp = index.get(linep);
				confusionMatrix[indexo][indexp]++;
				
				if(linep.equals("css") && lineo.equals("sql"))
					check++;
			}
			bro.close();
			brp.close();
			
			System.out.println("=== Confusion Matirx ===");
			System.out.println("    a    b    c    d    e    f    g    h    i    j    k    l    m    <--- classified as");
			for(int i = 0; i < 13; i++)
			{
				for(int j = 0; j < 13; j++)
				{
					System.out.print(confusionMatrix[i][j] + "\t");
				}
				System.out.println(" |");
			}
			
			System.out.println(check);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Evaluation ev = new Evaluation();
//		ev.judge();
//		System.out.println(ev.fileNum + "\t" + ev.fileRightNum + "\t" + ((double)ev.fileRightNum / (double)ev.fileNum));
		
		ev.judgeMatrix();
	}
}

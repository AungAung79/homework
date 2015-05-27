package org.ics.llc.textRank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class main {
	public static void main(String[] args)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("data/QuestionsBothProcessed.txt")));
			PrintWriter pw = new PrintWriter("data/Keyword.txt");
			String temp;
			int line = 0;
			while((temp = br.readLine()) != null)
			{
				line++;
				if(line % 10000 == 0)
					System.out.println(line);
				textRank tRank = new textRank(temp);
				tRank.randomWalk();
				List<Map.Entry<String, Double>> infos = tRank.keyword();
				for(int i = 0; i < infos.size() && i < 20; i++)
				{
					String idString= infos.get(i).getKey();
					double count = infos.get(i).getValue();
					pw.print(idString+","+count+"\t");
				}
				pw.println();
			}
			br.close();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

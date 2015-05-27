package org.ics.llc.count;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class count {
	File file = new File("results");
	
	double globelPer;
	double globelRec;
	double globelF;
	int RecNum;
	
	File outputFile = new File("plot/count.csv");
	
	public count(){
		
	}
	
	public void deal()
	{
		try {
			PrintWriter pw = new PrintWriter(outputFile);
			
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++)
			{
				File countFile = files[i].getAbsoluteFile();
				BufferedReader br = new BufferedReader(new FileReader(countFile));
				String name = files[i].getName();
				pw.print(name.subSequence(0, name.length()-4)+",");
				String line;
				globelPer = 0;
				globelRec = 0;
				globelF = 0;
				RecNum = 0;
				while((line = br.readLine()) != null)
				{
					if(line.contains("Average"))
						continue;
					RecNum++;
					String[] sp = line.split("\t");
					int length = sp.length;
					String perString = sp[length-3].substring(0, sp[length-3].length()-1);
					double localPer = Double.parseDouble(perString);
					String recString = sp[length-2].substring(0, sp[length-2].length()-1);
					double localRec = Double.parseDouble(recString);
					double localF;
					if(sp[length-1].equals("NaN"))
						localF = 0;
					else
						localF = Double.parseDouble(sp[length-1]);
					globelPer += localPer;
					globelRec += localRec;
					globelF += localF;
				}
				br.close();
				globelPer = globelPer / RecNum;
				globelRec = globelRec / RecNum;
				globelF = globelF / RecNum / 100;
				pw.print(globelPer+"%,"+globelRec+"%,"+globelF);
				pw.println();
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		count c = new count();
		c.deal();
	}
}

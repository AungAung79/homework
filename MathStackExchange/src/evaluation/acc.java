package evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.ics.llc.textRank.textRank;
import org.xml.sax.SAXException;

public class acc {
	public static void main(String[] args){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("data/QuestionsTags_removeLowNumTags.txt")));
			HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
			String temp;
			int num = 0;
			while((temp = br.readLine()) != null)
			{
				String[] sp = temp.split("\t");
				for(int i = 0; i < sp.length; i++)
				{
					if(!tagIndex.containsKey(sp[i]))
					{
						num++;
						tagIndex.put(sp[i], num);
					}
				}
			}
			
			
			br = new BufferedReader(new FileReader(new File("data/QuestionsTags_removeLowNumTags.txt")));
			BufferedReader br2 = new BufferedReader(new FileReader(new File("data/Keyword.txt")));
			PrintWriter pw = new PrintWriter(new File("data/StrongPro.txt"));
			PrintWriter pw2 = new PrintWriter(new File("data/WeakPro.txt"));
			HashSet<String> set = new HashSet<String>();
			//String temp;
			String temp2;
			int line = 0;
			double ratio = 0;
			double precision = 0;
			while((temp = br.readLine()) != null && (temp2 = br2.readLine()) != null)
			{
				line++;
				if(line % 10000 == 0)
					System.out.println(line);
				String[] sp = temp.split("\t");
				for(int i = 0; i < sp.length; i++)
				{
					set.add(sp[i]);
				}
				String[] sp2 = temp2.split("\t");
				int count = 0;
				for(int i = 0; i < sp2.length; i++)
				{
					String[] sp3 = sp2[i].split(",");
					if(set.contains(sp3[0]))
					{
						count++;
						pw.println(line+"\t"+tagIndex.get(sp3[0])+"\t"+sp3[1]);
					}
					if(tagIndex.containsKey(sp3[0]))
					{
						pw2.println(line+"\t"+tagIndex.get(sp3[0])+"\t"+sp3[1]);
					}
				}
				ratio = ratio + (double)count / set.size();
				precision = precision + (double)count / sp2.length;
				set.clear();
			}
			br.close();
			br2.close();
			pw.close();
			pw2.close();
			System.out.println("recall:" + ratio / line);
			System.out.println("precision:" + precision / line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

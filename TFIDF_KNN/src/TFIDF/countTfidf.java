package TFIDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class countTfidf {
	LinkedHashMap<String, Integer> tf = new LinkedHashMap<String, Integer>();
	LinkedHashMap<String, Double> df = new LinkedHashMap<String, Double>();
	int documentCount = 0;
	
	public void readVocabulary()
	{
		File file = new File("data/wordFreRemoved50ForBoth.txt");
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null)
			{
				String[] sp = line.split(" ");
				tf.put(sp[0], 0);
				df.put(sp[0], 0.0);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void count()
	{
		File file = new File("select_data/QuestionsBothProcessed.txt");
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			HashSet<String> wordsHashSet = new HashSet<String>();
			int countline = 0;
			while((line = br.readLine()) != null)
			{
				countline++;
				if(countline % 10000 == 0)
					System.out.println(countline);
				String[] sp = line.split(" ");
				for(int i = 0; i < sp.length; i++)
				{
					wordsHashSet.add(sp[i]);
				}
				Iterator<String> it = wordsHashSet.iterator();
				while(it.hasNext())
				{
					String temp = it.next();
					if(df.containsKey(temp))
						df.put(temp, df.get(temp) + 1);
				}
				wordsHashSet.clear();
				documentCount++;
			}
			br.close();
			
			Iterator<Map.Entry<String, Double>> iter = df.entrySet().iterator();
			while(iter.hasNext())
			{
				Map.Entry<String, Double> entry = (Map.Entry<String, Double>)iter.next();
				String tempA = entry.getKey();
				double tempB = entry.getValue();
				if(tempB == 0.0)
					df.put(tempA, 0.0);
				else
					df.put(tempA, Math.log(documentCount / tempB));
			}
			
			br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter("result/tfidf.csv");
			countline = 0;
			Iterator<Map.Entry<String, Integer>> iterTF = tf.entrySet().iterator();
			while(iterTF.hasNext())
			{
				Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iterTF.next();
				String tempA = entry.getKey();
				pw.print(tempA + ",");
			}
			pw.println();
			while((line = br.readLine()) != null)
			{
				countline++;
				if(countline % 10000 == 0)
					System.out.println(countline);
				String[] sp = line.split(" ");
				for(int i = 0; i < sp.length; i++)
				{
					if(tf.containsKey(sp[i]))
						tf.put(sp[i], tf.get(sp[i]) + 1);
				}
				iterTF = tf.entrySet().iterator();
				while(iterTF.hasNext())
				{
					Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iterTF.next();
					String tempA = entry.getKey();
					int tempB = entry.getValue();
					tf.put(tempA, 0);
					double a = df.get(tempA) * (double)tempB;
					if(a == 0)
						pw.print(",");
					else
						pw.print(String.format("%.4f", a)+",");
				}
				pw.println();
			}
			br.close();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cossimilarity()
	{
		File file = new File("result/tfidf.csv");
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null)
			{
				
			}
			br.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		countTfidf ct = new countTfidf();
		ct.readVocabulary();
		ct.count();
	}
}

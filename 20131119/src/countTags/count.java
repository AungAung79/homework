package countTags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class count {
	public static void main(String[] args)
	{
		HashMap<String, Integer> countHashMap = new HashMap<String, Integer>();
		File file = new File("countTagData/QuestionsTags.txt");
		
		double[][] intersection;
		double[][] union;
		
		double relevanceA;
		double relevanceS;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null)
			{
				String[] sp = line.split("\t");
				for(int i = 0; i < sp.length; i++)
				{
					if(countHashMap.containsKey(sp[i]))
					{
						countHashMap.put(sp[i], countHashMap.get(sp[i])+1);
					}
					else {
						countHashMap.put(sp[i], 1);
					}
				}
			}
			br.close();
			
			List<Map.Entry<String, Integer>> infos = new ArrayList<Map.Entry<String, Integer>>(countHashMap.entrySet());
			Collections.sort(infos, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2){
					return (o2.getValue().compareTo(o1.getValue()));
				}
			});
			
			PrintWriter pw = new PrintWriter("result/countTag.csv");
			
			for(int i = 0; i < infos.size(); i++)
			{
				String idString= infos.get(i).getKey();
				int count = infos.get(i).getValue();
				pw.println(idString+","+count);
			}
			pw.close();
			
			System.out.println(infos.size());
			
			for(int i = infos.size() - 1; i > 0; i--)
			{
				int count = infos.get(i).getValue();
				if(count <= 50)
					infos.remove(i);
			}
			
			System.out.println(infos.size());
			
			HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
			
			for(int i = 0; i < infos.size(); i++)
			{
				String idString= infos.get(i).getKey();
				tagIndex.put(idString, i);
			}
			
			pw = new PrintWriter("result/TagCoOccurrence.csv");
			
			intersection = new double[infos.size()][infos.size()];
			union = new double[infos.size()][infos.size()];
			br = new BufferedReader(new FileReader(file));
			int linenum = 0;
			while((line = br.readLine()) != null)
			{
				linenum++;
				if(linenum % 10000 == 0)
					System.out.println(linenum);
				String[] sp = line.split("\t");
				for(int i = 0; i < sp.length; i++)
				{
					if(!tagIndex.containsKey(sp[i]))
						continue;
					for(int j = 0; j < sp.length; j++)
					{
						if(!tagIndex.containsKey(sp[j]))
							continue;
						if(i != j)
						{
							int tempA = tagIndex.get(sp[i]);
							int tempB = tagIndex.get(sp[j]);
							intersection[tempA][tempB] = intersection[tempA][tempB] + 1;
							union[tempA][tempB] = union[tempA][tempB] - 1;
							for(int k = 0; k < infos.size(); k++)
							{
								if(k != tempB)
									union[k][tempB] = union[k][tempB] + 1;
								if(k != tempA)
									union[tempA][k] = union[tempA][k] + 1;
							}
						}
					}
				}
			}
			br.close();
			
			for(int i = 0; i < infos.size(); i++)
			{
				for(int j = 0; j < infos.size(); j++)
				{
					String idStringI = infos.get(i).getKey();
					String idStringJ = infos.get(j).getKey();
					if(i != j)
					{
						relevanceA = 0;
						relevanceS = 0;
						pw.print(idStringI+",");
						pw.print(idStringJ+"\t");
						relevanceA = intersection[i][j] / infos.get(i).getValue();
						relevanceS = intersection[i][j] / union[i][j];
						pw.println(intersection[i][j]+"\t"+union[i][j]+"\t"+relevanceA+"\t"+relevanceS);
					}
				}
			}
			
//			for(int i = 0; i < infos.size(); i++)
//			{
//				String idStringI = infos.get(i).getKey();
//				if(i % 100 == 0)
//					System.out.println("i is "+i);
//				double[][] intersectionTemp = new double[5000][infos.size()];
//				double[][] unionTemp = new double[5000][infos.size()];
//				br = new BufferedReader(new FileReader(file));
//				while((line = br.readLine()) != null)
//				{
//					String[] sp = line.split("\t");
//					HashSet<String> tempSet = new HashSet<String>();
//					for(int k = 0; k < sp.length; k++)
//					{
//						tempSet.add(sp[k]);
//					}
//					for(int m = 0; m < 5000 && m < infos.size() - i; m++)
//					{
//						idStringI = infos.get(m+i).getKey();
////						if(m % 100 == 0)
////							System.out.println("m is "+(m+i));
//						for(int k = 0; k < infos.size(); k++)
//						{
//							if(m + i == k)
//								continue;
//							String idStringK = infos.get(k).getKey();
//							if(tempSet.contains(idStringI) && tempSet.contains(idStringK))
//								intersectionTemp[m][k] = intersectionTemp[m][k] + 1;
//							if(tempSet.contains(idStringI) || tempSet.contains(idStringK))
//								unionTemp[m][k] = unionTemp[m][k] + 1;
//						}
//					}
//					
//				}
//				br.close();
//				
//				for(int m = 0; m < 5000 && m < infos.size(); m++)
//				{
//					idStringI = infos.get(m + i).getKey();
//					for(int j = 0; j < infos.size(); j++)
//					{
//						String idStringJ = infos.get(j).getKey();
//						if(j % 100 == 0)
//							System.out.println("j is "+j);
//						if(m + i != j)
//						{
////							boolean intersectionFlag = false;
////							boolean unionFlag = false;
////							intersection = 0;
////							union = 0;
//							relevanceA = 0;
//							relevanceS = 0;
////							br = new BufferedReader(new FileReader(file));
////							while((line = br.readLine()) != null)
////							{
////								String[] sp = line.split("\t");
////								for(int k = 0; k < sp.length; k++)
////								{
////									if(sp[k].equals(idStringI))
////									{
////										if(unionFlag)
////											intersectionFlag = true;
////										unionFlag = true;
////									}
////									if(sp[k].equals(idStringJ))
////									{
////										if(unionFlag)
////											intersectionFlag = true;
////										unionFlag = true;
////									}
////								}
////								if(unionFlag)
////									union++;
////								if(intersectionFlag)
////									intersection++;
////								intersectionFlag = false;
////								unionFlag = false;
////							}
////							br.close();
//							pw.print(idStringI+",");
//							pw.print(idStringJ+"\t");
//							relevanceA = intersectionTemp[m][j] / infos.get(m+i).getValue();
//							relevanceS = intersectionTemp[m][j] / unionTemp[m][j];
//							pw.println(intersectionTemp[m][j]+"\t"+unionTemp[m][j]+"\t"+relevanceA+"\t"+relevanceS);
//						}
//					}
//				}
//				
//				
//				i = i + 5000;
//			}
			pw.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package org.ics.llc.languageTagNaiveBayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;

public class TagNB {
	String feature = "/Users/jimmy/StackOverflow/parsed-data/QuestionsOtherTags.txt";
	String label = "/Users/jimmy/StackOverflow/parsed-data/QuestionsLanguageType.txt";
	
	HashMap<String, Integer> featureindex = new HashMap<String, Integer>();
	//HashMap<Integer, String> indexfeature = new HashMap<Integer, String>();
	HashMap<String, Integer> labelindex = new HashMap<String, Integer>();
	HashMap<Integer, String> indexlabel = new HashMap<Integer, String>();
	
	double[] Y;
	double[][] XTrue;
	double[][] XFalse;
	
	public void getFeatureLength()
	{
		try {
			File fea = new File(feature);
			BufferedReader brfea = new BufferedReader(new FileReader(fea));
			String linefea;
			while((linefea = brfea.readLine()) != null)
			{
				String[] sp = linefea.split("\t");
				for(int i = 0; i < sp.length; i++)
				{
					String temp = sp[i];
					if(temp.equals(""))
						continue;
					if(!featureindex.containsKey(temp))
					{
						//indexfeature.put(featureindex.size(), temp);
						featureindex.put(temp, featureindex.size());
					}
				}
			}
			brfea.close();
			System.out.println(featureindex.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getLabelLength()
	{
		try {
			File lab = new File(label);
			BufferedReader brlab = new BufferedReader(new FileReader(lab));
			String linelab;
			while((linelab = brlab.readLine()) != null)
			{
				if(!labelindex.containsKey(linelab))
				{
					indexlabel.put(labelindex.size(), linelab);
					labelindex.put(linelab, labelindex.size());
				}
			}
			brlab.close();
			System.out.println(labelindex.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void trainNB()
	{
		getFeatureLength();
		getLabelLength();
		String train = "/Users/jimmy/StackOverflow/parsed-data/Method3/data_train.txt";
		
		Y = new double[labelindex.size()];
		XTrue = new double[labelindex.size()][featureindex.size()];
		XFalse = new double[labelindex.size()][featureindex.size()];
		
		for(int i = 0; i < Y.length; i++)
		{
			Y[i] = 0;
			for(int j = 0; j < XTrue[0].length; j++)
			{
				XTrue[i][j] = 0;
				XFalse[i][j] = 0;
			}
		}
		
		HashSet<Integer> indexSet = new HashSet<Integer>();
		
		try {
			File tr = new File(train);
			BufferedReader brtr = new BufferedReader(new FileReader(tr));
			String linetr;
			while((linetr = brtr.readLine()) != null)
			{
				indexSet.clear();
				String[] sp = linetr.split(",");
				for(int i = 0; i < sp.length - 1; i++)
				{
					if(sp[i].equals(""))
						continue;
					indexSet.add(featureindex.get(sp[i]));
				}
				int lIndexTemp = labelindex.get(sp[sp.length - 1]);
				Y[lIndexTemp]++;
				for(int i = 0; i < XTrue[0].length; i++)
				{
					if(indexSet.contains(i))
					{
						XTrue[lIndexTemp][i]++;
					}
					else {
						XFalse[lIndexTemp][i]++;
					}
				}
			}
			brtr.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		double sum = 0;
		for(int i = 0; i < Y.length; i++)
		{
			sum += Y[i];
			for(int j = 0; j < XTrue[0].length; j++)
			{
				XTrue[i][j] /= Y[i];
				XFalse[i][j] /= Y[i];
			}
		}
		
		for(int i = 0; i < Y.length; i++)
		{
			Y[i] /= sum;
		}
	}
	
	public void testNB()
	{
		int check = 0;
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
		
		String test = "/Users/jimmy/StackOverflow/parsed-data/Method3/data_test.txt";
		HashSet<Integer> indexSet = new HashSet<Integer>();
		double[] result = new double[labelindex.size()];
		try {
			File te = new File(test);
			BufferedReader brte = new BufferedReader(new FileReader(te));
			String linete;
			while((linete = brte.readLine()) != null)
			{
				indexSet.clear();
				for(int i = 0; i < result.length; i++)
				{
					result[i] = Y[i];
				}
				String[] sp = linete.split(",");
				for(int i = 0; i < sp.length - 1; i++)
				{
					if(sp[i].equals(""))
						continue;
					indexSet.add(featureindex.get(sp[i]));
				}
				for(int i = 0; i < XTrue[0].length; i++)
				{
					for(int j = 0; j < result.length; j++)
					{
						if(indexSet.contains(i))
						{
							result[j] *= XTrue[j][i];
						}
						else {
							result[j] *= XFalse[j][i];
						}
					}
				}
				double max = 0;
				for(int i = 0; i < result.length; i++)
				{
					if(max < result[i])
						max = result[i];
				}
				int predict = 0;
				for(int i = 0; i < result.length; i++)
				{
					if(max == result[i])
						predict = i;
				}
				confusionMatrix[index.get(sp[sp.length - 1])][index.get(indexlabel.get(predict))]++;
				if(indexlabel.get(predict).equals("css") && sp[sp.length - 1].equals("sql"))
					check++;
			}
			brte.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	}
	
	public void print()
	{
		try {
			PrintWriter pw = new PrintWriter(new File("/Users/jimmy/StackOverflow/parsed-data/Method3/ProbabilityOutput.txt"));
			for(int i = 0; i < Y.length; i++)
			{
				pw.print(Y[i] + ",");
			}
			pw.println();
			pw.println("-----------------");
			
			for(int i = 0; i < Y.length; i++)
			{
				for(int j = 0; j < XTrue[0].length; j++)
				{
					pw.print(XTrue[i][j] + ",");
				}
				pw.println();
			}
			pw.println("-----------------");
			
			for(int i = 0; i < Y.length; i++)
			{
				for(int j = 0; j < XTrue[0].length; j++)
				{
					pw.print(XFalse[i][j] + ",");
				}
				pw.println();
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		TagNB tnb = new TagNB();
		tnb.trainNB();
		tnb.testNB();
	}
}

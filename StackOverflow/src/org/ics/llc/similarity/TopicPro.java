package org.ics.llc.similarity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.ics.llc.lda.PathConfig;

public class TopicPro {
	int K;
	double traintheta[][];
	int trainNo[];
	double testtheta[][];
	int testNo[];
	
	String resPath = PathConfig.LdaResultsPath;
	
	public void init() throws Exception
	{
		BufferedReader brtrain = new BufferedReader(new InputStreamReader(new FileInputStream(resPath+"lda_100.theta")));
		String line;
		int linenum=0;
		while((line=brtrain.readLine()) != null)
		{
			String[] lineParts = line.split(":");
			String[] parts = lineParts[1].split("\t");
			K = parts.length;
			linenum++;
		}
		System.out.println(linenum);
		trainNo = new int[linenum];
		traintheta = new double[linenum][K];
		
		brtrain = new BufferedReader(new InputStreamReader(new FileInputStream(resPath+"lda_100.theta")));
		linenum=0;
		while((line=brtrain.readLine()) != null)
		{
			String[] lineParts = line.split(":");
			trainNo[linenum] = Integer.valueOf(lineParts[0]);
			String[] parts = lineParts[1].split("\t");
			for(int temp = 0; temp < parts.length; temp++)
			{
				traintheta[linenum][temp] = Double.valueOf(parts[temp]);
			}
			linenum++;
		}
		
		BufferedReader brtest = new BufferedReader(new InputStreamReader(new FileInputStream(resPath+"lda_predict.theta")));
		linenum=0;
		while((line=brtest.readLine()) != null)
		{
			linenum++;
		}
		testNo = new int[linenum];
		testtheta = new double[linenum][K];
		
		brtest = new BufferedReader(new InputStreamReader(new FileInputStream(resPath+"lda_predict.theta")));
		linenum=0;
		while((line=brtest.readLine()) != null)
		{
			String[] lineParts = line.split(":");
			testNo[linenum] = Integer.valueOf(lineParts[0]);
			String[] parts = lineParts[1].split("\t");
			for(int temp = 0; temp < parts.length; temp++)
			{
				testtheta[linenum][temp] = Double.valueOf(parts[temp]);
			}
			linenum++;
		}
	}
	
	public void Print()
	{
		System.out.println(K);
		System.out.println(traintheta.length);
		System.out.println(testtheta.length);
		//System.out.println(trainNo[179999]);
	}
	
	public void deal() throws Exception
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(resPath + "index.txt"));
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(resPath + "sim.txt"));
		for(int i = 0; i<testtheta.length; i++)
		{
			double[] sim=Similarity.computeKL(traintheta, testtheta[i]);
			int[] top=Similarity.top20pro(sim);
			writer.write(testNo[i]+":");
			writer2.write(testNo[i]+":");
			for(int m = 0; m < top.length; m++)
			{
				writer.write(trainNo[top[m]]+ "\t");
				writer2.write(sim[top[m]]+ "\t");
			}
			writer.write("\n");
			writer2.write("\n");
		}
		writer.close();
		writer2.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		long a=System.currentTimeMillis();
		TopicPro tp = new TopicPro();
		tp.init();
		tp.Print();
		tp.deal();
		long b=System.currentTimeMillis();
		System.out.println((b-a)/1000);
	}
}

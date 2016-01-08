package org.ics.llc.TextMatarTagNB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class textRank {
	int N = 2;  //co-occur window
	double d = 0.8;  //transform probablilty
	
	int vertice;
	HashMap<String, Integer> word = new HashMap<String, Integer>();
	double[][] graph;
	double[] outVer;
	double[] score;
	double[] oldScore;
	
	public textRank(String s)
	{
		String[] sp = s.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			if(!word.containsKey(sp[i]))
				word.put(sp[i], word.size());
		}
		vertice = word.size();
		graph = new double[vertice][vertice];
		for(int i = 0; i < sp.length; i++)
		{
			int j = i - N;
			int k = i + N;
			if(j < 0)
			{
				if(k >= sp.length)
				{
					for(int m = 0; m < sp.length; m++)
					{
						int indexi = word.get(sp[i]);
						int indexj = word.get(sp[m]);
						if(indexi != indexj)
							graph[indexi][indexj] = graph[indexi][indexj] + 1;
					}
				}
				else {
					for(int m = 0; m <= k; m++)
					{
						int indexi = word.get(sp[i]);
						int indexj = word.get(sp[m]);
						if(indexi != indexj)
							graph[indexi][indexj] = graph[indexi][indexj] + 1;
					}
				}
			}
			else {
				if(k >= sp.length)
				{
					for(int m = j; m < sp.length; m++)
					{
						int indexi = word.get(sp[i]);
						int indexj = word.get(sp[m]);
						if(indexi != indexj)
							graph[indexi][indexj] = graph[indexi][indexj] + 1;
					}
				}
				else {
					for(int m = j; m <= k; m++)
					{
						int indexi = word.get(sp[i]);
						int indexj = word.get(sp[m]);
						if(indexi != indexj)
							graph[indexi][indexj] = graph[indexi][indexj] + 1;
					}
				}
			}
		}
		
//		boolean checkResult = check(graph);
//		if(checkResult)
//		{
//			//System.out.println("true");
//		}
//		else {
//			System.out.println("false--------------------------------------");
//		}
		
		outVer = new double[vertice];
		for(int i = 0; i < outVer.length; i++)
		{
			double temp = 0;
			for(int j = 0; j < graph[0].length; j++)
			{
				if(graph[i][j] != 0)
					temp+=graph[i][j];
			}
			outVer[i] = temp; 
		}
		
		score = new double[vertice];
		oldScore = new double[vertice];
		for(int i = 0; i < score.length; i++)
		{
			score[i] = (double)(1 / score.length);
			oldScore[i] = (double)(1 / score.length);
		}
	}
	
	public boolean check(double[][] graph)
	{
		for(int i = 0; i < graph.length; i++)
		{
			for(int j = i; j < graph[0].length; j++)
			{
				if(graph[i][j] != graph[j][i])
					return false;
			}
		}
		return true;
	}
	
	public void randomWalk()
	{
		while(true)
		{
			for(int i = 0; i < score.length; i++)
			{
				double tempScore = 0;
				for(int j = 0; j < score.length; j++)
				{
					if(graph[j][i] != 0)
					{
						tempScore += graph[j][i] * oldScore[j] / outVer[j];
					}
				}
				score[i] = 1 - d + d * tempScore;
			}
			double threshold = 0.0;
			for(int i = 0; i < score.length; i++)
			{
				threshold += (score[i] - oldScore[i]) * (score[i] - oldScore[i]);
				oldScore[i] = score[i]; 
			}
			if(threshold <= 0.0001)
				return;
		}
	}
	
	public List<Map.Entry<String, Double>> keyword()
	{
		HashMap<String, Double> proHashMap = new HashMap<String, Double>();
		for(Map.Entry<String, Integer> entry : word.entrySet())
		{
			int num = entry.getValue();
			proHashMap.put(entry.getKey(), score[num]);
		}
		List<Map.Entry<String, Double>> infos = new ArrayList<Map.Entry<String, Double>>(proHashMap.entrySet());
		Collections.sort(infos, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,Map.Entry<String, Double> o2){
				return (o2.getValue().compareTo(o1.getValue()));
			}
		});
		
		return infos;
	}
}

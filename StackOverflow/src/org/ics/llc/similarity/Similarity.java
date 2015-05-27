package org.ics.llc.similarity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Similarity {
	public static double[] compute(double[][] a,double[] b)
	{
		double sim[] = new double[a.length];
		for(int i = 0; i<a.length; i++)
		{
			double sum = 0;
			double sumtemp = 0;
			for(int j = 0; j<a[i].length; j++)
			{
				sumtemp = Math.sqrt(a[i][j]) - Math.sqrt(b[j]);
				sumtemp = sumtemp * sumtemp;
				sum += sumtemp;
			}
			sim[i] = Math.sqrt(sum);
		}
		return sim;
	}
	
	public static double[] computeKL(double[][] a,double[] b)
	{
		double sim[] = new double[a.length];
		for(int i = 0; i<a.length; i++)
		{
			double sum = 0;
			double sumtemp = 0;
			for(int j = 0; j<a[i].length; j++)
			{
				sumtemp = Math.log(b[j] / a[i][j]) / Math.log(2);
				sumtemp = b[j] * sumtemp;
				sum += sumtemp;
			}
			sim[i] = sum;
		}
		return sim;
	}
	
	public static int[] top20pro(double[] sim)
	{
		List<Integer> tWordsIndexArray = new ArrayList<Integer>(); 
		for(int j = 0; j < sim.length; j++){
			tWordsIndexArray.add(new Integer(j));
		}
		Collections.sort(tWordsIndexArray, new Similarity.TwordsComparable(sim));
		int topNum = 50;
		int[] top = new int[topNum];
		for(int i = 0; i < topNum; i++)
		{
			//System.out.print(sim[tWordsIndexArray.get(i)]+" ");
			top[i] = tWordsIndexArray.get(i);
		}
		//System.out.println();
		return top;
	}
	
	public static class TwordsComparable implements Comparator<Integer> {
		
		public double [] sortProb; // Store probability of each word in topic k
		
		public TwordsComparable (double[] sortProb){
			this.sortProb = sortProb;
		}

		@Override
		public int compare(Integer o1, Integer o2) {
			// TODO Auto-generated method stub
			//Sort topic word index according to the probability of each word in topic k
			if(sortProb[o1] > sortProb[o2]) return 1;
			else if(sortProb[o1] < sortProb[o2]) return -1;
			else return 0;
		}
	}
}

package org.ics.llc.lda;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LdaPredicting {
	int M,K,V;
	float alpha; //doc-topic dirichlet prior parameter 
	float beta; //topic-word dirichlet prior parameter
	int iterations;//Times of iterations
	int[][] newdoc;
	int[][] newz;
	int[][] newnmk;
	int[] newnmkSum;
	int[][] newnkt;
	int[] newnktSum;
	double [][] newphi;//Parameters for topic-word distribution K*V
	double [][] newtheta;//Parameters for doc-topic distribution M*K
	
	public void init(int k,int v,float al,float be,int it,int[][] nkt,int[] nktSum)
	{
		K=k;
		V=v;
		alpha=al;
		beta=be;
		iterations=it;
		newphi = new double[K][V];
		
		newnkt = new int[K][V];
		newnktSum = new int[K];
		for(int i = 0; i < K;i++)
		{
			for(int j = 0; j < V;j++)
			{
				newnkt[i][j] = nkt[i][j];
			}
			newnktSum[i] = nktSum[i];
		}
	}
	
	public void predict(Documents d) throws IOException
	{
		M = d.testdocs.size();
		newnmk = new int[M][K];
		newnmkSum = new int[M];
		newtheta = new double[M][K];
		
		newdoc = new int[M][];
		for(int m = 0; m < M; m++)
		{
			int N = d.testdocs.get(m).docWords.length;
			newdoc[m]=new int[N];
			for(int n = 0; n < N; n++){
				newdoc[m][n] = d.testdocs.get(m).docWords[n];
			}
		}
		
		newz = new int[M][];
		for(int m = 0; m < M; m++)
		{
			int N = d.testdocs.get(m).docWords.length;
			newz[m] = new int[N];
			for(int n = 0; n < N; n++){
				int initTopic = (int)(Math.random() * K);// From 0 to K - 1
				newz[m][n] = initTopic;
				//number of words in doc m assigned to topic initTopic add 1
				newnmk[m][initTopic]++;
				//number of terms doc[m][n] assigned to topic initTopic add 1
				newnkt[initTopic][newdoc[m][n]]++;
				// total number of words assigned to topic initTopic add 1
				newnktSum[initTopic]++;
			}
			// total number of words in document m is N
			newnmkSum[m] = N;
		}
		
		for(int i = 0; i < iterations; i++)
		{
			System.out.println("Iteration " + i);
			
			//Use Gibbs Sampling to update z[][]
			for(int m = 0; m < M; m++)
			{
				int N = d.testdocs.get(m).docWords.length;
				for(int n = 0; n < N; n++){
					// Sample from p(z_i|z_-i, w)
					int newTopic = predictSampleTopicZ(m,n);
					newz[m][n] = newTopic;
				}
			}
		}
		
		//Firstly update parameters
		for(int k = 0; k < K; k++){
			for(int t = 0; t < V; t++){
				newphi[k][t] = (newnkt[k][t] + beta) / (newnktSum[k] + V * beta);
			}
		}
		
		for(int m = 0; m < M; m++){
			for(int k = 0; k < K; k++){
				newtheta[m][k] = (newnmk[m][k] + alpha) / (newnmkSum[m] + K * alpha);
			}
		}
		
		//Secondly print model variables
		String resPath = PathConfig.LdaResultsPath;
		String modelName = "lda_predict";
		
		//lda.phi K*V
		BufferedWriter writer = new BufferedWriter(new FileWriter(resPath + modelName + ".phi"));		
		for (int a = 0; a < K; a++){
			for (int b = 0; b < V; b++){
				writer.write(newphi[a][b] + "\t");
			}
			writer.write("\n");
		}
		writer.close();
		
		//lda.theta M*K
		writer = new BufferedWriter(new FileWriter(resPath + modelName + ".theta"));
		for (int a = 0; a < M; a++){
			writer.write(d.testdocs.get(a).No+":");
			for(int b = 0; b < K; b++){
				writer.write(newtheta[a][b] + "\t");
			}
			writer.write("\n");
		}
		
		writer.close();
		
		//lda.tassign
		writer = new BufferedWriter(new FileWriter(resPath + modelName + ".tassign"));
		for (int a = 0; a < M; a++){
			for(int b = 0; b < newdoc[a].length; b++){
				writer.write(newdoc[a][b] + ":" + newz[a][b] + "\t");
			}
			writer.write("\n");
		}
		
		writer.close();
	}
	
	private int predictSampleTopicZ(int m,int n) {
		// TODO Auto-generated method stub
		// Sample from p(z_i|z_-i, w) using Gibbs upde rule
		
		//Remove topic label for w_{m,n}
		int oldTopic = newz[m][n];
		newnmk[m][oldTopic]--;
		newnkt[oldTopic][newdoc[m][n]]--;
		newnmkSum[m]--;
		newnktSum[oldTopic]--;
		
		//Compute p(z_i = k|z_-i, w)
		double [] p = new double[K];
		for(int k = 0; k < K; k++){
			p[k] = (newnkt[k][newdoc[m][n]] + beta) / (newnktSum[k] + V * beta) * (newnmk[m][k] + alpha) / (newnmkSum[m] + K * alpha);
		}
		
		//Sample a new topic label for w_{m, n} like roulette
		//Compute cumulated probability for p
		for(int k = 1; k < K; k++){
			p[k] += p[k - 1];
		}
		double u = Math.random() * p[K - 1]; //p[] is unnormalised
		int newTopic;
		for(newTopic = 0; newTopic < K; newTopic++){
			if(u < p[newTopic]){
				break;
			}
		}
		
		//Add new topic label for w_{m, n}
		newnmk[m][newTopic]++;
		newnkt[newTopic][newdoc[m][n]]++;
		newnmkSum[m]++;
		newnktSum[newTopic]++;
		return newTopic;
	}
}

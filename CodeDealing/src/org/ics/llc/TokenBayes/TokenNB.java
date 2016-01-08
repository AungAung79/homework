package org.ics.llc.TokenBayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.UpdateableClassifier;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.evaluation.ConfusionMatrix;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffLoader.ArffReader;

public class TokenNB {
	public void train() throws Exception
	{
		File file = new File("/Users/jimmy/StackOverflow/parsed-data/Method2/data.arff");
		BufferedReader br = new BufferedReader(new FileReader(file));
		NaiveBayesUpdateable nbu = new NaiveBayesUpdateable();
		
		ArffLoader arff = new ArffLoader();
		arff.setFile(file);
		Instances structure = arff.getStructure();
		structure.setClassIndex(structure.numAttributes() - 1);
		
		nbu.buildClassifier(structure);
		
		Instance inst;
		int num = 0;
		while((inst = arff.getNextInstance(structure)) != null)
		{
			nbu.updateClassifier(inst);
			num++;
//			if(num == 9000)
//				break;
			if(num == 413454)
				break;
		}
		System.out.println(num);
		
		Instances testInstances = arff.getStructure();
		testInstances.setClassIndex(testInstances.numAttributes() - 1);
		while((inst = arff.getNextInstance(structure)) != null)
		{
			testInstances.add(inst);
		}
		
		System.out.println(testInstances.numInstances());
		
		Evaluation evaluation = new Evaluation(structure);
		evaluation.evaluateModel(nbu, testInstances);
		System.out.println(evaluation.toClassDetailsString());
		System.out.println(evaluation.toSummaryString());
		System.out.println(evaluation.toMatrixString());
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TokenNB tNb = new TokenNB();
		tNb.train();
	}
}

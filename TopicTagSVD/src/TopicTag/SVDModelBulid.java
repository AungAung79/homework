package TopicTag;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.*;

public class SVDModelBulid {
	int TopicNUM;
	int TagNUM;
	String tag[];
	int Num;
	
	HashMap<String, Integer> tagToIndex = new HashMap<String, Integer>();
	HashMap<Integer, String> indexToTag = new HashMap<Integer, String>();
	
	RealMatrix matrix;
	
	RealMatrix newMatrix;
	int f = 200;
	
	HashSet<String> tagOrig = new HashSet<String>();
	int RecNum = 20;
	
	public void init() throws Exception
	{
		BufferedReader brtrain = new BufferedReader(new InputStreamReader(new FileInputStream("lda_100.theta")));
		String line;
		int linenum=0;
		while((line=brtrain.readLine()) != null)
		{
			String[] lineParts = line.split(":");
			String[] parts = lineParts[1].split("\t");
			TopicNUM = parts.length;
			linenum++;
		}
		System.out.println(linenum);
		brtrain.close();
		
		BufferedReader tagReader = new BufferedReader(new InputStreamReader(new FileInputStream("QuestionsTags.txt")));
		linenum=0;
		while((line=tagReader.readLine()) != null)
		{
			linenum++;
		}
		Num = linenum;
		tag = new String[Num+1];
		tagReader.close();
		
		tagReader = new BufferedReader(new InputStreamReader(new FileInputStream("QuestionsTags.txt")));
		linenum=1;
		while((line=tagReader.readLine()) != null)
		{
			tag[linenum] = line;
			linenum++;
		}
		tagReader.close();
		
		for(int i = 1; i < tag.length; i++)
		{
			String tagor = tag[i];
			String[] splitParts = tagor.split("\t");
			for(int sptemp = 0; sptemp < splitParts.length; sptemp++)
			{
				String part = splitParts[sptemp];
				if(!tagToIndex.containsKey(part))
				{
					int count = tagToIndex.size();
					tagToIndex.put(part, count);
					indexToTag.put(count, part);
				}
			}
		}
		TagNUM = tagToIndex.size();
		
		System.out.println("Topic Num:"+TopicNUM);
		System.out.println("Tag Num:"+TagNUM);
		
		matrix = MatrixUtils.createRealMatrix(TopicNUM, TagNUM);
		System.out.println(matrix.getRowDimension());
		System.out.println(matrix.getColumnDimension());
		
		brtrain = new BufferedReader(new InputStreamReader(new FileInputStream("lda_100.theta")));
		while((line=brtrain.readLine()) != null)
		{
			String[] lineParts = line.split(":");
			int No = Integer.valueOf(lineParts[0]);
			String tagor = tag[No];
			String[] splitParts = tagor.split("\t");
			int[] index = new int[splitParts.length];
			for(int sptemp = 0; sptemp < splitParts.length; sptemp++)
			{
				String part = splitParts[sptemp];
				index[sptemp] = tagToIndex.get(part);
			}
//			System.out.println(index.length);
			
			String[] parts = lineParts[1].split("\t");
			for(int temp = 0; temp < parts.length; temp++)
			{
				double value = Double.valueOf(parts[temp]);
				if(value < 0.0001)
					continue;
				for(int j = 0; j < index.length; j++)
				{
					matrix.setEntry(temp, index[j], value+matrix.getEntry(temp, index[j]));
				}
			}
		}
		System.out.println("Matrix created!");
	}
	
	public void svd()
	{
		SVDModel model = new SVDModel(matrix);
		RealMatrix U = model.returnU(f);
		RealMatrix S = model.returnS(f);
		RealMatrix V = model.returnV(f);
		newMatrix = U.multiply(S).multiply(V.transpose());
		System.out.println("SVD finished!");
	}
	
	public void predict() throws Exception
	{
		BufferedReader brtest = new BufferedReader(new InputStreamReader(new FileInputStream("lda_predict.theta")));
		BufferedWriter writer = new BufferedWriter(new FileWriter("tagRecommended.txt"));
		String line;
		double globelpercentage = 0;
		double globelrecall = 0;
		double globelfmeasure = 0;
		int recommendNum = 0;
		while((line=brtest.readLine()) != null)
		{
			tagOrig.clear();
			String[] lineParts = line.split(":");
			int No = Integer.valueOf(lineParts[0]);
			writer.write(No+":");
			String tagor = tag[No];
			String[] splitParts = tagor.split("\t");
			for(int sptemp = 0; sptemp < splitParts.length; sptemp++)
			{
				String part = splitParts[sptemp];
				if(!tagOrig.contains(part))
					tagOrig.add(part);
			}
			int hitnum = 0;
			
			RealVector vector = newMatrix.getRowVector(0);
			String[] parts = lineParts[1].split("\t");
			double prevalue = Double.valueOf(parts[0]);
			vector = vector.mapMultiply(prevalue);
			for(int temp = 1; temp < parts.length; temp++)
			{
				double value = Double.valueOf(parts[temp]);
				RealVector tempVector = newMatrix.getRowVector(temp);
				vector = vector.combineToSelf(1, value, tempVector);
			}
			
			HashMap<Integer, Double> hehe = new HashMap<>();
			for(int i = 0; i < vector.getDimension(); i++)
			{
				hehe.put(i, vector.getEntry(i));
			}
			
			List<Map.Entry<Integer,Double>> infos = new ArrayList<Map.Entry<Integer, Double>>(hehe.entrySet());
			
			Collections.sort(infos, new Comparator<Map.Entry<Integer, Double>>() {
				public int compare(Map.Entry<Integer, Double> o1,Map.Entry<Integer, Double> o2){
					return (o2.getValue().compareTo(o1.getValue()));
				}
			});
			
			for(int i=0; i<RecNum && i<infos.size();i++)
			{
				int id= infos.get(i).getKey();
				String idString = indexToTag.get(id);
				//System.out.println(idString);
				if(tagOrig.contains(idString))
					hitnum++;
				writer.write(idString+ "\t");
			}
			
			double hitpercentage = (double)hitnum/RecNum*100.0;
			double hitrecall = (double)hitnum/(tagOrig.size())*100;
			double f_measure;
			if(hitpercentage!=0 || hitrecall!=0)
				f_measure = 2*hitpercentage*hitrecall/(hitpercentage+hitrecall);
			else {
				f_measure = 0;
			}
			globelpercentage += hitpercentage;
			globelrecall += hitrecall;
			globelfmeasure += f_measure;
			recommendNum++;
			writer.write(Double.toString(hitpercentage)+"%\t");
			writer.write(Double.toString(hitrecall)+"%\t");
			writer.write(Double.toString(f_measure));
			writer.write("\n");
		}
		
		System.out.println(recommendNum);
		globelpercentage = globelpercentage / recommendNum;
		globelrecall = globelrecall / recommendNum;
		globelfmeasure = globelfmeasure / recommendNum;
		writer.write("Average percentage: "+Double.toString(globelpercentage)+"%\t");
		writer.write("Average recall: "+Double.toString(globelrecall)+"%\t");
		writer.write("Average fscore: "+Double.toString(globelfmeasure));
		writer.write("\n");
		
		writer.close();
		brtest.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		SVDModelBulid bulid = new SVDModelBulid();
		bulid.init();
		bulid.svd();
		bulid.predict();
	}
}

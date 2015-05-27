package org.ics.llc.lda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Class for corpus which consists of M documents
 * @author yangliu
 * @blog http://blog.csdn.net/yangliuy
 * @mail yangliuyx@gmail.com
 */

public class Documents {
	
	ArrayList<Document> docs; 
	ArrayList<Document> testdocs;
	Map<String, Integer> termToIndexMap;
	ArrayList<String> indexToTermMap;
	Map<String,Integer> termCountMap;
	Set<Integer> ranSet;
	
	public Documents(){
		docs = new ArrayList<Document>();
		testdocs = new ArrayList<Document>();
		termToIndexMap = new HashMap<String, Integer>();
		indexToTermMap = new ArrayList<String>();
		termCountMap = new HashMap<String, Integer>();
		ranSet = new HashSet<Integer>();
	}
	
	public void initran()
	{
		while(ranSet.size()<20000)
		{
			int temp = (int)(Math.random() * 200000);
			if(!ranSet.contains(temp))
			{
				ranSet.add(temp);
			}
		}
	}
	
	public void readDocs(String docsPath){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(docsPath)));
			String line;
			Integer linenum=0;
			while((line=br.readLine()) != null)
			{
				linenum++;
				if(linenum % 10000 == 0)
				{
					System.out.print(linenum+" ");
					System.out.print(termToIndexMap.size()+" ");
					System.out.print(docs.size()+" ");
					System.out.println(testdocs.size());
				}
				Document doc = new Document(line, linenum, termToIndexMap, indexToTermMap, termCountMap);
				if(!ranSet.contains(doc.No))
					docs.add(doc);
				else {
					testdocs.add(doc);
				}
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("WordList"));	
			for(int i=0;i<indexToTermMap.size();i++)
			{
				writer.write(indexToTermMap.get(i));
				writer.write("\n");
			}
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class Document {
		public int No;
		public int[] docWords;
		
		public Document(String line, int no, Map<String, Integer> termToIndexMap, ArrayList<String> indexToTermMap, Map<String, Integer> termCountMap){
			//Read file and initialize word index array
			No = no;
			ArrayList<String> words = new ArrayList<String>();
			FileUtil.tokenizeAndLowerCase(line, words);
			
			//Remove stop words and noise words
			for(int i = 0; i < words.size(); i++){
				if(Stopwords.isStopword(words.get(i)) || isNoiseWord(words.get(i))){
					words.remove(i);
					i--;
				}
			}
			//Transfer word to index
			this.docWords = new int[words.size()];
			for(int i = 0; i < words.size(); i++){
				String word = words.get(i);
				if(!termToIndexMap.containsKey(word)){
					int newIndex = termToIndexMap.size();
					termToIndexMap.put(word, newIndex);
					indexToTermMap.add(word);
					termCountMap.put(word, new Integer(1));
					docWords[i] = newIndex;
				} else {
					docWords[i] = termToIndexMap.get(word);
					termCountMap.put(word, termCountMap.get(word) + 1);
				}
			}
			words.clear();
		}
		
		public boolean isNoiseWord(String string) {
			// TODO Auto-generated method stub
			string = string.toLowerCase().trim();
			Pattern MY_PATTERN = Pattern.compile(".*[a-zA-Z]+.*");
			Matcher m = MY_PATTERN.matcher(string);
			// filter @xxx and URL
			if(string.matches(".*www\\..*") || string.matches(".*\\.com.*") || 
					string.matches(".*http:.*") )
				return true;
			if (!m.matches()) {
				return true;
			} else
				return false;
		}
		
	}
}

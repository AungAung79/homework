package snaff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class convertSO {

	public void read()
	{
		File file1=new File("SOdata/QuestionsTags_removeLowNumTags.txt");
		File file2=new File("SOdata/QuestionsBothProcessed.txt");
		File pro=new File("SOdata/tasBibLikeSort.dat");
		String line1;
		String line2;
		int num = 0;
		try {
			BufferedReader reader1=new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
			BufferedReader reader2=new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
			PrintWriter pWriter=new PrintWriter(pro);
			while(((line1 = reader1.readLine()) != null) && ((line2 = reader2.readLine()) != null))
			{
				num++;
				String[] sp = line1.split("\t");
				String[] sp2 = line2.split(" ");
				String line2Cov = "";
				for(int i = 0; i < 4 && i < sp2.length; i++)
				{
					line2Cov += sp2[i] + " ";
				}
				for(int i = 0; i < sp.length; i++)
				{
					if(num<=8000)
						pWriter.println(num+"\t"+sp[i]+"\t"+num+"\t1\t2008-07-31T21:42:52.667\t"+line2Cov);
					else if(num<=10000){
						pWriter.println(num+"\t"+sp[i]+"\t"+num+"\t1\t2009-09-15T21:42:52.667\t"+line2Cov);
					}
					else if(num<=15000){
						pWriter.println(num+"\t"+sp[i]+"\t"+num+"\t1\t2009-11-28T21:42:52.667\t"+line2Cov);
					}
//					else {
//						pWriter.println(num+"\t"+sp[i]+"\t"+num+"\t1\t3409-11-28T21:42:52.667\t"+line2Cov);
//					}
				}
				
			}
			reader1.close();
			reader2.close();
			pWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)
	{
		convertSO cn = new convertSO();
		cn.read();
	}
}

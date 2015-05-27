package snaff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class convert {

	public void read()
	{
		File file1=new File("Mathdata/QuestionsTags_removeLowNumTags.txt");
		File file2=new File("Mathdata/QuestionsBothProcessed.txt");
		File pro=new File("Mathdata/tasBibLikeSort.dat");
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
				for(int i = 0; i < sp.length; i++)
				{
					if(num<=13469)
						pWriter.println(num+"\t"+sp[i]+"\t"+num+"\t1\t2008-07-31T21:42:52.667\t"+line2);
					else if(num<=17958){
						pWriter.println(num+"\t"+sp[i]+"\t"+num+"\t1\t2009-09-15T21:42:52.667\t"+line2);
					}
					else if(num<=19758){
						pWriter.println(num+"\t"+sp[i]+"\t"+num+"\t1\t2009-11-28T21:42:52.667\t"+line2);
					}
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
		convert cn = new convert();
		cn.read();
	}
}

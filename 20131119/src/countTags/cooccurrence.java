package countTags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class cooccurrence {

	public static void main(String[] args)
	{
		File file = new File("result/TagCoOccurrence.csv");
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			int linenum = 0;
			PrintWriter pw = new PrintWriter("result/TagCoOccurrenceMax.csv");
			while((line = br.readLine()) != null)
			{
				linenum++;
//				if(linenum > 100)
//					break;
//				System.out.println(line);
				String[] sp = line.split("\t");
				double union = Double.parseDouble(sp[4]);
				if(union > 0.005)
					pw.println(sp[0] + "\t" + sp[4]);
			}
			System.out.println(linenum);
			br.close();
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

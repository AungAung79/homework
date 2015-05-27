package user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountUser {
	public static void main(String[] args)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("User/QuestionsUser.txt")));
			BufferedReader br2 = new BufferedReader(new FileReader(new File("User/QuestionsTags.txt")));
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			PrintWriter pw = new PrintWriter("User/Count.txt");
			String temp;
			String temp2;
			int line = 0;
			while((temp = br.readLine()) != null && (temp2 = br2.readLine()) != null)
			{
				line++;
				if(line % 10000 == 0)
					System.out.println(line);
				if(map.containsKey(temp))
					map.put(temp, map.get(temp) + 1);
				else {
					map.put(temp, 1);
				}
			}
			for(Map.Entry<String, Integer> entry : map.entrySet())
			{
				String key = entry.getKey();
				int num = entry.getValue();
				pw.println(key + "\t" + num);
			}
			br.close();
			br2.close();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

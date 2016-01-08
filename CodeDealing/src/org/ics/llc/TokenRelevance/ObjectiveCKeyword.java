package org.ics.llc.TokenRelevance;

import java.io.PrintWriter;
import java.util.HashSet;

public class ObjectiveCKeyword {
	HashSet<String> keyword = new HashSet<String>();
	
	public ObjectiveCKeyword()
	{
		String word = "int float while char export sizeof typedef const struct for union "
				+"unsigned long volatile static bool mutable if do return goto void "
				+"enum else break extern asm case short default double register explicit "
				+"signed typename this switch continue wchar_t inline readonly assign "
				+"readwrite self @synchronized id typeof "
				+"nonatomic super unichar IBOutlet IBAction strong weak copy "
				+"in out inout bycopy byref oneway __strong __weak __block __autoreleasing "
				+"@private @protected @public @try @property @end @throw @catch @finally "
				+"@autoreleasepool @synthesize @dynamic @selector @optional @required";
		
		String other = "false true FALSE TRUE nil YES NO NULL "
				+"BOOL dispatch_once_t dispatch_queue_t dispatch_sync dispatch_async dispatch_once "
				+"@interface @class @protocol @implementation";
		
		String[] sp = word.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			keyword.add(sp[i].toLowerCase());
		}
		
		sp = other.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			keyword.add(sp[i].toLowerCase());
		}
	}
	
	public int getRelevance(String s)
	{
		String[] sp = s.split(" ");
		int relevance = 0;
		for(int i = 0; i < sp.length; i++)
		{
			if(keyword.contains(sp[i]))
				relevance++;
		}
		return relevance;
	}
	
	public void printKeyword()
	{
		try {
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Token/objckeyword.txt");
			for (String s : keyword) {
				pw.println(s);
			}
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

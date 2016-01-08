package org.ics.llc.TokenRelevance;

import java.io.PrintWriter;
import java.util.HashSet;

public class CPPKeyword {
	HashSet<String> keyword = new HashSet<String>();
	
	public CPPKeyword()
	{
		String word = "false int float while private char catch export virtual operator sizeof "
				+"dynamic_cast|10 typedef const_cast|10 const struct for static_cast|10 union namespace "
				+"unsigned long volatile static protected bool template mutable if public friend "
				+"do goto auto void enum else break extern using true class asm case typeid "
				+"short reinterpret_cast|10 default double register explicit signed typename try this "
				+"switch continue inline delete alignof constexpr decltype "
				+"noexcept nullptr static_assert thread_local restrict _Bool complex _Complex _Imaginary "
				+"atomic_bool atomic_char atomic_schar "
				+"atomic_uchar atomic_short atomic_ushort atomic_int atomic_uint atomic_long atomic_ulong atomic_llong "
				+"atomic_ullong";
		String built_in = "std string cin cout cerr clog stringstream istringstream ostringstream "
				+"auto_ptr deque list queue stack vector map set bitset multiset multimap unordered_set "
				+"unordered_map unordered_multiset unordered_multimap array shared_ptr abort abs acos "
				+"asin atan2 atan calloc ceil cosh cos exit exp fabs floor fmod fprintf fputs free frexp "
				+"fscanf isalnum isalpha iscntrl isdigit isgraph islower isprint ispunct isspace isupper "
				+"isxdigit tolower toupper labs ldexp log10 log malloc memchr memcmp memcpy memset modf pow "
				+"printf putchar puts scanf sinh sin snprintf sprintf sqrt sscanf strcat strchr strcmp "
				+"strcpy strcspn strlen strncat strncmp strncpy strpbrk strrchr strspn strstr tanh tan "
				+"vfprintf vprintf vsprintf include";
		
		String[] sp = word.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			keyword.add(sp[i].toLowerCase());
		}
		
		sp = built_in.split(" ");
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
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Token/cppkeyword.txt");
			for (String s : keyword) {
				pw.println(s);
			}
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

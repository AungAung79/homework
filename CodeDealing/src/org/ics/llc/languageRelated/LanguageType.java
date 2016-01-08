package org.ics.llc.languageRelated;

public enum LanguageType {
	CSharp("c#",0),
	CPlusPlus("c++",1),
	C("c",2),
	CSS("css",3),
	HTML("html",4),
	Java("java",5),
	JavaScript("javascript",6),
	ObjectiveC("objective-c",7),
	PHP("php",8),
	Perl("perl",9),
	Python("python",10),
	Ruby("ruby",11),
	SQL("sql",12);
	
	private String _name;
	private int _id;
	
	LanguageType(String name, int id)
	{
		_name = name;
		_id = id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String toString()
	{
		return _name;
	}
}

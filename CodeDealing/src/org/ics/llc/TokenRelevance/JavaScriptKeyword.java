package org.ics.llc.TokenRelevance;

import java.io.PrintWriter;
import java.util.HashSet;

public class JavaScriptKeyword {
	HashSet<String> keyword = new HashSet<String>();
	
	public JavaScriptKeyword()
	{
		String word = "in of if for while finally var new function do return void else break catch "
				+"instanceof with throw case default try this switch continue typeof delete "
				+"let yield const export super debugger as async await";
		
		String built_in = "eval isFinite isNaN parseFloat parseInt decodeURI decodeURIComponent "
				+"encodeURI encodeURIComponent escape unescape Object Function Boolean Error "
				+"EvalError InternalError RangeError ReferenceError StopIteration SyntaxError "
				+"TypeError URIError Number Math Date String RegExp Array Float32Array "
				+"Float64Array Int16Array Int32Array Int8Array Uint16Array Uint32Array "
				+"Uint8Array Uint8ClampedArray ArrayBuffer DataView JSON Intl arguments require "
				+"module console window document Symbol Set Map WeakSet WeakMap Proxy Reflect "
				+"Promise";
		
		String literal = "true false null undefined NaN Infinity";
		
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
		
		sp = literal.split(" ");
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
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Token/jskeyword.txt");
			for (String s : keyword) {
				pw.println(s);
			}
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

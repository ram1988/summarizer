package com.summarizer;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.NPChunker;
import util.StopWords;
import edu.illinois.cs.cogcomp.lbj.chunk.Chunker;
import edu.illinois.cs.cogcomp.lbj.pos.POSTagger;
import LBJ2.nlp.seg.Token;
import LBJ2.nlp.SentenceSplitter;
import LBJ2.nlp.WordSplitter;
import LBJ2.nlp.seg.PlainToTokenParser;
import LBJ2.parse.ChildrenFromVectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String paragraph = "Traversal of a doubly-linked list can be in either direction. In fact, the direction of traversal can change many times, if desired.";
    	
    	NPChunker chunker = new NPChunker();
    	Map<String,Integer> nounPhrases = chunker.getNounPhrases(paragraph);
    	
    	String[] sentences = chunker.getSentences(paragraph);
    	
    	System.out.println(nounPhrases);
    	int[] scores = new int[sentences.length];
    	int i = 0;
    	for(String sent:sentences) {
    		Map<String,Integer> nounPhrase = chunker.getNounPhrases(sent);
    		Set<Map.Entry<String,Integer>> entryMap = nounPhrase.entrySet();
    		
    		scores[i] = 0;
    		
    		for(Map.Entry<String, Integer> entry:entryMap) {
    			scores[i] = scores[i]+entry.getValue();
    		}
    		
    		//scores[i] = scores[i] * nounPhrase.size(); 
    		//multiplication of Sum of term frq and no. of nouns in the sentences
    		System.out.println("Sentence--"+i+"---"+scores[i]);
    		i++;
    	}
    	
    	
    }
}

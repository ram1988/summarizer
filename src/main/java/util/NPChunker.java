package util;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import LBJ2.nlp.SentenceSplitter;
import LBJ2.nlp.WordSplitter;
import LBJ2.nlp.seg.PlainToTokenParser;
import LBJ2.nlp.seg.Token;
import edu.illinois.cs.cogcomp.lbj.chunk.Chunker;
import edu.illinois.cs.cogcomp.lbj.pos.POSTagger;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class NPChunker {

	
	public Map<String,Integer> getNounPhrases(String para) {
		
		
		Map<String,Integer> nounPhrases = new HashMap<String,Integer>();
    	String[] sentenceArr = getSentences(para.toLowerCase());
    	
		//String[] sentenceArr = {"I am first. I am second."};
		Chunker chunker = new Chunker();
    	
    	PlainToTokenParser parser =
          new PlainToTokenParser(new WordSplitter(new SentenceSplitter(sentenceArr)));
        Token w = null;
        String np = "";
        
        while(( w = (Token) parser.next())!=null) {
        	String tag = chunker.discreteValue(w);
        	if(!StopWords.isStopword(w.form)) {
        		if(tag.contains("NP")) {
        			np = np+w.form+" ";
        			continue;
        		}
        		
        	} 
    		
    		if(!np.equals("")) {
        		int ct = 1;
        		np = np.trim();
        		if(nounPhrases.containsKey(np)) {
        			ct = nounPhrases.get(np)+1;
        		} 
        		nounPhrases.put(np, ct);
        		np = "";
    		}
        }
        
        return nounPhrases;
	}
	
	public String[] getSentences(String para) {
		Reader reader = new StringReader(para);
    	DocumentPreprocessor dp = new DocumentPreprocessor(reader);

    	List<String> sentenceList = new LinkedList<String>();
    	Iterator<List<HasWord>> it = dp.iterator();
    	while (it.hasNext()) {
    	   StringBuilder sentenceSb = new StringBuilder();
    	   List<HasWord> sentence = it.next();
    	   
    	   for (HasWord token : sentence) {
    	      if(sentenceSb.length()>1) {
    	         sentenceSb.append(" ");
    	      }
    	      sentenceSb.append(token);
    	   }
    	   sentenceList.add(sentenceSb.toString());
    	}
    	
    	String[] sentenceArr = new String[sentenceList.size()];
    	sentenceArr = sentenceList.toArray(sentenceArr);
    	
    	return sentenceArr;
	}
	
	/*
	public String getTag() {
		
	}*/
}

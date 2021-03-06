package iitb.rbased.util;

import java.util.ArrayList;

/**
 * The need of this class arises from the fact that there we cannot treat words as just words 
 * There is a lot of meta information that is associated with the word and the corresponding index
 * @author aman
 *
 */
public class Word {
	private String val; //the string content of the word
	private Integer idx; //the index of the word in that sentence
	//the start and the end offset of this word in this sentence
	private int startOff;
	private int endOff;
	public Word(Integer idx, String str) {
		this.idx = idx;
		this.val = str.toLowerCase();
	}
	public Word(Integer idx, String str, Integer startOff, Integer endOff) {
		this.idx = idx;
		this.val = str.toLowerCase();
		this.startOff = startOff;
		this.endOff = endOff;
	}
	public Word(Word w) {
		val = new String(w.getVal());
		idx = w.idx;
		startOff = w.startOff;
		endOff = w.endOff;
	}
	public Word() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "(" + val + ", " + idx.toString() + ")";
//		return val;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		result = prime * result + ((val == null) ? 0 : val.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		Word other = (Word) obj;
		if (idx == null) {
			if (other.idx != null)
				return false;
		} else if (!(idx == other.idx))
			return false;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}
	
	public static boolean wordListContainsVal(ArrayList<Word> list, String val) {
		for (Word word : list) { //iterate over the word and see if there is a match
			if (word.val.equals(val)) {
				return true;
			}
		}
		return false;
	}

	public void setStartOff(Integer startOff) {
		this.startOff = startOff;
	}

	public void setEndOff(Integer endOff) {
		this.endOff = endOff;
	}

	public Integer getStartOff() {
		return startOff;
	}

	public Integer getEndOff() {
		return endOff;
	}
}

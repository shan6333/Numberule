//sg
package util.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import meta.ModifyingTypes;
import util.Number;
import util.Word;
import edu.stanford.nlp.trees.TreeGraphNode;
import edu.stanford.nlp.trees.TypedDependency;

/**
 * An unweighted, undirected graph The graph is stored in adjacency list
 * representation each of the nodes has a number, and there are maps that store
 * the string value stored at each of the nodes "Word" wraps both the value at a
 * position and the index
 */
public class Graph {
	private HashMap<Word, ArrayList<Word> > adjMap; // the adjacency map
	
	//Mapping from index to the corresponding node, required
	//for fast graph ops
	private HashMap<Integer, Word> nodeWordMap;
	private HashMap<Word, Integer> wordNodeMap; 
	
	//A mapping from all the word vals to corresponding indexes
	private HashMap<String, Integer> valIdxMap;
	//Modifiers of the words
	private HashMap<Word, HashSet<Word> > modifiersMap;
	
	
	
	public final static int MAX = 1000;
	
	
	private Graph() {
		adjMap = new HashMap<Word, ArrayList<Word> >();
		nodeWordMap = new HashMap<Integer, Word>();
		wordNodeMap = new HashMap<Word, Integer>();
		modifiersMap = new HashMap<>();
		valIdxMap = new HashMap<String, Integer>();
	}
	
	/**
	 * Floyd-Warshall All pairs shortest path
	 */
	/*
	private void allPairs() {
		int dist[][] = new int[numNodes + 2][numNodes + 2];
		for(int i = 0; i < adj.size(); i++) {
			ArrayList<Integer> adjI = adj.get(i);
		//	System.out.println(adjI);
			for(int j = 0; j < adjI.size(); j++) {
				dist[i][adjI.get(j)] = 1;
			}
		}
		for(int i = 0; i < numNodes; i++) {
			for(int j = 0; j < numNodes; j++) {
				dist[i][j] = MAX;
			}
		}
		//Arrays.fill(dist, Integer.MAX_VALUE);
		for(int  k = 0; k < numNodes; k++) {
			for(int i = 0; i < numNodes; i++) {
				for(int j = 0; j < numNodes; j++) {
					dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
				}
			}
		}
		
		for(int i = 0; i < numNodes; i++) {
			for(int j = 0; j < numNodes; j++) {
				if(dist[i][j] != MAX) {
					Word w1 = nodeWordMap.get(i);
					Word w2 = nodeWordMap.get(j);
					if(null == w1 || null == w2) continue;
					Pair<Word, Word> keyPair = new Pair<Word, Word>(w1, w2);
					System.out.println(w1.getVal() + " - " + w2.getVal() + " = " + dist[i][j]);
					pathLenMap.put(keyPair, dist[i][j]);
				}
			}
		}
	}
	*/

	/**
	 * The factory method that takes the typed dependencies and returns a graph
	 * @param tdi
	 * @return
	 */
	public static Graph makeDepGraph(Iterator<TypedDependency> tdi) {
		Graph depGraph = new Graph();

		/**
		 * Add nodes to the graph
		 */
		while (tdi.hasNext()) {
			TypedDependency td1 = tdi.next();
			TreeGraphNode depNode = td1.dep();
			TreeGraphNode govNode = td1.gov();

			Word govWord = new Word(govNode.index(), govNode.value().toLowerCase());
			Word depWord = new Word(depNode.index(), depNode.value().toLowerCase());
			
			depGraph.addNode(depNode.index(), depWord);
			depGraph.addNode(govNode.index(), govWord);
			
			//undirected, add edges between depWord and govWord
			depGraph.addEdge(depWord, govWord);
			depGraph.addEdge(govWord, depWord);
	
			// governor is being modified
			
			if (ModifyingTypes.isModifier(td1.reln().toString())) {
				
				//dependencies are bidirectional
				depGraph.addModifier(govWord, depWord);
				depGraph.addModifier(depWord, govWord);
			}
			// System.out.println(govNode.value() + " -> " + depNode.value());
		}
		// depGraph.listModifiers();
		System.out.println(depGraph.adjMap.keySet());
		for(Word w : depGraph.wordNodeMap.keySet()) {
			depGraph.valIdxMap.put(w.getVal(), w.getIdx());
		}
		//depGraph.allPairs();
		return depGraph;

	}

	// undirected graph
	public void addEdge(Word a, Word b) {
		System.out.println("Adding : " + a + " -> " + b);
		if(adjMap.keySet().contains(a)) {
			adjMap.get(a).add(b);
		} else {
			ArrayList<Word> adjListA = new ArrayList<Word>();
			adjListA.add(b);
			adjMap.put(a, adjListA);
		}
	}

	/**
	 * Returns the neighboring words of w
	 * @param curr
	 * @return
	 */
	public ArrayList<Word> getNbr(Word curr) {
		System.out.println(adjMap.keySet());
		return adjMap.get(curr);
	}

	/**
	 * All the words are always stored in lower case
	 * 
	 * @param pos
	 * @param word
	 */
	public void addNode(int pos, Word w) {
		nodeWordMap.put(pos, w);
		wordNodeMap.put(w, pos);
	}

	public ArrayList<Word> getWordsOnPath(Word src, Word des) {
		return BFS.getPath(this, src, des);
	}

	public String getLabel(int num) {
		if(nodeWordMap.containsKey(num))
			return nodeWordMap.get(num).getVal();
		else
			return null;
	}

	public Integer getIdx(String ip) {
		ip  = ip.toLowerCase(); //everything is stored in graph in lower case
		if(valIdxMap.containsKey(ip))
			return valIdxMap.get(ip);
		else
			return null;
	}

	public void addModifier(Word moddedWord, Word modifier) {
		if(modifiersMap.containsKey(moddedWord)) {
			modifiersMap.get(moddedWord).add(modifier);
		} else {
			HashSet<Word> tmp = new HashSet<Word>();
			tmp.add(modifier);
			modifiersMap.put(moddedWord, tmp);
		}
		
	}

	public HashSet<Word> getModifiers(Word modifiedWord) {
		if(modifiersMap.containsKey(modifiedWord))
			return modifiersMap.get(modifiedWord);
		
		return null;
	}

	public void listModifiers() {
		for (Word word : modifiersMap.keySet()) {
			System.err.println(word + " -> " + modifiersMap.get(word));
		}
	}

	public int distance(Word country, Number currNumber) {
		return getWordsOnPath(country, currNumber).size();
	
	}

}
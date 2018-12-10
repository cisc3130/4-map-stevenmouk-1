import java.util.*;

public class Bigrams {

	public static class Pair<T1, T2> {
		public T1 first;
		public T2 second;
		public Pair(T1 first, T2 second) {
			this.first = first;
			this.second = second;
		}
	}

	protected Map<Pair<String, String>, Float> bigramCounts;
	protected Map<String, Float> unigramCounts;

	// TODO: Given filename fn, read in the file word by word
	// For each word:
	// 1. call process(word)
	// 2. increment count of that word in unigramCounts
	// 3. increment count of new Pair(prevword, word) in bigramCounts
	public Bigrams(String fn) {

	}

	// TODO: Given words w1 and w2,
	// 1. replace w1 and w2 with process(w1) and process(w2)
	// 2. print the words
	// 3. if bigram(w1, w2) is not found, print "Bigram not found"
	// 4. print how many times w1 appears
	// 5. print how many times (w1, w2) appears
	// 6. print count(w1, w2)/count(w1)
	public float lookupBigram(String w1, String w2) {
		return (float) 0.0;
	}

	protected String process(String str) {
		return str.toLowerCase().replaceAll("[^a-z]", "");
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Bigrams <FILENAME>");
			System.out.println(args.length);
			return;
		}

		Bigrams bg = new Bigrams(args[0]);

		List<Pair<String, String>> wordpairs = Arrays.asList(
			new Pair("with", "me"), 
			new Pair("the", "grass"),
			new Pair("the", "king"),
			new Pair("to", "you")
		);

		for (Pair<String, String> p : wordpairs) {
			bg.lookupBigram(p.first, p.second);
		}

		System.out.println(bg.process("adddaWEFEF38234---+"));
	}

}

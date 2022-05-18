import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Bigrams {

	public static class Pair<T1, T2> {
		public T1 first;
		public T2 second;
		public int hashCode;

		public Pair(T1 first, T2 second) {
			this.first = first;
			this.second = second;
			this.hashCode = Objects.hash(first, second);
		}

		@Override
		public boolean equals(Object other) {
			if (this == other)
				return true;
			if (other == null || getClass() != other.getClass())
				return false;
			Pair<String, String> otherPair = (Pair<String, String>) other;
			return this.first.equals(otherPair.first) && this.second.equals(otherPair.second);
		}

		@Override
		public int hashCode() {
			return this.hashCode;
		}
	}

	protected Map<Pair<String, String>, Float> bigramCounts;
	protected Map<String, Float> unigramCounts;
	ArrayList<String> wordList;

	// TODO: Given filename fn, read in the file word by word
	// For each word:
	// 1. call process(word)
	// 2. increment count of that word in unigramCounts
	// 3. increment count of new Pair(prevword, word) in bigramCounts
	public Bigrams(String fn) {

		try {
			// reads in a file based on the fine name fn
			File file = new File("./gutenberg/" + fn);

			// arraylist to hold the inital words of the file
			wordList = new ArrayList<String>(100000000);

			// creates the hashmaps for unigram and bigrams
			bigramCounts = new HashMap<>();
			unigramCounts = new HashMap<>();

			// scanner to read in the file
			Scanner scanner = new Scanner(file);

			// loops over the file to read in each line
			while (scanner.hasNextLine()) {

				// splits each line into words and adds those words to the word list array
				wordList.addAll(Arrays.asList(scanner.nextLine().split("\\p{javaSpaceChar}+")));
			}

			// closes the scanner and trims the wordlist arrayList
			scanner.close();
			wordList.trimToSize();

			// loops over wordlist array
			for (int i = 0; i < wordList.size(); i++) {

				// calls process method on each word in wordlist
				String processedWord = process(wordList.get(i));

				// adds each word as the key in the hashmap and 1 for value if never seen, adds
				// 1 if that key is seen again
				unigramCounts.merge(processedWord, 1F, Float::sum);

				// makes sure that the previousword is not null
				if (i != 0) {

					// class process method on the previous word
					String prevProcessedWord = process(wordList.get(i - 1));

					// adds the current word and the previous word to a pair
					Pair<String, String> pair = new Pair<>(prevProcessedWord, processedWord);

					// adds the pair as a key to bigrams with 1 if not seen before and adds 1 if
					// seen again
					bigramCounts.merge(pair, 1F, Float::sum);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// TODO: Given words w1 and w2,
	// 1. replace w1 and w2 with process(w1) and process(w2)
	// 2. print the words
	// 3. if bigram(w1, w2) is not found, print "Bigram not found"
	// 4. print how many times w1 appears
	// 5. print how many times (w1, w2) appears
	// 6. print count(w1, w2)/count(w1)
	public float lookupBigram(String w1, String w2) {

		// processes both words w1 and 2
		w1 = process(w1);
		w2 = process(w2);

		// makes a pair with words w1 and w2
		Pair pair = new Pair(w1, w2);

		// checks in pair is in Bigramcounts. Prints not found if pair is not in
		// bigramcounts
		if (!bigramCounts.containsKey(pair)) {
			System.out.println("Bigram not found");
			return -1f;
		}

		// prints w1
		System.out.println("w1:" + w1);

		// prints w2
		System.out.println("w2:" + w2);

		// prints w1 count
		System.out.println(w1 + " appears " + unigramCounts.get(w1));

		// prints the bigram count
		System.out.println("Bigram of " + w1 + " and " + w2 + " appears " +
				bigramCounts.get(pair));

		// prints the bigramCount/unigram count
		System.out.println("BigramCount/unigramCount: " + bigramCounts.get(pair) /
				unigramCounts.get(w1));
		System.out.println("");

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
				new Pair("to", "you"));

		for (Pair<String, String> p : wordpairs) {
			bg.lookupBigram(p.first, p.second);
		}

		System.out.println(bg.process("adddaWEFEF38234---+"));
	}

}

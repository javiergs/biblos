package javiergs;

/**
 * A genius that can think, write and read.
 */
public class Genius {
	
	/**
	 * Thinks.
	 *
	 * @return a message indicating that the genius is thinking.
	 */
	public String think() {
		return ("I'm thinking...");
	}
	
	/**
	 * Writes text on a paper.
	 *
	 * @param paper the paper where the text will be written.
	 * @param text the text to write.
	 */
	public void write(Paper paper, String text) {
		paper.write(text);
	}
	
	/**
	 * Reads text from a paper.
	 *
	 * @param paper the paper to read.
	 * @return the text read from the paper.
	 */
	public String read (Paper paper) {
		return paper.read();
	}
	
}
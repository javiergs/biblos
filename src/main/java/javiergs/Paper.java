package javiergs;

/**
 * Represents a paper sheet.
 */
public interface Paper {
	
	/**
	 * Writes text on the paper.
	 *
	 * @param text the text to write.
	 */
	void write(String text);
	
	/**
	 * Reads the text written on the paper.
	 *
	 * @return the text written on the paper.
	 */
	String read();
	
}
package de.caydenno1.cxvqoignore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CxvqoIgnoreRegexes {
	private static final List<Pattern> patterns = new ArrayList<>();

	static {
		patterns.add(Pattern.compile("(?:.*cxvqo.*)", Pattern.CASE_INSENSITIVE));
		patterns.add(Pattern.compile("(?:.*denisapain.*)", Pattern.CASE_INSENSITIVE));
	}
 
	public static void add(String regex) throws PatternSyntaxException {
		patterns.add(Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
	}

	public static boolean remove(int index) {
		if (index < 0 || index >= patterns.size()) {
			return false;
		}
		patterns.remove(index);
		return true;
	}

	public static List<Pattern> getPatterns() {
		return Collections.unmodifiableList(patterns);
	}

	public static boolean shouldIgnore(String message) {
		for (Pattern pattern : patterns) {
			if (pattern.matcher(message).matches()) {
				return true;
			}
		}
		return false;
	}
}
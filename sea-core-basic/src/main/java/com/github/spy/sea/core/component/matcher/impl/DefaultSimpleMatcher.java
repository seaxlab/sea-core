package com.github.spy.sea.core.component.matcher.impl;

import com.github.spy.sea.core.component.matcher.SimpleMatcher;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * module name
 * <p>
 * org.apache.commons.io.FilenameUtils.wildcardMatch
 *
 * @author spy
 * @version 1.0 2021/12/28
 * @since 1.0
 */
@Slf4j
public class DefaultSimpleMatcher implements SimpleMatcher {

    @Override
    public boolean match(String target, String wildcard) {
        if (target == null && wildcard == null) {
            return true;
        }
        if (target == null || wildcard == null) {
            return false;
        }
        final String[] wcs = splitOnTokens(wildcard);
        boolean anyChars = false;
        int textIdx = 0;
        int wcsIdx = 0;
        final Deque<int[]> backtrack = new ArrayDeque<>(wcs.length);

        // loop around a backtrack stack, to handle complex * matching
        do {
            if (!backtrack.isEmpty()) {
                final int[] array = backtrack.pop();
                wcsIdx = array[0];
                textIdx = array[1];
                anyChars = true;
            }

            // loop whilst tokens and text left to process
            while (wcsIdx < wcs.length) {

                if (wcs[wcsIdx].equals("?")) {
                    // ? so move to next text char
                    textIdx++;
                    if (textIdx > target.length()) {
                        break;
                    }
                    anyChars = false;

                } else if (wcs[wcsIdx].equals("*")) {
                    // set any chars status
                    anyChars = true;
                    if (wcsIdx == wcs.length - 1) {
                        textIdx = target.length();
                    }

                } else {
                    // matching text token
                    if (anyChars) {
                        // any chars then try to locate text token
                        textIdx = checkIndexOf(target, textIdx, wcs[wcsIdx]);
                        if (textIdx == NOT_FOUND) {
                            // token not found
                            break;
                        }
                        final int repeat = checkIndexOf(target, textIdx + 1, wcs[wcsIdx]);
                        if (repeat >= 0) {
                            backtrack.push(new int[]{wcsIdx, repeat});
                        }
                    } else {
                        // matching from current position
                        if (!checkRegionMatches(target, textIdx, wcs[wcsIdx])) {
                            // couldnt match token
                            break;
                        }
                    }

                    // matched text token, move text index to end of matched token
                    textIdx += wcs[wcsIdx].length();
                    anyChars = false;
                }

                wcsIdx++;
            }

            // full match
            if (wcsIdx == wcs.length && textIdx == target.length()) {
                return true;
            }

        } while (!backtrack.isEmpty());

        return false;
    }

    private String[] splitOnTokens(final String text) {
        // used by wildcardMatch
        // package level so a unit test may run on this

        if (text.indexOf('?') == NOT_FOUND && text.indexOf('*') == NOT_FOUND) {
            return new String[]{text};
        }

        final char[] array = text.toCharArray();
        final ArrayList<String> list = new ArrayList<>();
        final StringBuilder buffer = new StringBuilder();
        char prevChar = 0;
        for (final char ch : array) {
            if (ch == '?' || ch == '*') {
                if (buffer.length() != 0) {
                    list.add(buffer.toString());
                    buffer.setLength(0);
                }
                if (ch == '?') {
                    list.add("?");
                } else if (prevChar != '*') {// ch == '*' here; check if previous char was '*'
                    list.add("*");
                }
            } else {
                buffer.append(ch);
            }
            prevChar = ch;
        }
        if (buffer.length() != 0) {
            list.add(buffer.toString());
        }

        return list.toArray(EMPTY_STRING_ARRAY);
    }

    private static final int NOT_FOUND = -1;
    private static final String[] EMPTY_STRING_ARRAY = new String[0];


    //
    public int checkIndexOf(final String str, final int strStartIndex, final String search) {
        final int endIndex = str.length() - search.length();
        if (endIndex >= strStartIndex) {
            for (int i = strStartIndex; i <= endIndex; i++) {
                if (checkRegionMatches(str, i, search)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Checks if one string contains another at a specific index using the case-sensitivity rule.
     * <p>
     * This method mimics parts of {@link String#regionMatches(boolean, int, String, int, int)}
     * but takes case-sensitivity into account.
     *
     * @param str           the string to check, not null
     * @param strStartIndex the index to start at in str
     * @param search        the start to search for, not null
     * @return true if equal using the case rules
     * @throws NullPointerException if either string is null
     */
    public boolean checkRegionMatches(final String str, final int strStartIndex, final String search) {
        //return str.regionMatches(!sensitive, strStartIndex, search, 0, search.length());
        return str.regionMatches(strStartIndex, search, 0, search.length());
    }

}

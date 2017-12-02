/*
 */

package com.nitsoft.util;

import com.nitsoft.util.EmailUtil;
import com.nitsoft.util.FileUtil;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.security.auth.x500.X500Principal;


/**
 * Miscellaneous String processing methods.
 *
 * @author Yasufuku Hiromi
 */
public class StringUtil
{

    /** Logging category */

    //////////////////////////////////////////////////////////////
    // Constructors and initialisation.

    /**
     * (Private constructor) Never instantiated.
     */
    private StringUtil() {
    }


    //////////////////////////////////////////////////////////////
    // Public class methods.

    /**
     * Find and replace the specified substring within the original string with
     * the given replacement, and return the result.
     */
    public static String findAndReplace(String orig, String sub, String rep) {

        StringBuffer out = new StringBuffer();
        int index = 0;
        int oldIndex = index;
        while (index != -1) {
            index = orig.indexOf(sub, index);
            if (index != -1) {
                out.append(orig.substring(oldIndex, index));
                index += sub.length();
                oldIndex = index;
                out.append(rep);
            } else {
                out.append(orig.substring(oldIndex));
            }
        }

        return out.toString();
    }

    /**
     * For a given string orig, find and replace every instance of key strings
     * with their respective values.
     *
     * @param orig The original string.
     * @param sub The keys to find and their respective values to replace with.
     */
    public static String findAndReplaceAll(String orig, Map sub) {
       Iterator i = sub.keySet().iterator();
       while (i.hasNext()) {
           String key = (String) i.next();
           String value = (String) sub.get(key);
           if (value == null) {
              
              continue;
           }
           orig = findAndReplace(orig, key, value);
       }
       return orig;
    }

    /**
     * Convert an int to a String and pad it with zeros on the left making
     * it <code>digits</code> digits wide. If the int is already this wide
     * or wider, it will be unchanged. Any minus sign at the front will be
     * preserved, and not included in the length to which the string is
     * padded.
     */
    public static String zeroPadInt(int number, int digits) {
        String s = String.valueOf(number);
       if (s.length() >= digits)
           return s;

       // Digits is never negative here.
       int finalLength = digits + 1;
       if (finalLength < 0)              // Arithmetic overflow.
           finalLength = digits;

       StringBuffer out = new StringBuffer(finalLength);
       if (s.charAt(0) == '-') {
           out.append("-");
           int numZeros = digits - s.length() + 1;
           for (int i=0; i<numZeros; i++) {
              out.append("0");
           }
           out.append(s.substring(1));
       } else {
           int numZeros = digits - s.length();
           for (int i=0; i<numZeros; i++) {
              out.append("0");
           }
           out.append(s);
       }
       s = out.toString();
        return s;
    }

    /**
     * Attempt to extract a single line from a stacktrace indicating whence
     * a method was invoked. This is slow, and depends on the stacktrace
     * being multiple lines in the format "at package.class.method ...".
     *
     * @param t              A {@link Throwable} containing a stacktrace. Normally
     *                     this is created with: <blockquote><code>
     *                            Throwable t = new Throwable(); <br>
     *                            t.fillInStackTrace();
     *                     </code></blockquote> If null, this method will return
     *                     an empty String.
     *
     * @param me       The package name or fully qualified class name of
     *                     the method being invoked. All lines in the stacktrace
     *                     that contain this string will be skipped over, and
     *                     the next line in the stacktrace will be returned.
     *
     * @return              A string containing a line describing the class,
     *                     method, and possibly line number of the deepest
     *                     invoker not containing the string <code>me</code>.
     *                     This may be the string <code>"unknown"</code> if
     *                     the invoker cannot be determined.
     */
    public static String extractCaller(Throwable t, String me) {
       try {
           if (t == null || me == null)
              return "";

           // Get the stack trace out of the Throwable as a String.
           // It sucks that we have to do it this way.
           StringWriter swriter = new StringWriter(512);
           PrintWriter pwriter = new PrintWriter(swriter);
           t.printStackTrace(pwriter);
           pwriter.close();
           String st = swriter.toString();

           // Find the last line with me in it.
           int index = st.indexOf(me);
           int nextIndex = st.indexOf(me, index + 1);
           while (nextIndex >= 0) {
              index = nextIndex;
              nextIndex = st.indexOf(me, index + 1);
           }

           // Find the beginning and end of the next line.
           index = st.indexOf("at ", index) + 3;
           nextIndex = st.indexOf('\n', index);
           while (st.charAt(nextIndex) == '\r' || st.charAt(nextIndex) == '\r')
              nextIndex--;

           return st.substring(index, nextIndex + 1).trim();
       } catch (Exception e) {
//           Category log = Category.getInstance("net.keyring.util.StringUtil");
//           log.warn("Caught exception in debugging code", e);
           // Given that this is debugging code, we never want it to fail.
           return "(Method failed; exception logged.)";
       }
    }

    /**
     * Test whether a path is "safe" - i.e. doesnt contain ..'s and
     * is not absolute.
     * @param p path to check
     */
    public static boolean safePath(String p) {
       return !p.startsWith("/") && !p.startsWith("../")
           && !p.endsWith("/..") && p.indexOf("/../") == -1;
    }

    /**
     * Return a String containing the entire contents of the given file.
     * This will be as efficient as the {@link java.io.RandomAccessFile}
     * class is. (And that can be quite efficient if implemented with
     * the right native code.)
     *
     * @param file       The file to read.
     * @param encoding       The encoding to use to convert the bytes in the
     *                     file to a String. If this is null, the `conversion'
     *                     will be simply to set the top eight bits of each
     *                     char to zero, and the bottom to the byte.
     */
    public static String readFile(File file, String encoding)
       throws IOException, UnsupportedEncodingException
    {
       RandomAccessFile in = new RandomAccessFile(file, "r");
       long length = in.length();

       if (length <= 0) {
           in.close();
           return "";
       }

       if (length > (Integer.MAX_VALUE)) {
           in.close();
           throw new IOException("File too large: " + length + " bytes > "
              + Integer.MAX_VALUE + " bytes.");
       }

       byte[] contents = new byte[(int) length];
       in.readFully(contents);
       in.close();

       if (encoding == null)
           return new String(contents, (byte) 0);
       else
           return new String(contents, encoding);
    }

    /**
     * Return a byte array containing the entire contents of the given
     * InputStream. This reads the InputStream up to EOF, but does not close
     * it.
     *
     * @param inputStream The InputStream to read.
     * @param encoding       The encoding to use to convert the bytes from the
     *                     input stream to a String. If this is null, the
     *                     `conversion' will be simply to set the top eight bits
     *                     of each char to zero, and the bottom to the byte.
     */
    public static byte[] readInputStream(InputStream input)
       throws IOException
    {
       if (input == null)
           return new byte[0];

       // Buffer the input stream, if it's not already buffered.
       BufferedInputStream in;
       if (input instanceof BufferedInputStream)
           in = (BufferedInputStream) input;
       else
           in = new BufferedInputStream(input);

       // Read in the data, chunk by chunk, until we hit EOF.
       LinkedList chunkList = new LinkedList();
       int totalLength = 0;
       while (true) {
           RISChunk chunk = new RISChunk();
           chunk.length = in.read(chunk.data);
           if (chunk.length == -1)
              break;       // EOF
           if (chunk.length > 0) {
              chunkList.add(chunk);
              totalLength += chunk.length;
           }
       }

       // Copy the chunks into one huge array.
       byte[] contents = new byte[totalLength];
       int index = 0;
       for (Iterator listi = chunkList.iterator(); listi.hasNext(); ) {
           RISChunk chunk = (RISChunk) listi.next();
           System.arraycopy(chunk.data, 0, contents, index, chunk.length);
           index += chunk.length;
       }

       return contents;
    }

    /** Used by readInputStream above. */
    private static class RISChunk
    {
       // If you change allocation size of this array,
       // also change the unit test borderline cases.
       final byte[] data = new byte[4096];
       int length;
    }

    /**
     * Expand c style \\ escapes escapes like C does.
     *
     */
    public static String unescapeBackslashedCharacters(String s) {
       StringBuffer sb = new StringBuffer(s.length() * 2);
       char[] buf = new char[s.length()];
       char c;

       s.getChars(0, s.length(), buf, 0);
       for (int i = 0; i < buf.length; i++) {
           if (buf[i] != '\\' || i == buf.length - 1) {
              sb.append(buf[i]);
              continue;
           }
           switch (buf[++i]) {
           case 'a':
              c = '\007';       /* Bell */
              break;
           case 'b':
              c = '\010';       /* Backspace */
              break;
           case 't':
              c = '\011';       /* Horizontal Tab */
              break;
           case 'n':
              c = '\012';       /* New Line */
              break;
           case 'v':
              c = '\013';       /* Vertical Tab */
              break;
           case 'f':
              c = '\014';       /* Form Feed */
              break;
           case 'r':
              c = '\015';       /* Carriage Return */
              break;
           case 'e':
              c = '\033';       /* Escape */
              break;
           case '0':
           case '1':
           case '2':
           case '3':
           case '4':
           case '5':
           case '6':
           case '7':
           {
              int b = 0;
              for (int cnt = 0; cnt < 3; cnt++) {
                  if (i == buf.length)
                     break;
                  char ch = buf[i++];
                  if (ch < '0' || ch > '7') {
                     i--;
                     break;
                  }
                  b = (b << 3) | (ch - '0');
              }
              i--;
              c = (char)b;
              break;
           }
           default:
              c = buf[i];
              break;
           }
           sb.append(c);
       }
       return sb.toString();
    }

    /**
     * Check string is ascii characters.
     *
     * @param s is a string to check
     */
    public static boolean isAscii(String s) {
       if ( s == null)
           return false;

       char[] chars = s.toCharArray();
       for (int i = 0; i < chars.length; i++) {
           if (chars[i] < 0x20 || chars[i] >= 0x7F)
              return false;
       }
       return true;
    }

    /**
     * Given a string, convert it to a hexadecimal string of the
     * form "0xn1n2n3n4n5..." where n1, n2, etc. are two-digit
     * hexadecimal numbers. This is a format that SQL server will
     * accept for an insert into a varchar. (The 0xn1n2... string
     * should not be quoted). We do this to handle characters in
     * japanese and other non-ascii strings.
     *
     * <p> If any chars > 255 are encountered in the String, they
     * will be converted to `3F' (a question mark).
     *
     * <p> In order to ensure that the result of this is valid when
     * placed into an INSERT or UPDATE statement, this returns an string
     * consisting of just two single quotes if the input string is empty.
     */
    public static String toSQLHexString(String s) {
        int length = s.length();
        if (length == 0)
            return "''";

        StringBuffer retval = new StringBuffer(length * 2 + 3);
        retval.append("0x");
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c > 0xff)
                c = 0x3f;
            if (c < 0x10)
                retval.append('0');
            retval.append(Integer.toHexString(c));
        }
        return retval.toString();
    }

    /**
     * Return <tt>true</tt> if <tt>container</tt> contains within
     * it the String <tt>contained</tt>. Either argument may be null,
     * in which case <tt>false</tt> will be returned.
     */
    public static boolean contains(String container, String contained) {
       if (container == null || contained == null)
           return false;
       if (contained.equals(""))
           return true;

       char firstChar = contained.charAt(0);
       int containedLength = contained.length();

       // contained must be within the first maxlen chars of the
       // container because after that it's longer than what remains.
       int maxlen = container.length() - contained.length();

       // We look for a possible start, which is the next place in
       // container where we see the first char of contained.
       // Then we try a full match at that point.
       int start = -1;
       while (start < maxlen) {
           start = container.indexOf(firstChar, start + 1);
           if (start < 0)
              break;
           if (container.regionMatches(start, contained, 0, containedLength))
              return true;
       }
       return false;
    }

    /**
     * Prepend a backslash (<tt>\</tt>) before every occurance of
     * any character in <tt>toEscape</tt>.
     *
     * <p> Note that if you want backslashes escaped, you must ensure
     * that backslash is a part of the <tt>toEscape</tt> String.
     *
     * @param string       The string that should have its instances of
     *                     <tt>toEscape</tt> preceeded by a backslash.
     * @param toEscape       The characters to escape with a backslash.
     *
     * @return       The String which added the escape sequence character.
     */
    public static String escapeWithBackslash(String string, String toEscape) {
       int strlen = string.length();
       int escapeCount = searchString(string, toEscape);
       if (escapeCount == 0)
           return string;

       StringBuffer buff = new StringBuffer(strlen + escapeCount);
       for (int i = 0; i < strlen; i++) {
           char c = string.charAt(i);
           if (toEscape.indexOf(c) >= 0)
              buff.append('\\');
           buff.append(c);
       }
       return buff.toString();
    }

    /**
     * Delete the character in <tt>delChars</tt> from <tt>string</tt>.
     *
     * @param string       String which eliminates the character
     *                     in <tt>delChars</tt>.
     * @param delChars       The characters to delete.
     *
     * @return       The String which deleted the character.
     */
    public static String deleteCharacters(String string, String delChars) {
       int strlen = string.length();
       int delCount = searchString(string, delChars);
       if (delCount == 0)
           return string;

       StringBuffer buff = new StringBuffer(strlen + delCount);
       for (int i = 0; i < strlen; i++) {
           char c = string.charAt(i);
           if (delChars.indexOf(c) == -1)
              buff.append(c);
       }
       return buff.toString();
    }

    /**
     * Replace the character in <tt>fromChars</tt> to <tt>toChar</tt>.
     *
     * @param string       String which replace the character
     *                     in <tt>fromChars</tt>.
     * @param fromChars       The characters to replace.
     * @param toChar       The character after conversion.
     *
     * @return       The String which replaced the character.
     */
    public static String replaceCharacters(String string, String fromChars,
           String toChar)
    {
       int strlen = string.length();
       int replaceCount = searchString(string, fromChars);
       if (replaceCount == 0)
           return string;

       StringBuffer buff = new StringBuffer(strlen + replaceCount);
       for (int i = 0; i < strlen; i++) {
           char c = string.charAt(i);
           if (fromChars.indexOf(c) >= 0)
              buff.append(toChar);
           else
                  buff.append(c);
       }
       return buff.toString();
    }

    /**
     * Count the search character in string.
     *
     * @param string       The string which should count the character
     *                     in <tt>search</tt>.
     * @param search       The character to search.
     *
     * @return       The number of the counted character.
     */
    private static int searchString(String string, String search) {
       int strlen = string.length();
       int searchCount = 0;
       for (int i = 0; i < strlen; i++) {
           if (search.indexOf(string.charAt(i)) >= 0)
              searchCount++;
       }
       return searchCount;
    }

    /**
     * Restriction of the number of characters.
     *
     * <p> Larger string than restrict is effective to restrict-3.
     * The omitted character is transposed to "...".
     *
     * @param string       String which restriction the character.
     * @param restrict       The number of restrictions
     *
     * @return       The string which restriction the character.
     */
    public static String restrictLength(String string, int restrict) {
       if (string.length() > restrict) {
           return string.substring(0, restrict - 3) + "...";
       } else {
           return string;
       }
    }

    /**
     * The comment included in the HTML string is removed.
     *
     * @param string       HTML string.
     *
     * @return       The HTML string by which the comment was removed.
     */
    public static String cutOutHtmlComment(String string) {
       return cutOutString(string, "<!--", "-->");
    }

    /**
     * The partial string surrounded by the specified token is removed.
     *
     * @param string       The string which removes a partial string.
     * @param startStr       The start string which shows a partial string.
     * @param endStr       The last string which shows a partial string.
     *
     * @return       The string by which the partial string was removed.
     */
    private static String cutOutString(String string, String startStr,
       String endStr)
    {
       int startLength = startStr.length();
       int endLength = endStr.length();
       StringBuffer buf = new StringBuffer();

       int startPos = string.indexOf(startStr);
       if (startPos == -1)
           return string;

       while (startPos >= 0) {
           buf.append(string.substring(0, startPos));
           string = string.substring(startPos + startLength);
           int endPos = string.indexOf(endStr);
           if (endPos == -1)
              return buf.toString();
           string = string.substring(endPos + endLength);
           startPos = string.indexOf(startStr);
       }
       buf.append(string);
       return buf.toString();
    }

    /**
     * Return the Unicode String corresponding to the given Shift_JIS string.
     * The high byte of each character in the parameter is ignored, and
     * the low bytes are treated as an Shift_JIS sequence.
     *
     * <p> This is basically a hack we use because our source code files
     * are in Shift_JIS format. If we switch them to UTF-8 (or whatever the
     * particular Java compiler we use cares to use), we will no longer need
     * this, as we'll be able to initialise strings directly.
     */
    public static String SJIS(String s) {
       try {
           byte[] bytes = s.getBytes("ISO-8859-1"); // Transparent.
           return new String(bytes, "Shift_JIS");
       } catch (UnsupportedEncodingException e) {
           // This can't legally happen, according to the Java spec.
           throw new InternalError("Encoding not supported: "
              + e.getMessage());
       }
    }

    public static Date getDate(TimeZone timezone, String strDate) {
	Calendar calendar = Calendar.getInstance(timezone);
       try {
           int year = Integer.parseInt(strDate.substring(0, 4));
           int month = Integer.parseInt(strDate.substring(4, 6));
           int day = Integer.parseInt(strDate.substring(6, 8));
           int hour = Integer.parseInt(strDate.substring(8, 10));
           int min = Integer.parseInt(strDate.substring(10, 12));
           int sec = Integer.parseInt(strDate.substring(12, 14));
          
           
           calendar.set(year, month - 1, day, hour, min, sec);
       } catch (NumberFormatException e) {
           return null;
       } catch (IndexOutOfBoundsException ee) {
           return null;
       }
       return  calendar.getTime();
    }

    public static Date getDate(String timezone, String strDate) {
        return getDate(TimeZone.getTimeZone(timezone), strDate);
    }

    /*
     * 与えられたDNを正規化して返します�??
     * DNと判断出来な�?場合�?�trim�?けして返します�??
     */
    public static String getCanonicalDN(String dn) {
       String canonical;
       try {
           canonical = new X500Principal(dn).getName(X500Principal.CANONICAL);
       } catch (IllegalArgumentException e) {
           canonical = dn.trim();
       }
       return canonical;
    }

    /**
     * <tt>str</tt> をHEXコード文字�?�にして返します�??
     * 変換後�?��?字�?��?� [0x + HEXコード] の書式に変換されます�??
     *
     * <p> 例：str="123"の場�?
     * <ul>
     *      <lu> return="0x31 0x32 0x33"
     * </ul>
     *
     * @param str   変換対象�?字�??
     *
     * @return  <tt>str</tt> のHEXコード変換後�?��?字�??
     *
     * @throws  IllegalArgumentException
     *          <tt>str</tt> に <tt>null</tt> を指定した�?�合にスローします�??
     */
    public static String toHex(String str)
        throws IllegalArgumentException
    {
        if (str == null)
            throw new IllegalArgumentException("str was null.");

        if (str.length() == 0)
            return "";

        StringBuffer sb = new StringBuffer();
        char[] cbuf = str.toCharArray();
        for (int idx = 0; idx < cbuf.length; idx++)
            sb.append("0x" + Integer.toHexString(cbuf[idx]) + " ");
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }


    /**
     * <tt>bytes</tt> �?16進数にして返します�??
     */
    private static final char _hexcodes[] =
            {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F'
            };
    private static final int _shifts[] =
            {
                28, 24, 20, 16, 12, 8, 4, 0
            };
    public static String toHexCode(byte[] bytes) {
	StringBuffer sb = new StringBuffer(bytes.length * 2);
	for (int i = 0; i < bytes.length; i++) {
	    for (int j = 0; j < 2; j++) {
		sb.append(_hexcodes[(bytes[i] >> _shifts[j + 6]) & 15]);
	    }
	}
        return sb.toString();
    }

	/**
	 * 引数で�?定された�?字�?�を?��文字毎に区�?り�?�それぞれを?��バイト�?�?��６�?�数とした
     * バイト�?��?�に変換して戻します�??
	 *
	 * @param �?字�??
	 * @return バイト�?��??
	 */
    public static byte[] toHexArray(String str) {
        if (str == null || str.trim().length() != 32)
            throw new IllegalStateException(
                    "Hex String was too short. [" + str + "]");
        byte[] bytes = new byte[16];
        for (int kIdx = 0, bIdx = 0; kIdx < str.length(); kIdx+=2, bIdx++)
            bytes[bIdx] =
            (byte) Integer.parseInt(str.substring(kIdx, kIdx + 2), 16);

        return bytes;
    }

    /**
     * �?定された�?字�?�がIntegerに変換可能な�?字�?�かど�?かを判断します�??
     *
     * @param str   チェ�?ク対象�?字�??
     *
     * @return  Integer変換可能な場合�?� <tt>true</tt> を返します�??
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * <tt>str</tt> が空�?字�?�場合に <tt>true</tt> を返します�??
     *
     * @param   str     検査する�?字�?��??
     * @param   trim    <tt>true</tt> の場合�??<tt>str</tt> の先�?�と末尾の空白
     *                  を取り除�?てから空�?字かど�?かをチェ�?クします�??
     *
     * @return  <tt>str</tt> が空�?字な�? <tt>true</tt> を返します�??
     */
    public static boolean isEmpty(String str, boolean trim) {
        if (str == null)
            return true;
        return trim ? str.trim().length() == 0 : str.length() == 0;
    }

    /**
     * <tt>str</tt> が空�?字�?�場合に <tt>true</tt> を返します�??
     * �?字�?�が空白のみの場合でも空白とはみなしません。（指定された�?字�?��?�
     * 長さがゼロより大きけれ�?�空�?字ではな�?と判断します�?��?
     *
     * @param   str 検査する�?字�?��??
     *
     * @return  空�?字な�? <tt>true</tt> を返します�??
     */
    public static boolean isEmpty(String str) {
        return isEmpty(str, false);
    }

	/**
	* �?定ファイルの中に含まれるtargetをreplaceに置換する�??
	*
	* @param   infilename �?のファイルパス
	* @param   outfilename 変更後�?�ファイルを保存するパス
	* @param   target 変更前�?��?字�??
	* @param   replace 変更後�?��?字�??
	*
	 * @throws Exception
	*/
    public static void replace(String infilename, String outfilename, String target, String replace) throws Exception {

		String[] lines = FileUtil.getLines(infilename);
		for (int j = 0; j < lines.length; j++) {
			lines[j] = lines[j].replaceAll(target,replace);
		}
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(outfilename)));
		for (int j = 0; j < lines.length; j++) {
			output.println(lines[j]);
		}
		output.close();
	}

	/**
	* �?定ファイルの中に含まれるtargetをreplaceに置換する�??
	*
	* @param   String �?のファイルパス
	* @param   outfilename 変更後�?�ファイルを保存するパス
	 * @param replaceItems1
	* @param   str 変更前�?��?字�??1
	* @param   str 変更後�?��?字�??1
	*
	 * @throws Exception
	*/
    public static void replace(String infilename, String outfilename, List<Map> replaceItems) throws Exception {


		String[] lines = FileUtil.getLines(infilename);
		for (int j = 0; j < lines.length; j++) {
			for(int i = 0; i < replaceItems.size(); i++){
				String target = replaceItems.get(i).get("target").toString();
				String replace = replaceItems.get(i).get("replace").toString();
				lines[j] = lines[j].replaceAll(target,replace);
			}
		}
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(outfilename)));
		for (int j = 0; j < lines.length; j++) {
			output.println(lines[j]);
		}
		output.close();
	}

	/**
	* UTF8でURLエンコードする�??
	*
	* @param   String �?字�??
	 * @throws UnsupportedEncodingException
	*
	* @throws UnsupportedEncodingException
	*/
    public static String urlEncode(String str){
    	try {
    		if(str != null)
    			return URLEncoder.encode(str,"utf-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		return null;
    }
    /**
     * Check string is number or not
     * @param str
     * @return {true}: is number; {false}: not number
     */
    public static boolean isNumber(String str){
        if (str.trim().equals("")) {
            return false;
        }
        else {
            for (char c : str.toCharArray()){
                if (!Character.isDigit(c)) return false;
            }
        }
        return true;
    }
    
    public static boolean isNumberUsingRegex(String str){
         return str.matches("-?\\d+(\\.\\d+)?");
    }
    
    /**
     * Check string is boolean type or not
     * @param str
     * @return {true}: is boolean; {false}: not boolean
     */
    public static boolean isBoolean(String str) {
        if (str == null) return false;
        else {
            String value = str.toLowerCase().trim();
            return "true".equals(value) || "false".equals(value);
        }
    }
    
    /**
     * Check valid notification. It's only 1,0 value and width is 6
     * @param str
     * @return boolean
     */
    public static boolean validNotification(String str) {
        return str.matches("[01]{6}");
    }
    /**
     * Check each element is number or not
     * @param arrayStr
     * @return {boolean} true: if all element is number | false: if one of them is not number
     */
    public static boolean isListNumber(String [] arrayStr){
        boolean check = true;
        if (arrayStr.length > 0){
           for (int i=0; i < arrayStr.length; i++) {
               if (arrayStr[i] == null || !StringUtil.isNumber(arrayStr[i])){
                   check = false;
                   break;
               }
           }
        }
        else {
            check = false;
        }
        return check;
    }
    
    /**
     * Check the string whether contains special character or not
     * @param str 
     * @return boolean
     */
    public static boolean containSpecialCharacter(String str){
        //String contains all special characters
        String specialChars = "<>%*+[]?|\\=";
        boolean check = false;
        if (str != null && !str.equals("")){
            for (int i = 0; i < specialChars.length(); i++){
                if (str.indexOf(specialChars.charAt(i)) > -1){
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
    
    /**
     * Check valid data on each row on CSV file. This method is only used in this context (this file CSV)
     * @param data : our data context (CSV format)
     * @return {boolean} : all data in a row is all valid and otherwise
     * @throws IOException 
     */
    public static boolean checkValidData(String[] data) throws IOException {
        String[] listData = new String[data.length];
        //Trim all element in this array
        for (int i = 0; i < data.length; i++){
            listData[i] = data[i].trim();
        }
        boolean check = false;
        switch (listData.length){
            case 3: 
                String name = listData[0]+listData[1];
                check = !StringUtil.containSpecialCharacter(name) && EmailUtil.isEmailFormat(listData[2]) && name.length()<=64 && listData[2].length()<=255;
                break;
            case 4: 
                String name1 = listData[0]+listData[1]+listData[2];
                check = !StringUtil.containSpecialCharacter(name1) && EmailUtil.isEmailFormat(listData[3]) && name1.length() <= 96  && listData[3].length()<=255;
                break;
            case 5: 
                String name2 = listData[0]+listData[1]+listData[2];
                check = !StringUtil.containSpecialCharacter(name2) && EmailUtil.isEmailFormat(listData[3]) && name2.length() <= 96  && listData[3].length()<=255 && listData[4].length()<=128;
                break;
            default:
                check = false;
                break;
        }
       return check;
    }
    /**
     * Check valid data on each row on user CSV file. This method is only used in this context (this file user CSV)
     * @param data
     * @return {boolean} : all data in a row is all valid and otherwise
     * @throws IOException 
     */
    public static boolean checkValidUserData(String[] data) throws IOException {
        String[] listData = new String[data.length];
        //Trim all element in this array
        for (int i = 0; i < data.length; i++){
            listData[i] = data[i].trim();
        }
        boolean check = false;
        switch (listData.length){
            case 4: 
                String name = listData[0]+listData[1];
                check = !StringUtil.containSpecialCharacter(name) && EmailUtil.isEmailFormat(listData[2]) && name.length()<=64 && listData[2].length()<=32 && listData[2].length()>=6 && listData[3].length()<=255;
                break;
            case 5: 
                String name1 = listData[0]+listData[1]+listData[2];
                check = !StringUtil.containSpecialCharacter(name1) && EmailUtil.isEmailFormat(listData[4]) && name1.length() <= 96  && listData[3].length()<=32 && listData[3].length()>=6 && listData[4].length()<=255;
                break;
            case 6: 
                String name2 = listData[0]+listData[1]+listData[2];
                check = !StringUtil.containSpecialCharacter(name2) && EmailUtil.isEmailFormat(listData[4]) && name2.length() <= 96  && listData[3].length()<=32 && listData[3].length()>=6 && listData[4].length()<=255 && listData[5].length()<=128;
                break;
            default:
                check = false;
                break;
        }
       return check;
    }
}
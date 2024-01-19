package my.edu.utem.ftmk.fis9.global.util;

import java.util.Base64;

/**
 * @author Satrya Fajri Pratama
 */
public class StringProtector
{
	private static char[] key = "ftmkutem".toCharArray();

	private static String xor(String text)
	{
		char[] message = text.toCharArray();
		int length = message.length;
		char[] msg = new char[length];

		for (int i = 0; i < length; i++)
			msg[i] = (char) (message[i] ^ key[i % key.length]);

		return new String(msg);
	}

	public static String encrypt(String text, int level)
	{
		if (text != null)
			for (int i = 0; i < level; i++)
				text = Base64.getEncoder().encodeToString(xor(text).getBytes());

		return text;
	}

	public static String decrypt(String text, int level)
	{
		if (text != null)
			for (int i = 0; i < level; i++)
				text = xor(new String(Base64.getDecoder().decode(text)));

		return text;
	}
}
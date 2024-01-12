package my.edu.utem.ftmk.fis9.global.util;

public class MalayNumberConverter
{
	private static final String[] numbers = new String[] {"kosong", "satu",
			"dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan",
			"sembilan"};
	private static final String[] suffixes = new String[] {"", "puluh", "ratus",
			"ribu", "puluh ribu", "ratus ribu", "juta", "puluh juta",
			"ratus juta", "milyun"};

	public static String toMalay(double number)
	{
		int ringgit = (int) number, cent = (int) (number * 100 - ringgit * 100),
				powR = 0;
		String textR = "",
				textC = (cent / 10 == 0 ? "" : numbers[cent / 10] + " puluh")
						+ (cent % 10 == 0 ? "" : " " + numbers[cent % 10]);

		while (ringgit > 0)
		{
			textR = numbers[ringgit % 10] + " " + suffixes[powR++] + " "
					+ textR;
			ringgit /= 10;
		}

		int firstMillion = -1, lastMillion = -1;
		int firstThousand = -1, lastThousand = -1;

		do
		{
			firstMillion = textR.indexOf("juta");
			lastMillion = textR.lastIndexOf("juta");

			if (firstMillion != lastMillion)
				textR = textR.substring(0, firstMillion - 1)
						+ textR.substring(firstMillion + 4);
		} while (firstMillion != lastMillion);

		do
		{
			firstThousand = textR.indexOf("ribu");
			lastThousand = textR.lastIndexOf("ribu");

			if (firstThousand != lastThousand)
				textR = textR.substring(0, firstThousand - 1)
						+ textR.substring(firstThousand + 4);
		} while (firstThousand != lastThousand);

		textC = textC.replaceAll("satu puluh", "sepuluh");
		textC = textC.replaceAll("sepuluh satu", "sebelas");
		textC = textC.replaceAll("sepuluh dua", "dua belas");
		textC = textC.replaceAll("sepuluh tiga", "tiga belas");
		textC = textC.replaceAll("sepuluh empat", "empat belas");
		textC = textC.replaceAll("sepuluh lima", "lima belas");
		textC = textC.replaceAll("sepuluh enam", "enam belas");
		textC = textC.replaceAll("sepuluh tujuh", "tujuh belas");
		textC = textC.replaceAll("sepuluh delapan", "delapan belas");
		textC = textC.replaceAll("sepuluh sembilan", "sembilan belas");

		textR = textR.replaceAll("satu puluh", "sepuluh");
		textR = textR.replaceAll("sepuluh satu", "sebelas");
		textR = textR.replaceAll("sepuluh dua", "dua belas");
		textR = textR.replaceAll("sepuluh tiga", "tiga belas");
		textR = textR.replaceAll("sepuluh empat", "empat belas");
		textR = textR.replaceAll("sepuluh lima", "lima belas");
		textR = textR.replaceAll("sepuluh enam", "enam belas");
		textR = textR.replaceAll("sepuluh tujuh", "tujuh belas");
		textR = textR.replaceAll("sepuluh delapan", "delapan belas");
		textR = textR.replaceAll("sepuluh sembilan", "sembilan belas");
		textR = textR.replaceAll("kosong puluh ", "");
		textR = textR.replaceAll("kosong ratus ", "");
		textR = textR.replaceAll("kosong ribu", "ribu");
		textR = textR.replaceAll("kosong juta", "juta");

		if (textR.trim().endsWith("kosong"))
			textR = textR.substring(0, textR.length() - 8);

		return "RINGGIT MALAYSIA "
				+ (textR.trim() + (textC.equals("") ? "" : " DAN SEN " + textC))
						.toUpperCase().replaceAll("  ", " ")
				+ " SAHAJA";
	}
}
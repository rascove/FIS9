package my.edu.utem.ftmk.fis9.postfelling.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.global.util.DateConverter;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;

/**
 * @author Satrya Fajri Pratama, Zurina
 */
public class PostFellingSurveyLetterGenerator
{
	private static Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font bolditalic = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLDITALIC);
	private static Font bold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);

	public static void generate(File file, PostFellingSurvey postFellingSurvey, District district, Range range, int level)
			throws Exception
	{
		
		
		
		Document document = new Document(PageSize.A4, 56.7f, 56.7f, 56.7f, 56.7f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addTitle("MAKLUMAT KERJA-KERJA PEMBUKAAN POST-F");
		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Sesi Bancian");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");

		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("ms"));
		PdfPTable header = new PdfPTable(4);
		PdfPTable detail = new PdfPTable(4);
		PdfPCell fc = new PdfPCell(new Phrase(" ", normal));

		calendar.setTimeInMillis(postFellingSurvey.getPostFellingSurveyID());

		fc.setBorder(0);
		fc.setColspan(4);

		header.getDefaultCell().setBorder(0);
		header.getDefaultCell().setLeading(11f, 0f);
		header.setWidths(new float[]
		{
				50f, 15f, 5f, 30f
		});
		header.setWidthPercentage(100f);

		detail.getDefaultCell().setBorder(0);
		detail.getDefaultCell().setLeading(11f, 0f);
		detail.setWidths(new float[]
		{
				5f, 30f, 5f, 60f
		});
		detail.setWidthPercentage(100f);

		header.addCell(new Phrase("", normal));
		header.addCell(new Phrase("Ruj. Kami", normal));
		header.addCell(new Phrase(":", normal));
		header.addCell(new Phrase("", normal));
		header.addCell(new Phrase("", normal));
		header.addCell(new Phrase("Bertarikh", normal));
		header.addCell(new Phrase(":", normal));
		header.addCell(new Phrase(sdf.format(calendar.getTime()), normal));
		header.addCell(new Phrase("", normal));
		header.addCell(new Phrase("Bersamaan", normal));
		header.addCell(new Phrase(":", normal));
		header.addCell(new Phrase(DateConverter.getHijriDate(calendar), normal));

		detail.addCell(new Phrase("", normal));
		detail.addCell(new Phrase("Hutan Simpan", normal));
		detail.addCell(new Phrase(":", normal));
		detail.addCell(new Phrase(postFellingSurvey.getForestName(), normal));
		detail.addCell(new Phrase("", normal));
		detail.addCell(new Phrase("No. Kompatmen/Blok", normal));
		detail.addCell(new Phrase(":", normal));
		detail.addCell(new Phrase(postFellingSurvey.getComptBlockNo(), normal));
		detail.addCell(new Phrase("", normal));
		detail.addCell(new Phrase("Keluasan", normal));
		detail.addCell(new Phrase(":", normal));
		detail.addCell(new Phrase(postFellingSurvey.getArea() + " ha", normal));
		detail.addCell(new Phrase("", normal));
		detail.addCell(new Phrase("Tahun", normal));
		detail.addCell(new Phrase(":", normal));
		detail.addCell(new Phrase("" + postFellingSurvey.getYear(), normal));

		if (level > 1)
		{
			detail.addCell(new Phrase("", normal));
			detail.addCell(new Phrase("Tarikh Kerja", normal));
			detail.addCell(new Phrase(":", normal));
			detail.addCell(
					new Phrase(sdf.format(postFellingSurvey.getStartDate()) + " - " + sdf.format(postFellingSurvey.getEndDate()), normal));
		}

		if (postFellingSurvey.getTenderNo() != null)
		{
			detail.addCell(fc);
			detail.addCell(new Phrase("", normal));
			detail.addCell(new Phrase("No. Sebut Harga", normal));
			detail.addCell(new Phrase(":", normal));
			detail.addCell(new Phrase(postFellingSurvey.getTenderNo(), normal));
		}

		document.add(new Paragraph("\n\n\n", normal));
		document.add(header);

		if (level == 1 || level == 2 || level == 3)
		{
			document.add(
					new Paragraph(15f,
							"\nPegawai Hutan Daerah\nPejabat Hutan Daerah " + district.getName() + ",\n"
									+ district.getAddress() + "\n" + district.getStateName() + "\n\nTuan,\n\n",
							normal));
			document.add(new Paragraph(15f, "MAKLUMAT KERJA-KERJA PEMBUKAAN POST-F", bold));
		}
		else if (level == 31)
		{
			document.add(new Paragraph(15f, "\n" + postFellingSurvey.getTeamLeaderName() + "\nPejabat Hutan " + district.getStateName()
									+ "\n\nTuan,\n\n",
							normal));
			document.add(new Paragraph(15f, "LANTIKAN SEBAGAI KETUA PASUKAN UNTUK KERJA-KERJA PEMBUKAAN POST-F", bold));
		}

		document.add(new Paragraph(15f,
				"\nDengan hormatnya saya di arah merujuk kepada perkara di atas.\n\n2. Dimaklumkan maklumat-maklumat Post-F adalah seperti berikut:\n ",
				normal));
		document.add(detail);
		document.add(new Paragraph(15f, "\nSekian, terima kasih.", normal));
		document.add(new Paragraph(15f, "\n\"BERKHIDMAT UNTUK NEGARA\"", bolditalic));
		document.add(new Paragraph(15f, "\nSaya yang menjalankan amanah,\n\n\n\n", normal));
		document.add(new Paragraph(15f, "                                                      ", bold));

		//if (level == 1 || level == 2 )
			document.add(new Paragraph(18f, "Nama: " + postFellingSurvey.getCreatorName(), normal));
		//else if (level == 2)
		//	document.add(new Paragraph(18f, "Nama: " + district.getOfficerName(), normal));
		//else if (level == 3)
		//	document.add(new Paragraph(18f, "Nama: " + range.getAsstOfficerName(), normal));

		document.close();
	}
}
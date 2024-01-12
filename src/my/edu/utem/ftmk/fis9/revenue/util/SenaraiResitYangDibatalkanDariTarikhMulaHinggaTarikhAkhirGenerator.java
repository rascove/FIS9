package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeSet;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class SenaraiResitYangDibatalkanDariTarikhMulaHinggaTarikhAkhirGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.NORMAL);

	public static void generate(File file, Date startDate, Date endDate,
			ArrayList<String[]> receipts) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				90.71f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Senarai Resit Yang Dibatalkan");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("SENARAI RESIT YANG DIBATALKAN");

		TreeSet<String> criterias = new TreeSet<>();

		for (String[] row : receipts)
			if (!criterias.contains(row[8]))
				criterias.add(row[8]);

		Date date = new Date();
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		PdfPCell no = new PdfPCell(new Phrase("BIL", titleFont));
		PdfPCell receiptNo = new PdfPCell(
				new Phrase("NOMBOR RESIT", titleFont));
		PdfPCell receiptDate = new PdfPCell(new Phrase("TARIKH", titleFont));
		PdfPCell type = new PdfPCell(new Phrase("BENTUK BAYARAN", titleFont));
		PdfPCell operator = new PdfPCell(new Phrase("OPERATOR", titleFont));
		PdfPCell trust = new PdfPCell(new Phrase("AMANAH", titleFont));
		PdfPCell code = new PdfPCell(new Phrase("KOD HASIL", titleFont));
		PdfPCell description = new PdfPCell(
				new Phrase("BUTIRAN BAYARAN PERIHAL", titleFont));
		PdfPCell money = new PdfPCell(new Phrase("AMAUN (RM)", titleFont));

		no.setBorder(0);
		receiptNo.setBorder(0);
		receiptDate.setBorder(0);
		type.setBorder(0);
		operator.setBorder(0);
		trust.setBorder(0);
		code.setBorder(0);
		description.setBorder(0);
		money.setBorder(0);

		no.setBorderWidthTop(0.5f);
		receiptNo.setBorderWidthTop(0.5f);
		receiptDate.setBorderWidthTop(0.5f);
		type.setBorderWidthTop(0.5f);
		operator.setBorderWidthTop(0.5f);
		trust.setBorderWidthTop(0.5f);
		code.setBorderWidthTop(0.5f);
		description.setBorderWidthTop(0.5f);
		money.setBorderWidthTop(0.5f);

		no.setBorderWidthBottom(0.5f);
		receiptNo.setBorderWidthBottom(0.5f);
		receiptDate.setBorderWidthBottom(0.5f);
		type.setBorderWidthBottom(0.5f);
		operator.setBorderWidthBottom(0.5f);
		trust.setBorderWidthBottom(0.5f);
		code.setBorderWidthBottom(0.5f);
		description.setBorderWidthBottom(0.5f);
		money.setBorderWidthBottom(0.5f);

		no.setBorderWidthLeft(0.5f);
		money.setBorderWidthRight(0.5f);

		no.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		receiptNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		receiptDate.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		type.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		operator.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		trust.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		description.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		money.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		boolean first = true;

		for (String current : criterias)
		{
			if (!first)
				document.newPage();

			PdfPTable aboutInner = new PdfPTable(
					new float[] {0.2f, 0.05f, 0.1f, 0.65f});
			PdfPTable content = new PdfPTable(new float[] {0.05f, 0.1f, 0.1f,
					0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.15f});

			content.setHeaderRows(3);

			aboutInner.setWidthPercentage(100f);
			content.setWidthPercentage(100f);

			aboutInner.getDefaultCell().setBorder(0);
			content.getDefaultCell().setBorder(0);

			content.getDefaultCell()
					.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			aboutInner.addCell(new Phrase("MENERIMA", titleFont));
			aboutInner.addCell(new Phrase("", titleFont));
			aboutInner.addCell(new Phrase("KOD", titleFont));
			aboutInner.addCell(new Phrase("PERIHAL", titleFont));
			aboutInner.addCell(new Phrase("JABATAN", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase("0080", contentFont));
			aboutInner.addCell(
					new Phrase("JABATAN PERHUTANAN NEGERI", contentFont));
			aboutInner.addCell(new Phrase("PTJ/PK", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase("80000100", contentFont));
			aboutInner.addCell(new Phrase(
					"IBU PEJABAT JABATAN PERHUTANAN NEGERI", contentFont));

			PdfPCell aboutOuter = new PdfPCell(aboutInner);

			aboutOuter.setColspan(9);

			content.addCell(aboutOuter);
			content.addCell(new Phrase(" ", contentFont));
			content.completeRow();
			content.addCell(no);
			content.addCell(receiptNo);
			content.addCell(receiptDate);
			content.addCell(type);
			content.addCell(operator);
			content.addCell(trust);
			content.addCell(code);
			content.addCell(description);
			content.addCell(money);

			int index = 0;
			double sum = 0;

			for (String[] row : receipts)
			{
				if (row[8].equals(current))
				{
					double amount = df.parse(row[7]).doubleValue();
					sum += amount;
					PdfPCell desc = new PdfPCell(
							new Phrase(row[5], contentFont));
					PdfPCell value = new PdfPCell(
							new Phrase(df.format(amount), contentFont));

					desc.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					desc.setBorder(0);

					value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					value.setBorder(0);

					content.addCell(
							new Phrase(String.valueOf(++index), contentFont));
					content.addCell(new Phrase(row[0], contentFont));
					content.addCell(new Phrase(row[8], contentFont));
					content.addCell(new Phrase(row[1], contentFont));
					content.addCell(new Phrase(row[2], contentFont));
					content.addCell(new Phrase(row[3], contentFont));
					content.addCell(new Phrase(row[4], contentFont));
					content.addCell(desc);
					content.addCell(value);
				}
			}

			PdfPCell total = new PdfPCell(
					new Phrase("JUMLAH    \u00A0", titleFont));
			PdfPCell value = new PdfPCell(
					new Phrase(df.format(sum), contentFont));

			total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			total.setColspan(8);
			total.setBorder(0);

			value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value.setBorderWidthLeft(0);
			value.setBorderWidthRight(0);

			content.addCell(total);
			content.addCell(value);

			document.add(content);

			first = false;
		}

		PdfPTable notes = new PdfPTable(new float[] {0.1f, 0.45f, 0.45f});
		PdfPCell cell = new PdfPCell(
				new Phrase("Nota: Maklumat Bentuk Bayaran", contentFont));

		cell.setBorder(0);
		cell.setColspan(3);

		notes.setWidthPercentage(80f);
		notes.getDefaultCell().setBorder(0);

		notes.addCell(cell);
		notes.addCell(new Phrase("", contentFont));
		notes.addCell(new Phrase(
				"1. TU - Kutipan Tunai\n3. KW - Kutipan Kiriman Wang\n5. DB - Kutipan Draf Bank\n7. KD - Kutipan Kad Debit\n9. PT - Kutipan Pindahan Telegraf / Pindahan Kredit",
				contentFont));
		notes.addCell(new Phrase(
				"2. CK - Kutipan Cek\n4. WP - Kutipan Wang Pos\n6. KK - Kutipan Kad Kredit\n8. MT - Kutipan MEPS Tunai",
				contentFont));

		document.add(new Paragraph("\n", contentFont));
		document.add(notes);
		document.close();

		PdfReader reader = new PdfReader(new FileInputStream(temp));
		int count = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
		PdfPCell genDate = new PdfPCell(
				new Phrase("TARIKH: " + sdf.format(date), titleFont));
		PdfPCell genTime = new PdfPCell(new Phrase(
				"MASA: " + new SimpleDateFormat("hh:mm aa").format(date),
				titleFont));
		PdfPCell title = new PdfPCell(new Phrase(
				"KERAJAAN NEGERI SEMBILAN\nSISTEM PERAKAUNAN PTJ KEWANGAN\nSENARAI RESIT YANG DIBATALKAN DARI "
						+ sdf.format(startDate) + " HINGGA "
						+ sdf.format(endDate),
				titleFont));

		genDate.setBorder(0);
		genTime.setBorder(0);
		title.setBorder(0);

		title.setColspan(3);
		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (int i = 1; i <= count; i++)
		{
			PdfPTable stamp = new PdfPTable(new float[] {0.2f, 0.2f, 0.6f});
			PdfPCell pageNo = new PdfPCell(
					new Phrase("MUKASURAT: " + i + "/" + count, titleFont));

			stamp.setTotalWidth(document.right() - document.left());

			pageNo.setBorder(0);
			pageNo.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			stamp.addCell(genDate);
			stamp.addCell(genTime);
			stamp.addCell(pageNo);
			stamp.addCell(title);

			stamp.writeSelectedRows(0, -1, 28.35f, 566.65f,
					stamper.getOverContent(i));
		}

		stamper.close();
		reader.close();
		temp.delete();
	}

	public static void main(String[] args)
	{
		try (RevenueFacade rFacade = new RevenueFacade();)
		{
			GregorianCalendar start = new GregorianCalendar(2018, 6, 1);
			GregorianCalendar end = new GregorianCalendar(2018, 6, 31);
			ArrayList<String[]> receipts = rFacade
					.getReceiptsString(start.getTime(), end.getTime(), 2);

			SenaraiResitYangDibatalkanDariTarikhMulaHinggaTarikhAkhirGenerator
					.generate(new File("cancelledreceipt.pdf"), start.getTime(),
							end.getTime(), receipts);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
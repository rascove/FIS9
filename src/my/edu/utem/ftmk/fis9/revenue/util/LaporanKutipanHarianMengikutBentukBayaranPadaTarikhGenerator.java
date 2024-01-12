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
public class LaporanKutipanHarianMengikutBentukBayaranPadaTarikhGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	public static void generate(File file, Date startDate, Date endDate,
			ArrayList<String[]> receipts, int reportType) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				90.71f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Laporan Kutipan Harian Mengikut Bentuk Bayaran");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN KUTIPAN HARIAN MENGIKUT BENTUK BAYARAN");

		TreeSet<String> criterias = new TreeSet<>();

		for (String[] row : receipts)
			if (!criterias.contains(row[1]))
				criterias.add(row[1]);

		Date date = new Date();
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		PdfPCell no = new PdfPCell(new Phrase("BIL", titleFont));
		PdfPCell receiptNo = new PdfPCell(
				new Phrase("NOMBOR RESIT", titleFont));
		PdfPCell name = new PdfPCell(new Phrase("NAMA PEMBAYAR", titleFont));
		PdfPCell time = new PdfPCell(new Phrase("MASA URUSNIAGA", titleFont));
		PdfPCell trust = new PdfPCell(new Phrase("KOD AMANAH", titleFont));
		PdfPCell code = new PdfPCell(new Phrase("KOD HASIL", titleFont));
		PdfPCell money = new PdfPCell(new Phrase("AMAUN (RM)", titleFont));

		no.setBorder(0);
		receiptNo.setBorder(0);
		name.setBorder(0);
		time.setBorder(0);
		trust.setBorder(0);
		code.setBorder(0);
		money.setBorder(0);

		no.setBorderWidthTop(0.5f);
		receiptNo.setBorderWidthTop(0.5f);
		name.setBorderWidthTop(0.5f);
		time.setBorderWidthTop(0.5f);
		trust.setBorderWidthTop(0.5f);
		code.setBorderWidthTop(0.5f);
		money.setBorderWidthTop(0.5f);

		no.setBorderWidthBottom(0.5f);
		receiptNo.setBorderWidthBottom(0.5f);
		name.setBorderWidthBottom(0.5f);
		time.setBorderWidthBottom(0.5f);
		trust.setBorderWidthBottom(0.5f);
		code.setBorderWidthBottom(0.5f);
		money.setBorderWidthBottom(0.5f);

		no.setBorderWidthLeft(0.5f);
		money.setBorderWidthRight(0.5f);

		no.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		receiptNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		name.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		time.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		trust.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		money.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (String current : criterias)
		{
			PdfPTable aboutInner = new PdfPTable(
					new float[] {0.2f, 0.05f, 0.1f, 0.65f});
			PdfPTable content = new PdfPTable(
					new float[] {0.05f, 0.1f, 0.4f, 0.1f, 0.1f, 0.1f, 0.15f});

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
			aboutInner.addCell(new Phrase("BENTUK BAYARAN", titleFont));
			aboutInner.addCell(new Phrase(":", titleFont));
			aboutInner.addCell(new Phrase(current, contentFont));
			aboutInner.addCell(
					new Phrase(current.toUpperCase(), contentFont));

			PdfPCell aboutOuter = new PdfPCell(aboutInner);

			aboutOuter.setColspan(7);

			content.addCell(aboutOuter);
			content.addCell(new Phrase(" ", contentFont));
			content.completeRow();
			content.addCell(no);
			content.addCell(receiptNo);
			content.addCell(name);
			content.addCell(time);
			content.addCell(trust);
			content.addCell(code);
			content.addCell(money);

			int index = 0;
			double sum = 0;

			for (String[] row : receipts)
			{
				if (row[1].equals(current))
				{
					double amount = df.parse(row[7]).doubleValue();
					sum += amount;
					PdfPCell desc = new PdfPCell(
							new Phrase(row[9], contentFont));
					PdfPCell value = new PdfPCell(
							new Phrase(df.format(amount), contentFont));

					desc.setBorder(0);
					value.setBorder(0);

					value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					content.addCell(
							new Phrase(String.valueOf(++index), contentFont));
					content.addCell(new Phrase(row[0], contentFont));
					content.addCell(desc);
					content.addCell(new Phrase(row[10], contentFont));
					content.addCell(new Phrase(row[3], contentFont));
					content.addCell(new Phrase(row[4], contentFont));
					content.addCell(value);
				}
			}

			PdfPCell total = new PdfPCell(
					new Phrase("JUMLAH    \u00A0", titleFont));
			PdfPCell value = new PdfPCell(
					new Phrase(df.format(sum), contentFont));

			total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			total.setColspan(6);
			total.setBorder(0);

			value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value.setBorderWidthLeft(0);
			value.setBorderWidthRight(0);

			content.addCell(total);
			content.addCell(value);

			document.add(content);
			document.newPage();
		}

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
				"KERAJAAN NEGERI SEMBILAN\nSISTEM PERAKAUNAN PTJ KEWANGAN\nLAPORAN KUTIPAN HARIAN MENGIKUT BENTUK BAYARAN PADA "
						+ sdf.format(startDate),
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
			GregorianCalendar start = new GregorianCalendar(2018, 6, 12);
			GregorianCalendar end = new GregorianCalendar(2018, 6, 31);
			ArrayList<String[]> receipts = rFacade
					.getReceiptsString(start.getTime(), end.getTime(), 4);

			if (receipts.size() != 0)
				LaporanKutipanHarianMengikutBentukBayaranPadaTarikhGenerator
						.generate(new File("incomebymethod.pdf"),
								start.getTime(), end.getTime(), receipts, 4);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
package my.edu.utem.ftmk.fis9.hall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.hall.controller.manager.HallFacade;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class RingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhirGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			14, Font.BOLD);
	private static final Font subtitleFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static final Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private static final Font underlinedFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);

	public static void generate(File file, Date startDate, Date endDate,
			String[] header, ArrayList<String[]> contents) throws Exception
	{
		Document document = new Document(PageSize.A4, 28.35f, 28.35f, 130.39f,
				28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Ringkasan Pengeluaran Hasil Hutan");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("RINGKASAN PENGELUARAN HASIL HUTAN");

		PdfPTable content = new PdfPTable(
				new float[] {0.1f, 0.3f, 0.15f, 0.15f, 0.15f, 0.15f});
		PdfPCell[] headers = new PdfPCell[] {
				new PdfPCell(new Phrase("Kod", boldFont)),
				new PdfPCell(new Phrase("Nama Spesis", boldFont)),
				new PdfPCell(new Phrase("Royalti", boldFont)),
				new PdfPCell(new Phrase("Ses", boldFont)),
				new PdfPCell(new Phrase("Jumlah", boldFont)),
				new PdfPCell(new Phrase("Isipadu", boldFont))};
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		double royalty = 0, cess = 0, total = 0, volume = 0;

		content.setHeaderRows(1);
		content.setWidthPercentage(100f);
		content.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		for (PdfPCell headerCell : headers)
		{
			headerCell.setBackgroundColor(BaseColor.GRAY);
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			content.addCell(headerCell);
		}

		for (String[] row : contents)
		{
			PdfPCell code = new PdfPCell(new Phrase(row[0], contentFont));
			double r = df.parse(row[2]).doubleValue(),
					c = df.parse(row[3]).doubleValue(),
					t = df.parse(row[4]).doubleValue(),
					v = df.parse(row[5]).doubleValue();
			royalty += r;
			cess += c;
			total += t;
			volume += v;

			code.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			content.addCell(code);
			content.addCell(new PdfPCell(new Phrase(row[1], contentFont)));
			content.addCell(new Phrase(df.format(r), contentFont));
			content.addCell(new Phrase(df.format(c), contentFont));
			content.addCell(new Phrase(df.format(t), contentFont));
			content.addCell(new Phrase(df.format(v), contentFont));
		}

		PdfPCell[] footers = new PdfPCell[] {
				new PdfPCell(new Phrase("JUMLAH", boldFont)),
				new PdfPCell(new Phrase(df.format(royalty), boldFont)),
				new PdfPCell(new Phrase(df.format(cess), boldFont)),
				new PdfPCell(new Phrase(df.format(total), boldFont)),
				new PdfPCell(new Phrase(df.format(volume), boldFont))};

		footers[0].setColspan(2);

		for (PdfPCell footerCell : footers)
		{
			footerCell.setBackgroundColor(BaseColor.GRAY);
			footerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			content.addCell(footerCell);
		}

		document.add(content);
		document.close();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy",
				new Locale("ms"));
		PdfReader reader = new PdfReader(new FileInputStream(temp));
		int count = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
		String genTime = "Tarikh: "
				+ new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(date);
		PdfPCell title = new PdfPCell(
				new Phrase("JABATAN PERHUTANAN NEGERI SEMBILAN", titleFont));
		PdfPCell subtitle = new PdfPCell(
				new Phrase("Ringkasan Pengeluaran Hasil Hutan Dari Bulan "
						+ sdf.format(startDate) + " hingga "
						+ sdf.format(endDate), subtitleFont));

		title.setBorder(0);
		title.setColspan(4);
		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		subtitle.setBorder(0);
		subtitle.setColspan(4);
		subtitle.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (int i = 1; i <= count; i++)
		{
			PdfContentByte contentByte = stamper.getOverContent(i);
			PdfPTable stamp = new PdfPTable(
					new float[] {0.15f, 0.2f, 0.2f, 0.45f});

			stamp.setTotalWidth(document.right() - document.left());
			stamp.getDefaultCell().setBorder(0);

			PdfPCell time = new PdfPCell(
					new Phrase("Laporan: IPHR02\n" + genTime + "\nMukasurat: "
							+ i + "/" + count, contentFont));

			time.setBorder(0);
			time.setColspan(4);

			stamp.addCell(time);
			stamp.addCell(title);
			stamp.addCell(subtitle);
			stamp.addCell(new Phrase("No. Lesen:", boldFont));
			stamp.addCell(new Phrase(header[0], underlinedFont));
			stamp.addCell(new Phrase("Nama Pelesen:", boldFont));
			stamp.addCell(new Phrase(header[1], underlinedFont));
			stamp.addCell(new Phrase("No. Akaun:", boldFont));
			stamp.addCell(new Phrase("", boldFont));
			stamp.addCell(new Phrase("BPJH:", boldFont));
			stamp.addCell(new Phrase(header[2], underlinedFont));

			stamp.writeSelectedRows(0, -1, 28.35f, 813.65f, contentByte);
		}

		stamper.close();
		reader.close();
		temp.delete();
	}

	public static void main(String[] args)
	{
		try (HallFacade hFacade = new HallFacade())
		{
			Date startDate = new GregorianCalendar(2018, 0, 1).getTime();
			Date endDate = new GregorianCalendar(2018, 11, 31).getTime();
			int licenseID = 11;
			String[] header = hFacade
					.getHeaderRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
							licenseID);
			ArrayList<String[]> contents = hFacade
					.getContentRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
							startDate, endDate, licenseID);

			RingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhirGenerator
					.generate(new File("outputsummary.pdf"), startDate, endDate,
							header, contents);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
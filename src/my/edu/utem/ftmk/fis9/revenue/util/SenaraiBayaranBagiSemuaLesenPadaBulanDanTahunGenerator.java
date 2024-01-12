package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
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
public class SenaraiBayaranBagiSemuaLesenPadaBulanDanTahunGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	public static void generate(File file, Date date,
			ArrayList<String[]> listOfPaymentForLicenses) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				113.39f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Senarai Bayaran Bagi Semua Lesen");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("SENARAI BAYARAN BAGI SEMUA LESEN");

		TreeSet<String> criterias = new TreeSet<>();
		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		for (String[] row : listOfPaymentForLicenses)
		{
			if (!criterias.contains(row[0]))
			{
				criterias.add(row[0]);
				map.put(row[0], row[1]);
			}
		}

		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		String month = new SimpleDateFormat("MMMM", new Locale("ms"))
				.format(date), year = new SimpleDateFormat("yyyy").format(date);
		PdfPTable content = null;
		double sum = 0;
		boolean first = true;

		for (String criteria : criterias)
		{
			if (!first)
			{
				document.add(content);
				document.newPage();
			}

			content = new PdfPTable(
					new float[] {0.15f, 0.15f, 0.15f, 0.25f, 0.15f, 0.15f});
			PdfPTable about = new PdfPTable(new float[] {0.2f, 0.05f, 0.75f});
			PdfPCell def = content.getDefaultCell();
			String[] headers = new String[] {"Tarikh Resit", "No. Resit",
					"No. Lesen", "Nama Pelesen", "Cara", "Jumlah"};
			double subsum = 0;

			content.setHeaderRows(3);
			content.setWidthPercentage(100f);

			about.setWidthPercentage(100f);
			about.getDefaultCell().setBorder(0);

			def.setBorder(0);
			def.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			about.addCell(new Phrase("Jenis Bayaran", titleFont));
			about.addCell(new Phrase(":", titleFont));
			about.addCell(new Phrase(criteria + " ("
					+ map.get(criteria).trim().toUpperCase() + ")", titleFont));
			about.addCell(new Phrase("Jenis Borang", titleFont));
			about.addCell(new Phrase(":", titleFont));
			about.addCell(new Phrase("RESIT", titleFont));
			about.addCell(new Phrase("Bulan", titleFont));
			about.addCell(new Phrase(":", titleFont));
			about.addCell(new Phrase(month, titleFont));
			about.addCell(new Phrase("Tahun", titleFont));
			about.addCell(new Phrase(":", titleFont));
			about.addCell(new Phrase(year, titleFont));

			PdfPCell aboutOuter = new PdfPCell(about);
			
			aboutOuter.setColspan(6);
			aboutOuter.setBorder(0);
			
			content.addCell(aboutOuter);
			content.addCell(new Phrase(" ", contentFont));
			content.completeRow();
			
			for (int i = 0; i < 6; i++)
			{
				String header = headers[i];
				PdfPCell cell = new PdfPCell(new Phrase(header, titleFont));

				if (i > 0)
					cell.setBorderWidthLeft(0);

				if (i < 5)
					cell.setBorderWidthRight(0);

				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				content.addCell(cell);
			}

			for (String[] row : listOfPaymentForLicenses)
			{
				if (row[0].equals(criteria))
				{
					for (int i = 0; i < 5; i++)
					{
						String value = row[i + 2];
						PdfPCell cell = new PdfPCell(
								new Phrase(value, contentFont));

						if (i > 0)
							cell.setBorderWidthLeft(0);

						if (i != 3)
							cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

						cell.setBorderWidthRight(0);
						content.addCell(cell);
					}

					double value = df.parse(row[7]).doubleValue();
					PdfPCell cell = new PdfPCell(
							new Phrase(df.format(value), contentFont));
					sum += value;
					subsum += value;

					cell.setBorderWidthLeft(0);
					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					content.addCell(cell);
				}
			}

			PdfPCell cell1 = new PdfPCell(new Phrase("Jumlah", titleFont));
			PdfPCell cell2 = new PdfPCell(
					new Phrase(df.format(subsum), titleFont));
			first = false;

			cell1.setColspan(5);
			cell1.setBorderWidthRight(0);

			cell2.setBorderWidthLeft(0);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			content.addCell(cell1);
			content.addCell(cell2);
		}

		PdfPCell cell1 = new PdfPCell(
				new Phrase("Jumlah Keseluruhan", titleFont));
		PdfPCell cell2 = new PdfPCell(new Phrase(df.format(sum), titleFont));
		first = false;

		cell1.setColspan(5);
		cell1.setBorderWidthRight(0);

		cell2.setBorderWidthLeft(0);
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		content.addCell(cell1);
		content.addCell(cell2);

		document.add(content);
		document.close();

		PdfReader reader = new PdfReader(new FileInputStream(temp));
		int count = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
		PdfPCell genTime = new PdfPCell(new Phrase(
				new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(new Date()),
				contentFont));
		PdfPCell title = new PdfPCell(
				new Phrase("JABATAN PERHUTANAN NEGERI SEMBILAN",
						new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
		PdfPCell subtitle = new PdfPCell(
				new Phrase("Senarai Bayaran Bagi Semua Lesen",
						new Font(Font.FontFamily.TIMES_ROMAN, 12,
								Font.BOLD | Font.UNDERLINE)));

		genTime.setBorder(0);
		title.setBorder(0);
		subtitle.setBorder(0);

		title.setColspan(3);
		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		subtitle.setColspan(3);
		subtitle.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (int i = 1; i <= count; i++)
		{
			PdfPTable stamp = new PdfPTable(new float[] {0.1f, 0.05f, 0.85f});

			stamp.setTotalWidth(document.right() - document.left());
			stamp.getDefaultCell().setBorder(0);

			stamp.addCell(new Phrase("Laporan", contentFont));
			stamp.addCell(new Phrase(":", contentFont));
			stamp.addCell(new Phrase("IPHR-T005", contentFont));
			stamp.addCell(new Phrase("Tarikh", contentFont));
			stamp.addCell(new Phrase(":", contentFont));
			stamp.addCell(genTime);
			stamp.addCell(new Phrase("Mukasurat", contentFont));
			stamp.addCell(new Phrase(":", contentFont));
			stamp.addCell(new Phrase(i + "/" + count, contentFont));
			stamp.addCell(title);
			stamp.addCell(subtitle);

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
			Date date = new GregorianCalendar(2018, 6, 12).getTime();
			ArrayList<String[]> listOfPaymentForLicenses = rFacade
					.getSenaraiBayaranBagiSemuaLesenPadaBulanDanTahun(date);

			SenaraiBayaranBagiSemuaLesenPadaBulanDanTahunGenerator.generate(
					new File("paymentlist.pdf"), date,
					listOfPaymentForLicenses);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
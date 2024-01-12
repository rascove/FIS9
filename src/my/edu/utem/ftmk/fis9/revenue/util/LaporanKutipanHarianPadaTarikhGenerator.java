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
import java.util.concurrent.atomic.DoubleAdder;

import com.itextpdf.text.BaseColor;
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
public class LaporanKutipanHarianPadaTarikhGenerator
{
	private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			10, Font.BOLD);
	private static final Font contentFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	public static void generate(File file, Date date,
			ArrayList<String[]> listOfDailyTransactions) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				104.88f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Laporan Kutipan Harian");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN KUTIPAN HARIAN");

		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		PdfPTable content = new PdfPTable(
				new float[] {0.15f, 0.15f, 0.55f, 0.15f});
		PdfPCell[] headers = new PdfPCell[] {
				new PdfPCell(new Phrase(
						"LAPORAN KUTIPAN HARIAN: " + sdf.format(date),
						titleFont)),
				new PdfPCell(new Phrase("NO. TRANSAKSI", titleFont)),
				new PdfPCell(new Phrase("TARIKH", titleFont)),
				new PdfPCell(new Phrase("PENERANGAN", titleFont)),
				new PdfPCell(new Phrase("NILAI", titleFont))};
		LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
		LinkedHashMap<String, DoubleAdder> map2 = new LinkedHashMap<>();
		String current = null;
		double total = 0;

		map1.put("1", "RESIT");
		map1.put("2", "BAUCAR");
		map1.put("3", "JURNAL");

		map2.put("1", new DoubleAdder());
		map2.put("2", new DoubleAdder());
		map2.put("3", new DoubleAdder());

		content.setHeaderRows(2);
		content.setWidthPercentage(100f);
		content.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		headers[0].setColspan(4);

		for (PdfPCell header : headers)
		{
			header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			content.addCell(header);
		}

		for (String[] row : listOfDailyTransactions)
		{
			if (!row[0].equals(current))
			{
				if (current != null)
				{
					PdfPCell footer1 = new PdfPCell(
							new Phrase("JUMLAH BESAR", titleFont));
					PdfPCell footer2 = new PdfPCell(new Phrase(
							df.format(map2.get(current).doubleValue()),
							titleFont));

					footer1.setColspan(3);
					footer1.setBackgroundColor(BaseColor.LIGHT_GRAY);
					footer1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

					footer2.setBackgroundColor(BaseColor.LIGHT_GRAY);
					footer2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

					content.addCell(footer1);
					content.addCell(footer2);
				}

				current = row[0];
				PdfPCell header = new PdfPCell(
						new Phrase(map1.get(current), titleFont));

				header.setColspan(4);
				content.addCell(header);
			}

			double amount = df.parse(row[4]).doubleValue();
			PdfPCell value = new PdfPCell(
					new Phrase(df.format(amount), titleFont));
			total += amount;

			map2.get(current).add(amount);
			value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			content.addCell(new Phrase(row[1], contentFont));
			content.addCell(new Phrase(row[2], contentFont));
			content.addCell(new PdfPCell(new Phrase(row[3], contentFont)));
			content.addCell(value);
		}

		PdfPCell footer1 = new PdfPCell(new Phrase("JUMLAH BESAR", titleFont));
		PdfPCell footer2 = new PdfPCell(new Phrase(
				df.format(map2.get(current).doubleValue()), titleFont));

		footer1.setColspan(3);
		footer1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		footer1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		footer2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		footer2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		content.addCell(footer1);
		content.addCell(footer2);

		footer1 = new PdfPCell(
				new Phrase("JUMLAH BESAR KESELURUHAN", titleFont));
		footer2 = new PdfPCell(new Phrase(df.format(total), titleFont));

		footer1.setColspan(3);
		footer1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		footer1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		footer2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		footer2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		content.addCell(footer1);
		content.addCell(footer2);

		PdfPTable footer = new PdfPTable(new float[] {0.1f, 0.4f, 0.1f, 0.4f});

		footer.setWidthPercentage(100f);
		footer.getDefaultCell().setBorder(0);

		footer.addCell(new Phrase("Dicetak Oleh", contentFont));
		footer.addCell(new Phrase(
				":  ___________________________________________________________",
				contentFont));
		footer.addCell(new Phrase("Disahkan Oleh", contentFont));
		footer.addCell(new Phrase(
				":  ___________________________________________________________",
				contentFont));
		footer.addCell(new Phrase("\nTarikh", contentFont));
		footer.addCell(new Phrase(
				"\n:  ___________________________________________________________",
				contentFont));
		footer.addCell(new Phrase("\nTarikh", contentFont));
		footer.addCell(new Phrase(
				"\n:  ___________________________________________________________",
				contentFont));

		document.add(content);
		document.add(new Paragraph("\n\n", contentFont));
		document.add(footer);
		document.close();

		Date gen = new Date();
		PdfReader reader = new PdfReader(new FileInputStream(temp));
		int count = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
		PdfPCell genDate = new PdfPCell(
				new Phrase("TARIKH: " + sdf.format(gen), titleFont));
		PdfPCell genTime = new PdfPCell(new Phrase(
				"MASA: " + new SimpleDateFormat("hh:mm aa").format(gen),
				titleFont));
		PdfPCell title = new PdfPCell(new Phrase(
				"KERAJAAN NEGERI NEGERI SEMBILAN\nPEJABAT PERBENDAHARAAN NEGERI NEGERI SEMBILAN\nSISTEM PERAKAUNAN LEJAR AM",
				titleFont));
		PdfPCell subtitle = new PdfPCell(new Phrase("LAPORAN PENGURUSAN",
				new Font(Font.FontFamily.TIMES_ROMAN, 10,
						Font.BOLD | Font.UNDERLINE)));

		genDate.setBorder(0);
		genTime.setBorder(0);
		title.setBorder(0);
		subtitle.setBorder(0);

		title.setColspan(3);
		subtitle.setColspan(3);

		title.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		for (int i = 1; i <= count; i++)
		{
			PdfPTable stamp = new PdfPTable(new float[] {0.2f, 0.2f, 0.6f});
			PdfPCell pageNo = new PdfPCell(new Phrase(
					"MUKASURAT: " + i + "/" + count, titleFont));

			stamp.setTotalWidth(document.right() - document.left());

			pageNo.setBorder(0);
			pageNo.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			stamp.addCell(genDate);
			stamp.addCell(genTime);
			stamp.addCell(pageNo);
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
			ArrayList<String[]> listOfDailyTransactions = rFacade
					.getLaporanKutipanHarianPadaTarikh(date);

			LaporanKutipanHarianPadaTarikhGenerator.generate(
					new File("dailytransaction.pdf"), date,
					listOfDailyTransactions);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
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
import java.util.TreeSet;
import java.util.concurrent.atomic.DoubleAdder;

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
public class BukuTunaiPungutanTerimaanDariTarikhMulaHinggaTarikhAkhirGenerator
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
		document.addKeywords("Buku Tunai Pungutan/Terimaan");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("BUKU TUNAI PUNGUTAN/TERIMAAN");

		TreeSet<String> criterias1 = new TreeSet<>();
		TreeSet<String> criterias2 = new TreeSet<>();
		LinkedHashMap<String, DoubleAdder> types = new LinkedHashMap<>();

		for (String[] row : receipts)
			if (!criterias1.contains(row[8]))
				criterias1.add(row[8]);

		for (String[] row : receipts)
		{
			if (!criterias2.contains(row[1]))
			{
				criterias2.add(row[1]);
				types.put(row[1], new DoubleAdder());
			}
		}

		Date date = new Date();
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		PdfPTable aboutInner = new PdfPTable(
				new float[] {0.2f, 0.05f, 0.1f, 0.65f});
		PdfPTable content = new PdfPTable(9);
		PdfPTable footerOuter = new PdfPTable(1);
		PdfPTable footerInner = new PdfPTable(
				new float[] {0.2f, 0.4f, 0.15f, 0.25f});

		content.setHeaderRows(4);

		aboutInner.setWidthPercentage(100f);
		content.setWidthPercentage(100f);
		footerOuter.setWidthPercentage(60f);
		footerInner.setWidthPercentage(100f);

		aboutInner.getDefaultCell().setBorder(0);
		content.getDefaultCell().setBorder(0);
		footerInner.getDefaultCell().setBorder(0);

		content.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		aboutInner.addCell(new Phrase("", titleFont));
		aboutInner.addCell(new Phrase("", titleFont));
		aboutInner.addCell(new Phrase("KOD", titleFont));
		aboutInner.addCell(new Phrase("PERIHAL", titleFont));
		aboutInner.addCell(new Phrase("JABATAN", titleFont));
		aboutInner.addCell(new Phrase(":", titleFont));
		aboutInner.addCell(new Phrase("0080", contentFont));
		aboutInner
				.addCell(new Phrase("JABATAN PERHUTANAN NEGERI", contentFont));
		aboutInner.addCell(new Phrase("PTJ/PK", titleFont));
		aboutInner.addCell(new Phrase(":", titleFont));
		aboutInner.addCell(new Phrase("80000100", contentFont));
		aboutInner.addCell(new Phrase("IBU PEJABAT JABATAN PERHUTANAN NEGERI",
				contentFont));

		PdfPCell dateCell = new PdfPCell(new Phrase("TARIKH", titleFont));
		PdfPCell typeCell = new PdfPCell(
				new Phrase("BENTUK BAYARAN", titleFont));
		PdfPCell noCell = new PdfPCell(new Phrase("NOMBOR RESIT", titleFont));
		PdfPCell amountCell = new PdfPCell(new Phrase("AMAUN", titleFont));
		PdfPCell incomeCell = new PdfPCell(
				new Phrase("PEMBAYARAN KEPADA PERBENDAHARAAN", titleFont));
		PdfPCell collectorNo = new PdfPCell(
				new Phrase("NO. PEMUNGUT\nTARIKH SLIP BANK", titleFont));
		PdfPCell subSumCell = new PdfPCell(new Phrase("AMAUN", titleFont));
		PdfPCell receiptNoCell = new PdfPCell(new Phrase(
				"NO. RESIT PERBENDA-HARAAN\nTARIKH RESIT", titleFont));
		PdfPCell diffCell = new PdfPCell(
				new Phrase("PERBEZAAN HARI DI BANK", titleFont));
		PdfPCell statusCell = new PdfPCell(new Phrase("STATUS", titleFont));
		PdfPCell gapCell = new PdfPCell(new Phrase(" ", titleFont));

		dateCell.setRowspan(2);
		typeCell.setRowspan(2);
		noCell.setRowspan(2);
		amountCell.setRowspan(2);
		incomeCell.setColspan(5);
		gapCell.setColspan(9);

		gapCell.setBorder(0);

		dateCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		typeCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		noCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		amountCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		incomeCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		collectorNo.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		subSumCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		receiptNoCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		diffCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		statusCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

		dateCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		typeCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		noCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		amountCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		incomeCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		collectorNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		subSumCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		receiptNoCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		diffCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		statusCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		PdfPCell aboutOuter = new PdfPCell(aboutInner);

		aboutOuter.setColspan(9);

		content.addCell(aboutOuter);
		content.addCell(new Phrase(" ", contentFont));
		content.completeRow();
		content.addCell(dateCell);
		content.addCell(typeCell);
		content.addCell(noCell);
		content.addCell(amountCell);
		content.addCell(incomeCell);
		content.addCell(collectorNo);
		content.addCell(subSumCell);
		content.addCell(receiptNoCell);
		content.addCell(diffCell);
		content.addCell(statusCell);

		double sum = 0;

		for (String current : criterias1)
		{
			for (String type : criterias2)
			{
				boolean first = true;
				double typeSum = 0;
				int span = 0;

				for (String[] row : receipts)
				{
					if (row[8].equals(current) && row[1].equals(type))
					{
						double amount = df.parse(row[7]).doubleValue();
						sum += amount;
						typeSum += amount;
						span++;

						types.get(row[1]).add(amount);
					}
				}

				for (String[] row : receipts)
				{
					if (row[8].equals(current) && row[1].equals(type))
					{
						PdfPCell value = new PdfPCell(new Phrase(
								df.format(df.parse(row[7])), contentFont));

						value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
						value.setBorder(0);

						content.addCell(new Phrase(row[8], contentFont));
/*						content.addCell(
								new Phrase(map.get(row[1]), contentFont));*/
						content.addCell(
								new Phrase(row[1], contentFont));						
						content.addCell(new Phrase(row[0], contentFont));
						content.addCell(value);

						if (first)
						{
							first = false;
							PdfPCell noSlip = new PdfPCell(
									new Phrase(row[11] + "\n" + row[8], contentFont));
							PdfPCell sumType = new PdfPCell(new Phrase(
									df.format(typeSum), contentFont));
							PdfPCell blank = new PdfPCell(
									new Phrase("", contentFont));

							noSlip.setHorizontalAlignment(
									PdfPCell.ALIGN_CENTER);
							noSlip.setRowspan(span);
							noSlip.setBorder(0);

							sumType.setHorizontalAlignment(
									PdfPCell.ALIGN_RIGHT);
							sumType.setRowspan(span);
							sumType.setBorder(0);

							blank.setRowspan(span);
							blank.setBorder(0);

							content.addCell(noSlip);
							content.addCell(sumType);
							content.addCell(blank);
							content.addCell(blank);
							content.addCell(blank);
						}
					}
				}

				content.addCell(gapCell);
			}
		}

		PdfPCell total = new PdfPCell(new Phrase("JUMLAH BESAR", titleFont));
		PdfPCell value = new PdfPCell(new Phrase(df.format(sum), contentFont));
		PdfPCell blank = new PdfPCell(new Phrase(" ", titleFont));

		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		total.setColspan(3);
		total.setBorderWidthLeft(0);
		total.setBorderWidthRight(0);

		value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		value.setBorderWidthLeft(0);
		value.setBorderWidthRight(0);

		blank.setBorderWidthLeft(0);
		blank.setBorderWidthRight(0);

		content.addCell(total);
		content.addCell(value);
		content.addCell(blank);
		content.addCell(value);
		content.addCell(blank);
		content.addCell(blank);
		content.addCell(blank);

		PdfPCell classification = new PdfPCell(
				new Phrase("PENGKELASAN", titleFont));

		classification.setBorder(0);
		classification.setRowspan(types.size());

		footerInner.addCell(classification);

		for (String type : types.keySet())
		{
			PdfPCell sumType = new PdfPCell(new Phrase(
					df.format(types.get(type).doubleValue()), contentFont));

			sumType.setBorder(0);
			sumType.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			
			footerInner.addCell(new Phrase(type, titleFont));
			footerInner.addCell(new Phrase("JUMLAH", titleFont));
			footerInner.addCell(sumType);
		}

		footerOuter.addCell(footerInner);

		document.add(content);
		document.add(new Paragraph("\n", contentFont));
		document.add(footerOuter);

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
				"KERAJAAN NEGERI SEMBILAN\nSISTEM PERAKAUNAN PTJ KEWANGAN\nBUKU TUNAI PUNGUTAN/TERIMAAN DARI "
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
					.getReceiptsString(start.getTime(), end.getTime(), 3);

			BukuTunaiPungutanTerimaanDariTarikhMulaHinggaTarikhAkhirGenerator
					.generate(new File("incomebook.pdf"), start.getTime(),
							end.getTime(), receipts, 3);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
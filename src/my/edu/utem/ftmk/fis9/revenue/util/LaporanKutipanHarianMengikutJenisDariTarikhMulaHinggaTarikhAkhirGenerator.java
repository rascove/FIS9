package my.edu.utem.ftmk.fis9.revenue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

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
public class LaporanKutipanHarianMengikutJenisDariTarikhMulaHinggaTarikhAkhirGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
			Font.NORMAL);

	public static void generate(File file, Date startDate, Date endDate,
			ArrayList<ArrayList<String[]>> receipts) throws Exception
	{
		Document document = new Document(PageSize.A4.rotate(), 28.35f, 28.35f,
				90.71f, 28.35f);
		File temp = new File(
				(file.getParent() == null ? "" : "/" + file.getParent())
						+ "temp.pdf");

		PdfWriter.getInstance(document, new FileOutputStream(temp));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Laporan Kutipan Harian Mengikut Jenis");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle("LAPORAN KUTIPAN HARIAN MENGIKUT JENIS");

		Date date = new Date();
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		ArrayList<String[]> leftContent = receipts.get(0);
		ArrayList<String[]> rightContent = receipts.get(1);

		PdfPTable big = new PdfPTable(new float[] {0.45f, 0.01f, 0.54f});
		PdfPTable left = new PdfPTable(
				new float[] {0.1f, 0.2f, 0.2f, 0.2f, 0.3f});
		PdfPTable right = new PdfPTable(
				new float[] {0.4f, 0.1f, 0.2f, 0.1f, 0.2f});

		PdfPCell blank = new PdfPCell(new Phrase(" ", titleFont));
		PdfPCell headerLeft = new PdfPCell(
				new Phrase("Ringkasan Terimaan", titleFont));
		PdfPCell headerRightDesc = new PdfPCell(
				new Phrase("Ringkasan Kutipan", titleFont));
		PdfPCell headerRightReceived = new PdfPCell(
				new Phrase("Rekod Yang Diterima", titleFont));
		PdfPCell headerRightCancelled = new PdfPCell(
				new Phrase("Rekod Yang Dibatalkan", titleFont));

		blank.setBorder(0);

		headerLeft.setColspan(5);
		headerLeft.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		headerRightDesc.setRowspan(2);
		headerRightDesc.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		headerRightReceived.setColspan(2);
		headerRightReceived.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		headerRightCancelled.setColspan(2);
		headerRightCancelled.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		big.setWidthPercentage(100f);
		left.setWidthPercentage(100f);
		right.setWidthPercentage(100f);

		big.getDefaultCell().setBorder(0);

		left.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		right.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		left.addCell(headerLeft);
		left.addCell(new Phrase("Bil.", titleFont));
		left.addCell(new Phrase("Kod Amanah", titleFont));
		left.addCell(new Phrase("Kod Sodo", titleFont));
		left.addCell(new Phrase("Bil. Urusniaga", titleFont));
		left.addCell(new Phrase("Amaun (RM)", titleFont));

		int receivedCount = 0, cancelledCount = 0;
		double receivedSum = 0, cancelledSum = 0;

		for (int i = 0; i < leftContent.size(); i++)
		{
			String[] content = leftContent.get(i);
			int count = Integer.parseInt(content[2]);
			double amount = df.parse(content[3]).doubleValue();
			PdfPCell value = new PdfPCell(
					new Phrase(df.format(amount), contentFont));

			if (content[4].equals("Terima"))
			{
				receivedCount += count;
				receivedSum += amount;
			}
			else
			{
				cancelledCount += count;
				cancelledSum += amount;
			}

			value.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			left.addCell(new Phrase(String.valueOf(i + 1), contentFont));
			left.addCell(new Phrase(content[0], contentFont));
			left.addCell(new Phrase(content[1], contentFont));
			left.addCell(new Phrase(content[2], contentFont));
			left.addCell(value);
		}

		PdfPCell leftTotal = new PdfPCell(new Phrase(
				"Jumlah Terimaan (Mengikut Charge Line)", titleFont));
		PdfPCell leftTotalCount = new PdfPCell(new Phrase(
				String.valueOf(receivedCount + cancelledCount), titleFont));
		PdfPCell leftTotalSum = new PdfPCell(
				new Phrase(df.format(receivedSum + cancelledSum), titleFont));
		PdfPCell leftReceived = new PdfPCell(
				new Phrase("Bilangan Urusniaga Diterima", titleFont));
		PdfPCell leftReceivedCount = new PdfPCell(
				new Phrase(String.valueOf(receivedCount), titleFont));
		PdfPCell leftReceivedSum = new PdfPCell(
				new Phrase(df.format(receivedSum), titleFont));
		PdfPCell leftCancelled = new PdfPCell(
				new Phrase("Bilangan Urusniaga Batal", titleFont));
		PdfPCell leftCancelledCount = new PdfPCell(
				new Phrase(String.valueOf(cancelledCount), titleFont));
		PdfPCell leftCancelledSum = new PdfPCell(
				new Phrase(df.format(cancelledSum), titleFont));

		leftTotal.setBorderWidthBottom(0);
		leftTotalCount.setBorderWidthBottom(0);
		leftTotalSum.setBorderWidthBottom(0);
		leftCancelled.setBorderWidthBottom(0);
		leftCancelledCount.setBorderWidthBottom(0);
		leftCancelledSum.setBorderWidthBottom(0);
		leftCancelled.setBorderWidthTop(0);
		leftCancelledCount.setBorderWidthTop(0);
		leftCancelledSum.setBorderWidthTop(0);
		leftReceived.setBorderWidthTop(0);
		leftReceivedCount.setBorderWidthTop(0);
		leftReceivedSum.setBorderWidthTop(0);

		leftTotal.setColspan(3);
		leftReceived.setColspan(3);
		leftCancelled.setColspan(3);

		leftTotalCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		leftReceivedCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		leftCancelledCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		leftTotalSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		leftReceivedSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		leftCancelledSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		left.addCell(leftTotal);
		left.addCell(leftTotalCount);
		left.addCell(leftTotalSum);
		left.addCell(leftCancelled);
		left.addCell(leftCancelledCount);
		left.addCell(leftCancelledSum);
		left.addCell(leftReceived);
		left.addCell(leftReceivedCount);
		left.addCell(leftReceivedSum);

		for (int i = 0; i < 5; i++)
			left.addCell(blank);

		right.addCell(headerRightDesc);
		right.addCell(headerRightReceived);
		right.addCell(headerRightCancelled);
		right.addCell(new Phrase("Bil. Rekod", titleFont));
		right.addCell(new Phrase("Amaun (RM)", titleFont));
		right.addCell(new Phrase("Bil. Rekod", titleFont));
		right.addCell(new Phrase("Amaun (RM)", titleFont));

		receivedCount = cancelledCount = 0;
		receivedSum = cancelledSum = 0;

		for (int i = 0; i < rightContent.size(); i++)
		{
			String[] content = rightContent.get(i);
			int countReceived = Integer.parseInt(content[1]),
					countCancelled = Integer.parseInt(content[3]);
			double amountReceived = df.parse(content[2]).doubleValue(),
					amountCancelled = df.parse(content[4]).doubleValue();
			PdfPCell desc = new PdfPCell(new Phrase(
					(i + 1) + ". KUTIPAN " + content[0], contentFont));
			PdfPCell count1 = new PdfPCell(
					new Phrase(String.valueOf(countReceived), contentFont));
			PdfPCell count2 = new PdfPCell(
					new Phrase(String.valueOf(countCancelled), contentFont));
			PdfPCell value1 = new PdfPCell(
					new Phrase(df.format(amountReceived), contentFont));
			PdfPCell value2 = new PdfPCell(
					new Phrase(df.format(amountCancelled), contentFont));
			receivedCount += countReceived;
			cancelledCount += countCancelled;
			receivedSum += amountReceived;
			cancelledSum += amountCancelled;

			desc.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			count1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			count2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			value1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			value2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

			desc.setBorderWidthTop(0);
			count1.setBorderWidthTop(0);
			value1.setBorderWidthTop(0);
			count2.setBorderWidthTop(0);
			value2.setBorderWidthTop(0);

			desc.setBorderWidthBottom(0);
			count1.setBorderWidthBottom(0);
			value1.setBorderWidthBottom(0);
			count2.setBorderWidthBottom(0);
			value2.setBorderWidthBottom(0);

			right.addCell(desc);
			right.addCell(count1);
			right.addCell(value1);
			right.addCell(count2);
			right.addCell(value2);
		}

		PdfPCell rightTotal = new PdfPCell(
				new Phrase("Jumlah Kutipan Mengikut Jenis", titleFont));
		PdfPCell rightTotalCount = new PdfPCell(new Phrase(
				String.valueOf(receivedCount + cancelledCount), titleFont));
		PdfPCell rightTotalSum = new PdfPCell(
				new Phrase(df.format(receivedSum + cancelledSum), titleFont));
		PdfPCell rightReceived = new PdfPCell(
				new Phrase("Bilangan Urusniaga Diterima", titleFont));
		PdfPCell rightReceivedCount = new PdfPCell(
				new Phrase(String.valueOf(receivedCount), titleFont));
		PdfPCell rightReceivedSum = new PdfPCell(
				new Phrase(df.format(receivedSum), titleFont));
		PdfPCell rightCancelled = new PdfPCell(
				new Phrase("Bilangan Urusniaga Batal", titleFont));
		PdfPCell rightCancelledCount = new PdfPCell(
				new Phrase(String.valueOf(cancelledCount), titleFont));
		PdfPCell rightCancelledSum = new PdfPCell(
				new Phrase(df.format(cancelledSum), titleFont));
		PdfPCell empty = new PdfPCell(new Phrase(" ", titleFont));

		rightTotal.setBorderWidthBottom(0);
		rightTotalCount.setBorderWidthBottom(0);
		rightTotalSum.setBorderWidthBottom(0);
		rightCancelled.setBorderWidthBottom(0);
		rightCancelledCount.setBorderWidthBottom(0);
		rightCancelledSum.setBorderWidthBottom(0);
		rightCancelled.setBorderWidthTop(0);
		rightCancelledCount.setBorderWidthTop(0);
		rightCancelledSum.setBorderWidthTop(0);
		rightReceived.setBorderWidthTop(0);
		rightReceivedCount.setBorderWidthTop(0);
		rightReceivedSum.setBorderWidthTop(0);

		rightTotalCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		rightReceivedCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		rightCancelledCount.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		rightTotalSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		rightReceivedSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		rightCancelledSum.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

		empty.setBorderWidthBottom(0);

		right.addCell(rightTotal);
		right.addCell(rightTotalCount);
		right.addCell(rightTotalSum);
		right.addCell(empty);
		right.addCell(empty);

		empty.setBorderWidthTop(0);

		right.addCell(rightCancelled);
		right.addCell(empty);
		right.addCell(empty);
		right.addCell(rightCancelledCount);
		right.addCell(rightCancelledSum);

		empty.setBorderWidthBottom(0.5f);

		right.addCell(rightReceived);
		right.addCell(rightReceivedCount);
		right.addCell(rightReceivedSum);
		right.addCell(empty);
		right.addCell(empty);

		for (int i = 0; i < 5; i++)
			right.addCell(blank);

		big.addCell(left);
		big.addCell(blank);
		big.addCell(right);

		document.add(big);
		document.add(new Paragraph(
				"\n\nDisediakan Oleh:\n\nTandatangan:\n\n\nDisahkan Oleh:\n\nJawatan:\n\nTarikh:",
				titleFont));

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
				"KERAJAAN NEGERI SEMBILAN\nSISTEM PERAKAUNAN PTJ KEWANGAN\nLAPORAN KUTIPAN HARIAN MENGIKUT JENIS SEPERTI DARI "
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
			GregorianCalendar start = new GregorianCalendar(2018, 6, 12);
			ArrayList<ArrayList<String[]>> receipts = rFacade
					.getReceiptsReportType5(start.getTime(), start.getTime());

			LaporanKutipanHarianMengikutJenisDariTarikhMulaHinggaTarikhAkhirGenerator
					.generate(new File("incomebytype.pdf"), start.getTime(),
							start.getTime(), receipts);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
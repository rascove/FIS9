package my.edu.utem.ftmk.fis9.hall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Nor Azman Bin Mat Ariff
 */
public class BukuKawalanPengeluaranGenerator
{
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private static Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL);

	public static void generate(File file, String[] headerInfo, 
			ArrayList<String[]> currentStatusTaggingRecords,
			Date date)
			throws Exception
	{
		Document document = new Document(PageSize.A3.rotate(), 28.35f, 28.35f,
				28.35f, 28.35f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Penyata Keluaran Hasil");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.addTitle(
				"BUKU KAWALAN PENGELUARAN");

		//String month = new SimpleDateFormat("MMMM yyyy", new Locale("ms"))
				//.format(date);
		Paragraph titleBig = new Paragraph(
				"JABATAN PERHUTANAN NEGERI SEMBILAN\nBUKU KAWALAN PENGELUARAN BAGI " + headerInfo[0].toUpperCase()+"\nHUTAN: " + headerInfo[1].toUpperCase() +
				" DAERAH: " + headerInfo[2].toUpperCase() + " NO LESEN : " + headerInfo[3].toUpperCase()+ " LUAS : " + headerInfo[4]
						 + "\n\n",
				titleFont);

		float[] widths = new float[] {10, 5, 5, 5, 5, 6, 6, 6, 5, 6, 6, 5, 6, 6, 5, 6, 6, 5, 6, 6, 5, 5, 5};
	
		PdfPTable table = new PdfPTable(widths);

		titleBig.setAlignment(Paragraph.ALIGN_CENTER);

		table.setHeaderRows(2);
		table.setWidthPercentage(101f);

		PdfPCell titleSpecies = new PdfPCell(new Phrase("Spesis Pokok", titleFont));
		PdfPCell titleSerialNo = new PdfPCell(new Phrase("No Siri Tag Tanda Pokok", titleFont));
		PdfPCell titleTreeDiameter = new PdfPCell(new Phrase("Dia-\nmeter\n(cm)", titleFont));
		PdfPCell titleTreeVolume = new PdfPCell(
				new Phrase("Isipadu pokok\n(m\u00B3)", titleFont));
		PdfPCell titleLogEstimation = new PdfPCell(new Phrase("Anggar\n-an\nBil\nTual", titleFont));
		PdfPCell titleLogQuality = new PdfPCell(new Phrase("Kualiti Balak Pertama", titleFont));

		PdfPCell titlePanjang = new PdfPCell(new Phrase("Panjang\n(m)", titleFont));
		PdfPCell titleLogDiameter = new PdfPCell(new Phrase("Diameter\n(cm)", titleFont));
		PdfPCell titleLogVolume = new PdfPCell(new Phrase("Isipadu\n(m\u00B3)", titleFont));
		PdfPCell titleLog1 = new PdfPCell(new Phrase("KAYU BALAK 1", titleFont));
		PdfPCell titleLog2 = new PdfPCell(new Phrase("KAYU BALAK 2", titleFont));
		PdfPCell titleLog3 = new PdfPCell(new Phrase("KAYU BALAK 3", titleFont));
		PdfPCell titleLog4 = new PdfPCell(new Phrase("KAYU BALAK 4", titleFont));
		PdfPCell titleLog5 = new PdfPCell(new Phrase("KAYU BALAK 5", titleFont));
		PdfPCell titleTotalLog = new PdfPCell(new Phrase("Jumlah\nBil. Tual Balak", titleFont));
		PdfPCell titleTotalVolume = new PdfPCell(new Phrase("Jumlah Isipadu\n(m\u00B3)", titleFont));

		titleSpecies.setRowspan(2);
		titleSerialNo.setRowspan(2);
		titleTreeDiameter.setRowspan(2);
		titleTreeVolume.setRowspan(2);
		titleLogEstimation.setRowspan(2);
		titleLogQuality.setRowspan(2);
		titleLog1.setColspan(3);
		titleLog2.setColspan(3);
		titleLog3.setColspan(3);
		titleLog4.setColspan(3);
		titleLog5.setColspan(3);
		titleTotalLog.setRowspan(2);
		titleTotalVolume.setRowspan(2);
		
		titleSpecies.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		titleSpecies.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleSerialNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleSerialNo.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleTreeDiameter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTreeDiameter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleTreeVolume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTreeDiameter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLogEstimation.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLogEstimation.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLogQuality.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLogQuality.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titlePanjang.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titlePanjang.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLogDiameter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLogDiameter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLogVolume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLogVolume.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLog1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLog1.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLog2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLog2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLog3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLog3.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLog4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLog4.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleLog5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleLog5.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleTotalLog.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTotalLog.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		titleTotalVolume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTotalVolume.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);


		table.addCell(titleSpecies);
		table.addCell(titleSerialNo);
		table.addCell(titleTreeDiameter);
		table.addCell(titleTreeVolume);
		table.addCell(titleLogEstimation);
		table.addCell(titleLogQuality);
		table.addCell(titleLog1);
		table.addCell(titleLog2);
		table.addCell(titleLog3);
		table.addCell(titleLog4);
		table.addCell(titleLog5);
		table.addCell(titleTotalLog);
		table.addCell(titleTotalVolume);
		table.addCell(titlePanjang);
		table.addCell(titleLogDiameter);
		table.addCell(titleLogVolume);
		table.addCell(titlePanjang);
		table.addCell(titleLogDiameter);
		table.addCell(titleLogVolume);
		table.addCell(titlePanjang);
		table.addCell(titleLogDiameter);
		table.addCell(titleLogVolume);
		table.addCell(titlePanjang);
		table.addCell(titleLogDiameter);
		table.addCell(titleLogVolume);
		table.addCell(titlePanjang);
		table.addCell(titleLogDiameter);
		table.addCell(titleLogVolume);

		if (!currentStatusTaggingRecords.isEmpty())
		{
			for (String[] data : currentStatusTaggingRecords)
			{
				PdfPCell species = new PdfPCell(
						new Phrase(data[0], contentFont));
				PdfPCell serialNo = new PdfPCell(
						new Phrase(data[1], contentFont));
				PdfPCell treeDiameter = new PdfPCell(
						new Phrase(data[2], contentFont));
				PdfPCell treeVolume = new PdfPCell(
						new Phrase(data[3], contentFont));			
				PdfPCell logEstimation = new PdfPCell(
						new Phrase(data[4], contentFont));
				PdfPCell logQuality = new PdfPCell(
						new Phrase(data[5], contentFont));					

				species.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				serialNo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				treeDiameter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				treeVolume.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				logEstimation.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				logQuality.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				table.addCell(species);
				table.addCell(serialNo);
				table.addCell(treeDiameter);
				table.addCell(treeVolume);
				table.addCell(logEstimation);
				table.addCell(logQuality);

				for (int i = 6; i < 21; i++)
				{
					PdfPCell cell = null;
					if(!data[i].equalsIgnoreCase("X"))
					{
						cell = new PdfPCell(
								new Phrase(data[i], contentFont));
						cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					}
					else
					{
						cell = new PdfPCell(
								new Phrase(data[i], contentFont));
						cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					}
					table.addCell(cell);
				}
				

				PdfPCell totalLog = new PdfPCell(
						new Phrase(data[21], contentFont));	
				PdfPCell totalVolume = new PdfPCell(
						new Phrase(data[22], contentFont));

				totalLog.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				totalVolume.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				
				table.addCell(totalLog);
				table.addCell(totalVolume);
			}
		}

		document.add(titleBig);
		document.add(table);

		document.close();
	}
}
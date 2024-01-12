package my.edu.utem.ftmk.fis9.prefelling.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.global.util.RomanNumberConverter;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.prefelling.model.CuttingLimit;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingCuttingOption;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingReport;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;

/**
 * @author Satrya Fajri Pratama
 */
public class ClosingLetterGenerator
{
	private static Font normal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static Font bold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	
	public static void generate(File file, PreFellingSurvey preFellingSurvey, State state, TreeLimit treeLimit, Staff staff) throws Exception
	{
		Document document = new Document(PageSize.A4, 56.7f, 56.7f, 113.4f, 56.7f);

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		document.addTitle("PENETAPAN HAD BATAS TEBANGAN");
		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Sesi Bancian");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");

		PreFellingReport preFellingReport = preFellingSurvey.getPreFellingReport();
		PreFellingCuttingOption preFellingCuttingOption = preFellingSurvey.getPreFellingCuttingOption();
		ArrayList<CuttingLimit> cuttingLimits = preFellingSurvey.getCuttingLimits();
		
		if (preFellingCuttingOption == null)
		{
			ArrayList<PreFellingCuttingOption> preFellingCuttingOptions = preFellingReport.getPreFellingCuttingOptions();
			
			for (PreFellingCuttingOption sco : preFellingCuttingOptions)
				if (sco.getCuttingOptionID() == preFellingSurvey.getCuttingOptionID())
					preFellingCuttingOption = sco;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("ms"));
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat dfp = new DecimalFormat("0.00%");

		PdfPTable ref = new PdfPTable(3);
		PdfPTable location = new PdfPTable(3);
		PdfPTable line = new PdfPTable(1);
		PdfPTable info = new PdfPTable(6);
		Paragraph title = new Paragraph(12, "SURAT PENETAPAN HAD BATAS TEBANGAN\n\n", bold);
		Paragraph first = new Paragraph(12, "\nSaya memaklumkan bahawa berdasarkan kepada analisa keputusan pemprosesan data Inventori Hutan Sebelum Tebangan (Pre-F) yang telah dijalankan di kawasan tersebut di atas, Had Batas Tebangan serta maklumat berkaitan adalah seperti berikut:\n\n", normal);

		ref.getDefaultCell().setBorder(0);
		ref.getDefaultCell().setPadding(1);
		ref.setWidths(new float[] {45f, 15f, 40f});
		ref.setWidthPercentage(100);
		
		location.getDefaultCell().setBorder(0);
		location.getDefaultCell().setPadding(1);
		location.getDefaultCell().setPaddingLeft(0);
		location.setWidths(new float[] {50f, 5f, 45f});
		location.setWidthPercentage(100);
		
		line.getDefaultCell().setBorder(5);
		line.setWidthPercentage(100);
		
		info.getDefaultCell().setBorder(0);
		info.getDefaultCell().setPadding(1);
		info.getDefaultCell().setPaddingLeft(0);
		info.setWidths(new float[] {5f, 5f, 5f, 30f, 30f, 25f});
		info.setWidthPercentage(100);
		
		title.setAlignment(Paragraph.ALIGN_CENTER);
		first.setAlignment(Paragraph.ALIGN_JUSTIFIED);

		ref.addCell(new Phrase("", normal));
		ref.addCell(new Phrase("Rujukan kami", normal));
		ref.addCell(new Phrase(":  PHN.NS.", normal));
		ref.addCell(new Phrase("", normal));
		ref.addCell(new Phrase("Tarikh", normal));
		ref.addCell(new Phrase(":  " + sdf.format(new Date()), normal));
		
		location.addCell(new Phrase("PENETAPAN HAD BATAS TEBANGAN", bold));
		location.addCell(new Phrase("", bold));
		location.addCell(new Phrase("", bold));
		location.addCell(new Phrase("BAGI HUTAN SIMPAN", bold));
		location.addCell(new Phrase(":", bold));
		location.addCell(new Phrase(preFellingSurvey.getForestName().toUpperCase(), bold));
		location.addCell(new Phrase("NO. KOMPARTMEN/SUB KOMPARTMEN", bold));
		location.addCell(new Phrase(":", bold));
		location.addCell(new Phrase(preFellingSurvey.getComptBlockNo().toUpperCase(), bold));
		location.addCell(new Phrase("LUAS", bold));
		location.addCell(new Phrase(":", bold));
		location.addCell(new Phrase(preFellingSurvey.getArea() + " HEKTAR", bold));
		
		line.addCell(new Phrase("", bold));
		
		PdfPCell hbt = new PdfPCell(new Phrase("Had Batas Tebangan", bold));
		PdfPCell related = new PdfPCell(new Phrase("Maklumat Berkaitan", bold));
		PdfPCell empty = new PdfPCell(new Phrase(" ", bold));
		
		hbt.setBorder(0);
		hbt.setColspan(4);
		hbt.setPadding(1);
		hbt.setPaddingLeft(0);
		
		related.setBorder(0);
		related.setColspan(4);
		related.setPadding(1);
		related.setPaddingLeft(0);
		
		empty.setBorder(0);
		empty.setColspan(6);
		empty.setPadding(1);
		empty.setPaddingLeft(0);
		
		info.addCell(new Phrase("", bold));
		info.addCell(new Phrase("A.", bold));
		info.addCell(hbt);
		info.addCell(empty);
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(i)", normal));
		info.addCell(new Phrase("Dipterokarpa", normal));
		info.addCell(new Phrase(":  " + preFellingSurvey.getDipterocarpLimit() + " cm diameter", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(ii)", normal));
		info.addCell(new Phrase("Bukan Dipterokarpa", normal));
		info.addCell(new Phrase(":  " + preFellingSurvey.getNonDipterocarpLimit() + " cm diameter", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(iii)", normal));
		info.addCell(new Phrase("Chengal", normal));
		info.addCell(new Phrase(":  " + treeLimit.getChengalLimit() + " cm diameter", normal));
		info.addCell(new Phrase("", normal));
		
		if (cuttingLimits.isEmpty())
		{
			info.addCell(new Phrase("", normal));
			info.addCell(new Phrase("", normal));
			info.addCell(new Phrase("(iv)", normal));
			info.addCell(new Phrase("Lain-lain (Nyatakan)", normal));
			info.addCell(new Phrase(":  Tiada", normal));
			info.addCell(new Phrase("", normal));
		}
		else
		{
			for (int i = 0; i < cuttingLimits.size(); i++)
			{
				CuttingLimit cuttingLimit = cuttingLimits.get(i);
			
				info.addCell(new Phrase("", normal));
				info.addCell(new Phrase("", normal));
				info.addCell(new Phrase("(" + RomanNumberConverter.toRoman(i + 4) + ")", normal));
				info.addCell(new Phrase("Lain-lain (" + cuttingLimit.getSpeciesName() + ")", normal));
				info.addCell(new Phrase(":  " + cuttingLimit.getMinDiameter() + " cm diameter", normal));
				info.addCell(new Phrase("", normal));
			}
		}
		
		PdfPCell[] values = new PdfPCell[] {
				new PdfPCell(new Phrase("Anggaran isipadu bersih sehektar", normal)),
				new PdfPCell(new Phrase("Jumlah anggaran isipadu bersih", normal)),
				new PdfPCell(new Phrase("Anggaran bilangan pokok tebangan sehektar", normal)),
				new PdfPCell(new Phrase("Anggaran bilangan pokok dirian tinggal sehektar", normal)),
				new PdfPCell(new Phrase("Anggaran peratus Dipterokarpa dalam dirian asal", normal)),
				new PdfPCell(new Phrase("Anggaran peratus Dipterokarpa dalam dirian tinggal", normal))
		};
		
		for (PdfPCell value : values)
		{
			value.setBorder(0);
			value.setColspan(2);
			value.setPadding(1);
			value.setPaddingLeft(0);
		}
		
		info.addCell(empty);
		info.addCell(new Phrase("", bold));
		info.addCell(new Phrase("B.", bold));
		info.addCell(related);
		info.addCell(empty);
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(i)", normal));
		info.addCell(values[0]);
		info.addCell(new Phrase(":  " + df.format(preFellingCuttingOption.getRelativeTotalNetVolume()) + " m\u00B3", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(ii)", normal));
		info.addCell(values[1]);
		info.addCell(new Phrase(":  " + df.format(preFellingCuttingOption.getRelativeTotalNetVolume() * preFellingSurvey.getArea()) + " m\u00B3", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(iii)", normal));
		info.addCell(values[2]);
		info.addCell(new Phrase(":  " + Math.round(preFellingCuttingOption.getRelativeTotalCount()) + " pokok", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(iv)", normal));
		info.addCell(values[3]);
		info.addCell(new Phrase(":  " + Math.round(preFellingCuttingOption.getRelativeTotalNetCountPartial()) + " pokok", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(v)", normal));
		info.addCell(values[4]);
		info.addCell(new Phrase(":  " + dfp.format(preFellingReport.getOriginalStandRatio()), normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("", normal));
		info.addCell(new Phrase("(vi)", normal));
		info.addCell(values[5]);
		info.addCell(new Phrase(":  " + dfp.format(preFellingCuttingOption.getRelativeTotalNetCountPartialRatio()), normal));
		
		document.add(title);
		document.add(ref);
		document.add(new Paragraph(12, "PEGAWAI HUTAN DAERAH,\n" + preFellingSurvey.getDistrictName().toUpperCase(), bold));
		document.add(new Paragraph(12, "\nTuan,\n\n", normal));
		document.add(location);
		document.add(new Paragraph(12, "\n", normal));
		document.add(line);
		document.add(first);
		document.add(info);
		document.add(new Paragraph(12, "\n2.    Sila jalankan kerja-kerja penandaan pokok bagi kawasan tersebut.\n\nSekian, terima kasih.", normal));
		document.add(new Paragraph(12, "\n" + state.getMotto() + "\n", bold));
		document.add(new Paragraph(12, "\nSaya yang menjalankan amanah,\n\n\n\n", normal));
		document.add(new Paragraph(12, "(" + staff.getName() + ")", bold));
		document.add(new Paragraph(12, staff.getDesignationName() + ",\n" + staff.getStateName(), normal));
		
		document.newPage();
		
		Phrase message = new Phrase("\nAdalah saya dengan hormatnya merujuk kepada perkara tersebut di atas. Berdasarkan kepada analisa ", normal);
		Phrase destination = new Phrase("\nPHN.NS\nTarikh: " + sdf.format(new Date()) + "\n\n", normal);
		Phrase closing = new Phrase("\nSekian, untuk makluman dan tindakan tuan selanjutnya.\n\nSekian, terima kasih.\n\n", normal);
		
		message.add(new Chunk("INVENTORI HUTAN SEBELUM TEBANGAN", bold));
		message.add(new Chunk(" (", normal)); 
		message.add(new Chunk("Pre-Felling Forestry Inventory", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));
		message.add(new Chunk("), berikut adalah ", normal));
		message.add(new Chunk("HAD BATAS TEBANGAN", bold));
		message.add(new Chunk(" yang ditetapkan bagi kawasan tersebut di atas:\n ", normal));
		
		destination.add(new Chunk("PEGAWAI HUTAN DAERAH,\n" + preFellingSurvey.getDistrictName().toUpperCase(), bold));
		destination.add(new Chunk("\n\nTuan,\n ", normal));
		
		closing.add(new Chunk(state.getMotto(), bold));
		closing.add(new Chunk("\n\nSaya yang menjalankan amanah,\n\n\n\n\n", normal));
		closing.add(new Chunk("(" + staff.getName() + ")", bold));
		closing.add(new Chunk("\n" + staff.getDesignationName() + ",\n" + staff.getStateName(), normal));
		
		PdfPTable certificate = new PdfPTable(3);
		PdfPTable details = new PdfPTable(6);
		
		PdfPCell headerLeft = new PdfPCell(new Phrase("PEJABAT PERHUTANAN NEGERI,\nNEGERI SEMBILAN DARUL KHUSUS\nTINGKAT 4, BLOK C, WISMA NEGERI,\n70503 SEREMBAN\n ", normal));
		PdfPCell headerRight = new PdfPCell(new Phrase("TEL: 06-7659849\nFAX: 06-7623711\n ", normal));
		PdfPCell destinationCell = new PdfPCell(destination);
		PdfPCell titleCert = new PdfPCell(new Phrase("SIJIL HAD BATAS TEBANGAN", bold));
		PdfPCell licenseeLabel = new PdfPCell(new Phrase("\nNAMA PELESEN\nFAIL & TARIKH KELULUSAN\nHUTAN SIMPAN KEKAL\nBLOK/KOMPARTMEN\nKELUASAN", normal));
		PdfPCell licenseeDetail = new PdfPCell(new Phrase("\n:\n:\n:  " + preFellingSurvey.getForestName().toUpperCase() + "\n:  " + preFellingSurvey.getComptBlockNo().toUpperCase() + "\n:  " + preFellingSurvey.getArea() + " HEKTAR\n ", normal));
		PdfPCell messageCell = new PdfPCell(message);
		PdfPCell detailsCell = new PdfPCell(details);
		PdfPCell closingCell = new PdfPCell(closing);
		
		headerLeft.setColspan(2);
		headerLeft.setBorderWidthRight(0);
		headerRight.setBorderWidthLeft(0);		
		headerRight.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		
		destinationCell.setColspan(3);
		destinationCell.setBorderWidthTop(0);
		
		titleCert.setColspan(3);
		titleCert.setBorderWidthTop(0);
		titleCert.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		
		licenseeLabel.setBorderWidthRight(0);
		licenseeLabel.setVerticalAlignment(PdfPCell.ALIGN_TOP);
		
		licenseeDetail.setColspan(2);
		licenseeDetail.setBorderWidthLeft(0);
		licenseeDetail.setVerticalAlignment(PdfPCell.ALIGN_TOP);
		
		messageCell.setColspan(3);
		messageCell.setBorderWidthBottom(0);
		messageCell.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
		
		detailsCell.setColspan(3);
		detailsCell.setBorderWidthTop(0);
		detailsCell.setBorderWidthBottom(0);
		detailsCell.setPaddingLeft(3);
		detailsCell.setPaddingRight(3);
		
		closingCell.setColspan(3);
		closingCell.setBorderWidthTop(0);
		
		PdfPCell[] nums = new PdfPCell[] {
				new PdfPCell(new Phrase("i)", normal)),
				new PdfPCell(new Phrase("ii)", normal)),
				new PdfPCell(new Phrase("iii)", normal)),
				new PdfPCell(new Phrase("iv)", normal)),
				new PdfPCell(new Phrase("v)", normal)),
				new PdfPCell(new Phrase("vi)", normal)),
				new PdfPCell(new Phrase("vii)", normal)),
				new PdfPCell(new Phrase("viii)", normal)),
				new PdfPCell(new Phrase("ix)", normal)),
				new PdfPCell(new Phrase("x)", normal))
		};
		
		PdfPCell[] labels = new PdfPCell[] {
				new PdfPCell(new Phrase("Kaum Damar", normal)),
				new PdfPCell(new Phrase("Kaum Bukan Damar", normal)),
				new PdfPCell(new Phrase("Kaum Chengal", normal)),
				new PdfPCell(new Phrase("Sistem Pengurusan Hutan", normal)),
				new PdfPCell(new Phrase("Peratus Kaum Damar Dalam Dirian Asal", normal)),
				new PdfPCell(new Phrase("Isipadu Bersih", normal)),
				new PdfPCell(new Phrase("Jumlah Pengeluaran Balak", normal)),
				new PdfPCell(new Phrase("Dirian Pokok Ditebang", normal)),
				new PdfPCell(new Phrase("Dirian Pokok Tinggal", normal)),
				new PdfPCell(new Phrase("Peratus Kaum Damar Dalam Dirian Tinggal", normal))
		};
		
		PdfPCell[] detailValues = new PdfPCell[] {
				new PdfPCell(new Phrase("=  " + preFellingSurvey.getDipterocarpLimit() + " cm diameter", normal)),
				new PdfPCell(new Phrase("=  " + preFellingSurvey.getNonDipterocarpLimit() + " cm diameter", normal)),
				new PdfPCell(new Phrase("=  " + treeLimit.getChengalLimit() + " cm diameter", normal)),
				new PdfPCell(new Phrase("=  SMS", normal)),
				new PdfPCell(new Phrase("=  " + dfp.format(preFellingReport.getOriginalStandRatio()), normal)),
				new PdfPCell(new Phrase("=  " + df.format(preFellingCuttingOption.getRelativeTotalNetVolume()) + " m\u00B3/ha", normal)),
				new PdfPCell(new Phrase("=  " + df.format(preFellingCuttingOption.getRelativeTotalNetVolume() * preFellingSurvey.getArea()) + " m\u00B3", normal)),
				new PdfPCell(new Phrase("=  " + Math.round(preFellingCuttingOption.getRelativeTotalCount()) + " pk/ha", normal)),
				new PdfPCell(new Phrase("=  " + Math.round(preFellingCuttingOption.getRelativeTotalNetCountPartial()) + " pk/ha", normal)),
				new PdfPCell(new Phrase("=  " + dfp.format(preFellingCuttingOption.getRelativeTotalNetCountPartialRatio()), normal))
		};
		
		for (int i = 0; i < 10; i++)
		{
			nums[i].setBorderWidthRight(0);
			labels[i].setBorderWidthRight(0);
			detailValues[i].setBorderWidthLeft(0);
		}
		
		certificate.setWidths(new float[] {30f, 52f, 18f});
		certificate.setWidthPercentage(100);
		
		details.setWidths(new float[] {4f, 23f, 23f, 4f, 23f, 23f});
		details.setWidthPercentage(100);
		
		details.addCell(nums[0]);
		details.addCell(labels[0]);
		details.addCell(detailValues[0]);
		details.addCell(nums[5]);
		details.addCell(labels[5]);
		details.addCell(detailValues[5]);
		details.addCell(nums[1]);
		details.addCell(labels[1]);
		details.addCell(detailValues[1]);
		details.addCell(nums[6]);
		details.addCell(labels[6]);
		details.addCell(detailValues[6]);
		details.addCell(nums[2]);
		details.addCell(labels[2]);
		details.addCell(detailValues[2]);
		details.addCell(nums[7]);
		details.addCell(labels[7]);
		details.addCell(detailValues[7]);
		details.addCell(nums[3]);
		details.addCell(labels[3]);
		details.addCell(detailValues[3]);
		details.addCell(nums[8]);
		details.addCell(labels[8]);
		details.addCell(detailValues[8]);
		details.addCell(nums[4]);
		details.addCell(labels[4]);
		details.addCell(detailValues[4]);
		details.addCell(nums[9]);
		details.addCell(labels[9]);
		details.addCell(detailValues[9]);
		
		certificate.addCell(headerLeft);
		certificate.addCell(headerRight);
		certificate.addCell(destinationCell);
		certificate.addCell(titleCert);
		certificate.addCell(licenseeLabel);
		certificate.addCell(licenseeDetail);
		certificate.addCell(messageCell);
		certificate.addCell(detailsCell);
		certificate.addCell(closingCell);
		
		document.add(certificate);
		document.close();
	}
}
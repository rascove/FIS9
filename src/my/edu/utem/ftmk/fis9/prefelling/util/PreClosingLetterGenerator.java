package my.edu.utem.ftmk.fis9.prefelling.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingCuttingOption;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingReport;

/**
 * @author Satrya Fajri Pratama
 */
public class PreClosingLetterGenerator
{
	private static Font normal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static Font size9 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
	private static Font bold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private static Font bold9 = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
	
	public static void generate(File file, PreFellingSurvey preFellingSurvey, TreeLimit treeLimit, State state, Staff staff, ArrayList<Integer> recommendations) throws Exception
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
		ArrayList<PreFellingCuttingOption> preFellingCuttingOptions = preFellingReport.getPreFellingCuttingOptions();
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("ms"));
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat dfp = new DecimalFormat("0.00%");
		Paragraph memo = new Paragraph("MEMO\n\n", bold);
		PdfPTable detail = new PdfPTable(2);

		memo.setAlignment(Paragraph.ALIGN_CENTER);
		
		detail.setWidths(new float[] {30f, 70f});
		detail.setWidthPercentage(100);

		detail.addCell(new Phrase("Kepada :", normal));
		detail.addCell(new Phrase("Pengarah Perhutanan Negeri, Negeri Sembilan", normal));
		detail.addCell(new Phrase("Daripada :", normal));
		detail.addCell(new Phrase(staff.getDesignationName(), normal));
		detail.addCell(new Phrase("Rujukan Kami :\nTarikh :", normal));
		detail.addCell(new Phrase("PHN.NS.\n" + sdf.format(new Date()), normal));

		PdfPCell cell = new PdfPCell();

		cell.setColspan(2);
		cell.setHorizontalAlignment(Phrase.ALIGN_JUSTIFIED);

		Paragraph title = new Paragraph("\nPENETAPAN HAD BATAS TEBANGAN DI KOMPT. " + preFellingSurvey.getComptBlockNo().toUpperCase() + ", HUTAN SIMPAN " + preFellingSurvey.getForestName().toUpperCase(), bold);
		Paragraph first = new Paragraph("\nSukacita dimaklumkan bahawa kerja-kerja Bancian Pre-F di kompartmen " + preFellingSurvey.getComptBlockNo() + ", Hutan Simpan " + preFellingSurvey.getForestName() + " telah siap dilaksanakan secara Jabatan. Berdasarkan analisa keputusan pemprosesan data Pre-F yang telah dijalankan, Had Batas Tebangan yang dicadangkan adalah seperti berikut:\n\n", normal);

		title.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		first.setAlignment(Paragraph.ALIGN_JUSTIFIED);

		cell.addElement(new Phrase("\nTuan,\n", normal));
		cell.addElement(title);
		cell.addElement(first);

		int size = recommendations.size();
		float widths[] = new float[size + 1], width = 45f / size;
		PdfPTable limit = new PdfPTable(size + 1);
		widths[0] = 55f;

		for (int i = 1; i <= size; i++)
			widths[i] = width;

		limit.setWidths(widths);
		limit.setWidthPercentage(100);

		PdfPCell criteria = new PdfPCell(new Phrase("Maklumat Analisa Pre-F", bold));
		PdfPCell group = new PdfPCell(new Phrase("Opsyen HBT", bold));

		criteria.setRowspan(2);
		criteria.setVerticalAlignment(Element.ALIGN_MIDDLE);
		criteria.setHorizontalAlignment(Element.ALIGN_CENTER);
		group.setColspan(size);
		group.setHorizontalAlignment(Element.ALIGN_CENTER);

		limit.addCell(criteria);
		limit.addCell(group);

		for (int i = 1; i <= size; i++)
		{
			PdfPCell option = new PdfPCell(new Phrase("Opsyen " + i, bold));

			option.setHorizontalAlignment(Element.ALIGN_CENTER);
			limit.addCell(option);
		}

		limit.addCell(new Phrase("Had Batas Tebangan (D:BD:C)", normal));

		for (int i = 0; i < size; i++)
		{
			int cuttingOptionID = recommendations.get(i);

			for (PreFellingCuttingOption option : preFellingCuttingOptions)
			{
				if (option.getCuttingOptionID() == cuttingOptionID)
				{
					PdfPCell pcell = new PdfPCell(new Phrase((int) option.getDipterocarpLimit() + ":" + (int) option.getNonDipterocarpLimit() + ":" + (int) treeLimit.getChengalLimit(), normal));

					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					limit.addCell(pcell);
				}
			}
		}
		
		limit.addCell(new Phrase("Anggaran isipadu bersih sehektar", normal));
		
		for (int i = 0; i < size; i++)
		{
			int cuttingOptionID = recommendations.get(i);

			for (PreFellingCuttingOption option : preFellingCuttingOptions)
			{
				if (option.getCuttingOptionID() == cuttingOptionID)
				{
					PdfPCell pcell = new PdfPCell(new Phrase(df.format(option.getRelativeTotalNetVolume()) + " m\u00B3", normal));

					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					limit.addCell(pcell);
				}
			}
		}
		
		limit.addCell(new Phrase("Anggaran bilangan pokok tebangan sehektar", normal));
		
		for (int i = 0; i < size; i++)
		{
			int cuttingOptionID = recommendations.get(i);

			for (PreFellingCuttingOption option : preFellingCuttingOptions)
			{
				if (option.getCuttingOptionID() == cuttingOptionID)
				{
					PdfPCell pcell = new PdfPCell(new Phrase(Math.round(option.getRelativeTotalCount()) + " pokok", normal));

					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					limit.addCell(pcell);
				}
			}
		}
		
		limit.addCell(new Phrase("Anggaran pokok dirian tinggal sehektar", normal));
		
		for (int i = 0; i < size; i++)
		{
			int cuttingOptionID = recommendations.get(i);

			for (PreFellingCuttingOption option : preFellingCuttingOptions)
			{
				if (option.getCuttingOptionID() == cuttingOptionID)
				{
					PdfPCell pcell = new PdfPCell(new Phrase(Math.round(option.getRelativeTotalNetCountPartial()) + " pokok", normal));

					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					limit.addCell(pcell);
				}
			}
		}
		
		limit.addCell(new Phrase("Anggaran peratus Kaum Dipterokarp dirian asal", normal));
		
		for (int i = 0; i < size; i++)
		{
			int cuttingOptionID = recommendations.get(i);

			for (PreFellingCuttingOption option : preFellingCuttingOptions)
			{
				if (option.getCuttingOptionID() == cuttingOptionID)
				{
					PdfPCell pcell = new PdfPCell(new Phrase(dfp.format(preFellingReport.getOriginalStandRatio()), normal));

					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					limit.addCell(pcell);
				}
			}
		}
		
		limit.addCell(new Phrase("Anggaran peratus Kaum Dipterokarp dirian tinggal", normal));
		
		for (int i = 0; i < size; i++)
		{
			int cuttingOptionID = recommendations.get(i);

			for (PreFellingCuttingOption option : preFellingCuttingOptions)
			{
				if (option.getCuttingOptionID() == cuttingOptionID)
				{
					PdfPCell pcell = new PdfPCell(new Phrase(dfp.format(option.getRelativeTotalNetCountPartialRatio()), normal));

					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					limit.addCell(pcell);
				}
			}
		}

		cell.addElement(limit);
		
		Paragraph second = new Paragraph("\n3.   Bersama-sama ini disertakan Ringkasan Opsyen HBT (Jadual 3) dan Buku Analisa Pre-F di kompartmen " + preFellingSurvey.getComptBlockNo() + ", HS " + preFellingSurvey.getForestName() + " untuk makluman dan rujukan tuan. Sukacita mendapat pertimbangan dan keputusan tuan selanjutnya.\n", normal);
		Paragraph close = new Paragraph("\n" + state.getMotto() + "\n", bold);
		Paragraph regard = new Paragraph("\nSaya yang menjalankan amanah,\n\n\n\n", normal);
		Paragraph name = new Paragraph("(" + staff.getName() + ")", bold);
		Paragraph role = new Paragraph(staff.getDesignationName() + ",\nJabatan Perhutanan Negeri,\n" + state.getName(), normal);
		
		second.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		close.setAlignment(Paragraph.ALIGN_JUSTIFIED);

		cell.addElement(second);
		cell.addElement(close);
		cell.addElement(regard);
		cell.addElement(name);
		cell.addElement(role);
		
		detail.addCell(cell);
		
		document.add(memo);
		document.add(detail);

		document.setMargins(28.35f, 28.35f, 28.35f, 28.35f);
		document.newPage();
		
		PdfPTable table3 = new PdfPTable(13);
		PdfPCell[] header3 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Opsyen Had Tebangan (cm)", bold9)),
				new PdfPCell(new Phrase("Pengeluaran Isipadu Bersih Sehektar (m\u00B3)", bold9)),
				new PdfPCell(new Phrase("Pokok Tebangan Sehektar (Bilangan)", bold9)),
				new PdfPCell(new Phrase("Bilangan Pokok Bersih Sehektar Dari Kelas Diameter + 30 cm - 45 cm dan Pokok Berdiameter + 45 cm yang Setara Dengannya Dalam Dirian Tinggal (Bilangan)", bold9))
		};
		PdfPCell[] subheader3 = new PdfPCell[]
		{
				new PdfPCell(new Phrase("Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Bukan Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Bukan Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Bukan Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("% Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Bukan Dipterokarp", bold9)),
				new PdfPCell(new Phrase("Jumlah", bold9)),
				new PdfPCell(new Phrase("% Dipterokarp", bold9))
		};
		
		table3.setWidthPercentage(100);
		
		header3[0].setColspan(2);
		header3[1].setColspan(3);
		header3[2].setColspan(4);
		header3[3].setColspan(4);
		
		for (int i = 0; i < 4; i++)
		{
			header3[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			header3[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			header3[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(header3[i]);
		}
		
		for (int i = 0; i < 13; i++)
		{
			subheader3[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
			subheader3[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
			subheader3[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(subheader3[i]);
		}
		
		for (int i = 0; i < preFellingCuttingOptions.size(); i++)
		{
			PreFellingCuttingOption option = preFellingCuttingOptions.get(i);
			int index = recommendations.indexOf(option.getCuttingOptionID());
			
			PdfPCell[] cells = new PdfPCell[]
			{
					new PdfPCell(new Phrase((index != -1 ? "(" + (index + 1) + ") ": "") + df.format(option.getDipterocarpLimit()), size9)),
					new PdfPCell(new Phrase(df.format(option.getNonDipterocarpLimit()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeDipNetVolume()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeNonDipNetVolume()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeTotalNetVolume()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeDipCount()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeNonDipCount()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeTotalCount()), size9)),
					new PdfPCell(new Phrase(dfp.format(option.getRelativeTotalCountRatio()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeDipNetCountPartial()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeNonDipNetCountPartial()), size9)),
					new PdfPCell(new Phrase(df.format(option.getRelativeTotalNetCountPartial()), size9)),
					new PdfPCell(new Phrase(dfp.format(option.getRelativeTotalNetCountPartialRatio()), size9))
			};
			
			for (int j = 0; j < cells.length; j++)
			{
				PdfPCell c = cells[j];
			
				if (index != -1)
					c.setBackgroundColor(j == 4 || j == 7 || j == 11 ? BaseColor.CYAN : BaseColor.YELLOW);
				
				c.setHorizontalAlignment(Element.ALIGN_CENTER);
				table3.addCell(c);
			}
		}
		
		Paragraph attachment = new Paragraph("JADUAL 3: OPSYEN HAD BATAS TEBANGAN TANPA MENGAMBILKIRA POKOK-POKOK DARI KELAS DIAMETER 15 - 30 cm\n\n", bold9);
		
		attachment.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(new Paragraph("INVENTORI HUTAN SEBELUM TEBANGAN (PRE-F)\nKOMPARTMEN " + preFellingSurvey.getComptBlockNo().toUpperCase() + ", HUTAN SIMPAN " + preFellingSurvey.getForestName().toUpperCase() + "\nDAERAH HUTAN: PHD " + preFellingSurvey.getDistrictName().toUpperCase() + "\n\n", bold9));
		document.add(attachment);
		document.add(table3);
		document.add(new Paragraph("\nNOTA: Peratus KAUM DIPTEROKARP yang berdiameter +30 cm dalam dirian asal = " + dfp.format(preFellingReport.getOriginalStandRatio()), size9));
		
		document.close();
	}
}
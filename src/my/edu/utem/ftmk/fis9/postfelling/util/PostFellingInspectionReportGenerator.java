package my.edu.utem.ftmk.fis9.postfelling.util;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import my.edu.utem.ftmk.fis9.global.util.RomanNumberConverter;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingInspectionLine;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingInspectionReport;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingReportElement;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;

/**
 * @author Satrya Fajri Pratama, Zurina
 */
public class PostFellingInspectionReportGenerator
{
	private static Font size12 = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
	private static Font size10 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static Font bold10 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private static Font bold12 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	private static Font italic12 = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
	private static Font font8 = new Font(Font.FontFamily.ZAPFDINGBATS, 8);
	private static Font font7 = new Font(Font.FontFamily.ZAPFDINGBATS, 7);
	private static Font font12 = new Font(Font.FontFamily.ZAPFDINGBATS, 12);
	private static char degree = '\u00B0';
	private static char lte = '\u2264';
	private static char gt = '\u003E';

	
	public static void generate(File file, PostFellingSurvey postFellingSurvey, ArrayList<Staff> inspectionStaffs) throws Exception
	{

		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("ms"));
		
		Document document = new Document(PageSize.A4, 14.175f, 14.175f, 50f, 14.175f);
		PdfWriter.getInstance(document, new FileOutputStream(file));
		//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
	    //Rotate event = new Rotate();
	    //writer.setPageEvent(event);
		PostFellingInspectionReport inspectionReport = postFellingSurvey.getPostFellingInspectionReport();
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String FONT = external.getRealPath("/") + "resources/fonts/FreeSans.ttf";
		BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font f = new Font(bf, 10);
		
		document.open();

		String titleStr = "\n\n\nLAPORAN SEMAKAN KERJA\nINVENTORI HUTAN SELEPAS TEBANGAN (POST-F)";
		if (postFellingSurvey.getTenderNo() == null)
			titleStr += " SECARA KONTRAK";
		else
			titleStr += " SECARA JABATAN";

		titleStr += "\nSEMAKAN KALI KE 1";// + postFellingSurvey.getInspectionNo();

		titleStr += "\n";
		Paragraph title = new Paragraph(titleStr, bold12);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		title.setIndentationLeft(40f);
		title.setIndentationRight(40f);
		document.addTitle(titleStr);
		document.addSubject("Jabatan Perhutanan Negeri Sembilan");
		document.addKeywords("Post-F");
		document.addAuthor("JPNS");
		document.addCreator("JPNS");
		document.add(title);
		
		
		
		//--
				PdfPTable tableAsas = new PdfPTable(3);
				tableAsas.setWidths(new float[]
				{
						10f, 8f, 82f
				});
				tableAsas.setWidthPercentage(100);
				
				ArrayList<PdfPCell> cellAsas = new ArrayList<PdfPCell>();
				
				cellAsas.add(new PdfPCell(new Phrase("1.0", bold12)));
				cellAsas.add(new PdfPCell(new Phrase("MAKLUMAT ASAS\n", bold12)));
				cellAsas.add(new PdfPCell(new Phrase(" ", size12)));
				cellAsas.add(new PdfPCell(new Phrase("(i)", size12)));
				cellAsas.add(new PdfPCell(new Phrase("No. Tawaran: "+ ( (postFellingSurvey.getTenderNo()!=null) ? postFellingSurvey.getTenderNo().toUpperCase(): ""), size12)));
				cellAsas.add(new PdfPCell(new Phrase(" ", font7)));
				cellAsas.add(new PdfPCell(new Phrase("(ii)", size12)));
				cellAsas.add(new PdfPCell(new Phrase("Nama Kontraktor: "+ ((postFellingSurvey.getTeamLeaderName()!=null)?postFellingSurvey.getTeamLeaderName().toUpperCase():""), size12)));
				cellAsas.add(new PdfPCell(new Phrase(" ", font7)));
				cellAsas.add(new PdfPCell(new Phrase("(iii)", size12)));
				cellAsas.add(new PdfPCell(new Phrase("Daerah Hutan: "+ postFellingSurvey.getDistrictName().toUpperCase(), size12)));
				cellAsas.add(new PdfPCell(new Phrase(" ", font7)));
				cellAsas.add(new PdfPCell(new Phrase("(iv)", size12)));
				cellAsas.add(new PdfPCell(new Phrase("Hutan Simpan: "+ postFellingSurvey.getForestName().toUpperCase(), size12)));
				cellAsas.add(new PdfPCell(new Phrase(" ", font7)));
				cellAsas.add(new PdfPCell(new Phrase("(v)", size12)));
				cellAsas.add(new PdfPCell(new Phrase("No. Kompartmen/Subkompartmen: "+ postFellingSurvey.getComptBlockNo(), size12)));
				cellAsas.add(new PdfPCell(new Phrase(" ", font7)));
				cellAsas.add(new PdfPCell(new Phrase("(vi)", size12)));
				cellAsas.add(new PdfPCell(new Phrase("Luas Kompartmen/Subkompartmen: "+ postFellingSurvey.getArea(), size12)));
				cellAsas.add(new PdfPCell(new Phrase(" ", font7)));
				cellAsas.add(new PdfPCell(new Phrase("(vii)", size12)));
				cellAsas.add(new PdfPCell(new Phrase("Jumlah Garis Inventori : "+ postFellingSurvey.getTotalInventoryLine()+"\n\n", size12)));
				
				
				cellAsas.get(1).setColspan(2);
				//cellAsas.get(3).setColspan(2);
				
				for (int i = 0; i < cellAsas.size(); i++)
				{
					cellAsas.get(i).setBorder(0);
					cellAsas.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					tableAsas.addCell(cellAsas.get(i));
				}
				//--
				
				PdfPTable tableTempoh = new PdfPTable(3);
				tableTempoh.setWidths(new float[]
				{
						10f, 8f, 82f
				});
				tableTempoh.setWidthPercentage(100);
				
				ArrayList<PdfPCell> cellTempoh = new ArrayList<PdfPCell>();
				
				cellTempoh.add(new PdfPCell(new Phrase("2.0", bold12)));
				cellTempoh.add(new PdfPCell(new Phrase("TEMPOH PELAKSANAAN KERJA\n", bold12)));
				cellTempoh.add(new PdfPCell(new Phrase(" ", size12)));
				cellTempoh.add(new PdfPCell(new Phrase("(i)", size12)));
				cellTempoh.add(new PdfPCell(new Phrase("Tarikh Milik Tapak: "+ ((postFellingSurvey.getOwnershipDate()!=null) ? sdf.format(postFellingSurvey.getOwnershipDate()).toUpperCase() : ""), size12)));
				cellTempoh.add(new PdfPCell(new Phrase(" ", font7)));
				cellTempoh.add(new PdfPCell(new Phrase("(ii)", size12)));
				cellTempoh.add(new PdfPCell(new Phrase("Tarikh Mula Kerja: "+ ((postFellingSurvey.getInspectionStartWorkDate()!=null) ? sdf.format(postFellingSurvey.getInspectionStartWorkDate()).toUpperCase(): ""), size12)));
				cellTempoh.add(new PdfPCell(new Phrase(" ", font7)));
				cellTempoh.add(new PdfPCell(new Phrase("(iii)", size12)));
				cellTempoh.add(new PdfPCell(new Phrase("Tarikh Siap Kerja: "+ ((postFellingSurvey.getInspectionEndWorkDate()!=null) ? sdf.format(postFellingSurvey.getInspectionEndWorkDate()).toUpperCase()+"\n\n": " \n\n "), size12)));
				
				
				cellTempoh.get(1).setColspan(2);
				//cellTempoh.get(3).setColspan(2);
				
				for (int i = 0; i < cellTempoh.size(); i++)
				{
					cellTempoh.get(i).setBorder(0);
					cellTempoh.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					tableTempoh.addCell(cellTempoh.get(i));
				}
				
				
				//--
				PdfPTable tableKawasan = new PdfPTable(3);
				tableKawasan.setWidths(new float[]
				{
						10f, 8f, 82f
				});
				tableKawasan.setWidthPercentage(100);
				
				ArrayList<PdfPCell> cellKawasan = new ArrayList<PdfPCell>();
				String inspectionLines = "";

				for (PostFellingInspectionLine lineNo : postFellingSurvey.getPostFellingInspectionLines())
				{
					inspectionLines += lineNo.getLineNo() + ", ";
				}

				inspectionLines = inspectionLines.substring(0, inspectionLines.length() - 2);
				cellKawasan.add(new PdfPCell(new Phrase("3.0", bold12)));
				cellKawasan.add(new PdfPCell(new Phrase("KAWASAN DISEMAK\n", bold12)));
				cellKawasan.add(new PdfPCell(new Phrase(" ", size12)));
				cellKawasan.add(new PdfPCell(new Phrase("(i)", size12)));
				cellKawasan.add(new PdfPCell(new Phrase("Bilangan Garis Inventori yang telah siap diinventori: "+ postFellingSurvey.getTotalInventoryLine(), size12)));
				cellKawasan.add(new PdfPCell(new Phrase(" ", font7)));
				cellKawasan.add(new PdfPCell(new Phrase("(ii)", size12)));
				cellKawasan.add(new PdfPCell(new Phrase("No. Garis Inventori yang disemak: "+ inspectionLines.toUpperCase(), size12)));
				cellKawasan.add(new PdfPCell(new Phrase(" ", font7)));
				cellKawasan.add(new PdfPCell(new Phrase("(iii)", size12)));
				cellKawasan.add(new PdfPCell(new Phrase("Bilangan Garis Inventori yang disemak: "+ postFellingSurvey.getPostFellingInspectionLines().size(), size12)));
				cellKawasan.add(new PdfPCell(new Phrase(" ", font7)));
				cellKawasan.add(new PdfPCell(new Phrase("(iv)", size12)));
				cellKawasan.add(new PdfPCell(new Phrase("Kadar Semakan: 10%\n\n", size12)));
				
				cellKawasan.get(1).setColspan(2);
				//cellKawasan.get(3).setColspan(2);
				
				for (int i = 0; i < cellKawasan.size(); i++)
				{
					cellKawasan.get(i).setBorder(0);
					cellKawasan.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					tableKawasan.addCell(cellKawasan.get(i));
				}
				
				
				//--
				
				PdfPTable tableButiran = new PdfPTable(3);
				tableButiran.setWidths(new float[]
				{
						10f, 8f, 82f
				});
				tableButiran.setWidthPercentage(100);
				
				
				ArrayList<PdfPCell> cellButiran = new ArrayList<PdfPCell>();
				cellButiran.add(new PdfPCell(new Phrase("4.0", bold12)));
				cellButiran.add(new PdfPCell(new Phrase("BUTIRAN SEMAKAN\n", bold12)));
				cellButiran.add(new PdfPCell(new Phrase(" ", size12)));
				cellButiran.add(new PdfPCell(new Phrase("4.1", size12)));
				cellButiran.add(new PdfPCell(new Phrase("Papan Tanda:\n\n", size12)));
				
				
				cellButiran.get(1).setColspan(2);
				//cellButiran.get(3).setColspan(2);
				
				for (int i = 0; i < cellButiran.size(); i++)
				{
					cellButiran.get(i).setBorder(0);
					cellButiran.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					tableButiran.addCell(cellButiran.get(i));
				}
				
				
				
		//--

		PdfPTable tableSemakanPapanTanda = new PdfPTable(3);

		tableSemakanPapanTanda.setWidths(new float[]
		{
				33f, 33f, 33f
		});
		tableSemakanPapanTanda.setWidthPercentage(80);

		ArrayList<PdfPCell> cellSemakanPapanTanda= new ArrayList<PdfPCell>();
		{
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("Ada*")));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("Tiada")));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("Boleh Terima")));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("Tidak Boleh Terima")));
			
		};

		cellSemakanPapanTanda.get(0).setColspan(2);
		cellSemakanPapanTanda.get(1).setRowspan(2);
		

		if (postFellingSurvey.getInspectionSignage() == 2)
		{
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("4", font12)));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("")));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("")));
			
		}
		else if (postFellingSurvey.getInspectionSignage() == 1)
		{
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("")));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("4", font12)));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("")));
		}
		else
		{
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("")));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("")));
			cellSemakanPapanTanda.add(new PdfPCell(new Phrase("4", font12)));
			
		}

		for (int i = 0; i < cellSemakanPapanTanda.size(); i++)
		{
			cellSemakanPapanTanda.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellSemakanPapanTanda.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
			cellSemakanPapanTanda.get(i).setPadding(5f);
			tableSemakanPapanTanda.addCell(cellSemakanPapanTanda.get(i));
		}

		
		
		//--
		
		PdfPTable tableGarisTapak = new PdfPTable(2);
		tableGarisTapak.setWidths(new float[]
		{
				10f, 90f
		});
		tableGarisTapak.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellGarisTapak = new ArrayList<PdfPCell>();
		cellGarisTapak.add(new PdfPCell(new Phrase("4.2", size12)));
		cellGarisTapak.add(new PdfPCell(new Phrase("Garis Tapak\n", size12)));
		cellGarisTapak.add(new PdfPCell(new Phrase(" ", size12)));
		cellGarisTapak.add(new PdfPCell(new Phrase("Semakan dijalankan dengan menggunakan Borang Semakan Garis Tapak Inventori Hutan Selepas Tebangan (Post-F). Maklumat semakan adalah seperti berikut [tandakan (/) di ruangan berkenaan]\n\n", size12)));
		
		
		cellGarisTapak.get(1).setColspan(2);
		
		for (int i = 0; i < cellGarisTapak.size(); i++)
		{
			cellGarisTapak.get(i).setBorder(0);
			cellGarisTapak.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tableGarisTapak.addCell(cellGarisTapak.get(i));
		}
		
		
		//--
		
		PdfPTable tableGarisTapak2 = new PdfPTable(5);

		tableGarisTapak2.setWidths(new float[]
		{
				6f, 78f, 7f, 2f, 7f
		});
		tableGarisTapak2.setWidthPercentage(100);

		ArrayList<PdfPCell> cellGaris = new ArrayList<PdfPCell>();
		
			cellGaris.add(new PdfPCell(new Phrase("(i)")));
			cellGaris.add(new PdfPCell(new Phrase("Bearing Garis Tapak betul mengikut Pelan Kawasan Kerja Post-F")));
			
			if (postFellingSurvey.isInspectionBearing()) 
			{
				cellGaris.add(new PdfPCell(new Phrase("4", font12)));
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("")));
			}
			else
			{	
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("4", font12)));	
			}
			cellGaris.add(new PdfPCell(new Phrase("(ii)")));
			cellGaris.add(new PdfPCell(new Phrase("Jarak Garis Tapak betul mengikut Pelan Kawasan Kerja Post-F")));
			
			if (postFellingSurvey.isInspectionLineDistance()) 
			{
				cellGaris.add(new PdfPCell(new Phrase("4", font12)));
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("")));
			}
			else
			{
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("4", font12)));	
			}
			
			
			cellGaris.add(new PdfPCell(new Phrase("(iii)")));
			cellGaris.add(new PdfPCell(new Phrase("Sepanjang Garis Tapak ditanda dengan pancang pada setiap jarak 20m")));
			
			if (postFellingSurvey.isInspectionStake())
			{
				cellGaris.add(new PdfPCell(new Phrase("4", font12)));
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("")));
			}
			else
			{
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("4", font12)));	
			}
			
			
			cellGaris.add(new PdfPCell(new Phrase("(iv)")));
			cellGaris.add(new PdfPCell(new Phrase("Pembetulan  kecuraman telah dibuat di kawasan cerun sepanjang Garis Tapak")));

			if (postFellingSurvey.isInspectionSteepness())
			{
				cellGaris.add(new PdfPCell(new Phrase("4", font12)));
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("")));
			}
			else
			{
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("")));
				cellGaris.add(new PdfPCell(new Phrase("4", font12)));	
			}
		
		

		for (int i = 0; i < cellGaris.size(); i++)
		{
			if (i == 2 || i == 4 || i == 7 || i == 9 || i == 12 || i == 14 || i == 17 || i == 19)
			{
				cellGaris.get(i).setUseVariableBorders(true);
				cellGaris.get(i).setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
				cellGaris.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
				cellGaris.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
				cellGaris.get(i).setPadding(5f);
			}
			else
			{
				cellGaris.get(i).setBorder(0);
				cellGaris.get(i).setHorizontalAlignment(Element.ALIGN_LEFT);
			}
			
			tableGarisTapak2.addCell(cellGaris.get(i));
		}

		
		//--
		
				PdfPTable tableGarisPetakInventori = new PdfPTable(3);
				tableGarisPetakInventori.setWidths(new float[]
				{
						10f, 8f, 82f
				});
				tableGarisPetakInventori.setWidthPercentage(100);
				
				ArrayList<PdfPCell> cellGarisPetakInventori = new ArrayList<PdfPCell>();
				cellGarisPetakInventori.add(new PdfPCell(new Phrase("4.3", size12)));
				cellGarisPetakInventori.add(new PdfPCell(new Phrase("Garis Inventori dan Petak Inventori\n", size12)));
				cellGarisPetakInventori.add(new PdfPCell(new Phrase(" ", size12)));
				cellGarisPetakInventori.add(new PdfPCell(new Phrase("Semakan dijalankan dengan menggunakan Borang Semakan Garis Inventori dan Penempatan Petak Inventori untuk Inventori Hutan Selepas Tebangan (Post-F). Maklumat semakan adalah seperti berikut\n\n", size12)));
				cellGarisPetakInventori.add(new PdfPCell(new Phrase(" ", size12)));
				cellGarisPetakInventori.add(new PdfPCell(new Phrase("4.3.1", size12)));
				cellGarisPetakInventori.add(new PdfPCell(new Phrase("Jarak di antara Garis Inventori dan Pemasangan Tiang Pemulaan serta Tiang Penghujung Garis Inventori.\n\n", size12)));
				
				
				cellGarisPetakInventori.get(1).setColspan(2);
				cellGarisPetakInventori.get(3).setColspan(2);
				
				for (int i = 0; i < cellGarisPetakInventori.size(); i++)
				{
					cellGarisPetakInventori.get(i).setBorder(0);
					cellGarisPetakInventori.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					tableGarisPetakInventori.addCell(cellGarisPetakInventori.get(i));
				}
				
				//--
		
		
		PdfPTable tableJarakGaris = new PdfPTable(9);

		tableJarakGaris.setWidths(new float[]
		{
				16f, 10f, 14f, 10f, 14f, 8f, 10f, 8f, 10f
		});
		tableJarakGaris.setWidthPercentage(100);

		ArrayList<PdfPCell> cellJarakGaris = new ArrayList<PdfPCell>();

		cellJarakGaris.add(new PdfPCell(new Phrase("No. Garis Inventori", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Kedudukan Garis Inventori", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Tiang Pemulaan Garis Inventori", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Tiang Penghujung Inventori", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Dari Garis Sebelum", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Dari Garis Selepas", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Ada", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Tiada", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Ada", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Tiada", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Jarak (m)", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("% Kesilapan", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("Jarak (m)", size10)));
		cellJarakGaris.add(new PdfPCell(new Phrase("% Kesilapan", size10)));

		cellJarakGaris.get(0).setRowspan(3);
		cellJarakGaris.get(1).setColspan(4);
		cellJarakGaris.get(2).setColspan(2);
		cellJarakGaris.get(3).setColspan(2);
		cellJarakGaris.get(4).setColspan(2);
		cellJarakGaris.get(5).setColspan(2);
		cellJarakGaris.get(6).setRowspan(2);
		cellJarakGaris.get(7).setRowspan(2);
		cellJarakGaris.get(8).setRowspan(2);
		cellJarakGaris.get(9).setRowspan(2);

		
		
		
		ArrayList<PostFellingReportElement> reportGarisTapak = inspectionReport.getReportGarisTiang();
		
		int count = 0;
		int currentIndex  = 0;
		for (PostFellingReportElement report : reportGarisTapak)
		{
			count++;
			currentIndex  = 0;
			if ((reportGarisTapak.size() - count) == 1) {
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[0],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[1],size10)));
				currentIndex = cellJarakGaris.size() -1 ;
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[2],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[3],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[4],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[5],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[6],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[7],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[8],size10)));
				
				cellJarakGaris.get(currentIndex).setBackgroundColor(BaseColor.DARK_GRAY);
				currentIndex++;
				cellJarakGaris.get(currentIndex).setBackgroundColor(BaseColor.DARK_GRAY);
				currentIndex++;
				cellJarakGaris.get(currentIndex).setBackgroundColor(BaseColor.DARK_GRAY);
				currentIndex++;
				cellJarakGaris.get(currentIndex).setBackgroundColor(BaseColor.DARK_GRAY);
				
			} else if((reportGarisTapak.size() - count) == 0) 
			{
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[0],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[1],size10)));
				currentIndex = cellJarakGaris.size() -1 ;
				cellJarakGaris.get(currentIndex).setBackgroundColor(BaseColor.DARK_GRAY);
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[2],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[3],size10)));
				currentIndex = cellJarakGaris.size() -1 ;
				cellJarakGaris.get(currentIndex).setBackgroundColor(BaseColor.DARK_GRAY);
				
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[4],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[5],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[6],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[7],size10)));
				cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[8],size10)));
			}	else
				
				for(int i = 0; i < report.getData().length; i++) {
					
					if (report.getData()[i].equals("/"))
							cellJarakGaris.add(new PdfPCell(new Phrase("4",font8)));
					else	
						cellJarakGaris.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
		}
		
		for (int i = 0; i < cellJarakGaris.size(); i++)
		{
			cellJarakGaris.get(i).setPaddingBottom(5f);
			cellJarakGaris.get(i).setPaddingTop(5f);
			cellJarakGaris.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellJarakGaris.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
			tableJarakGaris.addCell(cellJarakGaris.get(i));
		}
	//--
		
		PdfPTable tableBearingGaris = new PdfPTable(2);
		tableBearingGaris.setWidths(new float[]
		{
				10f, 90f
		});
		tableBearingGaris.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellBearingGaris = new ArrayList<PdfPCell>();
		cellBearingGaris.add(new PdfPCell(new Phrase("4.3.2", size12)));
		cellBearingGaris.add(new PdfPCell(new Phrase("Bearing Garis Inventori, Jarak antara Pusat Petak, Pancang Setiap 20m dan Pembetulan Kecuraman.\n\n", size12)));
		
		
		for (int i = 0; i < cellBearingGaris.size(); i++)
		{
			cellBearingGaris.get(i).setBorder(0);
			cellBearingGaris.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tableBearingGaris.addCell(cellBearingGaris.get(i));
		}
		

		
		//--
		
		PdfPTable tableBearingGaris2 = new PdfPTable(9);

		tableBearingGaris2.setWidths(new float[]
		{
				22f, 8f, 8f, 8f, 8f, 8f, 10f, 14f, 14f
		});
		tableBearingGaris2.setWidthPercentage(100);
		ArrayList<PostFellingReportElement> reportBearingGaris = inspectionReport.getReportBearingGaris();
		
		ArrayList<PdfPCell> cellBearingGaris2 = new ArrayList<PdfPCell>();

		cellBearingGaris2.add(new PdfPCell(new Phrase("No. Garis Inventori", size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase("Kesilapan Bearing (Bilangan)", size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase("Kesilapan Jarak Antara Pusat Petak (Bilangan)", size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase("Pancang Setiap 20m (Bilangan)", size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase("Pembetulan Kecuraman (Bilangan)", size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase(lte + "1" + degree, f)));
		cellBearingGaris2.add(new PdfPCell(new Phrase(gt + "1" + degree, size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase(lte + "2" + degree, f)));
		cellBearingGaris2.add(new PdfPCell(new Phrase(gt + "2" + degree, size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase("Ada", size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase("Tiada", size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase("Boleh Diterima", size10)));
		cellBearingGaris2.add(new PdfPCell(new Phrase("Tidak Boleh Diterima", size10)));

		cellBearingGaris2.get(0).setRowspan(2);
		cellBearingGaris2.get(1).setColspan(2);
		cellBearingGaris2.get(2).setColspan(2);
		cellBearingGaris2.get(3).setColspan(2);
		cellBearingGaris2.get(4).setColspan(2);
		
		
		count=0;
		for (PostFellingReportElement report : reportBearingGaris)
		{
			count++;
			currentIndex  = 0;
			
			
			if ((reportBearingGaris.size() - count) == 0) 
			{
				
				cellBearingGaris2.add(new PdfPCell(new Phrase(report.getData()[0],size10)));
				cellBearingGaris2.add(new PdfPCell(new Phrase(report.getData()[2],size10)));
				currentIndex = cellBearingGaris2.size() - 1;
				cellBearingGaris2.get(currentIndex).setColspan(2);
				cellBearingGaris2.add(new PdfPCell(new Phrase(report.getData()[4],size10)));
				currentIndex = cellBearingGaris2.size() - 1;
				cellBearingGaris2.get(currentIndex).setColspan(2);
				cellBearingGaris2.add(new PdfPCell(new Phrase(report.getData()[6],size10)));
				currentIndex = cellBearingGaris2.size() - 1;
				cellBearingGaris2.get(currentIndex).setColspan(2);
				cellBearingGaris2.add(new PdfPCell(new Phrase(report.getData()[8],size10)));
				currentIndex = cellBearingGaris2.size() - 1;
				cellBearingGaris2.get(currentIndex).setColspan(2);
				
			} else
			{
				for(int i = 0; i < report.getData().length; i++) 
				{
					
					if (report.getData()[i].equals("/"))
						cellBearingGaris2.add(new PdfPCell(new Phrase("4",font12)));
					else	
						cellBearingGaris2.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
				
				
			}
		}
			
		
		
		
		for (int i = 0; i < cellBearingGaris2.size(); i++)
		{
			cellBearingGaris2.get(i).setPaddingBottom(5f);
			cellBearingGaris2.get(i).setPaddingTop(5f);
			cellBearingGaris2.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellBearingGaris2.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
			tableBearingGaris2.addCell(cellBearingGaris2.get(i));
		}

		
		
		
		//--
		
				PdfPTable tableSempadan = new PdfPTable(2);
				tableSempadan.setWidths(new float[]
				{
						10f, 90f
				});
				tableSempadan.setWidthPercentage(100);
				
				ArrayList<PdfPCell> cellSempadan = new ArrayList<PdfPCell>();
				cellSempadan.add(new PdfPCell(new Phrase("4.4", size12)));
				cellSempadan.add(new PdfPCell(new Phrase("Sempadan\n\n", size12)));
				
				
				for (int i = 0; i < cellSempadan.size(); i++)
				{
					cellSempadan.get(i).setBorder(0);
					cellSempadan.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					tableSempadan.addCell(cellSempadan.get(i));
				}
				
				
		//--
				

		PdfPTable tableSempadan2 = new PdfPTable(9);
		tableSempadan2.setWidths(new float[]
		{
				20f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f
		});
		tableSempadan2.setWidthPercentage(100);

		ArrayList<PostFellingReportElement> reportSempadan = inspectionReport.getReportSempadan();
		
		ArrayList<PdfPCell> cellSempadan2 = new ArrayList<PdfPCell>();
		
		cellSempadan2.add(new PdfPCell(new Phrase("No. Garis Inventori", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Dari Garis Sebelum", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Dari Garis Selepas", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Tanda Pokok Sempadan", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Lebar Sempadan Dibersihkan", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Penandaan Sempadan2", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Lebar Sempadan Dibersihkan", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Boleh diterima", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Tidak Boleh diterima", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Boleh diterima", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Tidak Boleh diterima", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Boleh diterima", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Tidak Boleh diterima", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Boleh diterima", size10)));
		cellSempadan2.add(new PdfPCell(new Phrase("Tidak Boleh diterima", size10)));
		
		cellSempadan2.get(0).setRowspan(3);
		cellSempadan2.get(1).setColspan(4);
		cellSempadan2.get(2).setColspan(4);
		cellSempadan2.get(3).setColspan(2);
		cellSempadan2.get(4).setColspan(2);
		cellSempadan2.get(5).setColspan(2);
		cellSempadan2.get(6).setColspan(2);
		
		for (PostFellingReportElement report : reportSempadan)
		{
			
				for(int i = 0; i < report.getData().length; i++) 
				{
					
					if (report.getData()[i].equals("/"))
						cellSempadan2.add(new PdfPCell(new Phrase("4",font12)));
					else	
						cellSempadan2.add(new PdfPCell(new Phrase(report.getData()[i], size10)));
				}
				
				
			
		}
		
		for (int i = 0; i < cellSempadan2.size(); i++)
		{
			
			cellSempadan2.get(i).setPaddingBottom(5f);
			cellSempadan2.get(i).setPaddingTop(5f);
			cellSempadan2.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellSempadan2.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
			tableSempadan2.addCell(cellSempadan2.get(i));
		}
		
		
		
		
		//--
		
		PdfPTable tableKaedahInventori = new PdfPTable(2);
		tableKaedahInventori.setWidths(new float[]
		{
				10f, 90f
		});
		tableKaedahInventori.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellKaedahInventori = new ArrayList<PdfPCell>();
		cellKaedahInventori.add(new PdfPCell(new Phrase("4.5", size12)));
		cellKaedahInventori.add(new PdfPCell(new Phrase("Kaedah Inventori\n", size12)));
		
		
		for (int i = 0; i < cellKaedahInventori.size(); i++)
		{
			cellKaedahInventori.get(i).setBorder(0);
			cellKaedahInventori.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tableKaedahInventori.addCell(cellKaedahInventori.get(i));
		}
		
		
		
//--
		
		
		
		
		PdfPTable tablePokokKayu = new PdfPTable(3);
		tablePokokKayu.setWidths(new float[]
		{
				7f, 5f, 88f
		});
		tablePokokKayu.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellPokokKayu = new ArrayList<PdfPCell>();
		
		cellPokokKayu.add(new PdfPCell(new Phrase("(i)", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m) - Pokok Besar/Pokok Kecil", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Kelas kebesaran pokok yang perlu dibanci adalah >45cm diameter dan >30cm hingga 45cm diameter.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Maklumat seperti di Lampiran A.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		
		cellPokokKayu.add(new PdfPCell(new Phrase("(ii)", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Petak Kedua (25m x 20m) - Kayu Jaras Besar", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Kelas kebesaran pokok yang perlu dibanci adalah >15cm hingga 30cm diameter serta pokok kelas yang sama yang dilingkari pepanjat.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Maklumat seperti di Lampiran B.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		
		cellPokokKayu.add(new PdfPCell(new Phrase("(iii)", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Petak Ketiga (10m x 10m) - Kayu Jaras Kecil", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Kelas kebesaran pokok yang perlu dibanci adalah >5cm hingga 15cm diameter.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Maklumat seperti di Lampiran C.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		
		cellPokokKayu.add(new PdfPCell(new Phrase("(iv)", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Petak Keempat (5m x 5m) - Anak Pokok", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Kelas kebesaran pokok yang perlu dibanci adalah >1.5cm tinggi hingga 5cm diameter.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Maklumat seperti di Lampiran D.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		
		cellPokokKayu.add(new PdfPCell(new Phrase("(v)", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Petak Kelima (2m x 2m) - Anak Benih", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Kelas kebesaran pokok yang perlu dibanci adalah 15cm tinggi hingga 1.5 tinggi.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase("l", font7)));
		cellPokokKayu.add(new PdfPCell(new Phrase("Maklumat seperti di Lampiran D.", size12)));
		cellPokokKayu.add(new PdfPCell(new Phrase(" ", size12)));
		
		
		cellPokokKayu.get(1).setColspan(2);
		cellPokokKayu.get(8).setColspan(3);
		
		cellPokokKayu.get(10).setColspan(2);
		cellPokokKayu.get(17).setColspan(3);
		
		cellPokokKayu.get(19).setColspan(2);
		cellPokokKayu.get(26).setColspan(3);
		
		cellPokokKayu.get(28).setColspan(2);
		cellPokokKayu.get(35).setColspan(3);
		
		cellPokokKayu.get(37).setColspan(2);
		cellPokokKayu.get(44).setColspan(3);
		
		
		for (int i = 0; i < cellPokokKayu.size(); i++)
		{
			if (i==3 || i == 6 || i == 12 || i == 15 || i==21 ||i==24 || i==30 || i==33 || i==39 || i==42) 
					cellPokokKayu.get(i).setPaddingTop(6f);
			cellPokokKayu.get(i).setBorder(0);
			//cellPokokKayu.get(i).setVerticalAlignment(Element.ALIGN_TOP);
			cellPokokKayu.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tablePokokKayu.addCell(cellPokokKayu.get(i));
		}
		
		
		
		
		PdfPTable tableBuluh = new PdfPTable(3);
		tableBuluh.setWidths(new float[]
		{
				10f, 5f, 85f
		});
		tableBuluh.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellBuluh = new ArrayList<PdfPCell>();
		
		cellBuluh.add(new PdfPCell(new Phrase("4.5.2", size12)));
		cellBuluh.add(new PdfPCell(new Phrase("Buluh\n", size12)));
		cellBuluh.add(new PdfPCell(new Phrase(" ", size12)));
		cellBuluh.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)", size12)));
		cellBuluh.add(new PdfPCell(new Phrase(" ", size12)));
		cellBuluh.add(new PdfPCell(new Phrase("l", font7)));
		cellBuluh.add(new PdfPCell(new Phrase("Bilangan rumpun buluh yang diameter pangkalnya 50cm dan lebih", size12)));
		cellBuluh.add(new PdfPCell(new Phrase(" ", size12)));
		cellBuluh.add(new PdfPCell(new Phrase("l", font7)));
		cellBuluh.add(new PdfPCell(new Phrase("Maklumat semakan di Lampiran E\n\n", size12)));
		
		cellBuluh.get(1).setColspan(2);
		cellBuluh.get(3).setColspan(2);
		
		for (int i = 0; i < cellBuluh.size(); i++)
		{
			if (i==3 || i==5) 
				cellBuluh.get(i).setPaddingTop(6f);
			
			cellBuluh.get(i).setBorder(0);
			cellBuluh.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tableBuluh.addCell(cellBuluh.get(i));
		}
		
		
		
		
		PdfPTable tablePalma = new PdfPTable(3);
		tablePalma.setWidths(new float[]
		{
				10f, 5f, 85f
		});
		tablePalma.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellPalma = new ArrayList<PdfPCell>();
		
		cellPalma.add(new PdfPCell(new Phrase("4.5.3", size12)));
		cellPalma.add(new PdfPCell(new Phrase("Palma\n", size12)));
		cellPalma.add(new PdfPCell(new Phrase(" ", size12)));
		cellPalma.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)", size12)));
		cellPalma.add(new PdfPCell(new Phrase(" ", size12)));
		cellPalma.add(new PdfPCell(new Phrase("l", font7)));
		cellPalma.add(new PdfPCell(new Phrase("Bilangan batang langkap/bayas dan palma lain yang tingginya melebihi 2m", size12)));
		cellPalma.add(new PdfPCell(new Phrase(" ", size12)));
		cellPalma.add(new PdfPCell(new Phrase("l", font7)));
		cellPalma.add(new PdfPCell(new Phrase("Maklumat semakan di Lampiran F\n\n", size12)));
		
		cellPalma.get(1).setColspan(2);
		cellPalma.get(3).setColspan(2);
		
		for (int i = 0; i < cellPalma.size(); i++)
		{
			if (i==3 || i==5) 
				cellPalma.get(i).setPaddingTop(6f);
			
			cellPalma.get(i).setBorder(0);
			cellPalma.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tablePalma.addCell(cellPalma.get(i));
		}
		
		
		
		PdfPTable tableBertam = new PdfPTable(3);
		tableBertam.setWidths(new float[]
		{
				10f, 5f, 85f
		});
		tableBertam.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellBertam = new ArrayList<PdfPCell>();
		
		cellBertam.add(new PdfPCell(new Phrase("4.5.4", size12)));
		cellBertam.add(new PdfPCell(new Phrase("Bertam\n", size12)));
		cellBertam.add(new PdfPCell(new Phrase(" ", size12)));
		cellBertam.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)", size12)));
		cellBertam.add(new PdfPCell(new Phrase(" ", size12)));
		cellBertam.add(new PdfPCell(new Phrase("l", font7)));
		cellBertam.add(new PdfPCell(new Phrase("Bilangan rumpun yang diameter pangkalnya 50cm dan lebih", size12)));
		cellBertam.add(new PdfPCell(new Phrase(" ", size12)));
		cellBertam.add(new PdfPCell(new Phrase("l", font7)));
		cellBertam.add(new PdfPCell(new Phrase("Maklumat semakan di Lampiran F\n", size12)));
		
		cellBertam.get(1).setColspan(2);
		cellBertam.get(3).setColspan(2);
		
		for (int i = 0; i < cellBertam.size(); i++)
		{
			if (i==3 || i==5) 
				cellBertam.get(i).setPaddingTop(6f);
			
			cellBertam.get(i).setBorder(0);
			cellBertam.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tableBertam.addCell(cellBertam.get(i));
		}
		
		
		
		PdfPTable tablePepanjat = new PdfPTable(3);
		tablePepanjat.setWidths(new float[]
		{
				10f, 5f, 85f
		});
		tablePepanjat.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellPepanjat = new ArrayList<PdfPCell>();
		
		cellPepanjat.add(new PdfPCell(new Phrase("4.5.5", size12)));
		cellPepanjat.add(new PdfPCell(new Phrase("Pepanjat\n", size12)));
		cellPepanjat.add(new PdfPCell(new Phrase(" ", size12)));
		cellPepanjat.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)", size12)));
		cellPepanjat.add(new PdfPCell(new Phrase(" ", size12)));
		cellPepanjat.add(new PdfPCell(new Phrase("l", font7)));
		cellPepanjat.add(new PdfPCell(new Phrase("Bilangan pepanjat yang terdapat pada tiap-tiap Pokok Tebangan Akhir dan Pokok Tebangan Akhir yang Berpotensi berukuran diameter paras dada 15cm dan ke atas", size12)));
		cellPepanjat.add(new PdfPCell(new Phrase(" ", size12)));
		cellPepanjat.add(new PdfPCell(new Phrase("l", font7)));
		cellPepanjat.add(new PdfPCell(new Phrase("Maklumat semakan di Lampiran F\n\n", size12)));
		
		cellPepanjat.get(1).setColspan(2);
		cellPepanjat.get(3).setColspan(2);
		
		for (int i = 0; i < cellPepanjat.size(); i++)
		{
			if (i==3 || i==5) 
				cellPepanjat.get(i).setPaddingTop(6f);
			
			cellPepanjat.get(i).setBorder(0);
			cellPepanjat.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tablePepanjat.addCell(cellPepanjat.get(i));
		}
		
		
		
		
		PdfPTable tableResam = new PdfPTable(3);
		tableResam.setWidths(new float[]
		{
				10f, 5f, 85f
		});
		tableResam.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellResam = new ArrayList<PdfPCell>();
		
		cellResam.add(new PdfPCell(new Phrase("4.5.6", size12)));
		cellResam.add(new PdfPCell(new Phrase("Resam\n", size12)));
		cellResam.add(new PdfPCell(new Phrase(" ", size12)));
		cellResam.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)", size12)));
		cellResam.add(new PdfPCell(new Phrase(" ", size12)));
		cellResam.add(new PdfPCell(new Phrase("l", font7)));
		cellResam.add(new PdfPCell(new Phrase("Dibanci mengikut peratusan keluasan yang ditumbuhinya", size12)));
		cellResam.add(new PdfPCell(new Phrase(" ", size12)));
		cellResam.add(new PdfPCell(new Phrase("l", font7)));
		cellResam.add(new PdfPCell(new Phrase("Maklumat semakan di Lampiran G\n\n", size12)));
		
		cellResam.get(1).setColspan(2);
		cellResam.get(3).setColspan(2);
		
		for (int i = 0; i < cellResam.size(); i++)
		{
			if (i==3 || i==5) 
				cellResam.get(i).setPaddingTop(6f);
			
			cellResam.get(i).setBorder(0);
			cellResam.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tableResam.addCell(cellResam.get(i));
		}
		
		
		PdfPTable tableHalia = new PdfPTable(3);
		tableHalia.setWidths(new float[]
		{
				10f, 5f, 85f
		});
		tableHalia.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellHalia = new ArrayList<PdfPCell>();
		
		cellHalia.add(new PdfPCell(new Phrase("4.5.7", size12)));
		cellHalia.add(new PdfPCell(new Phrase("Jenis-jenis Halia\n", size12)));
		cellHalia.add(new PdfPCell(new Phrase(" ", size12)));
		cellHalia.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)", size12)));
		cellHalia.add(new PdfPCell(new Phrase(" ", size12)));
		cellHalia.add(new PdfPCell(new Phrase("l", font7)));
		cellHalia.add(new PdfPCell(new Phrase("Dibanci mengikut peratusan keluasan yang ditumbuhinya", size12)));
		cellHalia.add(new PdfPCell(new Phrase(" ", size12)));
		cellHalia.add(new PdfPCell(new Phrase("l", font7)));
		cellHalia.add(new PdfPCell(new Phrase("Maklumat semakan di Lampiran G\n\n", size12)));
		
		cellHalia.get(1).setColspan(2);
		cellHalia.get(3).setColspan(2);
		
		for (int i = 0; i < cellHalia.size(); i++)
		{
			if (i==3 || i==5) 
				cellHalia.get(i).setPaddingTop(6f);
			
			cellHalia.get(i).setBorder(0);
			cellHalia.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tableHalia.addCell(cellHalia.get(i));
		}
		
		
		//--
		PdfPTable tablePisang = new PdfPTable(3);
		tablePisang.setWidths(new float[]
		{
				10f, 5f, 85f
		});
		tablePisang.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellPisang = new ArrayList<PdfPCell>();
		
		cellPisang.add(new PdfPCell(new Phrase("4.5.7", size12)));
		cellPisang.add(new PdfPCell(new Phrase("Pisang\n", size12)));
		cellPisang.add(new PdfPCell(new Phrase(" ", size12)));
		cellPisang.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)", size12)));
		cellPisang.add(new PdfPCell(new Phrase(" ", size12)));
		cellPisang.add(new PdfPCell(new Phrase("l", font7)));
		cellPisang.add(new PdfPCell(new Phrase("Dibanci mengikut peratusan keluasan yang ditumbuhinya", size12)));
		cellPisang.add(new PdfPCell(new Phrase(" ", size12)));
		cellPisang.add(new PdfPCell(new Phrase("l", font7)));
		cellPisang.add(new PdfPCell(new Phrase("Maklumat semakan di Lampiran G\n\n", size12)));
		
		cellPisang.get(1).setColspan(2);
		cellPisang.get(3).setColspan(2);
		
		for (int i = 0; i < cellPisang.size(); i++)
		{
			if (i==3 || i==5) 
				cellPisang.get(i).setPaddingTop(6f);
			
			cellPisang.get(i).setBorder(0);
			cellPisang.get(i).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tablePisang.addCell(cellPisang.get(i));
		}
		
		
		//--
		
		//--
		PdfPTable tablePasukan = new PdfPTable(5);
		tablePasukan.setWidths(new float[]
		{
				10f, 5f, 36f, 30f, 19f
		});
		tablePasukan.setWidthPercentage(100);
		
		ArrayList<PdfPCell> cellPasukan = new ArrayList<PdfPCell>();
		
		long diff = postFellingSurvey.getInspectionEndWorkDate().getTime() - postFellingSurvey.getInspectionStartWorkDate().getTime();

        
		cellPasukan.add(new PdfPCell(new Phrase("5.0", bold12)));
		cellPasukan.add(new PdfPCell(new Phrase("PASUKAN DAN TARIKH SEMAKAN\n", bold12)));
		cellPasukan.add(new PdfPCell(new Phrase(" ", size12)));
		cellPasukan.add(new PdfPCell(new Phrase("Semakan Inventori Hutan Selepas Tebangan (Post-F) ini telah dijalankan selama "+(diff / 1000 / 60 / 60 / 24)+" iaitu dari tarikh "+ sdf.format(postFellingSurvey.getInspectionStartWorkDate()) +" hingga "+sdf.format(postFellingSurvey.getInspectionEndWorkDate())+" bersama-sama dengan kakitangan di bawah:\n\n", size12)));
		cellPasukan.add(new PdfPCell(new Phrase(" ", size12)));
		cellPasukan.add(new PdfPCell(new Phrase("Bil", bold12)));
		cellPasukan.add(new PdfPCell(new Phrase("Nama", bold12)));
		cellPasukan.add(new PdfPCell(new Phrase("Jawatan", bold12)));
		cellPasukan.add(new PdfPCell(new Phrase("Tandatangan",bold12)));
		
		
		String leaderDesignation = "";
		int c = 1;
		for (Staff staff: inspectionStaffs)
		{
			if (staff.getStaffID().equals(postFellingSurvey.getInspectionLeaderID()))
				leaderDesignation = staff.getDesignationName();
			cellPasukan.add(new PdfPCell(new Phrase(" ", size12)));
			cellPasukan.add(new PdfPCell(new Phrase(RomanNumberConverter.toRoman(c), size10)));
			cellPasukan.add(new PdfPCell(new Phrase(staff.getName().toUpperCase(), size10)));
			cellPasukan.add(new PdfPCell(new Phrase(staff.getDesignationName().toUpperCase(), size10)));
			cellPasukan.add(new PdfPCell(new Phrase("...............", size12)));
			c++;	
		}
	
		cellPasukan.get(1).setColspan(4);
		cellPasukan.get(3).setColspan(4);
		cellPasukan.get(3).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		for (int i = 0; i < cellPasukan.size(); i++)
		{
			cellPasukan.get(i).setBorder(0);
			//if (i>4 && i<9)
			//	cellPasukan.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
			
			tablePasukan.addCell(cellPasukan.get(i));
		}
		
		//--
		
		//--
				PdfPTable tableUlasanKetua = new PdfPTable(2);
				tableUlasanKetua.setWidths(new float[]
				{
						10f, 90f
				});
				tableUlasanKetua.setWidthPercentage(100);
				
				ArrayList<PdfPCell> cellUlasanKetua = new ArrayList<PdfPCell>();
				
				
		        
				cellUlasanKetua.add(new PdfPCell(new Phrase("6.0", bold12)));
				cellUlasanKetua.add(new PdfPCell(new Phrase("ULASAN DAN SYOR KETUA PASUKAN SEMAKAN\n", bold12)));
				cellUlasanKetua.add(new PdfPCell(new Phrase(" ")));
				cellUlasanKetua.add(new PdfPCell(new Phrase("(Nyatakan sama ada kerja-kerja inventori telah dijalankan mengikut spesifikasi dan syarat kerja yang ditetapkan dan berikan syor serta tindakan susulan):\n\n", size12)));
				cellUlasanKetua.add(new PdfPCell(new Phrase(" ", size12)));
				
				if (postFellingSurvey.getCommentInspectionLeader() == null || postFellingSurvey.getCommentInspectionLeader().length()==0)
					cellUlasanKetua.add(new PdfPCell(new Phrase("____________________________________________________________\n\n____________________________________________________________\n")));
				else 
					cellUlasanKetua.add(new PdfPCell(new Phrase(postFellingSurvey.getCommentInspectionLeader()+"\n\n", italic12)));
				cellUlasanKetua.add(new PdfPCell(new Phrase(" ")));
				cellUlasanKetua.add(new PdfPCell(new Phrase("___________________________\n\n(Tandatangan Ketua Pasukan Semakan)\n\nNama: "+postFellingSurvey.getInspectionLeaderName().toUpperCase()+"\n\nJawatan: "+leaderDesignation.toUpperCase()+"\n\nTarikh: ___________________________\n\n\n\n")));
				
				
				cellUlasanKetua.get(3).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				for (int i = 0; i < cellUlasanKetua.size(); i++)
				{
					cellUlasanKetua.get(i).setBorder(0);
					tableUlasanKetua.addCell(cellUlasanKetua.get(i));
				}
				
				//--
		
				//--
				PdfPTable tableUlasanPPPN = new PdfPTable(2);
				tableUlasanPPPN.setWidths(new float[]
				{
						10f, 90f
				});
				tableUlasanPPPN.setWidthPercentage(100);
				
				ArrayList<PdfPCell> cellUlasanPPPN = new ArrayList<PdfPCell>();
				
				
		        
				cellUlasanPPPN.add(new PdfPCell(new Phrase("7.0", bold12)));
				cellUlasanPPPN.add(new PdfPCell(new Phrase("ULASAN/KEPUTUSAN PPPN\n\n ", bold12)));
				cellUlasanPPPN.add(new PdfPCell(new Phrase(" ", size12)));
				
				if (postFellingSurvey.getCommentPPPN() == null || postFellingSurvey.getCommentPPPN().length()==0)
					cellUlasanPPPN.add(new PdfPCell(new Phrase("___________________________________________________________\n\n__________________________________________________________\n")));
				else 
					cellUlasanPPPN.add(new PdfPCell(new Phrase(postFellingSurvey.getCommentPPPN()+"\n\n", italic12)));
				cellUlasanPPPN.add(new PdfPCell(new Phrase(" ")));
				cellUlasanPPPN.add(new PdfPCell(new Phrase("\n\n___________________________\n\n(Tandatangan PPPN)\n\nNama: __________________________\n\nJawatan: ________________________\n\nTarikh: __________________________\n\n\n  ")));
				
				
				cellUlasanPPPN.get(3).setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				for (int i = 0; i < cellUlasanPPPN.size(); i++)
				{
					cellUlasanPPPN.get(i).setBorder(0);
					tableUlasanPPPN.addCell(cellUlasanPPPN.get(i));
				}
				
				//--
				Paragraph paraAsas = new Paragraph();
				paraAsas.setIndentationLeft(55f);
				paraAsas.setIndentationRight(60f);
				paraAsas.add(tableAsas);
				
				
				Paragraph paraTempoh = new Paragraph();
				paraTempoh.setIndentationLeft(55f);
				paraTempoh.setIndentationRight(60f);
				paraTempoh.add(tableTempoh);
				
				Paragraph paraKawasan = new Paragraph();
				paraKawasan.setIndentationLeft(55f);
				paraKawasan.setIndentationRight(60f);
				paraKawasan.add(tableKawasan);
				
				Paragraph paraButiran = new Paragraph();
				paraButiran.setIndentationLeft(55f);
				paraButiran.setIndentationRight(60f);
				paraButiran.add(tableButiran);
				
				Paragraph paraSemakanPapanTanda = new Paragraph();
				paraSemakanPapanTanda.setIndentationLeft(105f);
				paraSemakanPapanTanda.setIndentationRight(60f);
				paraSemakanPapanTanda.add(tableSemakanPapanTanda);
				
				Paragraph paraGarisTapak = new Paragraph();
				paraGarisTapak.setIndentationLeft(90f);
				paraGarisTapak.setIndentationRight(60f);
				paraGarisTapak.add(tableGarisTapak);
				
				Paragraph paraGarisTapak2 = new Paragraph();
				paraGarisTapak2.setIndentationLeft(130f);
				paraGarisTapak2.setIndentationRight(60f);
				paraGarisTapak2.add(tableGarisTapak2);
				
				Paragraph paraGarisPetakInventori = new Paragraph();
				paraGarisPetakInventori.setIndentationLeft(90f);
				paraGarisPetakInventori.setIndentationRight(60f);
				paraGarisPetakInventori.add(tableGarisPetakInventori);
				
				Paragraph paraJarakGaris = new Paragraph();
				paraJarakGaris.setIndentationLeft(130f);
				paraJarakGaris.setIndentationRight(60f);
				paraJarakGaris.add(tableJarakGaris);
				
				Paragraph paraBearingGaris = new Paragraph();
				paraBearingGaris.setIndentationLeft(130f);
				paraBearingGaris.setIndentationRight(60f);
				paraBearingGaris.add(tableBearingGaris);
				
				Paragraph paraBearingGaris2 = new Paragraph();
				paraBearingGaris2.setIndentationLeft(130f);
				paraBearingGaris2.setIndentationRight(60f);
				paraBearingGaris2.add(tableBearingGaris2);
				
				Paragraph paraSempadan = new Paragraph();
				paraSempadan.setIndentationLeft(90f);
				paraSempadan.setIndentationRight(60f);
				paraSempadan.add(tableSempadan);
				
				
				Paragraph paraSempadan2 = new Paragraph();
				paraSempadan2.setIndentationLeft(90f);
				paraSempadan2.setIndentationRight(60f);
				paraSempadan2.add(tableSempadan2);
				
				Paragraph paraKaedahInventori = new Paragraph();
				paraKaedahInventori.setIndentationLeft(90f);
				paraKaedahInventori.setIndentationRight(60f);
				paraKaedahInventori.add(tableKaedahInventori);
				
				Paragraph paraPokokKayu = new Paragraph();
				paraPokokKayu.setIndentationLeft(130f);
				paraPokokKayu.setIndentationRight(60f);
				paraPokokKayu.add(tablePokokKayu);
		
				Paragraph paraBuluh = new Paragraph();
				paraBuluh.setIndentationLeft(130f);
				paraBuluh.setIndentationRight(60f);
				paraBuluh.add(tableBuluh);
				
				Paragraph paraPalma = new Paragraph();
				paraPalma.setIndentationLeft(130f);
				paraPalma.setIndentationRight(60f);
				paraPalma.add(tablePalma);
				
				Paragraph paraBertam = new Paragraph();
				paraBertam.setIndentationLeft(130f);
				paraBertam.setIndentationRight(60f);
				paraBertam.add(tableBertam);
				
				Paragraph paraPepanjat = new Paragraph();
				paraPepanjat.setIndentationLeft(130f);
				paraPepanjat.setIndentationRight(60f);
				paraPepanjat.add(tablePepanjat);
				
				Paragraph paraResam = new Paragraph();
				paraResam.setIndentationLeft(130f);
				paraResam.setIndentationRight(60f);
				paraResam.add(tableResam);
				
				Paragraph paraHalia = new Paragraph();
				paraHalia.setIndentationLeft(130f);
				paraHalia.setIndentationRight(60f);
				paraHalia.add(tableHalia);
				
				Paragraph paraPisang = new Paragraph();
				paraPisang.setIndentationLeft(130f);
				paraPisang.setIndentationRight(60f);
				paraPisang.add(tablePisang);
				
				Paragraph paraPasukan = new Paragraph();
				paraPasukan.setIndentationLeft(55f);
				paraPasukan.setIndentationRight(60f);
				paraPasukan.add(tablePasukan);
				
				Paragraph paraUlasanKetua = new Paragraph();
				paraUlasanKetua.setIndentationLeft(55f);
				paraUlasanKetua.setIndentationRight(60f);
				paraUlasanKetua.add(tableUlasanKetua);
				
				Paragraph paraUlasanPPPN = new Paragraph();
				paraUlasanPPPN.setIndentationLeft(55f);
				paraUlasanPPPN.setIndentationRight(60f);
				paraUlasanPPPN.add(tableUlasanPPPN);
				
				
				//--
				
				
				PdfPTable tableLampiranHeader = new PdfPTable(4);
				
				tableLampiranHeader.setWidths(new float[]
				{
						30f, 30f, 25f,15f
				});
				tableLampiranHeader.setWidthPercentage(100);
				ArrayList<PdfPCell> cellLampiranHeader= new ArrayList<PdfPCell>();
				
				
				cellLampiranHeader.add(new PdfPCell(new Phrase("Daerah Hutan: "+postFellingSurvey.getDistrictName().toUpperCase(),size10)));
				cellLampiranHeader.add(new PdfPCell(new Phrase("Hutan Simpan: "+postFellingSurvey.getForestName().toUpperCase(),size10)));
				cellLampiranHeader.add(new PdfPCell(new Phrase("No. Kompartmen/Subkompartmen: "+postFellingSurvey.getComptBlockNo().toUpperCase(),size10)));
				cellLampiranHeader.add(new PdfPCell(new Phrase("Luas: "+postFellingSurvey.getArea()+" ha",size10)));
				cellLampiranHeader.add(new PdfPCell(new Phrase("No. Garis Inventori: "+inspectionLines.toUpperCase(),size10)));
				cellLampiranHeader.add(new PdfPCell(new Phrase("Tarikh Semakan: "+sdf.format(postFellingSurvey.getInspectionStartDate()).toUpperCase()+ "\n ",size10)));
				
				
				
				cellLampiranHeader.get(3).setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellLampiranHeader.get(5).setColspan(3);
				
				for (int i = 0; i< cellLampiranHeader.size();i++)
				{	
					cellLampiranHeader.get(i).setBorder(0);
					cellLampiranHeader.get(i).setVerticalAlignment(Element.ALIGN_TOP);
					tableLampiranHeader.addCell(cellLampiranHeader.get(i));
				}
				
				Paragraph paraLampiranDetails = new Paragraph();
				paraLampiranDetails.setIndentationLeft(20f);
				paraLampiranDetails.setIndentationRight(20f);
				paraLampiranDetails.add(tableLampiranHeader);
				
				
				Paragraph paraLampiranTitle = new Paragraph();
				paraLampiranTitle.setIndentationLeft(40f);
				paraLampiranTitle.setIndentationRight(40f);
				paraLampiranTitle.setSpacingAfter(10f);
				paraLampiranTitle.setAlignment(Element.ALIGN_CENTER);
				paraLampiranTitle.setFont(bold12);
				
				
				
				
				Paragraph paraLampiranHeader = new Paragraph();
				paraLampiranHeader.setIndentationLeft(20f);
				paraLampiranHeader.setIndentationRight(20f);
				paraLampiranHeader.setAlignment(Element.ALIGN_RIGHT);
				paraLampiranHeader.setFont(bold10);
				
				
				Paragraph paraLampiranFooter = new Paragraph();
				paraLampiranFooter.add(new Phrase("\n\n_______________________________\nTandatangan Ketua Pasukan Semakan",size10));
				paraLampiranFooter.add(new Phrase("\nNama: " + postFellingSurvey.getInspectionLeaderName().toUpperCase(),size10));
				paraLampiranFooter.add(new Phrase("\nJawatan: " + leaderDesignation.toUpperCase(),size10));
				paraLampiranFooter.add(new Phrase("\nTarikh: _______________________",size10));
				paraLampiranFooter.setIndentationLeft(20f);
				paraLampiranFooter.setIndentationRight(20f);
				paraLampiranFooter.setAlignment(Element.ALIGN_LEFT);
				
				//--
				
				PdfPTable tableLampiranA = new PdfPTable(16);
				ArrayList<PostFellingReportElement> reportLampiranA = inspectionReport.getReportLampiranA();
				tableLampiranA.setWidths(new float[]
				{
						3f, 10f,5f, 7f, 7f, 5f, 7f, 5f, 7f, 5f, 7f, 7f, 5f, 7f, 5f, 7f
				});
				tableLampiranA.setWidthPercentage(100);
				
				
				ArrayList<PdfPCell> cellLampiranA= new ArrayList<PdfPCell>();
				
				cellLampiranA.add(new PdfPCell(new Phrase("Bil",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("No. Petak Inventori",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)\nSemua Pokok >45cm",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Petak Utama (50m x 20m)\nSemua Pokok >30cm - 45cm",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Bilangan Pokok",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Kesilapan Inventori",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Bilangan Pokok",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Kesilapan Inventori",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Spesis",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Ukuran",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Spesis",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Ukuran",size10)));
				
				cellLampiranA.add(new PdfPCell(new Phrase("Bil Pokok",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Bil Pokok",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Bil Pokok",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("Bil Pokok",size10)));
				cellLampiranA.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				
				cellLampiranA.get(0).setRowspan(4);
				cellLampiranA.get(1).setRowspan(4);
				cellLampiranA.get(2).setColspan(7);
				cellLampiranA.get(3).setColspan(7);
				
				cellLampiranA.get(4).setColspan(3);
				cellLampiranA.get(5).setColspan(4);
				cellLampiranA.get(6).setColspan(3);
				cellLampiranA.get(7).setColspan(4);
				
				cellLampiranA.get(8).setRowspan(2);
				cellLampiranA.get(9).setRowspan(2);
				cellLampiranA.get(10).setRowspan(2);
				
				cellLampiranA.get(11).setColspan(2);
				cellLampiranA.get(12).setColspan(2);
				
				cellLampiranA.get(13).setRowspan(2);
				cellLampiranA.get(14).setRowspan(2);
				cellLampiranA.get(15).setRowspan(2);
				
				cellLampiranA.get(16).setColspan(2);
				cellLampiranA.get(17).setColspan(2);

				for (PostFellingReportElement report : reportLampiranA)
				{
					for(int i = 0; i < report.getData().length; i++)
					
					cellLampiranA.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
				
				
				
				for (int i = 0; i < cellLampiranA.size(); i++)
				{
					cellLampiranA.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellLampiranA.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
					cellLampiranA.get(i).setPaddingTop(5f);
					cellLampiranA.get(i).setPaddingBottom(5f);
					tableLampiranA.addCell(cellLampiranA.get(i));
				}

				
				
				
				
				Paragraph paraLampiranA = new Paragraph();
				paraLampiranA.setIndentationLeft(20f);
				paraLampiranA.setIndentationRight(20f);
				
				paraLampiranA.add(tableLampiranA);
				
				
		//
				
//--
				
				PdfPTable tableLampiranB = new PdfPTable(9);
				ArrayList<PostFellingReportElement> reportLampiranB = inspectionReport.getReportLampiranB();
				tableLampiranB.setWidths(new float[]
				{
						3f, 20f, 11f, 11f, 11f, 11f, 11f, 11f, 11f
				});
				tableLampiranB.setWidthPercentage(100);
				
				
				ArrayList<PdfPCell> cellLampiranB= new ArrayList<PdfPCell>();
				
				cellLampiranB.add(new PdfPCell(new Phrase("Bil",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("No. Petak Inventori",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Petak Kedua (25m x 20m)\nSemua Pokok >15cm - 30cm diameter",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Bilangan Pokok",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Kesilapan Inventori",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Spesis",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Ukuran",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Bil Pokok",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("Bil Pokok",size10)));
				cellLampiranB.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				
				
				cellLampiranB.get(0).setRowspan(4);
				cellLampiranB.get(1).setRowspan(4);
				cellLampiranB.get(2).setColspan(7);
				
				cellLampiranB.get(3).setColspan(3);
				cellLampiranB.get(4).setColspan(4);
				cellLampiranB.get(5).setRowspan(2);
				cellLampiranB.get(6).setRowspan(2);
				cellLampiranB.get(7).setRowspan(2);
				
				cellLampiranB.get(8).setColspan(2);
				cellLampiranB.get(9).setColspan(2);
			
				

				for (PostFellingReportElement report : reportLampiranB)
				{
					for(int i = 0; i < report.getData().length; i++)
						cellLampiranB.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
				
				for (int i = 0; i < cellLampiranB.size(); i++)
				{
					cellLampiranB.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellLampiranB.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
					cellLampiranB.get(i).setPaddingTop(5f);
					cellLampiranB.get(i).setPaddingBottom(5f);
					tableLampiranB.addCell(cellLampiranB.get(i));
				}

				Paragraph paraLampiranB = new Paragraph();
				paraLampiranB.setIndentationLeft(20f);
				paraLampiranB.setIndentationRight(20f);
				paraLampiranB.add(tableLampiranB);
				
				//--
				
				PdfPTable tableLampiranC = new PdfPTable(9);
				ArrayList<PostFellingReportElement> reportLampiranC = inspectionReport.getReportLampiranC();
				tableLampiranC.setWidths(new float[]
				{
						3f, 20f, 11f, 11f, 11f, 11f, 11f, 11f, 11f
				});
				tableLampiranC.setWidthPercentage(100);
				
				
				ArrayList<PdfPCell> cellLampiranC= new ArrayList<PdfPCell>();
				
				cellLampiranC.add(new PdfPCell(new Phrase("Bil",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("No. Petak Inventori",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Petak Ketiga (10m x 10m)\nSemua Pokok >5cm - 15cm diameter",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Bilangan Pokok",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Kesilapan Inventori",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Spesis",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Ukuran",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Bil Pokok",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("Bil Pokok",size10)));
				cellLampiranC.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				
				
				cellLampiranC.get(0).setRowspan(4);
				cellLampiranC.get(1).setRowspan(4);
				cellLampiranC.get(2).setColspan(7);
				
				cellLampiranC.get(3).setColspan(3);
				cellLampiranC.get(4).setColspan(4);
				cellLampiranC.get(5).setRowspan(2);
				cellLampiranC.get(6).setRowspan(2);
				cellLampiranC.get(7).setRowspan(2);
				
				cellLampiranC.get(8).setColspan(2);
				cellLampiranC.get(9).setColspan(2);
			
				

				for (PostFellingReportElement report : reportLampiranC)
				{
					for(int i = 0; i < report.getData().length; i++)
						cellLampiranC.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
				
				for (int i = 0; i < cellLampiranC.size(); i++)
				{
					cellLampiranC.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellLampiranC.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
					cellLampiranC.get(i).setPaddingTop(5f);
					cellLampiranC.get(i).setPaddingBottom(5f);
					tableLampiranC.addCell(cellLampiranC.get(i));
				}

				Paragraph paraLampiranC = new Paragraph();
				paraLampiranC.setIndentationLeft(20f);
				paraLampiranC.setIndentationRight(20f);
				paraLampiranC.add(tableLampiranC);
				
				//--
				
				PdfPTable tableLampiranD = new PdfPTable(12);
				ArrayList<PostFellingReportElement> reportLampiranD = inspectionReport.getReportLampiranD();
				tableLampiranD.setWidths(new float[]
				{
						3f, 17f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f
				});
				tableLampiranD.setWidthPercentage(100);
				
				
				ArrayList<PdfPCell> cellLampiranD= new ArrayList<PdfPCell>();
				
				cellLampiranD.add(new PdfPCell(new Phrase("Bil",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("No. Petak Inventori",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Petak Keempat / Kelima (5m x 5m / 2m x 2m)\nSemua Pokok 5cm diameter dan kebawah",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Anak Pokok >1.5m Tinggi - 1.5m Tinggi",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Anak Benih 15cm Tinggi - 5cm Diameter",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Bilangan Anak Pokok",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Kesilapan Spesis",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Bilangan Anak Benih",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Kesilapan Spesis",size10)));
				
				
				cellLampiranD.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Bilangan",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				
				cellLampiranD.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("Bilangan",size10)));
				cellLampiranD.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				
				
				cellLampiranD.get(0).setRowspan(4);
				cellLampiranD.get(1).setRowspan(4);
				cellLampiranD.get(2).setColspan(10);
				
				cellLampiranD.get(3).setColspan(5);
				cellLampiranD.get(4).setColspan(5);
				
				cellLampiranD.get(5).setColspan(3);
				cellLampiranD.get(6).setColspan(2);
				cellLampiranD.get(7).setColspan(3);
				cellLampiranD.get(8).setColspan(2);

				for (PostFellingReportElement report : reportLampiranD)
				{
					for(int i = 0; i < report.getData().length; i++)
						cellLampiranD.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
				
				for (int i = 0; i < cellLampiranD.size(); i++)
				{
					cellLampiranD.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellLampiranD.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
					cellLampiranD.get(i).setPaddingTop(5f);
					cellLampiranD.get(i).setPaddingBottom(5f);
					tableLampiranD.addCell(cellLampiranD.get(i));
				}

				Paragraph paraLampiranD = new Paragraph();
				paraLampiranD.setIndentationLeft(20f);
				paraLampiranD.setIndentationRight(20f);
				paraLampiranD.add(tableLampiranD);
				
				
				//--
				
				PdfPTable tableLampiranE = new PdfPTable(5);
				ArrayList<PostFellingReportElement> reportLampiranE = inspectionReport.getReportLampiranE();
				tableLampiranE.setWidths(new float[]
				{
						6f, 28f, 22f,22f,22f
				});
				tableLampiranE.setWidthPercentage(90);
				
				
				ArrayList<PdfPCell> cellLampiranE= new ArrayList<PdfPCell>();
				
				cellLampiranE.add(new PdfPCell(new Phrase("Bil",size10)));
				cellLampiranE.add(new PdfPCell(new Phrase("No. Petak Inventori",size10)));
				cellLampiranE.add(new PdfPCell(new Phrase("Bilangan Rumpun Buluh yang dibanci Asal",size10)));
				cellLampiranE.add(new PdfPCell(new Phrase("Bilangan Rumpun Buluh yang dibanci Semula",size10)));
				cellLampiranE.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				
				for (PostFellingReportElement report : reportLampiranE)
				{
					for(int i = 0; i < report.getData().length; i++)
						cellLampiranE.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
				
				for (int i = 0; i < cellLampiranE.size(); i++)
				{
					cellLampiranE.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellLampiranE.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
					cellLampiranE.get(i).setPaddingTop(5f);
					cellLampiranE.get(i).setPaddingBottom(5f);
					tableLampiranE.addCell(cellLampiranE.get(i));
				}

				Paragraph paraLampiranE = new Paragraph();
				paraLampiranE.setIndentationLeft(20f);
				paraLampiranE.setIndentationRight(20f);
				paraLampiranE.add(tableLampiranE);
				
				//--
				
				PdfPTable tableLampiranF = new PdfPTable(11);
				ArrayList<PostFellingReportElement> reportLampiranF = inspectionReport.getReportLampiranF();
				tableLampiranF.setWidths(new float[]
				{
						3f, 16f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f,
				});
				tableLampiranF.setWidthPercentage(100);
				
				
				ArrayList<PdfPCell> cellLampiranF= new ArrayList<PdfPCell>();
				
				cellLampiranF.add(new PdfPCell(new Phrase("Bil",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("No. Petak Inventori",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Bilangan Batang Palma",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Bilangan Rumpun Bertam",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Bilangan Pepanjat Melingkari Pokok",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranF.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				
				
				cellLampiranF.get(0).setRowspan(2);
				cellLampiranF.get(1).setRowspan(2);
				cellLampiranF.get(2).setColspan(3);
				cellLampiranF.get(3).setColspan(3);
				cellLampiranF.get(4).setColspan(3);
				
				for (PostFellingReportElement report : reportLampiranF)
				{
					for(int i = 0; i < report.getData().length; i++)
						cellLampiranF.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
				
				for (int i = 0; i < cellLampiranF.size(); i++)
				{
					cellLampiranF.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellLampiranF.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
					cellLampiranF.get(i).setPaddingTop(5f);
					cellLampiranF.get(i).setPaddingBottom(5f);
					tableLampiranF.addCell(cellLampiranF.get(i));
				}

				Paragraph paraLampiranF = new Paragraph();
				paraLampiranF.setIndentationLeft(20f);
				paraLampiranF.setIndentationRight(20f);
				paraLampiranF.add(tableLampiranF); 
				
				//--
				
				PdfPTable tableLampiranG = new PdfPTable(11);
				ArrayList<PostFellingReportElement> reportLampiranG = inspectionReport.getReportLampiranG();
				tableLampiranG.setWidths(new float[]
				{
						3f, 16f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f,
				});
				tableLampiranG.setWidthPercentage(100);
				
				
				ArrayList<PdfPCell> cellLampiranG= new ArrayList<PdfPCell>();
				
				cellLampiranG.add(new PdfPCell(new Phrase("Bil",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("No. Petak Inventori",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Peratusan Resam",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Peratusan Halia",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Peratusan Pisang",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Asal",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("Semakan",size10)));
				cellLampiranG.add(new PdfPCell(new Phrase("% Kesilapan",size10)));
				
				cellLampiranG.get(0).setRowspan(2);
				cellLampiranG.get(1).setRowspan(2);
				cellLampiranG.get(2).setColspan(3);
				cellLampiranG.get(3).setColspan(3);
				cellLampiranG.get(4).setColspan(3);
				
				for (PostFellingReportElement report : reportLampiranG)
				{
					for(int i = 0; i < report.getData().length; i++)
						cellLampiranG.add(new PdfPCell(new Phrase(report.getData()[i],size10)));
				}
				
				for (int i = 0; i < cellLampiranG.size(); i++)
				{
					cellLampiranG.get(i).setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellLampiranG.get(i).setHorizontalAlignment(Element.ALIGN_CENTER);
					cellLampiranG.get(i).setPaddingTop(5f);
					cellLampiranG.get(i).setPaddingBottom(5f);
					tableLampiranG.addCell(cellLampiranG.get(i));
				}

				Paragraph paraLampiranG = new Paragraph();
				paraLampiranG.setIndentationLeft(20f);
				paraLampiranG.setIndentationRight(20f);
				paraLampiranG.add(tableLampiranG); 
				
		document.add(paraAsas);
		document.add(paraTempoh);
		document.add(paraKawasan);
		document.add(paraButiran);
		document.add(paraSemakanPapanTanda);
		document.newPage();
		document.add(paraGarisTapak);
		document.add(paraGarisTapak2);
		document.add(new Paragraph("\n\n"));
		document.add(paraGarisPetakInventori);
		document.add(paraJarakGaris);
		document.newPage();
		document.add(paraBearingGaris);
		document.add(paraBearingGaris2);
		document.add(new Paragraph("\n\n"));
		document.add(paraSempadan);
		document.add(paraSempadan2);
		document.newPage();
		document.add(paraKaedahInventori);
		document.add(paraPokokKayu);
		document.add(paraBuluh);
		document.add(paraPalma);
		document.add(paraBertam);
		document.newPage();
		document.add(paraPepanjat);
		document.add(paraResam);
		document.add(paraHalia);
		document.add(paraPisang);
		document.add(paraPasukan);
		document.newPage();
		document.add(paraUlasanKetua);
		document.add(paraUlasanPPPN);
		document.setPageSize(PageSize.A4.rotate());
		//document.setMargins(14.175f, 14.175f, 50f, 14.175f);
		document.newPage();
		paraLampiranHeader.add("Lampiran A");
		paraLampiranTitle.add(new Phrase(inspectionReport.getHeaders()[0],bold12));
		document.add(paraLampiranHeader);	
		document.add(paraLampiranTitle);
		document.add(paraLampiranDetails);
		document.add(paraLampiranA);
		document.add(paraLampiranFooter);
		
		document.newPage();
		paraLampiranHeader.clear();
		paraLampiranHeader.add("Lampiran B");	
		paraLampiranTitle.clear();
		paraLampiranTitle.add(new Phrase(inspectionReport.getHeaders()[1],bold12));
		document.add(paraLampiranHeader);
		document.add(paraLampiranTitle);
		document.add(paraLampiranDetails);
		document.add(paraLampiranB);
		document.add(paraLampiranFooter);
		
		document.newPage();
		
		paraLampiranHeader.clear();
		paraLampiranHeader.add("Lampiran C");
		paraLampiranTitle.clear();
		paraLampiranTitle.add(new Phrase(inspectionReport.getHeaders()[2],bold12));
		document.add(paraLampiranHeader);
		document.add(paraLampiranTitle);
		document.add(paraLampiranDetails);
		document.add(paraLampiranC);
		document.add(paraLampiranFooter);
		
		document.newPage();
		paraLampiranHeader.clear();
		paraLampiranHeader.add("Lampiran D");
		paraLampiranTitle.clear();
		paraLampiranTitle.add(new Phrase(inspectionReport.getHeaders()[3],bold12));	
		document.add(paraLampiranHeader);
		document.add(paraLampiranTitle);
		document.add(paraLampiranDetails);
		document.add(paraLampiranD);
		document.add(paraLampiranFooter);
		
		document.newPage();
		paraLampiranHeader.clear();
		paraLampiranHeader.add("Lampiran E");
		paraLampiranTitle.clear();
		paraLampiranTitle.add(new Phrase(inspectionReport.getHeaders()[4],bold12));
		document.add(paraLampiranHeader);
		document.add(paraLampiranTitle);
		document.add(paraLampiranDetails);
		document.add(paraLampiranE);
		document.add(paraLampiranFooter);
		
		document.newPage();
		paraLampiranHeader.clear();
		paraLampiranHeader.add("Lampiran F");	
		paraLampiranTitle.clear();
		paraLampiranTitle.add(new Phrase(inspectionReport.getHeaders()[5],bold12));
		document.add(paraLampiranHeader);
		document.add(paraLampiranTitle);
		document.add(paraLampiranDetails);
		document.add(paraLampiranF);
		document.add(paraLampiranFooter);
		
		document.newPage();
		paraLampiranHeader.clear();
		paraLampiranHeader.add("Lampiran G");
		paraLampiranTitle.clear();
		paraLampiranTitle.add(new Phrase(inspectionReport.getHeaders()[6],bold12));
		document.add(paraLampiranHeader);
		document.add(paraLampiranTitle);
		document.add(paraLampiranDetails);
		document.add(paraLampiranG);
		document.add(paraLampiranFooter);
		
		

		document.close();
	}
}
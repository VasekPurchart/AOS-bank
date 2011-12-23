package cz.vasekpurchart.aos.bank.fileserver;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import cz.vasekpurchart.aos.bank.backendadapter.Transfer;
import cz.vasekpurchart.aos.bank.backendadapter.Type;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.inject.Inject;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author vasek
 */
public class FileServerService {

	private BackendAdapter backendAdapter;

	@Inject
	public FileServerService(BackendAdapter backendAdapter) {
		this.backendAdapter = backendAdapter;
	}

	public DataHandler getAccountStatement(int accountNumber) throws InvalidAccountException {
		DataSource dataSource = null;
		try {
			dataSource = new ByteArrayDataSource(createStatementsPdf(accountNumber, backendAdapter.getTransfers(accountNumber)).toByteArray(), "application/pdf");
		} catch (DocumentException ex) {
			Logger.getLogger(FileServerService.class.getName()).log(Level.SEVERE, null, ex);
		}
		return new DataHandler(dataSource);
	}

	private ByteArrayOutputStream createStatementsPdf(int accountNumber, List<Transfer> transferList) throws DocumentException {
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

		Paragraph p = new Paragraph("Account statement for account nr." + accountNumber, new Font(FontFamily.HELVETICA, 20));
		document.add(p);
		if (!transferList.isEmpty()) {
			PdfPTable table = new PdfPTable(3);
			table.setSpacingBefore(10);
			table.addCell("variable symbol");
			table.addCell("from/to account");
			table.addCell("amount");
			for (Transfer transfer : transferList) {
				Font f = new Font();
				if (transfer.getType() == Type.DEBET) f.setColor(BaseColor.RED);
				String id = transfer.getId() + "";
				if (transfer.getUniversalId() != null) id = transfer.getUniversalId() + "/" + id;
				table.addCell(new Phrase(id, f));
				table.addCell(new Phrase(transfer.getRemoteAccountNumber() + "/" + transfer.getRemoteBankCode(), f));
				String amount = transfer.getAmount() + "(" + transfer.getCurrency() + ")";
				if (transfer.getType() == Type.DEBET) amount = "-" + amount;
				table.addCell(new Phrase(amount, f));
			}
			document.add(table);
		} else {
			document.add(new Paragraph("No statements to show", new Font(FontFamily.TIMES_ROMAN)));
		}
		document.close();

		return outputStream;
	}

}

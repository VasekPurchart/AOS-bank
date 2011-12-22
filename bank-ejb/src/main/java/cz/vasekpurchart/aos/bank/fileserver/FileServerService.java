package cz.vasekpurchart.aos.bank.fileserver;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
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

		PdfPTable table = new PdfPTable(3);
		PdfPCell cell;
		cell = new PdfPCell(new Phrase("Account statement for account nr." + accountNumber));
		cell.setColspan(3);
		table.addCell(cell);

		if (!transferList.isEmpty()) {
			table.addCell("variable symbol");
			table.addCell("from/to account");
			table.addCell("amount");
			for (Transfer transfer : transferList) {
				table.addCell(transfer.getUniversalId() + "");
				table.addCell(transfer.getRemoteAccountNumber() + "/" + transfer.getRemoteBankCode());
				String amount = transfer.getAmount() + "(" + transfer.getCurrency() + ")";
				if (transfer.getType() == Type.DEBET) amount = "-" + amount;
				table.addCell(amount);
			}
		} else {
			cell = new PdfPCell(new Phrase("No statements to show"));
			cell.setColspan(3);
			table.addCell(cell);
		}
		document.add(table);
		document.close();

		return outputStream;
	}

}

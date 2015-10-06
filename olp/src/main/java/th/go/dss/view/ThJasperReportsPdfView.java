package th.go.dss.view;

import net.sf.jasperreports.engine.JRExporter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.googlecode.jthaipdf.jasperreports.engine.export.ThaiJRPdfExporter;

public class ThJasperReportsPdfView extends JasperReportsPdfView {
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	protected JRExporter createExporter() {
		// TODO Auto-generated method stub
		log.debug("createExporter!");
		return new ThaiJRPdfExporter();
	}
}

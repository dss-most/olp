package th.go.dss;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import th.go.dss.olp.dao.OlpDao;
import th.go.dss.view.ThJasperReportsPdfView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private OlpDao olpDao; 
	
	@Autowired 
	private ApplicationContext appContext;
	
	public OlpDao getOlpDao() {
		return olpDao;
	}

	public void setOlpDao(OlpDao olpDao) {
		this.olpDao = olpDao;
	}

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value="/json/listPlanActivity")
	public @ResponseBody List<Map<String, Object>> listPlanActivity(
			@RequestParam String fiscalYear
			) {
		return olpDao.findPlanActivitiesByFiscalYear(fiscalYear,true);
		
	}
	
	@RequestMapping(value="/json/listActivity")
	public @ResponseBody List<Map<String, Object>> listActivity(
			@RequestParam String fiscalYear,
			@RequestParam(required=false) Integer applicantId
			) {
		return olpDao.findActivitiesByFiscalYear(fiscalYear, applicantId);
		
	}
	
	@RequestMapping(value="/json/listCustomer")
	public @ResponseBody List<Map<String, Object>> listCustomer(@RequestParam String customerCode) {
		return olpDao.findApplicantByCustomerCode(customerCode);
	}
	
	@RequestMapping(value="/json/listRegisterNumber/{fiscalYear}/")
	public @ResponseBody List<Map<String, Object>> listRegisterNumber (
			@PathVariable String fiscalYear,
			@RequestParam String query) {
		
		String firstString = "";
		
		if(fiscalYear != null && fiscalYear.length() > 2) {
			firstString = fiscalYear.substring(fiscalYear.length()-2, fiscalYear.length());
		} 
		
		return olpDao.findRegisterNumberByLeadingString(firstString, query);
	}
	
	@RequestMapping(value="/json/listRegister") 
	public @ResponseBody  List<Map<String, Object>> listRegister (
			@RequestParam String fiscalYear,
			@RequestParam String firstRegisterNumber,
			@RequestParam String lastRegisterNumber,
			@RequestParam String activityType) {
		return olpDao.findRegisterNumber(fiscalYear, firstRegisterNumber, lastRegisterNumber, activityType); 
	
	}
	
	

	
	@RequestMapping(value="/reportPlanActivity")
	public ModelAndView reportPlanActivity(
			@RequestParam(required=true) String fiscalYear,
			@RequestParam(required=true) String reportPage
			) {
		
		List<Map<String, Object>> list = null;
		ModelAndView viewReturn = null;
		final Map<String, Object> model = new HashMap<>();
		
		if(reportPage.equals("excelReport")) {
			model.put("fiscalYear", fiscalYear);
			list = olpDao.findPlanActivitiesByFiscalYear(fiscalYear,true);
			
			model.put("planActivityList", list);
			
			viewReturn = new ModelAndView("planActivityExcelExport", model);
			
			
		} else if(reportPage.equals("pdfReport")) {
//			final JasperReportsPdfView view = new ThJasperReportsPdfView();
			
			model.put("fiscalYear", fiscalYear);
			
			HashMap<String, Object> line = new HashMap<String,Object>();
			line.put("fiscalYear", fiscalYear);
			list = new ArrayList<>();
			list.add(line);
			
			model.put("datasource",list);
			
			List<Map<String, Object>> list2 = null;
			list2 = olpDao.findPlanActivitiesByFiscalYear(fiscalYear,false);
			JRRewindableDataSource activityList = new JRBeanCollectionDataSource(list2);
			
		    model.put("activityList", activityList);
		    
			viewReturn = new ModelAndView("planActivityPdf", model);
		}
		
		logger.debug("returning view: " + viewReturn);
		
		return viewReturn;
	}
	
	@RequestMapping(value="/pdfReportByRegisterIds")
	public String pdfInvoiceByRegisterNumber(
			@RequestParam(required=true) Set<Integer> registerIds,
			@RequestParam(required=false) String reportPage,
			@RequestParam(required=false) Boolean chkbx_englishReport,
			@RequestParam(required=true) String fiscalYear,
			@RequestParam(required=true) String activityType,
			Model model) {
		
		List<Map<String, Object>> list = null;
		
		
		if("excelEMSExport".equals(reportPage)) {
			list = olpDao.findRegistrationsForEmsByIds(registerIds);
			model.addAttribute("registrationList", list);
		} else {
			String activitySearch = " LIKE '%' ";
			
			if(activityType.equals("qc") ){
				activitySearch = " LIKE 'QC%' ";
			} else if(activityType.equals("act")) {
				activitySearch = " NOT LIKE 'QC%' ";
			}
			
			list = olpDao.findRegistrationsById(registerIds, activitySearch);
		}
		
		for(Map<String, Object> map : list) {
			String actitivityCode = (String) map.get("ACTIVITY_CODE");
			
			if(actitivityCode != null ) {
			
				if(actitivityCode.startsWith("QC")) {
					map.put("IS_QC_ACTIVITY", true);
				} else {
					map.put("IS_QC_ACTIVITY", false);
				}
			}
		}
		
		if(list!= null && list.size() > 0 ) {
			
			model.addAttribute("fiscalYear", fiscalYear);
			
			if(chkbx_englishReport!=null && chkbx_englishReport == true) {
				model.addAttribute("isEnglishAddress", true);
			} else {
				model.addAttribute("isEnglishAddress", false);
			}
			
			model.addAttribute("feilds", list);
		}
		
		if("quotationReport".equals(reportPage)) {
			return "testReport";
		}
		
		return reportPage;
	}
	
	
	@RequestMapping(value={"","/","/home"})
	public String home(Model model) {		
		
		return "home";
	}
	

	@RequestMapping(value="/printPlan")
	public String printPlan(Model model) {
		
		
		return "printPlan";
	}
	
	@RequestMapping(value="/printInvoice2")
	public String printInvoice2(Model model) {
		
		
		return "printInvoice2";
	}
	
	@RequestMapping(value="/printInvoice") 
	public String printInvoice(
			@RequestParam(required=false) String fiscalYear,
			@RequestParam(required=false) String customerCode,
			@RequestParam(required=false) String activityId,
			@RequestParam(required=false) String allExceptActivityFlag,
			@RequestParam(required=false) String reportPage,
			@RequestParam(required=false) Boolean chkbx_englishReport,
			@RequestParam(required=false) String activityType,
			Model model) {
		
		logger.debug("reportPage: " + reportPage);
		logger.debug("activityId: " + activityId);
		logger.debug("customerCode: " + customerCode);
		logger.debug("chkbx_englishReport: " + chkbx_englishReport);
		
		Set<Integer> activityIdSet = new HashSet<Integer>();
		if(activityId != null) {
			StringTokenizer token = new StringTokenizer(activityId,",");
			while(token.hasMoreTokens()) {
				Integer id = Integer.parseInt((String) token.nextElement());
				activityIdSet.add(id);
			}
			
		}
		
		List<Map<String, Object>> list = null;
		
		if("quotationReport".equals(reportPage)) {
		
		
			if(customerCode == null || customerCode.length() <= 0) {
				Boolean allExceptActivity = false;
				if(allExceptActivityFlag != null && allExceptActivityFlag.length() > 0 ) {
					allExceptActivity = true;
				}
				Log.debug("allExceptActivity: " + allExceptActivity);
				list = olpDao.findRegistrationsByFiscalYearAndListofActivities(fiscalYear, activityIdSet, allExceptActivity);
			} else {
				list = olpDao.findRegistrationsByFiscalYearAndCustomerCode(fiscalYear, customerCode, activityIdSet);
			}
			
			
			if(list!= null && list.size() > 0 ) {
				
				model.addAttribute("fiscalYear", fiscalYear);
				
				if(chkbx_englishReport!=null && chkbx_englishReport == true) {
					model.addAttribute("isEnglishAddress", true);
				} else {
					model.addAttribute("isEnglishAddress", false);
				}
				
				model.addAttribute("feilds", list);
				return "testReport";
				
			} 
			
		} else if("confirmReport".equals(reportPage)) {

			if(customerCode == null || customerCode.length() <= 0) {
				list = null;
			} else {
				list = olpDao.findRegistrationsByFiscalYearAndCustomerCode(fiscalYear, customerCode, activityIdSet);
			}
			
			
			if(list!= null && list.size() > 0 ) {
				
				model.addAttribute("fiscalYear", fiscalYear);
				model.addAttribute("feilds", list);
				return "confirmReport";
				
			} 

		} else if("confirm2Report".equals(reportPage)) {

			if(customerCode == null || customerCode.length() <= 0) {
				Boolean allExceptActivity = false;
				if(allExceptActivityFlag != null && allExceptActivityFlag.length() > 0 ) {
					allExceptActivity = true;
				}
				Log.debug("allExceptActivity: " + allExceptActivity);
				list = olpDao.findRegistrationsByFiscalYearAndListofActivities(fiscalYear, activityIdSet, allExceptActivity);
			} else {
				list = olpDao.findRegistrationsByFiscalYearAndCustomerCode(fiscalYear, customerCode, activityIdSet);
			}
			
			for(Map<String, Object> map : list) {
				String actitivityCode = (String) map.get("ACTIVITY_CODE");
				if(actitivityCode.startsWith("QC")) {
					map.put("IS_QC_ACTIVITY", true);
				} else {
					map.put("IS_QC_ACTIVITY", false);
				}
			}
			
			
			if(list!= null && list.size() > 0 ) {
				
				model.addAttribute("fiscalYear", fiscalYear);
				
				
				if(chkbx_englishReport!=null && chkbx_englishReport == true) {
					model.addAttribute("isEnglishAddress", true);
				} else {
					model.addAttribute("isEnglishAddress", false);
				}
				
				Map<String, Object> test = list.get(0);
				
				logger.debug(test.get("ADD_RECEIPT").toString());
				
				model.addAttribute("feilds", list);
				return "confirm2Report";
				
			} 

		}
		
		
		return "printInvoice";
	}
	
}

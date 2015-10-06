package th.go.dss;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import th.go.dss.olp.dao.OlpDao;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private OlpDao olpDao; 
	
	public OlpDao getOlpDao() {
		return olpDao;
	}

	public void setOlpDao(OlpDao olpDao) {
		this.olpDao = olpDao;
	}

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
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
			
			Boolean isQCActivity = true;
			for(Map<String, Object> map : list) {
				String actitivityCode = (String) map.get("activity_code");
				if(actitivityCode.startsWith("QC")) {
					isQCActivity = isQCActivity && true;
				} else {
					isQCActivity = isQCActivity && false;
				}
			}
			
			
			if(list!= null && list.size() > 0 ) {
				
				model.addAttribute("fiscalYear", fiscalYear);
				model.addAttribute("isQCActivity", isQCActivity);
				
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

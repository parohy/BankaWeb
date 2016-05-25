package com.parohyapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.parohyapp.api.BankMailSender;
import com.parohyapp.api.HashGen;
import com.parohyapp.api.beans.CardBean;
import com.parohyapp.api.beans.EmailForm;
import com.parohyapp.api.beans.LoanBean;
import com.parohyapp.api.beans.LoginBean;
import com.parohyapp.api.beans.PasswordBean;
import com.parohyapp.api.beans.TransactionBean;
import com.parohyapp.bank.Client;
import com.parohyapp.database.ErrorCode;
import com.parohyapp.database.ErrorHandler;
import com.parohyapp.database.account.AccountDAO;
import com.parohyapp.database.card.CardDAO;
import com.parohyapp.database.client.ClientDAO;
import com.parohyapp.database.clientcontact.ContactDAO;

@Controller
public class HomePageController {
	
	@Autowired
	ClientDAO clientDAO;
	
	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	CardDAO cardDAO;
	
	@Autowired
	ContactDAO contactDAO;
	
	@Autowired
	BankMailSender bankMail;
	
	@RequestMapping(value = "/indexLogin")
	public ModelAndView getHomePageLogin(){
		ModelAndView model = new ModelAndView("loginPage");
		LoginBean loginBean = new LoginBean();
		model.addObject("loginBean",loginBean);
		return model;
	}
	
	@RequestMapping(value = "/loginClient", method = RequestMethod.POST)
	public String loginClient(@ModelAttribute("loginBean") LoginBean loginBean, ModelMap model){
		int id = clientDAO.getLoginClient(loginBean.getUsername(), loginBean.getPassword());
		if(id>1000){
			Client client = clientDAO.getClient(id);
			model.addAttribute("client",client);
			model.addAttribute("passwordBean", new PasswordBean());
			model.addAttribute("cardBean",new CardBean());
			model.addAttribute("transactionBean",new TransactionBean());
			model.addAttribute("loanBean", new LoanBean());
			return "homePage";
		}	
		model.addAttribute("error",ErrorHandler.findError(id));
		return "loginPage";
	}
	
	@RequestMapping(value = "sendEmail", method = RequestMethod.GET)
	public ModelAndView getEmailForm(@ModelAttribute("emailBean") EmailForm emailBean){
		ModelAndView model = new ModelAndView("forms/emailForm");
		return model;
	}
	
	@RequestMapping(value = "sendEmail", method = RequestMethod.POST)
	public String sendEmail(@ModelAttribute("emailBean") EmailForm emailBean, ModelMap model){
		int clientId = contactDAO.getIdByEmail(emailBean.getEmail());
	if(clientId > 1000){
			String code = HashGen.getCodedId(String.valueOf(clientId));
			bankMail.requestPassword(emailBean.getEmail(), code);
		}
		else{
			model.addAttribute("error","User not found");
			model.addAttribute("emailBean",new EmailForm());
			return "emailForm";
		}
		
		model.addAttribute("loginBean",new LoginBean());
		return "loginPage";
	}
	
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("passwordBean") PasswordBean passwordBean, ModelMap model){
		String id = HashGen.getDecodedId(passwordBean.getId());
		clientDAO.changePassword(passwordBean.getPassword(), Integer.parseInt(id));
		model.addAttribute("loginBean",new LoginBean());
		return "loginPage";
	}
	
	@RequestMapping(value = "passwordRequest", method = RequestMethod.GET)
	public ModelAndView passwordForm(@RequestParam(value = "client", required = true) String clientId){
		ModelAndView model = new ModelAndView("forms/newPassword");
		model.addObject("clientEmail",clientId);
		model.addObject("passwordBean",new PasswordBean());
		return model;
	}
	
	/*@RequestMapping(value = "transaction", method = RequestMethod.POST)
	public String completeTransaction(@ModelAttribute("transactionBean") TransactionBean transactionBean){
		
		return "redirect: "
	}*/
		
}

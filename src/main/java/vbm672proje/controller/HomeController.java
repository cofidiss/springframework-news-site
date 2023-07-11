package vbm672proje.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import vbm672proje.customException.UserMessageException;
import vbm672proje.dto.*;

import vbm672proje.viewModel.*;
import vbm672proje.service.*;

@Controller
public class HomeController {
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/SignUpPage")
	public ModelAndView GetSignUpPage(HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView("signUp", "Model", "");

	}

	@RequestMapping(value = "/SignOut")
	public ModelAndView SignOut(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Cookie cookie = new Cookie("Auth", "-1");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return new ModelAndView("redirect:/");

	}

	@RequestMapping(value = "/LoginPage")
	public ModelAndView GetLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Service service = new Service(context);

		Cookie cookies[] = request.getCookies();
		Boolean isCookieLegit = service.IsCookieLegit(cookies);

		if (isCookieLegit) {

			Integer personId = service.GetPersonIdFromCookies(cookies);

			return new ModelAndView("redirect:/");
		}
		String wrongAttempt = request.getParameter("wrongAttempt");
		String logonMessage = "";
		if (wrongAttempt != null) {

			logonMessage = "Yanlış kullanıcı Adı Ya da Şifre";

		}
		return new ModelAndView("login", "Model", logonMessage);

	}

	@RequestMapping(value = "/LoginUser")
	public ModelAndView LoginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Service service = new Service(context);

		Cookie cookies[] = request.getCookies();

		int personId = service.CheckUserNameAndPassword(request.getParameter("userName"),
				request.getParameter("password"));

		if (personId == -1) {

			return new ModelAndView("redirect:/LoginPage?wrongAttempt=true");
		}
		Cookie cookie = new Cookie("Auth", service.EncryptText(Integer.toString(personId)));
		response.addCookie(cookie);
		return new ModelAndView("redirect:/");

	}

	@RequestMapping(value = "/SignUp")
	public ModelAndView SignUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Service service = new Service(context);
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		if (username == null || username.equals("") || password == null || password.equals("")) {
			return new ModelAndView("signUp", "Model", "Lütfen Bir Kullanıcı ve Parola Giriniz");

		}
		if (service.IsUserNameExists(username)) {

			return new ModelAndView("signUp", "Model", "Kullanıcı daha önce kayıt olmuştur.");

		}

		Integer personId = service.AddUserNameAndPassword(username, password);
		// service.InsertEmptyRecord(personId);
		Cookie cookie = new Cookie("Auth", service.EncryptText(Integer.toString(personId)));
		response.addCookie(cookie);
		return new ModelAndView("redirect:/");

	}

	@RequestMapping(value = "")
	public ModelAndView HomeAction(HttpServletResponse response, HttpServletRequest request) throws IOException {

		Service service = new Service(context);
		Cookie[] cookies = request.getCookies();

		Boolean isCookieLegit = service.IsCookieLegit(cookies);

		int personId = service.GetPersonIdFromCookies(cookies);
		NewsListVm newsListVm = new NewsListVm();
		newsListVm.setListOfCategoryDTOs(service.GetListOfCategoryDTOs());
		List<NewsDTO> listOfNewsDTOs;
		String categoryIdstr = request.getParameter("categoryId");
		int defaultCategoryId = 0;
		try {
			defaultCategoryId = Integer.parseInt(categoryIdstr, 10);
		} catch (Exception e) {

		}

		defaultCategoryId = service.FindDefaultCategoryId(defaultCategoryId);

		if (isCookieLegit) {
			newsListVm.setIsSiteAdmin(service.IsSiteAdmin(cookies));
			listOfNewsDTOs = service.GetNewsByCategoryId(defaultCategoryId, true);

		} else {

			listOfNewsDTOs = service.GetNewsByCategoryId(defaultCategoryId, false);
		}
		newsListVm.setListOfNewsDTOs(listOfNewsDTOs);

		String categoryName = service.GetCategoryNameFromCategoryId(defaultCategoryId);
		newsListVm.setCategoryName(categoryName);
		newsListVm.setUserName(service.GetUserNameFromPersonId(personId));
		newsListVm.setCategoryId(defaultCategoryId);
		newsListVm.setIsCategoryAdmin(service.IsCategoryAdmin(cookies));
		return new ModelAndView("home", "Model", newsListVm);
	}

	@RequestMapping(value = "/CategoryAdmin")
	public ModelAndView GetCategoryAdminPage(HttpServletResponse response, HttpServletRequest request)
			throws IOException {

		Service service = new Service(context);
		Cookie[] cookies = request.getCookies();
		Boolean isCategoryAdmin = service.IsCategoryAdmin(cookies);
		Boolean isSiteAdmin = service.IsSiteAdmin(cookies);
		int personId = -1;
		int categoryId = -1;
		
		if (isCategoryAdmin || isSiteAdmin) {
			if (isCategoryAdmin) {
				personId = service.GetPersonIdFromCookies(cookies);
				categoryId = service.GetCategoryIdofAdmin(personId);
			

			}
			if (service.IsSiteAdmin(cookies)) {
				String categoryIdParam = request.getParameter("CategoryId");
				personId = service.GetPersonIdFromCookies(cookies);
				categoryId = Integer.parseInt(categoryIdParam);
			

			}
			CategoryAdminVm categoryAdminVm = service.GetCategoryAdminVm(categoryId, personId);
			categoryAdminVm.setCategoryId(categoryId);
			return new ModelAndView("categoryAdmin", "Model", categoryAdminVm);
		} else {
			return new ModelAndView("redirect:/");

		}

	}

	@RequestMapping(value = "/SiteAdmin")
	public ModelAndView GetSiteAdminPage(HttpServletResponse response, HttpServletRequest request) throws IOException {

		Service service = new Service(context);
		Cookie[] cookies = request.getCookies();
		Boolean isSiteAdmin = service.IsSiteAdmin(cookies);
		if (isSiteAdmin) {

			int personId = service.GetPersonIdFromCookies(cookies);

			SiteAdminVm siteAdminVm = service.GetSiteAdminVm(personId);

			return new ModelAndView("siteAdmin", "Model", siteAdminVm);

		}

		return new ModelAndView("redirect:/");

	}

	@RequestMapping(value = "/GetNewsById")
	public ModelAndView GetNewsById(HttpServletResponse response, HttpServletRequest request) throws IOException {

		int newsIdToFind = Integer.parseInt(request.getParameter("Id"), 10);

		Service service = new Service(context);
		Cookie[] cookies = request.getCookies();
		Boolean isCookieLegit = service.IsCookieLegit(cookies);
		int personId = service.GetPersonIdFromCookies(cookies);
		SingleNewsVm singleNewsVm = new SingleNewsVm();

		if (isCookieLegit) {

			singleNewsVm = service.GetNewsVm(newsIdToFind);

		} else {

			if (service.IsNewsShared(newsIdToFind)) {
				singleNewsVm = service.GetNewsVm(newsIdToFind);

			} else {
				singleNewsVm.setHeader("Haberi görmek için yetkiniz yok");

			}

		}

		singleNewsVm.setUserName(service.GetUserNameFromPersonId(personId));

		return new ModelAndView("singleNews", "Model", singleNewsVm);
	}

	@RequestMapping(value = "/saveCategoryAdminForm", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String SaveCategoryAdminForm(HttpServletRequest request) throws Exception {

		Service service = new Service(context);
		Cookie[] cookies = request.getCookies();
		Boolean isCategoryAdmin = service.IsCategoryAdmin(cookies);
		Boolean isSiteAdmin= service.IsSiteAdmin(cookies);
		if (isCategoryAdmin ||isSiteAdmin ) {

			int personId = service.GetPersonIdFromCookies(cookies);

			String jsonBody = request.getParameter("param1");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			SavedCategoryAdminFormData savedCategoryAdminFormData = objectMapper.readValue(jsonBody,
					SavedCategoryAdminFormData.class);
			int categoryId = savedCategoryAdminFormData.getCategoryId();
			service.UpdateNewsTableRows(savedCategoryAdminFormData.getUpdatedRows(), personId, categoryId);

			service.InsertNewsTableRows(savedCategoryAdminFormData.getInsertedRows(), personId, categoryId);

			service.DeleteNewsTableRows(savedCategoryAdminFormData.getDeletedRowIds(), personId, categoryId);
			return "Kaydedildi";
		}
		return "00Yetkisiz İşlem";
		

	}

	@RequestMapping(value = "/saveSiteAdminForm", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String SaveSiteAdminForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Service service = new Service(context);
			Cookie[] cookies = request.getCookies();
			Boolean isSiteAdmin = service.IsSiteAdmin(cookies);
			if (isSiteAdmin) {

				int personId = service.GetPersonIdFromCookies(cookies);

				String jsonBody = request.getParameter("param1");
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				SavedSiteAdminFormData savedSiteAdminFormData = objectMapper.readValue(jsonBody,
						SavedSiteAdminFormData.class);
				service.DeleteCategoryTableRows(savedSiteAdminFormData.getDeletedRowIds(), personId);
				service.UpdateCategoryTableRows(savedSiteAdminFormData.getUpdatedRows(), personId);

				service.InsertCategoryTableRows(savedSiteAdminFormData.getInsertedRows(), personId);
				return "Kaydedildi";
			}

		return "00Yetkisiz İşlem";
		} catch (UserMessageException e) {

			return "00" + e.getMessage();

		}
	}

}
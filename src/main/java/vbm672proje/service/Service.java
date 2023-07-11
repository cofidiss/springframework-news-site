package vbm672proje.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import vbm672proje.customException.UserMessageException;
import vbm672proje.dto.CategoryDTO;
import vbm672proje.dto.NewsDTO;
import vbm672proje.viewModel.*;

public class Service {

	static String HaberlerTablePath;

	static String UsersTablePath;

	static String CategoryTablePath;
	public int personId;

	static String salt = "asd21s242";
	static String secret_Key = "ashgeg321s242";

	public Service(ServletContext context) {

		String contextPath = context.getRealPath("");
		int indexofAppRootPath = contextPath.indexOf(".metadata") - 1;
		String excelsFolderPath = contextPath.subSequence(0, indexofAppRootPath + 1)  + "\\vbm672proje\\src\\main\\webapp\\resources\\excelFiles";
		//excelsFolderPath = "C:\\Users\\sb\\Desktop\\finalproje repo\\vbm672proje\\src\\main\\webapp\\resources\\excelFiles";
		HaberlerTablePath = excelsFolderPath + "\\HaberlerTable.xls";
		UsersTablePath = excelsFolderPath + "\\UsersTable.xls";
		CategoryTablePath = excelsFolderPath + "\\CategoryTable.xls";
	}

	private static String returnStringValue(Cell cell) {
		CellType cellType = cell.getCellType();

		switch (cellType) {
		case NUMERIC:
			double doubleVal = cell.getNumericCellValue();
			return String.valueOf((int) doubleVal);
		case STRING:
			return cell.getStringCellValue();
		case ERROR:
			return String.valueOf(cell.getErrorCellValue());
		case BLANK:
			return "";
		case FORMULA:
			return cell.getCellFormula();
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		}
		return "error decoding string value of the cell";
	}

	public Integer AddUserNameAndPassword(String userName, String password) throws IOException {
		DeleteEmptyRows(UsersTablePath);
		FileInputStream inputWorkbook = new FileInputStream(UsersTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		DeleteEmptyRows(UsersTablePath);
		try {

			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			int lastRowNumber = sheet.getLastRowNum();
			Integer lastPersonId;
			if (lastRowNumber == 0) {

				lastPersonId = 0;

			} else {
				lastPersonId = (int) sheet.getRow(lastRowNumber).getCell(0).getNumericCellValue();

			}
			// Loop over first 10 column and lines

			Row row = sheet.createRow(lastRowNumber + 1);
			int personId = lastPersonId + 1;
			row.createCell(0).setCellValue(personId);
			row.createCell(1).setCellValue(userName);
			row.createCell(2).setCellValue(HashText(password));
			Boolean categoryAdmin = false;
			if (personId == 0) {

				categoryAdmin = true;
			}
			row.createCell(3).setCellValue(categoryAdmin);
			return lastPersonId + 1;
		}

		finally {

			FileOutputStream outputStream = new FileOutputStream(UsersTablePath);
			w.write(outputStream);
			inputWorkbook.close();
			w.close();
			outputStream.close();

		}

	}

	public Boolean IsUserNameExists(String userName) throws IOException {

		DeleteEmptyRows(UsersTablePath);
		FileInputStream inputWorkbook = new FileInputStream(UsersTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);

		try {

			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();

			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}

				if (row.getCell(1).getStringCellValue().equals(userName)) {
					return true;

				}
			}
			return false;
		} finally {

			inputWorkbook.close();
			w.close();
		}

	}

	public String HashText(String text) {

		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(text.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

	public int CheckUserNameAndPassword(String userName, String password) throws IOException {
		DeleteEmptyRows(UsersTablePath);
		String HashedPassword = HashText(password);
		FileInputStream inputWorkbook = new FileInputStream(UsersTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {

			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}

				Cell userNameCell = row.getCell(1);
				Cell passwordCell = row.getCell(2);
				String userNameInDB = userNameCell.getStringCellValue();
				if (userNameInDB.equals(userName)) {
					if (passwordCell.getStringCellValue().equals(HashedPassword)) {

						return (int) row.getCell(0).getNumericCellValue();
					} else {
						return -1;
					}

				}

			}
			return -1;
		} finally {

			inputWorkbook.close();
			w.close();
		}
	}

	public String GetUserNameFromPersonId(Integer personId) throws IOException {

		DeleteEmptyRows(UsersTablePath);
		FileInputStream inputWorkbook = new FileInputStream(UsersTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {

			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}

				Cell personIdCell = row.getCell(0);

				if (personIdCell.getNumericCellValue() == personId) {
					return row.getCell(1).getStringCellValue();

				}

			}
			return "Misafir";
		} finally {

			inputWorkbook.close();
			w.close();
		}

	}

	public int GetPersonIdFromCookies(Cookie[] cookies) {
		if (cookies == null) {

			return -1;
		}

		for (Cookie cookie : cookies) {

			if (cookie.getName().equals("Auth")) {
				String decryptedCookieVal = DecryptText(cookie.getValue());
				try {
					return Integer.parseInt(decryptedCookieVal, 10);
				} catch (NumberFormatException e) {

					return -1;
				}
			}

		}

		return -1;

	}

	public Boolean IsCookieLegit(Cookie[] cookies) throws IOException {

		Cookie cook;

		if (cookies != null) {
			for (Cookie element : cookies) {
				cook = element;
				if (cook.getName().equals("Auth")) {
					String decryptedCookieVal = DecryptText(cook.getValue());
					return IsPersonIdLegit(decryptedCookieVal);
				}

			}
			return false;
		} else {
			return false;

		}

	}

	public List<NewsDTO> GetNewsByCategoryId(int categoryId, Boolean isRegisteredUser) throws IOException {

		DeleteEmptyRows(HaberlerTablePath);

		FileInputStream inputWorkbook = new FileInputStream(HaberlerTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		ArrayList<NewsDTO> listOfNewsDTOs = new ArrayList<NewsDTO>();

		try {

			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				if (isRegisteredUser) {

					if (row.getCell(1).getNumericCellValue() == categoryId) {
						NewsDTO newsDTO = new NewsDTO();
						newsDTO.setBody(row.getCell(3).getStringCellValue());
						newsDTO.setHeader(row.getCell(2).getStringCellValue());
						newsDTO.setCategoryId((int) row.getCell(1).getNumericCellValue());
						newsDTO.setNewsId((int) row.getCell(0).getNumericCellValue());
						newsDTO.setIsShared(row.getCell(4).getBooleanCellValue());
						listOfNewsDTOs.add(newsDTO);

					}

				} else {
					if (row.getCell(1).getNumericCellValue() == categoryId
							&& row.getCell(4).getBooleanCellValue() == true) {
						NewsDTO newsDTO = new NewsDTO();
						newsDTO.setBody(row.getCell(3).getStringCellValue());
						newsDTO.setHeader(row.getCell(2).getStringCellValue());
						newsDTO.setCategoryId((int) row.getCell(1).getNumericCellValue());
						newsDTO.setNewsId((int) row.getCell(0).getNumericCellValue());
						newsDTO.setIsShared(row.getCell(4).getBooleanCellValue());
						listOfNewsDTOs.add(newsDTO);

					}

				}
			}
			return listOfNewsDTOs;

		} finally {

			w.close();
			inputWorkbook.close();

		}

	}

	public String GetCategoryNameFromCategoryId(int categoryIdToFind) throws IOException {

		DeleteEmptyRows(CategoryTablePath);
		FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				int categoryId = (int) row.getCell(0).getNumericCellValue();

				if (categoryIdToFind == categoryId) {
					return row.getCell(1).getStringCellValue();

				}

			}

		} finally {

			w.close();
			inputWorkbook.close();

		}

		return "Lütfen Haber Kategorisi Ekleyiniz";
	}

	public Boolean IsPersonIdLegit(String personIdString) throws IOException {
		try {
			int personId = Integer.parseInt(personIdString, 10);
			return IsPersonIdInDB(personId);

		} catch (NumberFormatException e) {

			return false;

		}

	}

	public Boolean IsPersonIdInDB(int personId) throws IOException {
		DeleteEmptyRows(UsersTablePath);
		FileInputStream inputWorkbook = new FileInputStream(UsersTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				Cell cell = row.getCell(0);
				int personIdInDB = (int) cell.getNumericCellValue();
				if (personIdInDB == personId) {
					return true;

				}

			}
			return false;
		} finally {

			w.close();
			inputWorkbook.close();

		}

	}

	public String DecryptText(String textToDecrypt) {

		try {

			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

			KeySpec spec = new PBEKeySpec(secret_Key.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			// Return decrypted string
			return new String(cipher.doFinal(Base64.getDecoder().decode(textToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;

	}

	public String EncryptText(String textToEncrypt) {

		try {

			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

			KeySpec spec = new PBEKeySpec(secret_Key.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

			return Base64.getEncoder().encodeToString(cipher.doFinal(textToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;

	}

	public int DeleteEmptyRows(String excelPath) throws IOException {

		FileInputStream inputWorkbook = new FileInputStream(excelPath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		Sheet sheet = w.getSheetAt(0);

		try {

			boolean stop = false;
			boolean nonBlankRowFound;
			short c;
			HSSFRow lastRow = null;
			HSSFCell cell = null;

			while (stop == false) {
				nonBlankRowFound = false;
				lastRow = (HSSFRow) sheet.getRow(sheet.getLastRowNum());
				for (c = lastRow.getFirstCellNum(); c <= lastRow.getLastCellNum(); c++) {
					cell = lastRow.getCell(c);
					if (cell != null && lastRow.getCell(c).getCellType() != CellType.BLANK) {
						nonBlankRowFound = true;
					}
				}
				if (nonBlankRowFound == true) {
					stop = true;
				} else {
					sheet.removeRow(lastRow);
				}
			}

			return sheet.getLastRowNum();

		} finally {

			FileOutputStream outputStream = new FileOutputStream(excelPath);
			w.write(outputStream);
			inputWorkbook.close();
			w.close();
			outputStream.close();

		}

	}

	public SingleNewsVm GetNewsVm(int newsIdToFind) throws IOException {

		DeleteEmptyRows(HaberlerTablePath);
		FileInputStream inputWorkbook = new FileInputStream(HaberlerTablePath);
		SingleNewsVm singleNewsVm = new SingleNewsVm();
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				Cell cell = row.getCell(0);
				int newsId = (int) cell.getNumericCellValue();
				if (newsId == newsIdToFind) {
					singleNewsVm.setBody(row.getCell(3).getStringCellValue());
					singleNewsVm.setHeader(row.getCell(2).getStringCellValue());
					return singleNewsVm;

				}

			}
			singleNewsVm.setBody("");
			singleNewsVm.setHeader("Haber Bulunamadı");
			return singleNewsVm;
		} finally {

			w.close();
			inputWorkbook.close();

		}
	}

	public boolean IsNewsShared(int newsIdToFind) throws IOException {

		DeleteEmptyRows(HaberlerTablePath);
		FileInputStream inputWorkbook = new FileInputStream(HaberlerTablePath);
		SingleNewsVm singleNewsVm = new SingleNewsVm();
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				Cell cell = row.getCell(0);
				int newsId = (int) cell.getNumericCellValue();
				if (newsId == newsIdToFind) {
					return row.getCell(4).getBooleanCellValue();

				}

			}

			return true;
		} finally {

			w.close();
			inputWorkbook.close();

		}

	}

	public Boolean IsCategoryAdmin(Cookie[] cookies) throws IOException {

		if (IsCookieLegit(cookies) == false) {
			return false;

		}

		int personId = GetPersonIdFromCookies(cookies);
		DeleteEmptyRows(CategoryTablePath);
		FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				Cell cell = row.getCell(2);

				List<Integer> personIdsOfCategoryAdmins = GetListofIntegersFromString(
						returnStringValue(cell).split(","));
				for (int j = 0; j < personIdsOfCategoryAdmins.size(); j++) {
					if (personIdsOfCategoryAdmins.get(j) == personId) {
						return true;

					}

				}

			}

			return false;
		} finally {

			w.close();
			inputWorkbook.close();

		}
	}

	public Boolean IsCategoryAdmin(int personId,int skipRowNo) throws IOException {

		DeleteEmptyRows(CategoryTablePath);
		FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null || i== skipRowNo) {

					continue;
				}
				Cell cell = row.getCell(2);

				List<Integer> personIdsOfCategoryAdmins = GetListofIntegersFromString(
						returnStringValue(cell).split(","));
				for (int j = 0; j < personIdsOfCategoryAdmins.size(); j++) {
					if (personIdsOfCategoryAdmins.get(j) == personId) {
						return true;

					}

				}

			}

			return false;
		} finally {

			w.close();
			inputWorkbook.close();

		}
	}

	public int GetCategoryIdofAdmin(int personId) throws IOException {

		DeleteEmptyRows(CategoryTablePath);
		FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				Cell cell = row.getCell(2);

				List<Integer> personIdsOfCategoryAdmins = GetListofIntegersFromString(
						returnStringValue(cell).split(","));
				for (int j = 0; j < personIdsOfCategoryAdmins.size(); j++) {
					if (personIdsOfCategoryAdmins.get(j) == personId) {
						return (int) row.getCell(0).getNumericCellValue();

					}

				}

			}

			return -1;
		} finally {

			w.close();
			inputWorkbook.close();

		}

	}

	public CategoryAdminVm GetCategoryAdminVm(int categoryId, int personId) throws IOException {

		CategoryAdminVm categoryAdminVm = new CategoryAdminVm();

		List<NewsDTO> listOfNewsDTOs = GetNewsByCategoryId(categoryId, true);
		categoryAdminVm.setListOfNewsDTOs(listOfNewsDTOs);
		categoryAdminVm.setCategoryName(GetCategoryNameFromCategoryId(categoryId));
		categoryAdminVm.setUserName(GetUserNameFromPersonId(personId));
		categoryAdminVm.setListOfCategoryDTOs(GetListOfCategoryDTOs());
		return categoryAdminVm;

	}

	public void UpdateNewsTableRows(List<NewsDTO> updatedRows, Integer personId, Integer categoryIdofAdmin)
			throws Exception {
		DeleteEmptyRows(HaberlerTablePath);
		FileInputStream inputWorkbook = new FileInputStream(HaberlerTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);

		try {

			for (NewsDTO newsDTO : updatedRows) {

				if (GetCategoryIdFromNewsId(newsDTO.getNewsId()) != categoryIdofAdmin) {

					throw new Exception("yetkisiz işlem");

				}

				// Get the first sheet
				Sheet sheet = w.getSheetAt(0);
				// Loop over first 10 column and lines
				int lastRowNumber = sheet.getLastRowNum();
				for (int i = 2; i <= lastRowNumber; i++) {
					Row row = sheet.getRow(i);
					if (row == null || row.getCell(0) == null) {
						continue;
					}

					if (row.getCell(0).getNumericCellValue() == newsDTO.getNewsId()) {

						row.createCell(2).setCellValue(newsDTO.getHeader());
						row.createCell(3).setCellValue(newsDTO.getBody());
						row.createCell(4).setCellValue(newsDTO.getIsShared());

						break;
					}
				}

			}

		} finally {

			FileOutputStream outputStream = new FileOutputStream(HaberlerTablePath);
			w.write(outputStream);
			inputWorkbook.close();
			w.close();
			outputStream.close();
		}
	}

	public void InsertNewsTableRows(List<NewsDTO> insertedRows, Integer personId, Integer categoryIdofAdmin)
			throws IOException {

		FileInputStream inputWorkbook = new FileInputStream(HaberlerTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		DeleteEmptyRows(HaberlerTablePath);
		try {
			Sheet sheet = w.getSheetAt(0);
			for (NewsDTO newsDTO : insertedRows) {
				int lastRowNo = sheet.getLastRowNum();
				// Get the first sheet

				// Loop over first 10 column and lines

				Row row = sheet.createRow(lastRowNo + 1);
				Integer lastRowId = (int) sheet.getRow(lastRowNo).getCell(0).getNumericCellValue();

				row.createCell(0).setCellValue(lastRowId + 1);
				row.createCell(1).setCellValue(categoryIdofAdmin);
				row.createCell(2).setCellValue(newsDTO.getHeader());
				row.createCell(3).setCellValue(newsDTO.getBody());
				row.createCell(4).setCellValue(newsDTO.getIsShared());

			}

		} finally {

			FileOutputStream outputStream = new FileOutputStream(HaberlerTablePath);
			w.write(outputStream);
			inputWorkbook.close();
			w.close();
			outputStream.close();

		}

	}

	public void DeleteNewsTableRows(List<Integer> deletedRowIds, Integer personId, Integer categoryIdofAdmin)
			throws Exception {

		FileInputStream inputWorkbook = new FileInputStream(HaberlerTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		DeleteEmptyRows(HaberlerTablePath);
		try {
			Sheet sheet = w.getSheetAt(0);
			for (Integer newsId : deletedRowIds) {
				if (GetCategoryIdFromNewsId(newsId) != categoryIdofAdmin) {

					throw new Exception("yetkisiz işlem");

				}
				int lastRowNumber = sheet.getLastRowNum();
				// Get the first sheet
				for (int i = 2; i <= lastRowNumber; i++) {
					Row row = sheet.getRow(i);
					if (row == null || row.getCell(0) == null) {
						continue;
					}
					if (row.getCell(0).getNumericCellValue() == newsId) {

						sheet.removeRow(row);

						break;
					}
				}

			}

		} finally {

			FileOutputStream outputStream = new FileOutputStream(HaberlerTablePath);
			w.write(outputStream);
			inputWorkbook.close();
			w.close();
			outputStream.close();
			DeleteEmptyRows(HaberlerTablePath);
		}

	}

	public int GetCategoryIdFromNewsId(int newsIdtoFind) throws IOException {

		DeleteEmptyRows(HaberlerTablePath);
		FileInputStream inputWorkbook = new FileInputStream(HaberlerTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				int newsId = (int) row.getCell(0).getNumericCellValue();

				if (newsId == newsIdtoFind) {
					return (int) row.getCell(1).getNumericCellValue();

				}

			}

		} finally {

			w.close();
			inputWorkbook.close();

		}

		return -1;
	}

	public Boolean IsSiteAdmin(Cookie[] cookies) throws IOException {

		if (IsCookieLegit(cookies) == false) {
			return false;

		}

		int personId = GetPersonIdFromCookies(cookies);
		DeleteEmptyRows(UsersTablePath);
		FileInputStream inputWorkbook = new FileInputStream(UsersTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {

					continue;
				}
				Cell cell = row.getCell(0);

				if ((int) cell.getNumericCellValue() == personId) {

					return row.getCell(3).getBooleanCellValue();

				}

			}

			return false;
		} finally {

			w.close();
			inputWorkbook.close();

		}
	}

	public List<String> GetListOfNonCategoryAdminsUserNames() throws IOException {

		DeleteEmptyRows(UsersTablePath);
		FileInputStream inputWorkbook = new FileInputStream(UsersTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		List<String> listOfNonCategoryAdminsUserNames = new ArrayList<String>();

		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);

				if (row == null || row.getCell(0) == null) {

					continue;
				}
				int personId = (int) row.getCell(0).getNumericCellValue();

				if (IsCategoryAdmin(personId,-1) == false && personId != 0) {
					listOfNonCategoryAdminsUserNames.add(row.getCell(1).getStringCellValue());

				}

			}
			return listOfNonCategoryAdminsUserNames;
		}

		finally {

			w.close();
			inputWorkbook.close();

		}

	}

	public SiteAdminVm GetSiteAdminVm(int personId) throws IOException {

		SiteAdminVm siteAdminVm = new SiteAdminVm();
		siteAdminVm.setUserName(GetUserNameFromPersonId(personId));

		siteAdminVm.setListOfCategoryDTOs(GetListOfCategoryDTOs());
		;
		siteAdminVm.setListOfNonCatetegoryAdminsUserNames(GetListOfNonCategoryAdminsUserNames());
		return siteAdminVm;
	}

	public List<Integer> GetListofIntegersFromString(String[] stringArr) {

		List<Integer> listOfnumbers = new ArrayList<Integer>();

		for (int i = 0; i < stringArr.length; i++) {

			listOfnumbers.add(Integer.parseInt(stringArr[i]));

		}

		return listOfnumbers;

	}

	public String GetDelimitedUserNamesFromPersonIds(List<Integer> personIdsArr) throws IOException {

		List<String> userNamesArr = new ArrayList<String>();

		for (int i = 0; i < personIdsArr.size(); i++) {

			userNamesArr.add(GetUserNameFromPersonId(personIdsArr.get(i)));

		}
		return String.join(",", userNamesArr);
	}

	public List<CategoryDTO> GetListOfCategoryDTOs() throws IOException {

		DeleteEmptyRows(CategoryTablePath);
		FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);

		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		List<CategoryDTO> lisOfCategoryDTOs = new ArrayList<CategoryDTO>();

		try {
			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);

				if (row == null || row.getCell(0) == null) {

					continue;
				}
				CategoryDTO categoryDTO = new CategoryDTO();

				categoryDTO.setCategoryId((int) row.getCell(0).getNumericCellValue());
				categoryDTO.setCategoryName(row.getCell(1).getStringCellValue());
				String[] stringArrayofPersonIds = returnStringValue(row.getCell(2)).split(",");

				categoryDTO.setStringOfAdminsUserNames(
						GetDelimitedUserNamesFromPersonIds(GetListofIntegersFromString(stringArrayofPersonIds)));

				lisOfCategoryDTOs.add(categoryDTO);

			}
			return lisOfCategoryDTOs;
		}

		finally {

			w.close();
			inputWorkbook.close();

		}

	}

	public void UpdateCategoryTableRows(List<CategoryDTO> updatedRows, int personId)
			throws IOException, UserMessageException {
		DeleteEmptyRows(CategoryTablePath);
		FileInputStream inputWorkbook = null;
		HSSFWorkbook w = null;

		for (CategoryDTO categoryDTO : updatedRows) {

			inputWorkbook = new FileInputStream(CategoryTablePath);
			w = new HSSFWorkbook(inputWorkbook);
			try {
				// Get the first sheet
				Sheet sheet = w.getSheetAt(0);
				// Loop over first 10 column and lines
				int lastRowNumber = sheet.getLastRowNum();
				for (int i = 2; i <= lastRowNumber; i++) {
					Row row = sheet.getRow(i);
					if (row == null || row.getCell(0) == null) {
						continue;
					}

					if (row.getCell(0).getNumericCellValue() == categoryDTO.getCategoryId()) {

						if (IsCategoryNameExists(categoryDTO.getCategoryName(),i)) {
							throw new UserMessageException(categoryDTO.getCategoryName() + " zaten eklidir.");

						}

						String delimetedPersonIds = String.join(",",
								GetListofPersonIdsFromDelimitedUserNames(categoryDTO.getStringOfAdminsUserNames()));

						String[] personIdstrArr = delimetedPersonIds.split(",");
						
						
						
						for (int j = 0; j < personIdstrArr.length; ++j) {							

					

							if (personIdstrArr[j].equals("0")  ||  IsCategoryAdmin(Integer.parseInt(personIdstrArr[j]),i)) {

								throw new UserMessageException(
										GetUserNameFromPersonId(Integer.parseInt(personIdstrArr[j]))
												+ " zaten kategori yöneticisidir.");

							}

						}
						row.createCell(1).setCellValue(categoryDTO.getCategoryName());
						row.createCell(2).setCellValue(delimetedPersonIds);

						break;
					}
				}

			} finally {
				FileOutputStream outputStream = new FileOutputStream(CategoryTablePath);
				w.write(outputStream);
				inputWorkbook.close();
				w.close();
				outputStream.close();
			}

		}

	}

	private boolean IsCategoryNameExists(String categoryName,int rowtoSkip) throws IOException {

		FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		DeleteEmptyRows(CategoryTablePath);
		List<Integer> categoryList = new ArrayList<Integer>();
		try {
			Sheet sheet = w.getSheetAt(0);

			int lastRowNumber = sheet.getLastRowNum();
			// Get the first sheet
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null || i == rowtoSkip) {
					continue;
				}
				if (row.getCell(1).getStringCellValue().equals(categoryName) ) {

					return true;

				}

			}
			return false;

		} finally {
	
			
			inputWorkbook.close();
			w.close();
	
		
		}
	}

	private List<String> GetListofPersonIdsFromDelimitedUserNames(String stringOfUserNames)
			throws IOException, UserMessageException {

		String[] listOfUserNames = stringOfUserNames.split(",");
		List<String> personIdsArr = new ArrayList<String>();

		for (int i = 0; i < listOfUserNames.length; i++) {

			personIdsArr.add(String.valueOf(GetPersonIdFromUserName(listOfUserNames[i])));

		}
		return personIdsArr;

	}

	private int GetPersonIdFromUserName(String userName) throws IOException, UserMessageException {

		DeleteEmptyRows(UsersTablePath);
		FileInputStream inputWorkbook = new FileInputStream(UsersTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		int personId = -1;
		try {

			// Get the first sheet
			Sheet sheet = w.getSheetAt(0);
			// Loop over first 10 column and lines
			int lastRowNumber = sheet.getLastRowNum();
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {
					continue;
				}

				if (row.getCell(1).getStringCellValue().equals(userName)) {

					personId = (int) row.getCell(0).getNumericCellValue();

					break;

				}
			}

			if (personId == -1) {

				throw new UserMessageException("Kullanıcı: " + userName + " bulunamadi.");
			}
			return personId;
		} finally {

			FileOutputStream outputStream = new FileOutputStream(UsersTablePath);
			w.write(outputStream);
			inputWorkbook.close();
			w.close();
			outputStream.close();
		}

	}

	@SuppressWarnings("resource")
	public void InsertCategoryTableRows(List<CategoryDTO> insertedRows, int personId)
			throws IOException, UserMessageException {

		

		for (CategoryDTO categoryDTO : insertedRows) {

			FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);
			HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
			Sheet sheet = w.getSheetAt(0);
			int lastRowNo = sheet.getLastRowNum();

			Row row = sheet.createRow(lastRowNo + 1);

			try {
				DeleteEmptyRows(CategoryTablePath);

				Integer lastRowId = (int) sheet.getRow(lastRowNo).getCell(0).getNumericCellValue();

				row.createCell(0).setCellValue(lastRowId + 1);
				if (IsCategoryNameExists(categoryDTO.getCategoryName(),0)) {
					throw new UserMessageException(categoryDTO.getCategoryName() + " zaten eklidir.");

				}
				row.createCell(1).setCellValue(categoryDTO.getCategoryName());

				List<String> personIdsStringArr = GetListofPersonIdsFromDelimitedUserNames(
						categoryDTO.getStringOfAdminsUserNames());

				String delimetedPersonIds = String.join(",", personIdsStringArr);

				String[] personIdstrArr = delimetedPersonIds.split(",");
				for (int j = 0; j < personIdstrArr.length; ++j) {

					if (IsCategoryAdmin(Integer.parseInt(personIdstrArr[j]),-1)) {

						throw new UserMessageException(GetUserNameFromPersonId(Integer.parseInt(personIdstrArr[j]))
								+ " zaten kategori yöneticisidir.");

					}

				}

				row.createCell(2).setCellValue(delimetedPersonIds);

			} catch (Exception e) {

				sheet.removeRow(row);
				throw e;
			}

			finally {
				FileOutputStream outputStream = new FileOutputStream(CategoryTablePath);
				w.write(outputStream);
				inputWorkbook.close();
				w.close();
				outputStream.close();
			}

		}

	}

	public void DeleteCategoryTableRows(List<Integer> deletedRowIds, int personId) throws IOException {

		FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		DeleteEmptyRows(CategoryTablePath);
		try {
			Sheet sheet = w.getSheetAt(0);
			for (Integer categoryId : deletedRowIds) {

				int lastRowNumber = sheet.getLastRowNum();
				// Get the first sheet
				for (int i = 2; i <= lastRowNumber; i++) {
					Row row = sheet.getRow(i);
					if (row == null || row.getCell(0) == null) {
						continue;
					}
					if (row.getCell(0).getNumericCellValue() == categoryId) {

						sheet.removeRow(row);

						break;
					}
				}

			}

		} finally {

			FileOutputStream outputStream = new FileOutputStream(CategoryTablePath);
			w.write(outputStream);
			inputWorkbook.close();
			w.close();
			outputStream.close();
			DeleteEmptyRows(CategoryTablePath);
		}

	}

	public int FindDefaultCategoryId(int categoryId) throws IOException {

		FileInputStream inputWorkbook = new FileInputStream(CategoryTablePath);
		HSSFWorkbook w = new HSSFWorkbook(inputWorkbook);
		DeleteEmptyRows(CategoryTablePath);
		List<Integer> categoryList = new ArrayList<Integer>();
		try {
			Sheet sheet = w.getSheetAt(0);

			int lastRowNumber = sheet.getLastRowNum();
			// Get the first sheet
			for (int i = 2; i <= lastRowNumber; i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {
					continue;
				}
				if (row.getCell(0).getNumericCellValue() == categoryId) {

					return categoryId;

				}
				categoryList.add((int) row.getCell(0).getNumericCellValue());

			}

			if (categoryList.size() == 0) {
				return -1;

			} else {
				return categoryList.get(0);

			}
		} finally {

			
			inputWorkbook.close();
			w.close();
		
		
		}

	}

}

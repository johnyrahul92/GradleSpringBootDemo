package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dao.TestDao;
import com.example.demo.dto.OlbUserDetailDo;
import com.example.demo.dto.PdfResponse;
import com.example.demo.dto.StmtDocument;
import com.example.demo.dto.StudentDo;
import com.example.demo.pojo.MonthlyStatementResponse;
import com.example.demo.pojo.TestBeanResponse;
import com.gulfbank.olb.olbintegrationservicesmanagement.service.olbintegrationservices.intf._1.GetWiseInvestmentDetailsReply;
import com.gulfbank.olb.olbintegrationservicesmanagement.service.olbintegrationservices.intf._1.GetWiseInvestmentDetailsRequest;
import com.gulfbank.olb.olbintegrationservicesmanagement.service.olbintegrationservices.intf._1.JaxWsHandlerResolver;
import com.gulfbank.olb.olbintegrationservicesmanagement.service.olbintegrationservices.intf._1.OlbIntegrationServices;
import com.gulfbank.olb.olbintegrationservicesmanagement.service.olbintegrationservices.intf._1.OlbIntegrationServices_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/demo")
@Api(value = "tdemo", description = "Test")
public class Controller {

	@Autowired
	TestDao testDao;
	
	

	@Value("${message}")
	private String message;

	private static final Logger LOG = LogManager.getLogger(Controller.class);

	@GetMapping(value = "/getForId")
	@ResponseBody
	@ApiOperation(value = "Get based on id from database", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error occurred while deleting File") })
	public String index(@RequestParam(value = "id", required = true) Integer id) {

		LOG.info("---------------- entered controller ----------");
		System.out.println(id);
		System.out.println(message);

		String name = testDao.getById(id);

		System.out.println("Name : " + name);

		return name;
	}
	
	
	@GetMapping(value = "/checkEnabledUser")
	@ResponseBody
	public OlbUserDetailDo checkEnabledUser(@RequestParam(value = "userId", required = true) String userId) {

		LOG.info("---------------- checkEnabledUser ----------");
		
		OlbUserDetailDo olbUserDetailDo = null;
		
		olbUserDetailDo= testDao.getUserDetail(userId);
		
		if (olbUserDetailDo == null) {			
			LOG.info("No_OLB User deatil found");
			
			
		}
		
		
		
		
		return olbUserDetailDo;
	}

	@PostMapping(value = "/saveStudent")
	@ResponseBody
	public StudentDo saveStudent(@RequestBody StudentDo studentDo) {
		System.out.println("Save ++++++++++++");

		testDao.saveStudent(studentDo);

		return studentDo;
	}

	@GetMapping(value = "/getForName")
	@ResponseBody
	public TestBeanResponse test(@RequestParam(value = "name", required = true) String name) {

		LOG.info("---------------- entered controller ----------");
		System.out.println(name);

		TestBeanResponse testBeanResponse = testDao.getByName(name);

		return testBeanResponse;
	}

	@GetMapping(value = "/getPDFTest")
	@ResponseBody
	public Set<PdfResponse> getPdfLinks() {

		/*
		 * SimpleSource simpleSource = new SimpleSource();
		 * 
		 * simpleSource.setName("Rahul"); simpleSource.setDescription(
		 * "Hai how  are you");
		 * 
		 * SimpleDestination destination=
		 * SimpleSourceDestinationMapper.INSTANCE.sourceToDestination(
		 * simpleSource);
		 * 
		 * System.out.println(destination.getName());
		 */

		try {

			System.out.println("------------------------");

			OlbIntegrationServices_Service olbWise = new OlbIntegrationServices_Service(
					new URL("http://192.168.1.105:9080/WISE/OlbIntegrationServices.wsdl"));

			olbWise.setHandlerResolver(new JaxWsHandlerResolver());

			OlbIntegrationServices olbIntegrationServices = olbWise.getOlbIntegrationServices();

			GetWiseInvestmentDetailsRequest getWiseInvestmentDetailsRequest = new GetWiseInvestmentDetailsRequest();
			getWiseInvestmentDetailsRequest.setCif("70175745");

			GetWiseInvestmentDetailsReply getWiseInvestmentDetailsReply = new GetWiseInvestmentDetailsReply();
			getWiseInvestmentDetailsReply = olbIntegrationServices
					.getWiseInvestmentDetails(getWiseInvestmentDetailsRequest);

			System.out.println(getWiseInvestmentDetailsReply);
			System.out.println("------------------------");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;

	}

	@PostMapping(value = "/getPDF")
	@ResponseBody
	public void getPDF(@RequestParam("username") String username, HttpServletResponse response) throws IOException {
		System.out.println("+++++++++Downloading+++++++++");
		System.out.println(username);
		String filePath = "/Users/gulfbank/Downloads/test.pdf";
		File downloadFile = new File(filePath);
		FileInputStream inStream = new FileInputStream(downloadFile);

		try {
			// content = new String();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=Download.pdf");
			ServletOutputStream out = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inStream.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

			out.flush();
			out.close();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@PostMapping(value = "/postPDF")
	@ResponseBody
	public String postPDF(@RequestParam("file") MultipartFile file, @RequestParam("username") String username,
			@RequestParam("id") String id, HttpServletResponse response) {

		String saved = testDao.saveFile(file, username, id);

		System.out.println(saved);
		System.out.println(username);
		System.out.println(file.getOriginalFilename());

		try {
			FileUtils.writeByteArrayToFile(new File(" /Users/gulfbank/Downloads/test.pdf"),
					IOUtils.toByteArray(file.getInputStream()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return file.getOriginalFilename();

	}

	@GetMapping(value = "/getPDFFromDb")
	@ResponseBody
	public void getPDFFromDb(@RequestParam("id") String id, HttpServletResponse response) throws IOException {

		System.out.println("inside get pdf from db");

		StmtDocument stmtDocument = testDao.getPDF(id);
		InputStream fis = null;
		ServletOutputStream out = null;
		try {

			if (null != stmtDocument && null != stmtDocument.getFileContent()) {

				System.out.println(stmtDocument.getCardNo());

				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + stmtDocument.getCardNo() + "\".pdf");

				fis = stmtDocument.getFileContent().getBinaryStream();
				out = response.getOutputStream();

				byte[] buffer = new byte[10240];

				int bytesRead = -1;
				while ((bytesRead = fis.read(buffer)) != -1) {
					out.write(buffer, 0, bytesRead);
				}

				System.out.println("Completed Download");

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();

			}
			if (fis != null) {
				fis.close();
			}

		}

	}
	
	@GetMapping(value = "/getLastSix")
	@ResponseBody
	public List<MonthlyStatementResponse> getLastSix(@RequestParam("cardNo") String cardNo, HttpServletResponse response) throws IOException {
	
		System.out.println("Get Last 6");		
		List<MonthlyStatementResponse> stmtDocuments=testDao.getLastSix(cardNo);
		return stmtDocuments;		
		
	}
	@PostMapping(value = "/getXmlJson")
	@ResponseBody
	public JSONObject getXmlJson(HttpServletResponse response) throws IOException, TransformerException, ParseException {
	
		System.out.println("getXmlJson");	
		
		
		String url="https://testgbonline.e-gulfbank.com/T005/internet";
        RestTemplate restTemplate= new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("fldLoginUserId", "DEVRETAIL4");
        map.add("fldlanguage", "eng");
        map.add("fldDeviceId", "01");
        map.add("fldLangId", "eng");
        map.add("fldRequestId", "RRTFC11");
        map.add("fldUserType", "EN1");
        map.add("fldHiddenTxnId", "TFC");

       ResponseEntity<String> respons= restTemplate.postForEntity(url,map,String.class);       
       StreamSource xmlSource = new StreamSource(new StringReader(respons.getBody()));
       
		File xsltFile = new File("src/main/resources/olb-login-username.xsl");
		
		TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));   
        
        StringWriter outWriter = new StringWriter();
        
        Result res =  new StreamResult(outWriter);
        transformer.transform(xmlSource,res);
        

        StringBuffer sb = outWriter.getBuffer();
        
    	response.setContentType("application/json");
    	JSONObject jsonObject = new JSONObject();
    	JSONParser parser = new JSONParser();
    			
    	
    	
       
		
        
        return (JSONObject) parser.parse(sb.toString());
		//return "skdjqs";		
		
	}


}

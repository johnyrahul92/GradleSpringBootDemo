package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dao.TestDao;
import com.example.demo.dto.PdfResponse;
import com.example.demo.dto.StudentDo;
import com.example.demo.dto.TestBeanDo;
import com.example.demo.map.SimpleDestination;
import com.example.demo.map.SimpleSource;
import com.example.demo.map.SimpleSourceDestinationMapper;
import com.example.demo.pojo.TestBeanResponse;

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

	@PostMapping(value = "/saveStudent")
	@ResponseBody
	public StudentDo saveStudent(@RequestBody StudentDo studentDo) {

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
	public  Set<PdfResponse> getPdfLinks(){
		
		
	/*	SimpleSource simpleSource = new SimpleSource();
		
		simpleSource.setName("Rahul");
		simpleSource.setDescription("Hai how  are you");
		
		SimpleDestination destination= SimpleSourceDestinationMapper.INSTANCE.sourceToDestination(simpleSource);
		
		System.out.println(destination.getName());*/
		
		Set<PdfResponse> pdfResponses = new HashSet<>();
		
		PdfResponse pdfResponse1 =new PdfResponse();
		PdfResponse pdfResponse2 =new PdfResponse();
		
		pdfResponse1.setPdfName("Pdf1");
		pdfResponse1.setPdfLink("/demo/getPDF");
		
		pdfResponses.add(pdfResponse1);
		
		pdfResponse2.setPdfName("Pdf2");
		pdfResponse2.setPdfLink("url2");
		
		pdfResponses.add(pdfResponse2);
		
		
		return pdfResponses;
		
		
	}

	@GetMapping(value = "/getPDF")
	@ResponseBody
	public void getPDF(@RequestParam("username") String username, HttpServletResponse response)
			throws IOException {
		System.out.println("+++++++++Downloading+++++++++");
		System.out.println(username);
		String filePath = "C://Users/online09/Desktop/PDF/vodofonebill_sept.pdf";
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
	public String postPDF(@RequestParam("file") MultipartFile file,@RequestParam("username") String username, HttpServletResponse response){
		System.out.println(username);
		System.out.println(file.getOriginalFilename());
		
		try {
            FileUtils.writeByteArrayToFile(new File("C://Users/online09/Desktop/PDF/test.pdf"),
                IOUtils.toByteArray(file.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
		
		return file.getOriginalFilename();
		
	}
	

}

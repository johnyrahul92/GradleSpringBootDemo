package com.example.demo.Dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.OlbUserDetailDo;
import com.example.demo.dto.StmtDocument;
import com.example.demo.dto.StudentDo;
import com.example.demo.dto.TestBeanDo;
import com.example.demo.dto.TestCo;
import com.example.demo.map.TestBeanMapper;
import com.example.demo.pojo.MonthlyStatementResponse;
import com.example.demo.pojo.TestBeanResponse;
import com.example.demo.pojo.TestCoResponse;

@Repository
@EnableTransactionManagement
public class TestDaoImpl implements TestDao {

	private static final Logger LOG = LogManager.getLogger(TestDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private LocalSessionFactoryBean logSessionFactory;

	@Override
	@Transactional
	public String getById(int id) {
		LOG.info("-------------- getById DAO ----------");

		TestBeanDo beanDo = new TestBeanDo();
		// beanDo= entityManager.find(TestBeanDo.class, id);

		System.out.println("Getting Sessiion");

		Session session = logSessionFactory.getObject().getCurrentSession();

		TestCo testCo =  new TestCo();

		Criteria cr = session.createCriteria(TestCo.class);
		cr.add(Restrictions.eq("cid", id));
		testCo = (TestCo) cr.uniqueResult();

		System.out.println(testCo.getName());

		/*beanDo = beanDo1.getTestBeanDo();

		System.out.println("list : " + beanDo.getDescription());*/

		/*if (testCos != null) {

			for (TestCo testCo : testCos) {
				System.out.println("Name : " + testCo.getName());
			}
		}*/

		return testCo.getName();
	}

	@Override
	@Transactional
	public void saveStudent(StudentDo studentDo) {
		
		Session session = logSessionFactory.getObject().getCurrentSession();
		
		session.saveOrUpdate(studentDo);

	//entityManager.persist(studentDo);
		System.out.println("saved");

	}

	@Override
	@Transactional
	public TestBeanResponse getByName(String name) {
		// TODO Auto-generated method stub

		Query query = entityManager.createQuery("select test from testbeando test where test.name= :name");
		query.setParameter("name", name);

		TestBeanDo beanDo = null;

		beanDo = (TestBeanDo) query.getSingleResult();
		
		
		TestBeanResponse beanResponse = TestBeanMapper.INSTANCE.dtoToPojo(beanDo);
		
		System.out.println(beanResponse.getName());
		
		Set<TestCoResponse> coResponses = beanResponse.getTestCos();
		
		for (TestCoResponse testCo : coResponses) {
			System.out.println("Name : " + testCo.getName());
		}
		

		return beanResponse;
	}

	@Override
	@Transactional
	public String saveFile(MultipartFile file, String name, String id)  {
		
		Session session = logSessionFactory.getObject().getCurrentSession();
		
		StmtDocument stmtDocument = new StmtDocument();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");  
		//Hibernate.getLobCreator(session).createBlob(file.getBytes())
		
		Date date;
		try {
			date = formatter.parse("12-APR-17");
			stmtDocument.setStatementDate( date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		stmtDocument.setStatementId(id);
		stmtDocument.setCardNo(name);
		
		try {
			stmtDocument.setFileContent(Hibernate.getLobCreator(session).createBlob(file.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.persist(stmtDocument);
		// TODO Auto-generated method stub
		return "Saved Succefully";
	}

	@Override
	@Transactional
	public StmtDocument getPDF(String id) {
		
		Session session = logSessionFactory.getObject().getCurrentSession();
		
		StmtDocument stmtDocument = new StmtDocument();
		
		Criteria cr = session.createCriteria(StmtDocument.class);
		cr.add(Restrictions.eq("statementId", id));
		stmtDocument = (StmtDocument) cr.uniqueResult();

		// TODO Auto-generated method stub
		return stmtDocument;
	}

	@Override
	@Transactional
	public List<MonthlyStatementResponse> getLastSix(String cardNo) {
		
		Session session = logSessionFactory.getObject().getCurrentSession();		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");		
		List<MonthlyStatementResponse> statementResponses = new ArrayList<>();	
		
		Criteria cr = session.createCriteria(StmtDocument.class);
		
		//cr.setProjection(Projections.property("statementId"));
		//cr.setProjection(Projections.property("statementDate"));
		cr.add(Restrictions.like("cardNo", ","+cardNo+",",MatchMode.ANYWHERE));
		cr.setMaxResults(6);
		cr.addOrder(Order.desc("statementDate"));	
		
		List<StmtDocument> stmtDocuments = cr.list();
		System.out.println(stmtDocuments.size());
		
		for (StmtDocument stmtDocument : stmtDocuments) {			
			
			MonthlyStatementResponse monthlyStatementResponse = new MonthlyStatementResponse();			
			monthlyStatementResponse.setStatementId(stmtDocument.getStatementId());
			monthlyStatementResponse.setStatementDate(formatter.format(stmtDocument.getStatementDate()).toUpperCase());			
			System.out.println("statements :  "+formatter.format(stmtDocument.getStatementDate()).toUpperCase() );			
			statementResponses.add(monthlyStatementResponse);		
		}
		
		return statementResponses;
		
		
	}

	@Override
	@Transactional
	public OlbUserDetailDo getUserDetail(String userId) {
		// TODO Auto-generated method stub
		
		OlbUserDetailDo olbUserDetailDo = new OlbUserDetailDo();
		
		Session session = logSessionFactory.getObject().getCurrentSession();
		
		Criteria cr = session.createCriteria(OlbUserDetailDo.class);
		cr.add(Restrictions.eq("idUser", userId));
		cr.add(Restrictions.eq("enable", 1));
		olbUserDetailDo = (OlbUserDetailDo) cr.uniqueResult();
		
		return olbUserDetailDo;
	}

}

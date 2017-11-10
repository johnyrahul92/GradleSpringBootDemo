package com.example.demo.Dao;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.StudentDo;
import com.example.demo.dto.TestBeanDo;
import com.example.demo.dto.TestCo;
import com.example.demo.map.TestBeanMapper;
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

		TestCo beanDo1 =  new TestCo();

		Criteria cr = session.createCriteria(TestCo.class);
		cr.add(Restrictions.eq("cid", id));
		beanDo1 = (TestCo) cr.uniqueResult();

		System.out.println(beanDo1.getName());

		beanDo = beanDo1.getTestBeanDo();

		System.out.println("list : " + beanDo.getDescription());

		/*if (testCos != null) {

			for (TestCo testCo : testCos) {
				System.out.println("Name : " + testCo.getName());
			}
		}*/

		return beanDo1.getName();
	}

	@Override
	@Transactional
	public void saveStudent(StudentDo studentDo) {

		entityManager.persist(studentDo);

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

}

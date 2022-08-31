package com.cocktail_app.cocktail;

import java.util.List;
import java.util.Iterator;

import com.cocktail_app.cocktail.Models.Sample;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CocktailApplication {
	private static SessionFactory factory;
	public static void main(String[] args) {
		/*
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		CocktailApplication CA = new CocktailApplication();

		// Add few samples records in database
		Integer empID1 = CA.addSample("Zara");
		Integer empID2 = CA.addSample("Daisy");
		Integer empID3 = CA.addSample("Caleb");

		// List down all the samples
		CA.listEmployees();

		// Update samples records
		CA.updateSample(empID1, "Bryce");

		// Delete a sample from the database
		CA.deleteSample(empID2);

		// List down new list of the samples
		CA.listEmployees();
		*/
		SpringApplication.run(CocktailApplication.class, args);

	}

	// Method to CREATE a sample in the database
	public Integer addSample(String name){
		Session session = factory.openSession();
		Transaction tx = null;
		Integer ID = null;

		try {
			tx = session.beginTransaction();
			Sample sample = new Sample(name);
			ID = (Integer) session.save(sample);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ID;
	}

	// Method to  READ all the samples
	public void listEmployees( ){
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List samples = session.createQuery("FROM Sample").list();
			for (Iterator iterator = samples.iterator(); iterator.hasNext();){
				Sample sample = (Sample) iterator.next();
				System.out.print("First Name: " + sample.getName());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method to UPDATE name for a sample
	public void updateSample(Integer ID, String name){
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Sample sample = session.get(Sample.class, ID);
			sample.setName(name);
			session.update(sample);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method to DELETE a sample from the records
	public void deleteSample(Integer ID){
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Sample sample = session.get(Sample.class, ID);
			session.delete(sample);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
}

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
		SpringApplication.run(CocktailApplication.class, args);
	}
}
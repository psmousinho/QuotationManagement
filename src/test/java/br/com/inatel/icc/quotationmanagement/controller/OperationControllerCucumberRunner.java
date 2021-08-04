package br.com.inatel.icc.quotationmanagement.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features")
@SpringBootTest
@ContextConfiguration(classes = SpringBootApplication.class)
@ActiveProfiles("dev")
public class OperationControllerCucumberRunner {

}

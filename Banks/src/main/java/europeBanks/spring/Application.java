package europeBanks.spring;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import europeBanks.spring.service.BudgetService;

//va a prendere gli altri package per non considerare solo quello corrente
@SpringBootApplication(scanBasePackages={"europeBanks.spring"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    		try {
    			BudgetService.setBudgets();
    			BudgetService.setMetadata();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }
}

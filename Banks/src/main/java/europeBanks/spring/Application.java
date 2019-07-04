package europeBanks.spring;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import europeBanks.spring.model.*;
import europeBanks.spring.service.BudgetService;

/**
 * Questa classe permette l'avvio dell'applicazione.
 * @author Damiano Bolognini
 * @author Francesco Tontarelli
 */

//va a prendere gli altri package per non considerare solo quello corrente
@SpringBootApplication(scanBasePackages={"europeBanks.spring"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    		try {
    			BudgetService.setBudgets();
				System.out.println("Droga");
				BudgetService.setMetadata(new ArrayList<Metadata>());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    }
}

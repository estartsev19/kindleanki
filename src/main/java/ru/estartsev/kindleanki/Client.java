package ru.estartsev.kindleanki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.estartsev.kindleanki.services.CsvService;

@Component
public class Client implements CommandLineRunner {
    @Autowired
    private CsvService csvService;

    @Override
    public void run(String... args) {
        if (args.length > 0 && args[0].equals("-l")) {
            csvService.createCsv(args[1]);
        } else {
            csvService.createCsv(null);
        }
    }
}
package ru.estartsev.kindleanki.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.estartsev.kindleanki.entity.Lookup;
import ru.estartsev.kindleanki.entity.Word;
import ru.estartsev.kindleanki.repository.WordRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Optional;

@Service
public class CsvService {


    @Autowired
    private WordRepository wordRepository;

    public void createCsv(String language) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .build();
        String fileLocation = getFileLocationInRoot("words.csv");
        String dateLocation = getFileLocationInRoot("time.cfg");
        LocalDateTime dateTime = getLastTime(dateLocation);
        Iterator<Word> wordIterator = wordRepository.findAll().iterator();
        try (CSVPrinter printer = csvFormat.print(new OutputStreamWriter(
                new FileOutputStream(fileLocation), StandardCharsets.UTF_8))) {
            while (wordIterator.hasNext()) {
                Word word = wordIterator.next();
                if (!preCheckWord(word, dateTime, language)) {
                    continue;
                }
                if (word.getTimestamp().isAfter(dateTime)) {
                    dateTime = word.getTimestamp();
                }
                Optional<Lookup> lookup = word.getLookups().stream().findFirst();
                printRecord(printer, word, lookup.orElse(null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeLastTime(dateLocation, dateTime);
    }

    private String getFileLocationInRoot(String fileName) {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        return path.substring(0, path.length() - 1) + fileName;
    }

    private void writeLastTime(String dateLocation, LocalDateTime dateTime) {
        try (OutputStreamWriter configWriter =
                     new OutputStreamWriter(new FileOutputStream(dateLocation), StandardCharsets.UTF_8)) {
            configWriter.write("time=" + dateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printRecord(CSVPrinter printer, Word word, Lookup lookup) {
        try {
            printer.printRecord(word.getStem(), lookup != null ? lookup.getUsage() : "", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean preCheckWord(Word word, LocalDateTime dateTime, String language) {
        if (word.getTimestamp().isBefore(dateTime)) {
            return false;
        }
        return language == null || word.getLang().equals(language.toLowerCase());
    }

    private LocalDateTime getLastTime(String fileLocation) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            String line = br.readLine();
            if (line == null) {
                return LocalDateTime.MIN;
            }
            if (line.startsWith("time=")) {
                return ZonedDateTime.parse(line.substring(5)).toLocalDateTime();
            }
        } catch (FileNotFoundException e) {
            return LocalDateTime.MIN;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LocalDateTime.MIN;
    }
}
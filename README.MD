Kindle To Anki Console Application
==================================

## What is it for?
This application allows you to transfer the words you learn and their usage from your Kindle Paperwhite (Vocabulary Builder) into a CSV file. By manually adding a translation or definition to the third column of the csv file, the file can be imported into Anki.

## Usage
1. Build the jar file with gradle.
2. Create a new folder, where you place the created jar file and vocab.db file.
3. Run the jar file from the console. The generated .csv file will be located in this directory. 

Also, in the folder is created a file time.cfg, which stores the time of the last added word in Vocabulary Builder. During the next conversion, only the words added after the date from the time.cfg file will be transferred.
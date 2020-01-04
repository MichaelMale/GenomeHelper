package uk.ac.aber.dcs.cs31810.genomehelper;

import uk.ac.aber.dcs.cs31810.genomehelper.exceptions.FASTAException;
import uk.ac.aber.dcs.cs31810.genomehelper.util.FASTA;
import uk.ac.aber.dcs.cs31810.genomehelper.util.FASTAParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        System.out.println("GenomeHelper - Last updated: 2019-12-11");
        System.out.println("Created by Michael Male mim39@aber.ac.uk");
        System.out.println("\n Please enter the name of the folder where " +
                "FASTA files are located");

        Scanner in = new Scanner(System.in);
        String fastaFileLocation = in.nextLine();
        in.close();

        List<FASTA> allFasta = new ArrayList<>();

        final File folder = new File(fastaFileLocation);

        if (folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                try {
                    allFasta.add(FASTA.parseFASTA(file.toString()));
                } catch (FASTAException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else System.err.println("Not a directory");

        StringBuilder csvOut = new StringBuilder();
        csvOut.append("Name,GC_Content,Length,Contigs\n");
        for (FASTA fasta : allFasta) {
            csvOut.append(fasta.outputInCsv());
        }
        try {
            String path = fastaFileLocation;
            path += "\\results.csv";

            Files.write(Paths.get(path), csvOut.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

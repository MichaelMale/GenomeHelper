package uk.ac.aber.dcs.cs31810.genomehelper.util;

import uk.ac.aber.dcs.cs31810.genomehelper.exceptions.FASTAException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FASTA {
    private String name;
    private List<Contig> contigs;
    private double gcContent;
    private int length;

    public FASTA() {
        name = "not_specified";
        contigs = new ArrayList<>();
    }

    public static FASTA parseFASTA(String fileName) throws FASTAException, FileNotFoundException {
        FASTA fasta = new FASTA();
        List<Contig> contigs = new ArrayList<>();

        String extension = "";
        int delimiter = fileName.lastIndexOf('.');
        if (delimiter > 0) {
            extension = fileName.substring(delimiter + 1);
        }

        if (!extension.equals("fa")) throw new FASTAException("File extension" +
                " is invalid.");

        fasta.setName(fileName.substring(0, fileName.lastIndexOf('.')));
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        String line = scan.nextLine();

        while (scan.hasNextLine()) {
            String identifier;
            StringBuilder sequence = new StringBuilder();

            identifier = line.substring(1);
            line = scan.nextLine();

            while (line.charAt(0) != '>') {
                sequence.append(line);

                if (scan.hasNextLine()) {
                    line = scan.nextLine();
                } else break;

            }

            Contig toAdd = new Contig(identifier, sequence.toString());
            contigs.add(toAdd);
        }
        fasta.setContigs(contigs);
        fasta.setGcContent(calculateGcContent(fasta.getContigs()));
        fasta.setLength();
        return fasta;
    }

    private static double calculateGcContent(List<Contig> contigs) {
        StringBuilder allSequences = new StringBuilder();

        for (Contig contig : contigs) {
            allSequences.append(contig.getSequence());
        }

        double denominator = allSequences.toString().length();
        double numerator = 0;

        for (char ch : allSequences.toString().toCharArray()) {
            if (ch == 'C' || ch == 'G') {
                numerator += 1;
            }
        }

        return (numerator / denominator) * 100;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLength() {
        for (Contig c : contigs) {
            this.length += c.getSequence().length();
        }
    }

    public double getGcContent() {
        return gcContent;
    }

    public void setGcContent(double gcContent) {
        this.gcContent = gcContent;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contig> getContigs() {
        return this.contigs;
    }

    public void setContigs(List<Contig> contigs) {
        this.contigs = contigs;
    }

    public String outputInCsv() {

        return this.getName() +
                "," +
                this.getGcContent() +
                "," +
                this.getLength() +
                "," +
                this.getContigs().size() +
                "\n";
    }
}

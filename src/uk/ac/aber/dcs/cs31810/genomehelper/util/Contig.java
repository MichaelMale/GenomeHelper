package uk.ac.aber.dcs.cs31810.genomehelper.util;

public class Contig {
    private String name;
    private String sequence;

    public Contig(String name, String sequence) {
        this.setName(name);
        this.setSequence(sequence);
    }

    public String getName() {
        return name;
    }

    public String getSequence() {
        return sequence;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }



}

package cl.duoc.test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "subjects")
public class Subject {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    protected Subject() {}
    public Subject(String name) { this.name = name; }
    public Long getId() { return id; }
    public String getName() { return name; }
}

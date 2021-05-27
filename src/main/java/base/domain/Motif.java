package base.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Motif {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "motif_triples",
            joinColumns = @JoinColumn(name = "motif_id"),
            inverseJoinColumns = @JoinColumn(name = "triple_id")
    )
    private Set<Triple> triples;

    public Motif() {
    }

    public Motif(Set<Triple> triples) {
        this.triples = triples;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Triple> getTriples() {
        return triples;
    }

    public void setTriples(Set<Triple> triples) {
        this.triples = triples;
    }
}

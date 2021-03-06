package base.domain;

import base.dataaccess.utils.SparqlUtils;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "triple", schema = "public")
public class Triple {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 2083)
    private String subject;
    @Column(nullable = false, length = 2083)
    private String predicate;
    @Column(nullable = false, length = 2083)
    private String object;

    @ManyToMany(mappedBy = "triples")
    private Set<Motif> relatedMotifs;

    public Triple() {
    }

    public Triple(String subject, String predicate, String object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Set<Motif> getRelatedMotifs() {
        return relatedMotifs;
    }

    public void setRelatedMotifs(Set<Motif> relatedMotifs) {
        this.relatedMotifs = relatedMotifs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple triple = (Triple) o;
        return SparqlUtils.getCanonicalString(subject).equals(SparqlUtils.getCanonicalString(triple.subject)) && predicate.equals(triple.predicate) && SparqlUtils.getCanonicalString(object).equals(SparqlUtils.getCanonicalString(triple.object));
    }

    @Override
    public int hashCode() {
        return Objects.hash(SparqlUtils.getCanonicalString(subject), predicate, SparqlUtils.getCanonicalString(object));
    }
}

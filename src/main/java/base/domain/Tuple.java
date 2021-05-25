package base.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Tuple {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String predicate;
    @Column(nullable = false)
    private String object;
    @Column(nullable = false)
    private Integer motifId;

    public Tuple() {
    }

    public Tuple(Long id, String subject, String predicate, String object, Integer motifId) {
        this.id = id;
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
        this.motifId = motifId;
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

    public Integer getMotifId() {
        return motifId;
    }

    public void setMotifId(Integer motifId) {
        this.motifId = motifId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return id.equals(tuple.id) && subject.equals(tuple.subject) && predicate.equals(tuple.predicate) && object.equals(tuple.object) && motifId.equals(tuple.motifId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, predicate, object, motifId);
    }
}

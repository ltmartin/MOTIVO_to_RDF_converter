package base.dataaccess;

import base.dataaccess.utils.SparqlUtils;
import base.domain.Triple;
import org.apache.jena.query.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Lazy
public class SparqlDAO {
    private final Logger logger = Logger.getLogger(SparqlDAO.class.getName());
    public static final byte ROLE_SUBJECT = 0;
    public static final byte ROLE_OBJECT = 1;

    @Value("${sparql.endpoint}")
    private String endpoint;

    public Set<Triple> getTriplesWithNodePlayingRole(String node, byte role){
        Set<Triple> results = new HashSet<>();
        String query = "";
        String sparqlCompatibleNode = SparqlUtils.getSparqlCompatibleString(node);
        if (ROLE_SUBJECT == role){
            query = "SELECT ?p ?o WHERE {"+ sparqlCompatibleNode +" ?p ?o}";
        } else if (ROLE_OBJECT == role)
            query = "SELECT ?s ?p WHERE {?s ?p "+ sparqlCompatibleNode +"}";

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query)) {
            ResultSet rs = qexec.execSelect();
            while (rs.hasNext()) {
                QuerySolution row = rs.next();

                Triple triple = null;
                String subject = null, object = null;
                String predicate = row.get("?p").toString();
                if (ROLE_SUBJECT == role){
                    subject = sparqlCompatibleNode;
                    object = row.get("?o").toString();
                } else if (ROLE_OBJECT == role){
                    object = sparqlCompatibleNode;
                    subject = row.get("?s").toString();
                }

                triple = new Triple(subject, predicate, object);

                results.add(triple);
            }
        } catch (QueryParseException e){
            System.out.println("===============================================");
            logger.log(Level.SEVERE, "Error processing the query: \n" + query + "\n");
            System.out.println("===============================================");
        }
        return results;
    }
}

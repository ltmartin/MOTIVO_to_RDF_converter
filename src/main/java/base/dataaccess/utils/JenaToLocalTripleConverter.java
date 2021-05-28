package base.dataaccess.utils;

import base.domain.Triple;

public class JenaToLocalTripleConverter {

    public static Triple convert(org.apache.jena.graph.Triple triple){
        Triple convertedTriple = new Triple();
        convertedTriple.setSubject(triple.getSubject().toString());
        convertedTriple.setPredicate(triple.getPredicate().toString());
        convertedTriple.setObject(triple.getObject().toString());
        return convertedTriple;
    }
}
